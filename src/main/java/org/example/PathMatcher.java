package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatcher {

    private Map<String, String> adress = new HashMap<>();

    public String getAddres() {
        return addres;
    }

    String addres;

    public Map<String, String> getAdress() {
        return adress;
    }

    public boolean match(String originalPath, String pathPattern) {


        String key = null;
        String value = null;


        if (originalPath.substring(1).split("/").length != pathPattern.split("/").length) {
            System.out.println(false);
        } else {

            Pattern keys = Pattern.compile("((\\{(.+)}))");
            Matcher matchKey = keys.matcher(pathPattern);
            while (matchKey.find()) {
                key = matchKey.group(3);
            }

            Pattern values = Pattern.compile("(\\w++)(.)((\\w+))");
            Matcher matchValue = values.matcher(originalPath);
            while (matchValue.find()) {
                value = matchValue.group(3);
            }
            adress.put(key, value);

            return true;
        }

        return false;
    }


}
