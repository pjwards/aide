package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.domain.forms.ConferenceForm;
import com.pjwards.aide.domain.validators.ConferenceFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
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
import java.util.List;

@Controller
@RequestMapping("/conferences")
public class ConferenceDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceDetailController.class);

    private ConferenceService conferenceService;
    private ConferenceFormValidator conferenceFormValidator;

    @Autowired
    public ConferenceDetailController(ConferenceService conferenceService, ConferenceFormValidator conferenceFormValidator) {
        this.conferenceService = conferenceService;
        this.conferenceFormValidator = conferenceFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(conferenceFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String root(Model model) {
        LOGGER.debug("Getting conference list page");

        List<Conference> conferences = conferenceService.findAll();
        List<Conference> advertisements = conferenceService.findAllByStatus(Status.OPEN);
        model.addAttribute("conferences", conferences);
        model.addAttribute("advertisements", advertisements);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    public ModelAndView add() throws ConferenceNotFoundException {
        LOGGER.debug("Getting adding page");
        return new ModelAndView("conference/add", "form", new ConferenceForm());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String handleAddConferenceForm(@Valid @ModelAttribute("form") ConferenceForm form,
                                          BindingResult bindingResult,
                                          @ModelAttribute("currentUser") CurrentUser currentUser) {
        LOGGER.debug("Processing add conference form={}, bindingResult={}", form, bindingResult);

        form.setHost(currentUser.getUser());

        if (bindingResult.hasErrors()) {
            // failed validation
            return "conference/add";
        }
        try {
            conferenceService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/";
    }

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

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/register")
    public String getRegister(Model model,
                              @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting register page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);

        return "conference/register";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/admin")
    public String getAdmin(Model model,
                           @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting admin page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);

        return "conference/admin";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/admin/update")
    public ModelAndView update(Model model,
                               @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting update page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);
        return new ModelAndView("conference/update", "form", new ConferenceForm(conference));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/admin/update")
    public String handleUpdateConferenceForm(@Valid @ModelAttribute("form") ConferenceForm form,
                                             BindingResult bindingResult,
                                             Model model,
                                             @PathVariable("id") Long id,
                                             @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Processing update conference form={}, bindingResult={}", form, bindingResult);

        form.setHost(currentUser.getUser());

        if (bindingResult.hasErrors()) {
            // failed validation
            Conference conference = conferenceService.findById(id);
            model.addAttribute("conference", conference);

            return "conference/update";
        }
        try {
            conferenceService.update(form, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + id + "/admin";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/admin/delete")
    public String delete(@PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Deleting conference : {}", id);
        conferenceService.deleteById(id);
        return "redirect:/";
    }
}
