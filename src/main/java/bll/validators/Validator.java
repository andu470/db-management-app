package bll.validators;

/**
 * @author Andrei Rotaru
 * This class represents an interface for validators
 */

public interface Validator<T> {
    public void validate(T t);
}
