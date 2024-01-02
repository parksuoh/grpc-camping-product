package com.example.product.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class FirstOptionName {

    @Column(name = "first_name")
    private String firstName;

    private FirstOptionName() {
    }

    public FirstOptionName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstOptionName that = (FirstOptionName) o;
        return Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }
}