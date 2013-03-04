package com.silvermindsoftware.sg.stripes.app.extensions;

import com.google.inject.Inject;
import com.silvermindsoftware.sg.stripes.app.service.TestService;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class TestTypeConverter implements TypeConverter<List> {

    private TestService testService;
    private Locale locale;

    @Inject
    public TestTypeConverter(TestService testService) {
        this.testService = testService;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public List convert(String input, Class<? extends List> targetType, Collection<ValidationError> errors) {
        List list = null;
        if (input != null && input.length() > 0) {
            list = testService.getList(Integer.valueOf(input));
        }
        return list;
    }

}
