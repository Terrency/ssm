package com.ssm.common.web.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ssm.common.web.base.BaseController;
import com.ssm.common.web.base.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Calendar;

@RestController
@RequestMapping("/captcha")
public class CaptchaController extends BaseController {

    @Autowired
    private Producer producer;

    /**
     * Ref: com.google.code.kaptcha.servlet.KaptchaServlet
     */
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = producer.createText();
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_DATE, Calendar.getInstance().getTime());
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
    }

    @GetMapping("/verifyCaptcha")
    public ResponseData verifyCaptcha(@RequestParam String captcha, HttpSession session) {
        return setData(captcha.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY)));
    }

}
