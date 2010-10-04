package com.silvermindsoftware.stripes.integration.guice;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.inject.Injector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GuiceUtilsTest {
    private final List<String> classes = Lists.newArrayList("A", "B", "C");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSplitClasses() throws Exception {
        final String[] myStrings = GuiceUtils.splitClasses(Joiner.on(", ").join(classes));
        assertEquals("Not all classes parsed", classes.size(), myStrings.length);
        for (String myString : myStrings) {
            assertTrue("[" + myString + "] was unexpected", classes.contains(myString.trim()));
        }
    }

    @Test
    public void testCreateClass() throws Exception {
        final String myInstance = GuiceUtils.createClass(String.class.getName(), String.class);
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
        failWithParams(TimeUnit.class.getName(), TimeUnit.class);
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        failWithParams(
            GuiceUtils.class.getName(),
            GuiceUtils.class,
            IllegalAccessError.class,
            "Don't call constructor on util");
    }

    @Test
    public void testCantInstantiate() throws Exception {
        failWithParams(Injector.class.getName(), Injector.class);
    }

    private void failWithParams(String aClassName, Class<?> aTargetType) {
        failWithParams(aClassName, aTargetType, CreateClassException.class, "thrown during instantiation");
    }

    private void failWithParams(String aClassName,
                                Class<?> aTargetType,
                                Class<? extends Throwable> anExceptionType,
                                String anErrorMsg) {
        thrown.expect(anExceptionType);
        thrown.expectMessage(anErrorMsg);
        GuiceUtils.createClass(aClassName, aTargetType);
    }
}
