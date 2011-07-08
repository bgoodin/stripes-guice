package com.silvermindsoftware.sg.stripes.integration.guice;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.silvermindsoftware.sg.exception.CreateClassException;
import com.silvermindsoftware.sg.stripes.guice.InjectorHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.silvermindsoftware.sg.utils.Helpers.createClass;
import static com.silvermindsoftware.sg.utils.Helpers.splitClasses;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GuiceUtilsTest {
    private final List<String> CLASSES = Lists.newArrayList("A", "B", "C");

    @NotNull @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSplitClasses() throws Exception {
        final String[] myStrings = splitClasses(Joiner.on(", ").join(CLASSES));
        assertEquals("Not all classes parsed", CLASSES.size(), myStrings.length);
        for (String myString : myStrings) {
            assertTrue("[" + myString + "] was unexpected", CLASSES.contains(myString.trim()));
        }
    }

    @Test
    public void testCreateClass() throws Exception {
        final String myInstance = createClass(String.class.getName(), String.class);
        assertNotNull(myInstance);
    }

    @Test
    public void testClassCastException() throws Exception {
        failWithParams(String.class.getName(), Integer.class);
    }

    @Test
    public void testNoSuchClass() throws Exception {
        failWithParams("foobar", String.class);
    }

    @Test
    public void testIllegalAccessException() throws Exception {
        failWithParams(InjectorHelper.class.getName(), InjectorHelper.class);
    }

    @Test
    public void testCantInstantiate() throws Exception {
        failWithParams(Injector.class.getName(), Injector.class);
    }

    private void failWithParams(@NotNull String aClassName, @NotNull Class<?> aTargetType) {
        failWithParams(aClassName, aTargetType, CreateClassException.class, "Exception when creating class");
    }

    private void failWithParams(@NotNull String aClassName,
                                @NotNull Class<?> aTargetType,
                                @NotNull Class<? extends Throwable> anExceptionType,
                                @NotNull String anErrorMsg) {
        thrown.expect(anExceptionType);
        thrown.expectMessage(anErrorMsg);
        createClass(aClassName, aTargetType);
    }
}
