package com.silvermindsoftware.sg.exception;

import org.jetbrains.annotations.NotNull;

import static java.text.MessageFormat.format;

public class CreateClassException extends RuntimeException {
    private static final long serialVersionUID = -1863429800590983472L;

    public CreateClassException(@NotNull String aMessage, @NotNull Throwable aCause) {
        super(aMessage, aCause);
    }

    @NotNull
    public static CreateClassException createException(@NotNull Exception anException,
                                                       @NotNull String aClassName) {
        return new CreateClassException(format("Exception when creating class {0}", aClassName), anException);
    }
}
