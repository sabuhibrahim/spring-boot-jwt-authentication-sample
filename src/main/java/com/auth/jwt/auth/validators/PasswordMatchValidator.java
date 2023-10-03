package com.auth.jwt.auth.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, PasswordMatchForm> {
	private String message;

	public void initialize(PasswordMatch arg0) {
		this.message = arg0.message();
	}

	@Override
	public boolean isValid(PasswordMatchForm registerForm, ConstraintValidatorContext context) {
		if (registerForm.getConfirmPassword() == null ||
				!registerForm.getConfirmPassword().equals(registerForm.getPassword())) {

			context.disableDefaultConstraintViolation();
			context
					.buildConstraintViolationWithTemplate(message)
					.addPropertyNode("confirmPassword")
					.addConstraintViolation();

			return false;
		}
		;
		return true;
	}

}
