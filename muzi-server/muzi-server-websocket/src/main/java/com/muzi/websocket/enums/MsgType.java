package com.muzi.websocket.enums;

public enum MsgType {
    MSG_COUNT_INIT("0","c初始化消息数量"),MSG_COUNT_CHANGE("1","消息数量变化"),MSG_TEXT("2","文本");
    private String code;
    private String name;

    MsgType(String code, String name) {
        this.code=code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
