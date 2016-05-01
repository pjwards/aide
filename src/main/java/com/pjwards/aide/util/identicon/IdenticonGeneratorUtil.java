package com.pjwards.aide.util.identicon;


import com.pjwards.aide.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:file.properties")
public class IdenticonGeneratorUtil {
    @Autowired
    private Utils utils;

    @Value("${file.identicon.filePath}")
    private String filePath;

    @Value("${file.identicon.realPath}")
    private String path;

    public Map<String, String> generator(String email) throws IOException {

        Map<String, String> map = new HashMap<>();

        String realPath = path + utils.fileNameHelper();
        String fileName = email + ".png";

        HashGeneratorInterface hashGenerator = new MessageDigestHashGenerator("MD5");

        BufferedImage identicon = IdenticonGenerator.generate(email, hashGenerator);

        //save identicon to file
        ImageIO.write(identicon, "PNG", new File(filePath + realPath));


        map.put("realPath", realPath);
        map.put("fileName", fileName);
        return map;
    }
}
