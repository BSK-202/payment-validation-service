package com.hulkhiretech.payment.constant;

import com.hulkhiretech.payment.service.impl.ValidatorCheck1;
import com.hulkhiretech.payment.service.impl.ValidatorCheck2;
import com.hulkhiretech.payment.service.interfaces.BusinessValidator;

public enum ValidatorRuleEnum {
	CHECK1("CHECK1", ValidatorCheck1.class),
	CHECK2("CHECK2", ValidatorCheck2.class);

	private final String validatorName;
	private final Class<? extends BusinessValidator> validatorClass;

	ValidatorRuleEnum(String validatorName,
			Class<? extends BusinessValidator> validatorClass) {
		this.validatorName = validatorName;
		this.validatorClass = validatorClass;
	}

	public String getValidatorName() {
		return validatorName;
	}

	public Class<? extends BusinessValidator> getValidatorClass() {
		return validatorClass;
	}

	public static Class<? extends BusinessValidator> getValidatorClass(
			String validatorName) {
		if (validatorName != null) {
			for (ValidatorRuleEnum validatorRule : values()) {
				if (validatorRule.validatorName.equals(validatorName.trim())) {
					return validatorRule.validatorClass;
				}
			}
		}

		throw new RuntimeException("No validator class found for name: " + validatorName);
	}
}
