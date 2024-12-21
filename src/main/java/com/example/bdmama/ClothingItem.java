package com.example.bdmama;

import java.io.Serializable;

public class ClothingItem implements Serializable {
    private String id;
    private String name;
    private String description;

    private String image;

    public ClothingItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClothingItem(String id, String name, String description, String image)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setBrand(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
