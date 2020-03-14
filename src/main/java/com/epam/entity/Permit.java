package com.epam.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Permit extends Entity {

    private Date orderDate; // or Calendar????
    private int countOrderedAnimals;
    private boolean permitType;
    private int userID;
    private int animalID;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getCountOrderedAnimals() {
        return countOrderedAnimals;
    }

    public void setCountOrderedAnimals(int countOrderedAnimals) {
        this.countOrderedAnimals = countOrderedAnimals;
    }

    public boolean isPermitType() {
        return permitType;
    }

    public void setPermitType(boolean permitType) {
        this.permitType = permitType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permit permit = (Permit) o;
        return countOrderedAnimals == permit.countOrderedAnimals &&
                permitType == permit.permitType &&
                userID == permit.userID &&
                animalID == permit.animalID &&
                orderDate.equals(permit.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, countOrderedAnimals, permitType, userID, animalID);
    }

    @Override
    public String toString() {
        return "Permit{" +
                "orderDate=" + orderDate +
                ", countOrderedAnimals=" + countOrderedAnimals +
                ", permitType=" + permitType +
                ", userID=" + userID +
                ", animalID=" + animalID +
                '}';
    }
}
