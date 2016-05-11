package com.pjwards.aide.controller.assets;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.validators.ImageValidator;
import com.pjwards.aide.service.assets.AssetsService;
import com.pjwards.aide.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/assets")
public class AssetsBasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsBasicController.class);

    @Autowired
    private ImageValidator imageValidator;

    @Autowired
    private Utils utils;

    @Autowired
    private AssetsService assetsService;

    @RequestMapping(value = "/upload/images", method = RequestMethod.POST)
    public Map<String, Object> upload(@ModelAttribute("currentUser")CurrentUser currentUser,
                                                  @RequestParam(value = "file") MultipartFile file) {
        LOGGER.debug("Upload images.");
        User user = currentUser.getUser();

        Map<String, Object> response = new LinkedHashMap<>();

        LOGGER.debug("File name={}, validated={}", file.getOriginalFilename(), imageValidator.validate(file.getOriginalFilename()));
        if(!imageValidator.validate(file.getOriginalFilename())){
            response.put("message", "The image upload was failed");
        } else {
            Assets assets = utils.fileSaveHelper(file, user, "/img/");

            if(assets != null){
                response.put("message", "The image uploaded successfully");
                response.put("assets", assetsService.add(assets));
            }
        }
        return response;
    }
}
