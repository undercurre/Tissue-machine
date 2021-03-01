package com.platform.common.exception;



public class ProtocolException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     */
    public ProtocolException(String message)
    {
        super(message);
    }
 
}