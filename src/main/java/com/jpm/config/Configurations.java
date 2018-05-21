package com.jpm.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configurations {

    public static Properties map;

    private static InputStream input = null;

    public static void load(){
        Properties prop = new Properties();
        try (InputStream inputStream = input = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(input);
            map = prop;
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
