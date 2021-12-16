package com.example.uni.entities;

import com.example.uni.exceptions.InvalidDataException;

public abstract class Person {
    private String firstName;
    private String lastName;

    protected Person() {
    }

    protected Person(String firstName, String lastName) throws InvalidDataException {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new InvalidDataException("Name cannot be null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
