package com.example.product.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class SecondOptionName {

    @Column(name = "second_name")
    private String secondName;

    private SecondOptionName() {
    }

    public SecondOptionName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondOptionName that = (SecondOptionName) o;
        return Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondName);
    }
}
