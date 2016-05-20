package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.domain.forms.ConferenceForm;
import com.pjwards.aide.domain.validators.ConferenceFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/conferences")
public class ConferenceDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceDetailController.class);

    private ConferenceService conferenceService;
    private ConferenceFormValidator conferenceFormValidator;
    private UserService userService;

    @Autowired
    public ConferenceDetailController(ConferenceService conferenceService,
                                      ConferenceFormValidator conferenceFormValidator,
                                      UserService userService) {
        this.conferenceService = conferenceService;
        this.conferenceFormValidator = conferenceFormValidator;
        this.userService = userService;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(conferenceFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String root(Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 6) Pageable pageable,
                       @RequestParam(value = "k", required = false) String keyword) {
        LOGGER.debug("Getting conference list page");

        if (keyword != null && !keyword.equals("")) {
            model.addAttribute("conferences", conferenceService.findAll(pageable, keyword));
        } else {
            model.addAttribute("conferences", conferenceService.findAll(pageable));
        }

        List<Conference> advertisements = conferenceService.findAll(Status.OPEN);
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

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/register")
    public String getRegister(Model model,
                              @PathVariable("id") Long id,
                              @ModelAttribute("currentUser") CurrentUser currentUser,
                              @RequestParam(value = "company", required = false) String company) throws ConferenceNotFoundException, UserNotFoundException {
        LOGGER.debug("Getting register page");

        Conference conference = conferenceService.findById(id);

        User user = currentUser.getUser();

        if (company != null && !company.equals("") && !company.equals(user.getCompany())) {
            user.setCompany(company);
        }
        Set<Conference> conferences = user.getConferenceSet();
        conferences.add(conference);
        user.setConferenceSet(conferences);
        userService.update(user);

        Set<User> participants = conference.getParticipants();
        participants.add(user);
        conference.setParticipants(participants);
        conference = conferenceService.update(conference);

        model.addAttribute("conference", conference);

        return "redirect:/conferences/" + id;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/admin")
    public String getAdmin(Model model,
                           @ModelAttribute("currentUser") CurrentUser currentUser,
                           @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting admin page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        User user = currentUser.getUser();

        if (conference.isHost(user)) {
            return "conference/admin";
        } else if (conference.isManager(user)) {
            return "conference/admin_manager";
        } else if (conference.isSpeaker(user)) {
            return "conference/admin_speaker";
        }

        return "error/403";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/admin/update")
    public ModelAndView update(Model model,
                               @ModelAttribute("currentUser") CurrentUser currentUser,
                               @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting update page");

        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference", conference);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }
        return new ModelAndView("conference/update", "form", new ConferenceForm(conference));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/admin/update")
    public String handleUpdateConferenceForm(@Valid @ModelAttribute("form") ConferenceForm form,
                                             BindingResult bindingResult,
                                             Model model,
                                             @PathVariable("id") Long id,
                                             @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Processing update conference form={}, bindingResult={}", form, bindingResult);

        Conference conference = conferenceService.findById(id);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        form.setHost(currentUser.getUser());

        if (bindingResult.hasErrors()) {
            // failed validation
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
    public String delete(@PathVariable("id") Long id,
                         @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Deleting conference : {}", id);

        Conference conference = conferenceService.findById(id);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        conferenceService.deleteById(id);
        return "redirect:/";
    }
}
