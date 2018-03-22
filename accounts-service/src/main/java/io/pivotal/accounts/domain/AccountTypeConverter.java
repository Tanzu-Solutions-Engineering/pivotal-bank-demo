package io.pivotal.accounts.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts between AccountType enum to databse persistence value.
 * 
 * @author David Ferreira Pinto
 *
 */
@Converter
public class AccountTypeConverter implements
		AttributeConverter<AccountType, String> {

	@Override
	public String convertToDatabaseColumn(AccountType type) {
		switch (type) {
		case CURRENT:
			return "C";
		case SAVINGS:
			return "S";
		default:
			throw new IllegalArgumentException("Unknown" + type);
		}
	}

	@Override
	public AccountType convertToEntityAttribute(String dbData) {
		switch (dbData) {
		case "C":
			return AccountType.CURRENT;
		case "S":
			return AccountType.SAVINGS;
		default:
			throw new IllegalArgumentException("Unknown" + dbData);
		}
	}

}
