package bll.validators;

import model.Client;

import java.util.regex.Pattern;

/**
 * @author Andrei Rotaru
 * This class represents the validator for the phone of the client
 */

public class PhoneValidator implements Validator<Client>{
    private static final String PHONE_PATTERN = "[0-9]+";

    public void validate(Client t) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if(t.getPhone().length()!=10 || !pattern.matcher(t.getPhone()).matches()){
            throw new IllegalArgumentException("Phone is not a valid phone!");
        }
    }
}
