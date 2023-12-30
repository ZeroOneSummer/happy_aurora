package com.aurora.happy.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Log {

    private long time;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private String browser;
}
