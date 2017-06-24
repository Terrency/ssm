package com.ssm.common.web.controller;

import com.google.code.kaptcha.Producer;
import com.ssm.common.model.ModelMap;
import com.ssm.common.util.Constant;
import com.ssm.common.web.captcha.CaptchaService;
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
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private Producer captchaProducer;

    @ResponseBody
    @RequestMapping("/getCapToken")
    public ModelMap getCapToken() {
        return new ModelMap("capToken", captchaService.genCapToken());
    }

    /**
     * @see com.google.code.kaptcha.servlet.KaptchaServlet#doGet
     */
    @RequestMapping(value = "/getCapImage", method = RequestMethod.GET)
    public void getCapImage(@RequestParam("capToken") String capToken, HttpServletResponse response) throws Exception {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaService.getCapText(capToken);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
    }

    @ResponseBody
    @RequestMapping("/verify")
    public ModelMap verify(@RequestParam("capToken") String capToken, @RequestParam("capText") String capText) {
        try {
            return new ModelMap(Constant.REMOTE_VALIDATION_KEY, captchaService.doVerify(capToken, capText));
        } catch (Exception e) {
            return new ModelMap(Constant.REMOTE_VALIDATION_KEY, Boolean.FALSE);
        }
    }

}
