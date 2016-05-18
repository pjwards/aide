package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.forms.ProgramDateForm;
import com.pjwards.aide.domain.validators.ProgramDateFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.programdate.ProgramDateService;
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
public class ConferenceProgramDateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceProgramDateController.class);

    private ConferenceService conferenceService;
    private ProgramDateService programDateService;
    private ProgramDateFormValidator programDateFormValidator;

    @Autowired
    public ConferenceProgramDateController(ConferenceService conferenceService,
                                           ProgramDateService programDateService,
                                           ProgramDateFormValidator programDateFormValidator) {
        this.conferenceService = conferenceService;
        this.programDateService = programDateService;
        this.programDateFormValidator = programDateFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(programDateFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/days")
    public String getDays(Model model,
                          @PathVariable("conference-id") Long conferenceId) throws ConferenceNotFoundException {
        LOGGER.debug("Getting day list page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);

        return "programdate/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/days/add")
    public ModelAndView add(Model model,
                            @PathVariable("conference-id") Long conferenceId) throws ProgramDateNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting adding page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);

        return new ModelAndView("programdate/add", "form", new ProgramDateForm());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/days/add")
    public String handleAddProgramDateForm(@Valid @ModelAttribute("form") ProgramDateForm form,
                                           BindingResult bindingResult,
                                           Model model,
                                           @PathVariable("conference-id") Long conferenceId,
                                           @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Processing add program date form={}, bindingResult={}", form, bindingResult);

        if (bindingResult.hasErrors()) {
            // failed validation
            Conference conference = conferenceService.findById(conferenceId);
            model.addAttribute("conference", conference);
            return "programdate/add";
        }
        try {
            programDateService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/days";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/days/{id}")
    public ModelAndView update(Model model,
                               @PathVariable("conference-id") Long conferenceId,
                               @PathVariable("id") Long id) throws ProgramDateNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting update page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);
        ProgramDate programDate = programDateService.findById(id);
        model.addAttribute("programDate", programDate);

        return new ModelAndView("programdate/update", "form", new ProgramDateForm(programDate));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/days/{id}")
    public String handleUpdateProgramDateForm(@Valid @ModelAttribute("form") ProgramDateForm form,
                                              BindingResult bindingResult,
                                              Model model,
                                              @PathVariable("conference-id") Long conferenceId,
                                              @PathVariable("id") Long id,
                                              @ModelAttribute("currentUser") CurrentUser currentUser) throws ProgramDateNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Processing update program date form={}, bindingResult={}", form, bindingResult);

        if (bindingResult.hasErrors()) {
            // failed validation
            Conference conference = conferenceService.findById(conferenceId);
            model.addAttribute("conference", conference);
            ProgramDate programDate = programDateService.findById(id);
            model.addAttribute("programDate", programDate);

            return "programdate/update";
        }
        try {
            programDateService.update(form, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/days";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{conference-id}/admin/days/{id}")
    public String delete(@PathVariable("conference-id") Long conferenceId,
                         @PathVariable("id") Long id) throws ConferenceNotFoundException, ProgramDateNotFoundException {
        LOGGER.debug("Deleting program date : {}", id);
        programDateService.deleteById(id);
        return "redirect:/conferences/" + conferenceId + "/admin/days";
    }
}
