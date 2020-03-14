package com.epam.entity;

import java.util.Date;
import java.util.Objects;

public class Animal extends Entity {

    private String name;
    private Date termBegin;
    private Date termEnd;
    private int languageID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTermBegin() {
        return termBegin;
    }

    public void setTermBegin(Date termBegin) {
        this.termBegin = termBegin;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
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
        Animal animal = (Animal) o;
        return languageID == animal.languageID &&
                name.equals(animal.name) &&
                termBegin.equals(animal.termBegin) &&
                termEnd.equals(animal.termEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, termBegin, termEnd, languageID);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", termBegin=" + termBegin +
                ", termEnd=" + termEnd +
                ", languageID=" + languageID +
                '}';
    }
}
