package com.pjwards.aide.controller.session;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.service.session.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sessions")
public class SessionDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDetailController.class);

    @Autowired
    private SessionService sessionService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getDetails(Model model,
                             @PathVariable("id") Long id) throws ConferenceNotFoundException, SessionNotFoundException {
        LOGGER.debug("Getting details page");

        Session session = sessionService.findById(id);
        model.addAttribute("session", session);
        model.addAttribute("conference", session.getProgram().getDate().getConference());

        return "session/details";
    }

}
