package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.*;
import com.pjwards.aide.domain.enums.Check;
import com.pjwards.aide.domain.enums.Role;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.domain.forms.ConferenceForm;
import com.pjwards.aide.domain.forms.SignUpForm;
import com.pjwards.aide.domain.validators.ConferenceFormValidator;
import com.pjwards.aide.domain.validators.SignUpFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;
import com.pjwards.aide.exception.PresenceNotFoundException;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.conferencerole.ConferenceRoleService;
import com.pjwards.aide.service.presence.PresenceService;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/conferences")
public class ConferenceDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceDetailController.class);

    private ConferenceService conferenceService;
    private ConferenceFormValidator conferenceFormValidator;
    private ConferenceRoleService conferenceRoleService;
    private UserService userService;
    private SignUpFormValidator signUpFormValidator;
    private PresenceService presenceService;

    @Autowired
    public ConferenceDetailController(ConferenceService conferenceService, ConferenceFormValidator conferenceFormValidator,
                                      ConferenceRoleService conferenceRoleService, UserService userService,
                                      SignUpFormValidator signUpFormValidator, PresenceService presenceService) {
        this.conferenceService = conferenceService;
        this.conferenceFormValidator = conferenceFormValidator;
        this.conferenceRoleService = conferenceRoleService;
        this.userService = userService;
        this.signUpFormValidator = signUpFormValidator;
        this.presenceService = presenceService;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(conferenceFormValidator);
    }

    @InitBinder("form2")
    public void initBinderDummy(WebDataBinder binder) {
        binder.addValidators(signUpFormValidator);
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
    public ModelAndView add(@ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Getting adding page");

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        return new ModelAndView("conference/add", "form", new ConferenceForm());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String handleAddConferenceForm(@Valid @ModelAttribute("form") ConferenceForm form,
                                          BindingResult bindingResult,
                                          @ModelAttribute("currentUser") CurrentUser currentUser) {
        LOGGER.debug("Processing add conference form={}, bindingResult={}", form, bindingResult);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

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
                              @RequestParam(value = "company", required = false) String company) throws ConferenceNotFoundException, UserNotFoundException, ConferenceRoleNotFoundException {
        LOGGER.debug("Getting register page");

        Conference conference = conferenceService.findById(id);
        User user = currentUser.getUser();

        conferenceRoleService.add(new ConferenceRole()
                .setConferenceRole(Role.PARTICIPANT)
                .setConference(conference)
                .setUser(user));

        presenceService.add(new Presence()
                .setPresenceCheck(Check.ABSENCE)
                .setConference(conference)
                .setUser(user));


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

        int absence = 0;
        int presence = 0;

        for (Presence p : conference.getPresenceSetConference()) {
            if (p.getPresenceCheck().equals(Check.ABSENCE)) {
                absence++;
            } else {
                presence++;
            }
        }

        model.addAttribute("absence", absence);
        model.addAttribute("presence", presence);

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

    @RequestMapping(value = "/{id}/admin/list")
    public String getConferenceUserList(Model model,
                                        @ModelAttribute("currentUser") CurrentUser currentUser,
                                        @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting conference user list");

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(id);

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        List<ConferenceRole> conferenceRoles = conferenceRoleService.findByConference(conference);
        model.addAttribute("conferenceRoles", conferenceRoles);
        model.addAttribute("conference", conference);

        return "conference/list";
    }

    @RequestMapping(value = "/{id}/admin/edit_role", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> ajaxEditRoleConference(@PathVariable("id") Long id, @RequestBody Map<String, String> json) throws ConferenceRoleNotFoundException, UserNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Ajax edit role content={}", json);

        Map<String, Object> response = new LinkedHashMap<>();

        if (json.isEmpty()) {
            response.put("message", "400");
            return response;
        }
        Long jsonRoleId = Long.parseLong(json.get("j_data"));
        ConferenceRole conferenceRole = conferenceRoleService.findById(jsonRoleId);
        String jsonString = json.get("j_role");
        Role role = conferenceRole.getConferenceRole();
        Role targetRole = null;

        if (jsonString.equals("MANAGER")) {
            targetRole = Role.MANAGER;
        } else if (jsonString.equals("PARTICIPANT")) {
            targetRole = Role.PARTICIPANT;
        } else if (jsonString.equals("SPEAKER")) {
            targetRole = Role.SPEAKER;
        } else if (jsonString.equals("HOST")) {
            targetRole = Role.HOST;
        }

        if (role == targetRole) {
            response.put("message", "200");
            return response;
        }

        conferenceRole.setConferenceRole(targetRole);
        conferenceRoleService.updateContent(conferenceRole);

        response.put("message", "200");
        return response;
    }

    @RequestMapping("/{id}/admin/dummy")
    public ModelAndView getDummySignUpPage(@PathVariable("id") Long id,
                                           @ModelAttribute("currentUser") CurrentUser currentUser,
                                           Model model) throws ConferenceNotFoundException {
        LOGGER.debug("Getting dummy sign_up form");

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        Conference conference = conferenceService.findById(id);

        if (!conference.isHost(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        model.addAttribute("conference", conference);
        return new ModelAndView("conference/dummy", "form2", new SignUpForm());
    }

    @RequestMapping(value = "/{id}/admin/dummy", method = RequestMethod.POST)
    public String handleDummySignUpForm(@PathVariable("id") Long id,
                                        @ModelAttribute("currentUser") CurrentUser currentUser,
                                        @Valid @ModelAttribute("form2") SignUpForm form, BindingResult bindingResult) throws ConferenceNotFoundException {
        LOGGER.debug("Processing user sign_up form={}, bindingResult={}", form, bindingResult);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(id);

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        if (bindingResult.hasErrors()) {
            // failed validation
            return "conference/dummy";
        }
        try {
            userService.createDummy(form, conference);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "conference/dummy";
        }
        // ok, redirect
        return "redirect:/conferences/" + id + "/admin";
    }

    @RequestMapping(value = "/{id}/admin/presence")
    public String getConferencePresenceCheck(Model model,
                                             @ModelAttribute("currentUser") CurrentUser currentUser,
                                             @PathVariable("id") Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Getting conference presence check");

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        Conference conference = conferenceService.findById(id);

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        List<Presence> presences = presenceService.findByConference(conference);
        model.addAttribute("presences", presences);
        model.addAttribute("conference", conference);

        return "conference/presencelist";
    }

    @RequestMapping(value = "/{id}/admin/edit_presence", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> ajaxEditPresenceStatus(@PathVariable("id") Long id, @RequestBody Map<String, String> json) throws PresenceNotFoundException, UserNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Ajax edit role content={}", json);

        Map<String, Object> response = new LinkedHashMap<>();

        if (json.isEmpty()) {
            response.put("message", "400");
            return response;
        }
        Long jsonRoleId = Long.parseLong(json.get("j_data"));
        Presence presence = presenceService.findById(jsonRoleId);
        String jsonString = json.get("j_role");
        Check check = presence.getPresenceCheck();
        Check targetCheck = null;

        if (jsonString.equals("PRESENCE")) {
            targetCheck = Check.PRESENCE;
        } else if (jsonString.equals("ABSENCE")) {
            targetCheck = Check.ABSENCE;
        }

        if (check == targetCheck) {
            response.put("message", "200");
            return response;
        }

        presence.setPresenceCheck(targetCheck);
        presenceService.update(presence);

        response.put("message", "200");
        return response;
    }

}
