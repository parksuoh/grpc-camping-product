package com.example.product.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {

    @Column(name = "text")
    private String text;

    private Name() {
    }

    public Name(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(text, name.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}