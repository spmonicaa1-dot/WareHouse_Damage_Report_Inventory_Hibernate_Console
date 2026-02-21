package com.kce.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;

    @Column(name = "UNIT_OF_MEASURE")
    private String unitOfMeasure;

    @Column(name = "UNIT_PRICE")
    private double unitPrice;

    @Column(name = "ON_HAND_QUANTITY")
    private double onHandQuantity;

    @Column(name = "TOTAL_DAMAGED_QUANTITY")
    private double totalDamagedQuantity;

    // 🔥 Default Constructor (Compulsory for Hibernate)
    public Item() {
    }

    // Getters & Setters

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getOnHandQuantity() {
        return onHandQuantity;
    }

    public void setOnHandQuantity(double onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }

    public double getTotalDamagedQuantity() {
        return totalDamagedQuantity;
    }

    public void setTotalDamagedQuantity(double totalDamagedQuantity) {
        this.totalDamagedQuantity = totalDamagedQuantity;
    }
}
