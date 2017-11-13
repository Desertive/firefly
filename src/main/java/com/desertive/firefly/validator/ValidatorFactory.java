package com.desertive.firefly.validator;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public class ValidatorFactory {

	private Validator validator;

	public ValidatorFactory() {
		javax.validation.ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public <T> Boolean isValidOrThrow(T data) {
		try {
			return isEmptyOrThrow(validator.validate(data));
		} catch (ValidationException e) {
			throw new ValidationException(validationErrors(data));
		}
	}

	public <T> String validationErrors(T data) {
		return validator.validate(data)
				.stream()
				.map((item) -> item.getMessage())
				.collect(Collectors.joining(", "));
	}

	private <T> Boolean isEmptyOrThrow(Set<ConstraintViolation<T>> data) throws ValidationException {
		if (data.size() > 0) {
			throw new ValidationException();
		}
		return true;
	}
}
