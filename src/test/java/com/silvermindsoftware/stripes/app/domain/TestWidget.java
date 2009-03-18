package com.silvermindsoftware.stripes.app.domain;

public class TestWidget {

    public static final String DEFAULT_WIDGET_NAME = "Default Widget Name";

    private String widgetName;

    public TestWidget() {
        this.widgetName = DEFAULT_WIDGET_NAME;
    }

    public TestWidget(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getWidgetName() {
        return widgetName;
    }
}
