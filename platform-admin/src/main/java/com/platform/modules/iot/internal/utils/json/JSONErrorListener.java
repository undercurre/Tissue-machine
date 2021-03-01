package com.platform.modules.iot.internal.utils.json;

public interface JSONErrorListener {
    void start(String text);
    void error(String message, int column);
    void end();
}
