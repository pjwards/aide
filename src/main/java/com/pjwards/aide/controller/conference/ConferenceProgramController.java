package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.forms.ProgramForm;
import com.pjwards.aide.domain.validators.ProgramFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.program.ProgramService;
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
public class ConferenceProgramController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceProgramController.class);

    private ConferenceService conferenceService;
    private ProgramService programService;
    private ProgramFormValidator programFormValidator;

    @Autowired
    public ConferenceProgramController(ConferenceService conferenceService,
                                       ProgramService programService,
                                       ProgramFormValidator programFormValidator) {
        this.conferenceService = conferenceService;
        this.programService = programService;
        this.programFormValidator = programFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(programFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/programs")
    public String getPrograms(Model model,
                              @ModelAttribute("currentUser") CurrentUser currentUser,
                              @PathVariable("conference-id") Long conferenceId) throws ConferenceNotFoundException {
        LOGGER.debug("Getting program list page");

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

        return "program/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/programs/add")
    public ModelAndView add(Model model,
                            @ModelAttribute("currentUser") CurrentUser currentUser,
                            @PathVariable("conference-id") Long conferenceId) throws ProgramNotFoundException, ConferenceNotFoundException {
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

        return new ModelAndView("program/add", "form", new ProgramForm());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/programs/add")
    public String handleAddProgramForm(@Valid @ModelAttribute("form") ProgramForm form,
                                       BindingResult bindingResult,
                                       Model model,
                                       @PathVariable("conference-id") Long conferenceId,
                                       @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Processing add program form={}, bindingResult={}", form, bindingResult);

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
            return "program/add";
        }
        try {
            programService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/programs";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/programs/{id}")
    public ModelAndView update(Model model,
                               @ModelAttribute("currentUser") CurrentUser currentUser,
                               @PathVariable("conference-id") Long conferenceId,
                               @PathVariable("id") Long id) throws ProgramNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting update page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);
        Program program = programService.findById(id);
        model.addAttribute("program", program);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        return new ModelAndView("program/update", "form", new ProgramForm(program));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/programs/{id}")
    public String handleUpdateProgramForm(@Valid @ModelAttribute("form") ProgramForm form,
                                          BindingResult bindingResult,
                                          Model model,
                                          @PathVariable("conference-id") Long conferenceId,
                                          @PathVariable("id") Long id,
                                          @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException, ProgramNotFoundException {
        LOGGER.debug("Processing update program form={}, bindingResult={}", form, bindingResult);

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
            Program program = programService.findById(id);
            model.addAttribute("program", program);
            return "program/update";
        }
        try {
            programService.update(form, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/programs";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{conference-id}/admin/programs/{id}")
    public String delete(@PathVariable("conference-id") Long conferenceId,
                         @ModelAttribute("currentUser") CurrentUser currentUser,
                         @PathVariable("id") Long id) throws ConferenceNotFoundException, ProgramNotFoundException {
        LOGGER.debug("Deleting program : {}", id);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(conferenceId);

        if (!conference.isHost(currentUser.getUser())
                && !conference.isManager(currentUser.getUser())
                && !conference.isSpeaker(currentUser.getUser())) {
            return "error/403";
        }

        programService.deleteById(id);
        return "redirect:/conferences/" + conferenceId + "/admin/programs";
    }
}
