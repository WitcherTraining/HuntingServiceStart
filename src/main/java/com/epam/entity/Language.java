package com.epam.entity;

import java.util.Objects;

public class Language extends Entity {

    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language1 = (Language) o;
        return language.equals(language1.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language);
    }
}
