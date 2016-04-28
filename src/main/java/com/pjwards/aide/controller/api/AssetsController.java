package com.pjwards.aide.controller.api;


import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.exception.AssetsNotFoundException;
import com.pjwards.aide.service.assets.AssetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
public class AssetsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsController.class);

    @Autowired
    private AssetsService assetsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Assets> getAll() {
        LOGGER.debug("[API] Finding all assets.");
        return assetsService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@Valid @RequestBody Assets assets) {
        LOGGER.debug("[API] Creating new assets with information: {}", assets);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Assets created successfully");
        response.put("assets", assetsService.add(assets));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Assets getDetails(@PathVariable("id") Long id) throws AssetsNotFoundException {
        LOGGER.debug("[API] Finding assets with id: {}", id);
        return assetsService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id,
                                      @Valid @RequestBody Assets assets) throws AssetsNotFoundException {
        LOGGER.debug("[API] Updating assets with information: {}", assets);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Assets updated successfully");
        response.put("assets", assetsService.update(assets));
        return response;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) throws AssetsNotFoundException {
        LOGGER.debug("[API] Deleting assets with id: {}", id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Assets deleted successfully");
        response.put("assets", assetsService.deleteById(id));
        return response;
    }
}
