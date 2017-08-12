package com.ssm.common.web.controller;

import com.ssm.common.base.enums.StatusCode;
import com.ssm.common.web.base.BaseController;
import com.ssm.common.web.base.ResponseData;
import com.ssm.common.web.captcha.Captcha;
import com.ssm.common.web.captcha.ImgCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping("/captcha")
public class CaptchaController extends BaseController {

    @Autowired
    private ImgCaptchaService imgCaptchaService;

    @ResponseBody
    @RequestMapping("/genImgCaptchaToken")
    public ResponseData genImgCaptchaToken() {
        return setData(imgCaptchaService.genToken(Captcha.class.getSimpleName()));
    }

    /**
     * @see com.google.code.kaptcha.servlet.KaptchaServlet#doGet
     */
    @RequestMapping(value = "/getCaptchaImg", method = RequestMethod.GET)
    public void getCaptchaImg(@RequestParam String imgCaptchaToken, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String captcha = imgCaptchaService.genCaptcha(imgCaptchaToken);
        BufferedImage bi = imgCaptchaService.createImage(captcha);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
    }

    @ResponseBody
    @RequestMapping("/verifyImgCaptcha")
    public ResponseData verifyImgCaptcha(@RequestParam String imgCaptchaToken, @RequestParam String imgCaptcha) {
        try {
            return setData(imgCaptchaService.verify(imgCaptchaToken, imgCaptcha));
        } catch (Exception e) {
            return setData(false, Integer.toString(StatusCode.FAILURE.getCode()), e.getMessage());
        }
    }

}
