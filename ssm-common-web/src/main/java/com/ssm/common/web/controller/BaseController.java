package com.ssm.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController {

    @RequestMapping(value = "/{root}/{page}", method = {RequestMethod.GET})
    protected String toPage(@PathVariable String root, @PathVariable String page) throws Exception {
        return root + "/" + page;
    }

}