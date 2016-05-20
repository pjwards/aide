package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.Session;
import com.pjwards.aide.domain.forms.SessionForm;
import com.pjwards.aide.domain.validators.SessionFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.session.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/conferences")
public class ConferenceSessionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceSessionController.class);

    private ConferenceService conferenceService;
    private SessionService sessionService;
    private SessionFormValidator sessionFormValidator;

    @Autowired
    public ConferenceSessionController(ConferenceService conferenceService,
                                       SessionService sessionService,
                                       SessionFormValidator sessionFormValidator) {
        this.conferenceService = conferenceService;
        this.sessionService = sessionService;
        this.sessionFormValidator = sessionFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(sessionFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/sessions")
    public String getSessions(Model model,
                              @ModelAttribute("currentUser") CurrentUser currentUser,
                              @PathVariable("conference-id") Long conferenceId) throws ConferenceNotFoundException {
        LOGGER.debug("Getting session list page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return "error/403";
        }

        return "session/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/sessions/add")
    public ModelAndView add(Model model,
                            @ModelAttribute("currentUser") CurrentUser currentUser,
                            @PathVariable("conference-id") Long conferenceId) throws SessionNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting adding page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        return new ModelAndView("session/add", "form", new SessionForm());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/sessions/add")
    public String handleAddSessionForm(@Valid @ModelAttribute("form") SessionForm form,
                                       BindingResult bindingResult,
                                       Model model,
                                       @PathVariable("conference-id") Long conferenceId,
                                       @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Processing add session form={}, bindingResult={}", form, bindingResult);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(conferenceId);

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return "error/403";
        }

        if (bindingResult.hasErrors()) {
            // failed validation
            model.addAttribute("conference", conference);
            return "session/add";
        }
        try {
            sessionService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/sessions";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/sessions/{id}")
    public ModelAndView update(Model model,
                               @PathVariable("conference-id") Long conferenceId,
                               @ModelAttribute("currentUser") CurrentUser currentUser,
                               @PathVariable("id") Long id) throws SessionNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting update page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);
        Session session = sessionService.findById(id);
        model.addAttribute("session", session);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        return new ModelAndView("session/update", "form", new SessionForm(session));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/sessions/{id}")
    public String handleUpdateSessionForm(@Valid @ModelAttribute("form") SessionForm form,
                                          BindingResult bindingResult,
                                          Model model,
                                          @PathVariable("conference-id") Long conferenceId,
                                          @PathVariable("id") Long id,
                                          @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException, SessionNotFoundException {
        LOGGER.debug("Processing update session form={}, bindingResult={}", form, bindingResult);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(conferenceId);

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return "error/403";
        }

        if (bindingResult.hasErrors()) {
            // failed validation
            model.addAttribute("conference", conference);
            Session session = sessionService.findById(id);
            model.addAttribute("session", session);
            return "session/update";
        }
        try {
            sessionService.update(form, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/sessions";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{conference-id}/admin/sessions/{id}")
    public String delete(@PathVariable("conference-id") Long conferenceId,
                         @ModelAttribute("currentUser") CurrentUser currentUser,
                         @PathVariable("id") Long id) throws ConferenceNotFoundException, SessionNotFoundException {
        LOGGER.debug("Deleting session : {}", id);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(conferenceId);

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return "error/403";
        }

        sessionService.deleteById(id);
        return "redirect:/conferences/" + conferenceId + "/admin/sessions";
    }
}
