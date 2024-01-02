package com.example.product.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Description {

    @Column(name = "description")
    private String longText;

    private Description() {
    }

    public Description(String longText) {
        this.longText = longText;
    }

    @Override
    public String toString() {
        return longText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(longText, that.longText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longText);
    }
}