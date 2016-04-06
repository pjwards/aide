package com.pjwards.aide.service.sponsor;

import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.exception.SponsorNotFoundException;
import com.pjwards.aide.repository.SponsorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SponsorServiceImpl implements SponsorService{
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorServiceImpl.class);

    private SponsorRepository sponsorRepository;

    @Autowired
    public SponsorServiceImpl(SponsorRepository sponsorRepository){
        this.sponsorRepository = sponsorRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Sponsor> findAll() {
        LOGGER.debug("Finding all sponsors");

        List<Sponsor> sponsors = sponsorRepository.findAll();
        LOGGER.debug("Found {} sponsors", sponsors.size());

        return sponsors;
    }

    @Transactional
    @Override
    public Sponsor add(Sponsor added) {
        LOGGER.debug("Create a sponsor with Info: {}", added);

        added = sponsorRepository.save(added);
        LOGGER.debug("Successfully created");

        return added;
    }

    @Transactional(readOnly = true, rollbackFor = {SponsorNotFoundException.class})
    @Override
    public Sponsor findById(Long id) throws SponsorNotFoundException {
        LOGGER.debug("Find a sponsor by Id: {}", id);

        Sponsor found = sponsorRepository.findOne(id);

        if(found == null){
            LOGGER.debug("Not Found Sponsor by Id: {}", id);
            throw new SponsorNotFoundException("Not Found Sponsor by Id: " + id);
        }

        LOGGER.debug("Find the sponsor: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {SponsorNotFoundException.class})
    @Override
    public Sponsor update(Sponsor updated) throws SponsorNotFoundException {
        LOGGER.debug("Update the sponsor with Info: {}", updated);

        Sponsor found = findById(updated.getId());
        found.update(updated);
        sponsorRepository.save(found);

        LOGGER.debug("Successfully updated");

        return found;
    }

    @Transactional(rollbackFor = {SponsorNotFoundException.class})
    @Override
    public Sponsor deleteById(Long id) throws SponsorNotFoundException {
        LOGGER.debug("Delete the sponsor by Id: {}", id);

        Sponsor deleted = findById(id);
        sponsorRepository.delete(deleted);

        LOGGER.debug("Successfully deleted Info: {}", deleted);

        return deleted;
    }
}
