package com.epam.entity;

import java.util.Objects;

public class Organization extends Entity {

    private String name;
    private String description;
    private String logo; // ??????????????????
    private int languageID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return languageID == that.languageID &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                logo.equals(that.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, logo, languageID);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                ", languageID=" + languageID +
                '}';
    }
}
