package com.muzi.example.enums;

public enum DelFlag {
    Effect(false,"有效"),Lapse(true,"无效");
    private boolean code;
    private String name;
    DelFlag(boolean code, String name){
        this.code=code;
        this.name=name;
    }

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
