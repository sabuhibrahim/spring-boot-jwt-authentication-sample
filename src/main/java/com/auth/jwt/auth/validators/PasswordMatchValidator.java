package com.auth.jwt.auth.validators;

import com.auth.jwt.auth.forms.RegisterForm;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegisterForm> {
	private String message;

	public void initialize(PasswordMatch arg0) {
		this.message = arg0.message();
	}

	@Override
	public boolean isValid(RegisterForm registerForm, ConstraintValidatorContext context) {
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
