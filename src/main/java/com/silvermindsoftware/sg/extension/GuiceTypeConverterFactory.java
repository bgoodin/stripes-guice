package com.silvermindsoftware.sg.extension;

import com.google.inject.Injector;
import com.silvermindsoftware.sg.utils.Helpers;
import net.sourceforge.stripes.validation.DefaultTypeConverterFactory;
import net.sourceforge.stripes.validation.TypeConverter;

import java.util.Locale;

public class GuiceTypeConverterFactory extends DefaultTypeConverterFactory {

    @Override
    public TypeConverter getInstance(Class<? extends TypeConverter> clazz, Locale locale) throws Exception {
        Injector injector = Helpers.getInjector(getConfiguration().getServletContext());
        TypeConverter typeConverter = injector.getInstance(clazz);
        typeConverter.setLocale(locale);
        return typeConverter;
    }
}
