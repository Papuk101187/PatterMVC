package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatcherNew {


    private Map<String, String> params = new HashMap<>();
    String key=null;

    public String getKey() {
        return key;
    }

    public Map<String, String> getAdress() {
        return params;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean match(String originalPath, String pathPattern) {


        String[] original = originalPath.replaceAll("^\\/|\\/$", "").split("/");
        String[] pathPat = pathPattern.replaceAll("^\\/|\\/$", "").split("/");

        if (original.length != pathPat.length) {
            return false;
        }

        for (int i = 0; i < original.length; i++) {

            String originPart = original[i];

            String patternPart = pathPat[i];

            String parametrName = getParametrName(patternPart);

            if (parametrName != null) {
                key=parametrName;
                params.put(parametrName, originPart);
                System.out.println("params " + params);
            } else if (!patternPart.equals(originPart)) {
                return false;
            }
        }

        return true;
    }

    private final static Pattern is_PARAM_Pattern = Pattern.compile("^\\{(.+)\\}$");

    private String getParametrName(String pathMatcher) {

        Matcher matcher = is_PARAM_Pattern.matcher(pathMatcher);

        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);

    }

}
