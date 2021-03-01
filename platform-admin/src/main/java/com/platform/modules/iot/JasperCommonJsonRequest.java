package com.platform.modules.iot;

import com.platform.modules.iot.request.CommonJsonRequest;

public class JasperCommonJsonRequest extends CommonJsonRequest
{
    private String username;
    
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
