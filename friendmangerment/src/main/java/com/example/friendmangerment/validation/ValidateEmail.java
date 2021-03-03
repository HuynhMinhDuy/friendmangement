package com.example.friendmangerment.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateEmail {
    public boolean validaEmail (String email) {
        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher = regexPattern.matcher(email);
        return regMatcher.matches() ? true : false;
    }
}
