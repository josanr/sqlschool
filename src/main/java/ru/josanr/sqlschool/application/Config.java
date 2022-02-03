package ru.josanr.sqlschool.application;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class Config {

    private Properties props;

    public void loadProperties(String path) {
        this.props = new Properties();
        try (var input = Config.class.getResourceAsStream(path)) {

            props.load(input);
            var keys = props.stringPropertyNames();
            for (String key : keys) {
                props.setProperty(key, resolveEnvVars(props.getProperty(key)));
            }

        } catch (IOException e) {
            System.out.println("Could not read properties file: " + path);
            e.printStackTrace();
        }

    }

    public String getConfig(String key) {
        return props.getProperty(key);
    }


    private String resolveEnvVars(String input) {
        if (null == input) {
            return null;
        }

        var pattern = Pattern.compile("\\$\\{(\\w+)}|\\$(\\w+)");
        var matcher = pattern.matcher(input);
        var string = new StringBuilder();
        while (matcher.find()) {
            String envVarName = null == matcher.group(1) ? matcher.group(2) : matcher.group(1);
            String envVarValue = System.getenv(envVarName);
            matcher.appendReplacement(string, null == envVarValue ? "" : envVarValue);
        }
        matcher.appendTail(string);
        return string.toString();
    }
}
