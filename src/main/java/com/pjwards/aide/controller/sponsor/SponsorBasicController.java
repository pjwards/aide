package com.pjwards.aide.controller.sponsor;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.domain.forms.SponsorAddForm;
import com.pjwards.aide.domain.validators.ImageValidator;
import com.pjwards.aide.domain.validators.SponsorAddFormValidator;
import com.pjwards.aide.exception.SponsorNotFoundException;
import com.pjwards.aide.repository.AssetsRepository;
import com.pjwards.aide.repository.ConferenceRepository;
import com.pjwards.aide.repository.SponsorRepository;
import com.pjwards.aide.service.sponsor.SponsorService;
import com.pjwards.aide.util.Utils;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/conferences/{conference_id}/admin/sponsor")
public class SponsorBasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorBasicController.class);

    private SponsorService sponsorService;
    private SponsorAddFormValidator sponsorAddFormValidator;
    private final UrlValidator urlValidator = new UrlValidator() {
        /** allow missing scheme. */
        @Override
        public boolean isValid(String value) {
            return super.isValid(value) || super.isValid("http://" + value);
        }
    };

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private ImageValidator imageValidator;

    @Autowired
    private Utils utils;

    @Autowired
    public SponsorBasicController(SponsorService sponsorService, SponsorAddFormValidator sponsorAddFormValidator) {
        this.sponsorService = sponsorService;
        this.sponsorAddFormValidator = sponsorAddFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(sponsorAddFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    public ModelAndView add(Model model, @PathVariable("conference_id") Long conferenceId,
                            @ModelAttribute("currentUser") CurrentUser currentUser) throws SponsorNotFoundException {
        LOGGER.debug("Getting details page");

        Conference conference = conferenceRepository.findOne(conferenceId);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        model.addAttribute("conference", conference);
        return new ModelAndView("conference/sponsor/sponsoradd", "form", new SponsorAddForm());

    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String handleSponsorForm(Model model, @Valid @ModelAttribute("form") SponsorAddForm form,
                                    @ModelAttribute("currentUser") CurrentUser currentUser,
                                    BindingResult bindingResult, @PathVariable("conference_id") Long conferenceId) {
        LOGGER.debug("Processing add sponsor form={}, bindingResult={}", form, bindingResult);

        Conference conference = conferenceRepository.findOne(conferenceId);

        form.setConference(conference);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        if (bindingResult.hasErrors()) {
            // failed validation
            model.addAttribute("conference", conference);
            return "conference/sponsor/sponsoradd";
        }
        try {
            sponsorService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ok, redirect
        return "redirect:/conferences/" + conferenceId + "/admin";
    }

    @RequestMapping("/{sponsor_id}/update")
    public ModelAndView getSettingsSponsorUpdate(Model model,
                                                 @ModelAttribute("currentUser") CurrentUser currentUser,
                                                 @PathVariable("sponsor_id") Long sponsorId,
                                                 @PathVariable("conference_id") Long conferenceId) throws SponsorNotFoundException {
        LOGGER.debug("Getting settings Sponsor update form");
        Sponsor sponsor = sponsorRepository.findOne(sponsorId);
        Conference conference = conferenceRepository.findOne(conferenceId);
        model.addAttribute("conference", conference);
        model.addAttribute("sponsor", sponsor);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        return new ModelAndView("conference/sponsor/sponsorupdate");
    }

    @RequestMapping(value = "/{sponsor_id}/update", method = RequestMethod.POST)
    public ModelAndView updateSettingsSponsor(@PathVariable("sponsor_id") Long sponsorId,
                                              @RequestParam(value = "file", required = false) MultipartFile file,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "slug", required = false) String slug,
                                              @RequestParam(value = "rank", required = false) int rank,
                                              @RequestParam(value = "url", required = false) String url,
                                              @RequestParam(value = "description", required = false) String description,
                                              @PathVariable("conference_id") Long conferenceId,
                                              @ModelAttribute("currentUser") CurrentUser currentUser,
                                              Model model) throws SponsorNotFoundException {
        LOGGER.debug("Getting settings Sponsor update, name={}", name);

        ModelAndView modelAndView = new ModelAndView("conference/sponsor/sponsorupdate");

        Sponsor sponsor = sponsorService.findById(sponsorId);

        modelAndView.addObject("sponsor", sponsor);

        Conference conference = conferenceRepository.findOne(conferenceId);
        model.addAttribute("conference", conference);

        if (currentUser == null) {
            return new ModelAndView("redirect:/sign_in");
        }

        if (!conference.isHost(currentUser.getUser())) {
            return new ModelAndView("error/403");
        }

        /*
        Set name
         */
        if (name != null && !name.equals("")) {
            sponsor.update(sponsor.getSlug(), name, sponsor.getUrl(), sponsor.getDescription(), sponsor.getRank());

            sponsorRepository.save(sponsor);
            modelAndView.addObject("nameSuccess", "Changed successfully");
        } else {
            modelAndView.addObject("nameError", "Name is empty");
        }

        /*
        Set sponsor image
         */
        if (file != null && imageValidator.validate(file.getOriginalFilename())) {
            LOGGER.debug("File name={}, validated={}", file.getOriginalFilename(), imageValidator.validate(file.getOriginalFilename()));
            Assets assets = utils.fileSaveHelper(file, sponsor, "/img/");
            if (assets != null) {
                Assets oldAsset = sponsor.getAssets();
                utils.fileRemoveHelper(oldAsset.getRealPath());
                sponsor.setAssets(assets);
                sponsorRepository.save(sponsor);
                assetsRepository.delete(oldAsset);
                modelAndView.addObject("avatarSuccess", "The Image has been changed successfully");
            }
        }

        /*
         Set slug
         */
        if (slug != null && !slug.equals("")) {
            sponsor.update(slug, sponsor.getName(), sponsor.getUrl(), sponsor.getDescription(), sponsor.getRank());

            sponsorRepository.save(sponsor);
            modelAndView.addObject("slugSuccess", "Changed successfully");
        } else {
            modelAndView.addObject("slugError", "Slug is empty");
        }

        /*
         Set rank
         */
        if (rank >= 0 && rank <= 100) {
            sponsor.update(sponsor.getSlug(), sponsor.getName(), sponsor.getUrl(), sponsor.getDescription(), rank);

            sponsorRepository.save(sponsor);
            modelAndView.addObject("rankSuccess", "Changed successfully");
        } else {
            modelAndView.addObject("rankError", "Rank is wrong");
        }

        /*
        Set url
         */
        if (url != null && !url.equals("") && !urlValidator.isValid(url)) {
            sponsor.update(sponsor.getSlug(), sponsor.getName(), url, sponsor.getDescription(), sponsor.getRank());

            sponsorRepository.save(sponsor);
            modelAndView.addObject("urlSuccess", "Changed successfully");
        }

        /*
        Set description
         */
        if (description != null && !description.equals("")) {
            sponsor.update(sponsor.getSlug(), sponsor.getName(), sponsor.getUrl(), description, sponsor.getRank());

            sponsorRepository.save(sponsor);
            modelAndView.addObject("descriptionSuccess", "Changed successfully");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/list")
    public String getSponsorsPage(Model model,
                                  @ModelAttribute("currentUser") CurrentUser currentUser,
                                  @PathVariable("conference_id") Long conferenceId) {
        LOGGER.debug("Getting sponsors page");

        Conference conference = conferenceRepository.findOne(conferenceId);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        List<Sponsor> sponsorList = sponsorRepository.findAllWhereConference(conferenceId);
        model.addAttribute("sponsorList", sponsorList);
        model.addAttribute("conference", conference);

        return "conference/sponsor/sponsorlist";
    }

    @RequestMapping(value = "/{sponsor_id}/delete", method = RequestMethod.DELETE)
    public String deleteSponsor(@PathVariable("sponsor_id") Long id,
                                @ModelAttribute("currentUser") CurrentUser currentUser,
                                @PathVariable("conference_id") Long conferenceId) {
        LOGGER.debug("Sponsor id={}", id);

        Conference conference = conferenceRepository.findOne(conferenceId);

        if (currentUser == null) {
            return "redirect:/sign_in";
        }

        if (!conference.isHost(currentUser.getUser())) {
            return "error/403";
        }

        sponsorRepository.delete(id);

        return "redirect:/conferences/" + conferenceId + "/admin/sponsor/list";
    }
}
