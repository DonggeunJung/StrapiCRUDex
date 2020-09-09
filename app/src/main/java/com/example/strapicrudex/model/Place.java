package com.example.strapicrudex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("id")
    @Expose
    protected long id;
    @SerializedName("x")
    @Expose
    protected float x;
    @SerializedName("y")
    @Expose
    protected float y;

    public Place(float x, float y) {
        this(-1, x, y);
    }

    public Place(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
