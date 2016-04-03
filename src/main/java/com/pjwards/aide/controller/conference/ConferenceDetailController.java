package com.pjwards.aide.controller.conference;

import com.pjwards.aide.controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conferences")
public class ConferenceDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping()
    public String root() {
        LOGGER.debug("Getting home page");
        return "conference/index";
    }
}
