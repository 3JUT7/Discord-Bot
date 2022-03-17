package util;


import de.sematre.dsbmobile.DSBMobile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class Config{

    public static Properties prop = new Properties();


    public static DSBMobile dsbMobile;

    public static String prefix;
    public static String ownerId;
    public static String token;
    public static String dsbMobileUsername;
    public static String dsbMobilePassword;



    public static void loadConfig(){

        String fileName = "L11ON.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ignored) {
        }

        token = prop.getProperty("L11ON.token");
        ownerId = prop.getProperty("L11ON.ownerId");
        prefix = prop.getProperty("L11ON.prefix");

        dsbMobileUsername = prop.getProperty("DSBmobile.username");
        dsbMobilePassword = prop.getProperty("DSBmobile.password");

        dsbMobile  = new DSBMobile(dsbMobileUsername, dsbMobilePassword);

    }


}
