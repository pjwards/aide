package com.pjwards.aide.service.Assets;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.exception.AssetsNotFoundException;
import com.pjwards.aide.repository.AssetsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetsServiceImpl implements AssetsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsServiceImpl.class);

    private AssetsRepository assetsRepository;

    @Autowired
    public AssetsServiceImpl(AssetsRepository assetsRepository){
        this.assetsRepository = assetsRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Assets> findAll() {
        LOGGER.debug("Finding all assets");

        List<Assets> assets = assetsRepository.findAll();
        LOGGER.debug("Found {} assets", assets.size());

        return assets;
    }

    @Transactional
    @Override
    public Assets add(Assets added) {
        LOGGER.debug("Create assets with Info: {}", added);

        added = assetsRepository.save(added);
        LOGGER.debug("Successfully created");

        return added;
    }

    @Transactional(readOnly = true, rollbackFor = {AssetsNotFoundException.class})
    @Override
    public Assets findById(Long id) throws AssetsNotFoundException {
        LOGGER.debug("Find assets by Id: {}", id);

        Assets found = assetsRepository.findOne(id);

        if(found == null){
            LOGGER.debug("Not Found Asset by Id: {}", id);
            throw new AssetsNotFoundException("Not Found Assets by Id: " + id);
        }

        LOGGER.debug("Find the assets: {}", found);

        return found;
    }

    @Transactional(rollbackFor = {AssetsNotFoundException.class})
    @Override
    public Assets update(Assets updated) throws AssetsNotFoundException {
        LOGGER.debug("Update the assets with Info: {}", updated);

        Assets found = findById(updated.getId());
        found.update(updated);
        assetsRepository.save(found);

        LOGGER.debug("Successfully updated");

        return found;
    }

    @Transactional(rollbackFor = {AssetsNotFoundException.class})
    @Override
    public Assets deleteById(Long id) throws AssetsNotFoundException {
        LOGGER.debug("Delete the assets by Id: {}", id);

        Assets deleted = findById(id);
        assetsRepository.delete(deleted);

        LOGGER.debug("Successfully deleted Info: {}", deleted);

        return deleted;
    }
}
