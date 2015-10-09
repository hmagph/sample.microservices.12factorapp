package net.wasdev.twelvefactorapp;

import javax.json.JsonObject;

public class myObject {

    private JsonObject myData;

    public myObject(JsonObject data) {
        this.myData = data;
    }

    public JsonObject getData() {
        return this.myData;
    }

}
