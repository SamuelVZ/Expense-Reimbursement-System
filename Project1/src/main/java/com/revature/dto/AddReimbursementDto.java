package com.revature.dto;

import java.io.InputStream;
import java.util.Objects;

public class AddReimbursementDto {
    private int amount;
    private String description;
    private int typeId;
    private InputStream image;

    public AddReimbursementDto() {
    }

    public AddReimbursementDto(int amount, String description, int typeId, InputStream image) {
        this.amount = amount;
        this.description = description;
        this.typeId = typeId;
        this.image = image;
    }

    @Override
    public String toString() {
        return "AddReimbursementDto{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", typeId=" + typeId +
                ", image=" + image +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddReimbursementDto that = (AddReimbursementDto) o;
        return amount == that.amount && typeId == that.typeId && Objects.equals(description, that.description) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, typeId, image);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
