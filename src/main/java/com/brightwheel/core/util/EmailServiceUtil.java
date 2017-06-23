package com.brightwheel.core.util;

import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;

/**
 * Created by satya on 6/19/17.
 */
public class EmailServiceUtil {

    private static String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
    private static String DOMAIN = ATOM + "+(\\." + ATOM + "+)*";

    private static java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
            "^" + ATOM + "+(\\." + ATOM + "+)*@"
                    + DOMAIN
                    + "$",
            java.util.regex.Pattern.CASE_INSENSITIVE
    );



    public static boolean isValidEmail(String value) {
        Matcher m = pattern.matcher( value );
        if (!m.matches()) {
            return false;
        }
        // very loose email checking (even this simple check will fail certain valid (but unlikely) addresses)
        // rely on email sending for real email validation
        String[] nameDomain = value.split("\\@");
        if (nameDomain.length == 1) {
            // regex should have caught this case
            return false;
        } else {
            String[] domainTld = nameDomain[1].split("\\.");
            return domainTld.length != 1;
        }
    }
}
