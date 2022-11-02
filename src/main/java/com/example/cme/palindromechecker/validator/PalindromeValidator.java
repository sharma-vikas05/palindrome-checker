package com.example.cme.palindromechecker.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PalindromeValidator {
    private static final Logger LOG = LoggerFactory.getLogger("PalindromeValidator.class");

    public boolean validate(String value){
        if(null!= value) {

            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                LOG.error("Value contains Numbers in it {}", value);
                return Boolean.FALSE;
            } else if (value.contains(" ")) {
                LOG.error("Value contains Spaces in it {}", value);
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
