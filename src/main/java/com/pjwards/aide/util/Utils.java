package com.pjwards.aide.util;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.validators.ImageValidator;
import com.pjwards.aide.repository.AssetsRepository;
import com.pjwards.aide.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Component
@PropertySource("classpath:file.properties")
public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    @Autowired
    private ImageValidator imageValidator;

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.realPath}")
    private String realPath;

    @Value("${file.filePath}")
    private String filePath;

    @Value("${file.identicon.realPath}")
    private String identiconRealPath;

    /*
    Make random file name
     */
    public String fileNameHelper() {
        long currentTime = System.currentTimeMillis();
        return Long.toString(currentTime) + "_" + randomString(32);
    }

    /*
    File Remove helper
     */
    public void fileRemoveHelper(String realPath) {
        File file = new File(filePath + realPath);
        file.delete();
    }

    /*
    Directory Checker
     */
    public void checkDirectory(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }

    /*
    Generate random string by length
     */
    public String randomString(int length) {

        LOGGER.debug("Utils random string, length={}", length);

        if (length < 1) throw new IllegalArgumentException("length < 1: " + length);

        char[] symbols;
        char[] buf;

        StringBuilder tmp = new StringBuilder();
        Random random = new Random();

        for (char ch = '0'; ch <= '9'; ++ch) tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch) tmp.append(ch);
        symbols = tmp.toString().toCharArray();

        buf = new char[length];

        for (int idx = 0; idx < buf.length; ++idx) buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    /*
    Generate random hash by string
     */
    public String getRandomHash(String str) {

        LOGGER.debug("Utils random string, str={}", str);

        SecureRandom random = new SecureRandom();
        String randomString = new BigInteger(130, random).toString(32);

        LOGGER.debug("Utils random string, randomString={}", randomString);

        return new BCryptPasswordEncoder().encode(str + randomString);
    }

    public Assets profileSaveHelper(MultipartFile file, User user) {
        LOGGER.debug("Avatar save helper name={}, validated={}", file.getOriginalFilename(), imageValidator.validate(file.getOriginalFilename()));

        Assets assets = fileSaveHelper(file, user, identiconRealPath);

        if (assets != null) {
            Assets oldAsset = user.getAssets();
            fileRemoveHelper(oldAsset.getRealPath());
            user.setAssets(assets);
            userRepository.save(user);
            assetsRepository.delete(oldAsset);
            return assets;
        }

        return null;
    }

    public Assets fileSaveHelper(MultipartFile file, User user, String fileType) {

        String path = realPath + fileType + fileNameHelper();

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + path)));
            stream.write(bytes);
            stream.close();

            Assets assets = new Assets.Builder(file.getOriginalFilename(), path, (long) bytes.length, 0)
                    .user(user).build();

            assetsRepository.save(assets);

            return assets;
        } catch (Exception e) {
            return null;
        }
    }

    public Assets fileSaveHelperSponsor(MultipartFile file, Sponsor sponsor, String fileType) {

        String path = realPath + fileType + fileNameHelper();

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + path)));
            stream.write(bytes);
            stream.close();

            Assets assets = new Assets.Builder(file.getOriginalFilename(), path, (long) bytes.length, 0)
                    .sponsor(sponsor).build();

            assetsRepository.save(assets);

            return assets;
        } catch (Exception e) {
            return null;
        }
    }
}
