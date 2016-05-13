package com.pjwards.aide.controller.program;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.service.program.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/programs")
public class ProgramDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDetailController.class);

    @Autowired
    private ProgramService programService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getDetails(Model model,
                             @PathVariable("id") Long id) throws ConferenceNotFoundException, ProgramNotFoundException {
        LOGGER.debug("Getting details page");

        Program program = programService.findById(id);
        model.addAttribute("program", program);
        model.addAttribute("conference", program.getDate().getConference());

        return "program/details";
    }

}
