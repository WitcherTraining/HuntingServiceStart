package com.epam.entity;

import java.util.Objects;

public class Animal extends Entity {

    private String name;
    private String termBegin;
    private String termEnd;
    private int languageID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermBegin() {
        return termBegin;
    }

    public void setTermBegin(String termBegin) {
        this.termBegin = termBegin;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
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
