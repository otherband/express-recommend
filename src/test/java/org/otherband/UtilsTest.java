package org.otherband;

import org.junit.jupiter.api.Test;
import org.otherband.exceptions.UserInputException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.otherband.TestUtils.assertThrowsWithMessage;

class UtilsTest {
	@Test
	void throwsIfIsBlank() {
		assertThrowsWithMessage(UserInputException.class, () -> Utils.validateNotBlank("", "field1"), "[field1] cannot be blank");
		assertThrowsWithMessage(UserInputException.class, () -> Utils.validateNotBlank("\t", "field2"), "[field2] cannot be blank");
	}

	@Test
	void doesNotThrowIfNotBlank() {
		assertDoesNotThrow(() -> Utils.validateNotBlank("NOT BLANK", "ignored"));
	}
}