package org.uj;

import org.uj.exceptions.UserInputException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Utils {
    public static void validateNotBlank(String field, String fieldName) {
        if (isBlank(field)) throw UserInputException.formatted("[%s] cannot be blank", fieldName);
    }
}
