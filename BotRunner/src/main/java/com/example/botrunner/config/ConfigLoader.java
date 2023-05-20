package com.example.botrunner.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {

    private Properties botProp = null;
    private Properties appProp = null;
    private Logger logger = Logger.getLogger(ConfigLoader.class.getName());
    private static ConfigLoader instance;
    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private ConfigLoader(){
    }

    private Properties LoadConfig(String path){
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(path));
        }
        catch (IOException e) {
            logger.log(Level.SEVERE,"The file: " + path + " does not exists.\nPlease add it in the resources folder.");
            throw new RuntimeException(e);
        }
        return prop;
    }


    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public Properties getBotProp() {
        if (botProp == null)
        {
            botProp = LoadConfig(rootPath + "botAccount.properties");
        }
        return botProp;
    }

    public Properties getAppProp() {
        if(appProp == null)
        {
            appProp = LoadConfig(rootPath + "app.properties");

        }
        return appProp;
    }
}
