package com.pjwards.aide.controller.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.forms.RoomForm;
import com.pjwards.aide.domain.validators.RoomFormValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.room.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/conferences")
public class ConferenceRoomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceRoomController.class);

    private ConferenceService conferenceService;
    private RoomService roomService;
    private RoomFormValidator roomFormValidator;

    @Autowired
    public ConferenceRoomController(ConferenceService conferenceService,
                                    RoomService roomService,
                                    RoomFormValidator roomFormValidator) {
        this.conferenceService = conferenceService;
        this.roomService = roomService;
        this.roomFormValidator = roomFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(roomFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/rooms")
    public String getDays(Model model,
                          @PathVariable("conference-id") Long conferenceId) throws ConferenceNotFoundException {
        LOGGER.debug("Getting room list page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);

        return "room/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/rooms/add")
    public ModelAndView add(Model model,
                            @PathVariable("conference-id") Long conferenceId) throws RoomNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting adding page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);

        return new ModelAndView("room/add", "form", new RoomForm());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/rooms/add")
    public String handleAddRoomForm(@Valid @ModelAttribute("form") RoomForm form,
                                    BindingResult bindingResult,
                                    Model model,
                                    @PathVariable("conference-id") Long conferenceId,
                                    @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException {
        LOGGER.debug("Processing add room form={}, bindingResult={}", form, bindingResult);

        if (bindingResult.hasErrors()) {
            // failed validation
            Conference conference = conferenceService.findById(conferenceId);
            model.addAttribute("conference", conference);
            return "room/add";
        }
        try {
            roomService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/rooms";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/rooms/{id}")
    public ModelAndView update(Model model,
                               @PathVariable("conference-id") Long conferenceId,
                               @PathVariable("id") Long id) throws RoomNotFoundException, ConferenceNotFoundException {
        LOGGER.debug("Getting update page");

        Conference conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);
        Room room = roomService.findById(id);
        model.addAttribute("room", room);

        return new ModelAndView("room/update", "form", new RoomForm(room));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/rooms/{id}")
    public String handleUpdateRoomForm(@Valid @ModelAttribute("form") RoomForm form,
                                       BindingResult bindingResult,
                                       Model model,
                                       @PathVariable("conference-id") Long conferenceId,
                                       @PathVariable("id") Long id,
                                       @ModelAttribute("currentUser") CurrentUser currentUser) throws ConferenceNotFoundException, RoomNotFoundException {
        LOGGER.debug("Processing update room form={}, bindingResult={}", form, bindingResult);

        if (bindingResult.hasErrors()) {
            // failed validation
            Conference conference = conferenceService.findById(conferenceId);
            model.addAttribute("conference", conference);
            Room room = roomService.findById(id);
            model.addAttribute("room", room);
            return "room/update";
        }
        try {
            roomService.update(form, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin/rooms";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{conference-id}/admin/rooms/{id}")
    public String delete(@PathVariable("conference-id") Long conferenceId,
                         @PathVariable("id") Long id) throws ConferenceNotFoundException, RoomNotFoundException {
        LOGGER.debug("Deleting room : {}", id);
        roomService.deleteById(id);
        return "redirect:/conferences/" + conferenceId + "/admin/rooms";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conference-id}/admin/rooms/{id}/timer")
    public
    @ResponseBody
    Map<String, Object> timer(@PathVariable("conference-id") Long conferenceId,
                              @PathVariable("id") Long id,
                              @RequestParam("timer") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sssZ") Date timer)
            throws ConferenceNotFoundException, RoomNotFoundException {
        LOGGER.debug("Set room timer : {}", id);
        Room room = roomService.findById(id);
        room.setTimer(timer);
        roomService.update(room);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "The timer set successfully");
        response.put("timer", timer);

        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{conference-id}/admin/rooms/{id}/timer")
    public
    @ResponseBody
    Map<String, Object> timer(@PathVariable("conference-id") Long conferenceId,
                              @PathVariable("id") Long id)
            throws ConferenceNotFoundException, RoomNotFoundException {
        LOGGER.debug("Get room timer : {}", id);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ");

        Room room = roomService.findById(id);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "The timer set successfully");
        response.put("timer", room.getTimer() == null ? null : dateFormatter.format(room.getTimer()));

        return response;
    }
}
