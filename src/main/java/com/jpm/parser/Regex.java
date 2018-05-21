package com.jpm.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  This class is to perform regex operations
 */
public class Regex {
    public static boolean isRegexMatch(String patternRegex, String line) {
        Pattern pattern = Pattern.compile(patternRegex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<String> getRegexTokens(String patternRegex, String line) {

        Matcher matcher = getRegexMatcher(patternRegex, line);
        int totalCount = matcher.groupCount();
        List<String> infoTokens = new ArrayList<String>();
        for (int i = 0; i <= totalCount; i++) {
            infoTokens.add(matcher.group(i));
        }

        return infoTokens;
    }

    private static Matcher getRegexMatcher(String patternRegex, String line) {
        Pattern pattern = Pattern.compile(patternRegex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher;
        } else {
            return null;
        }
    }
}
