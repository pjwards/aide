package com.pjwards.aide.controller;

import com.pjwards.aide.service.conference.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping("/")
    public String root() {
        LOGGER.debug("Getting home page");
        return "redirect:/conferences";
    }

    @RequestMapping(value = "/400")
    public String error400() {
        LOGGER.debug("Getting error page");
        return "error/400";
    }

    @RequestMapping(value = "/403")
    public String error403() {
        LOGGER.debug("Getting error page");
        return "error/403";
    }

    @RequestMapping(value = "/404")
    public String error404() {
        LOGGER.debug("Getting error page");
        return "error/404";
    }

    @RequestMapping(value = "/500")
    public String error500() {
        LOGGER.debug("Getting error page");
        return "error/500";
    }
}
