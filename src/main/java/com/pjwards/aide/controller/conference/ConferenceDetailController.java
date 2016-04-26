package com.pjwards.aide.controller.conference;

import com.pjwards.aide.controller.HomeController;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/conferences")
public class ConferenceDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getDetails(Model model,
                       @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting details page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);

        return "conference/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/schedule")
    public String getSchedule(Model model,
                             @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting schedule page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);

        return "conference/schedule";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/registration")
    public String getRegistration() {
        LOGGER.debug("Getting register page");

        return "conference/register";
    }
}
