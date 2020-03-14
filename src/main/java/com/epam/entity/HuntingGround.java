package com.epam.entity;

import java.util.Objects;

public class HuntingGround extends Entity {

    private String name;
    private String description;
    private String dailyPrice;
    private String seasonPrice;
    private int organizationID;
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

    public String getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(String dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getSeasonPrice() {
        return seasonPrice;
    }

    public void setSeasonPrice(String seasonPrice) {
        this.seasonPrice = seasonPrice;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
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
        HuntingGround that = (HuntingGround) o;
        return organizationID == that.organizationID &&
                languageID == that.languageID &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                dailyPrice.equals(that.dailyPrice) &&
                seasonPrice.equals(that.seasonPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, dailyPrice, seasonPrice, organizationID, languageID);
    }

    @Override
    public String toString() {
        return "HuntingGround{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dailyPrice='" + dailyPrice + '\'' +
                ", seasonPrice='" + seasonPrice + '\'' +
                ", organizationID=" + organizationID +
                ", languageID=" + languageID +
                '}';
    }
}
