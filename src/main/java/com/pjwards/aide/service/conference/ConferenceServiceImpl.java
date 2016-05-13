package com.pjwards.aide.service.conference;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.enums.ContactType;
import com.pjwards.aide.domain.enums.Status;
import com.pjwards.aide.domain.forms.ConferenceForm;
import com.pjwards.aide.domain.validators.ImageValidator;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.repository.AssetsRepository;
import com.pjwards.aide.repository.ConferenceRepository;
import com.pjwards.aide.repository.ContactRepository;
import com.pjwards.aide.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    private ConferenceRepository conferenceRepository;
    private ContactRepository contactRepository;
    private AssetsRepository assetsRepository;
    private ImageValidator imageValidator;
    private Utils utils;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository,
                                 ContactRepository contactRepository,
                                 AssetsRepository assetsRepository,
                                 ImageValidator imageValidator,
                                 Utils utils) {
        this.conferenceRepository = conferenceRepository;
        this.contactRepository = contactRepository;
        this.assetsRepository = assetsRepository;
        this.imageValidator = imageValidator;
        this.utils = utils;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Conference> findAll() {
        LOGGER.debug("Finding all conferences.");

        List<Conference> conferences = conferenceRepository.findAll();
        LOGGER.debug("Found {} conferences.", conferences.size());

        return conferences;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Conference> findAllByStatus(Status status) {
        LOGGER.debug("Finding all conferences by status.");

        List<Conference> conferences = conferenceRepository.findAllByStatus(status);
        LOGGER.debug("Found {} conferences.", conferences.size());

        return conferences;
    }

    @Transactional
    @Override
    public Conference add(Conference added) {
        LOGGER.debug("Creating a new conference with information: {}", added);

        added = conferenceRepository.save(added);
        LOGGER.debug("Added a conference with information: {}", added);

        return added;
    }


    @Transactional(readOnly = true, rollbackFor = {ConferenceNotFoundException.class})
    @Override
    public Conference findById(Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Finding conference with id: {}", id);

        Conference found = conferenceRepository.findOne(id);
        LOGGER.debug("Found conference with information: {}", found);

        if (found == null) {
            LOGGER.debug("No conference found with id: {}", id);
            throw new ConferenceNotFoundException("No conference found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {ConferenceNotFoundException.class})
    @Override
    public Conference update(Conference updated) throws ConferenceNotFoundException {
        LOGGER.debug("Updating a conference with information: {}", updated);

        Conference found = findById(updated.getId());
        found.update(updated);
        conferenceRepository.save(found);
        LOGGER.debug("Updated the information of a conference to: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {ConferenceNotFoundException.class})
    @Override
    public Conference deleteById(Long id) throws ConferenceNotFoundException {
        LOGGER.debug("Deleting a conference with id: {}", id);

        Conference deleted = findById(id);
        conferenceRepository.delete(deleted);
        LOGGER.debug("Deleting conference: {}", deleted);

        return deleted;
    }

    @Transactional
    @Override
    public Conference create(ConferenceForm form) {
        LOGGER.debug("Create a user with Info: {}", form);

        Conference conference = new Conference.Builder(
                form.getName(),
                form.getSlogan(),
                form.getDescription())
                .status(form.getStatus())
                .charge(form.getCharge())
                .price(form.getPrice())
                .host(form.getHost())
                .location(form.getLocation())
                .locationUrl(form.getLocationUrl())
                .disqus(form.getDisqus())
                .build();

        if (form.getLat() != null && form.getLan() != null) {
            conference.setLat(form.getLat()).setLan(form.getLan());
        }

        conference = conferenceRepository.save(conference);

        if (form.getEmail() != null && !form.getEmail().equals("")) {
            contactRepository.save(
                    new Contact.Builder(ContactType.EMAIL, form.getEmail())
                            .conference(conference)
                            .build());
        }
        if (form.getFacebook() != null && !form.getFacebook().equals("")) {
            contactRepository.save(
                    new Contact.Builder(ContactType.FACEBOOK, form.getFacebook())
                            .conference(conference)
                            .build());
        }
        if (form.getTwitter() != null && !form.getTwitter().equals("")) {
            contactRepository.save(
                    new Contact.Builder(ContactType.TWITTER, form.getTwitter())
                            .conference(conference)
                            .build());
        }
        if (form.getGithub() != null && !form.getGithub().equals("")) {
            contactRepository.save(
                    new Contact.Builder(ContactType.GITHUB, form.getGithub())
                            .conference(conference)
                            .build());
        }
        if (form.getGooglePlus() != null && !form.getGooglePlus().equals("")) {
            contactRepository.save(
                    new Contact.Builder(ContactType.GOOGLEPLUS, form.getGooglePlus())
                            .conference(conference)
                            .build());
        }

        System.out.println(form.getAssets().size());

        final Conference finalConference = conference;
        form.getAssets().stream().filter(file -> imageValidator.validate(file.getOriginalFilename())).forEach(file -> {
            Assets assets = utils.fileSaveHelper(file, form.getHost(), "/img/");
            assetsRepository.save(assets.setConference(finalConference));
        });

        LOGGER.debug("Successfully created");
        return conference;
    }
}
