package com.app.homeycam.Rawheaders.Settings;

public class Model {
    public String title;
    public Integer type;

    public Model(String name, Integer type) {
        this.title = name;
        this.type = type;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
