package com.cs.smoothie.model;

import jakarta.persistence.*;

@Entity
@Table(name = "smoothie")
public class Smoothie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "nutrition")
    private String nutrition;

    @Column(name = "enabled")
    private boolean enabled;

    public Smoothie() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean isPublished) {
        this.enabled = isPublished;
    }

    @Override
    public String toString() {
        return "Smoothie[id=" + id + ", name=" + name + "]";
    }
}
