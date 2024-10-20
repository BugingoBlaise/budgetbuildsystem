package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Supplier;


public class MaterialDto {
    private String materialName;
    private String supplierDetails;
    private float price;
    private String imagePath;

    private Supplier supplier;

    public MaterialDto() {
    }

    public MaterialDto(String materialName, String supplierDetails, float price, String imagePath, Supplier supplier) {
        this.materialName = materialName;
        this.supplierDetails = supplierDetails;
        this.price = price;
        this.imagePath = imagePath;
        this.supplier = supplier;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSupplierDetails() {
        return supplierDetails;
    }

    public void setSupplierDetails(String supplierDetails) {
        this.supplierDetails = supplierDetails;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
