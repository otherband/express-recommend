package org.uj;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUtils {
    public static <T extends Throwable> void assertThrowsWithMessage(Class<T> throwableClass,
                                                                     Executable executable,
                                                                     String message) {
        T throwable = assertThrows(throwableClass, executable);
        assertEquals(message, throwable.getMessage());
    }
}
