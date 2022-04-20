package com.revature.model;

import java.io.InputStream;
import java.util.Objects;

public class Reimbursement {

    private int id;
    private int amount;
    private String dateSubmitted;
    private String dateResolved;
    private String description;


    private User employee;
    private User manager;

    private String statusName;
    private String typeName;


    public Reimbursement() {
    }

    public Reimbursement(int id, int amount, String dateSubmitted, String dateResolved, String description, User employee, User manager, String statusName, String typeName) {
        this.id = id;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.dateResolved = dateResolved;
        this.description = description;
        this.employee = employee;
        this.manager = manager;
        this.statusName = statusName;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateSubmitted='" + dateSubmitted + '\'' +
                ", dateResolved='" + dateResolved + '\'' +
                ", description='" + description + '\'' +
                ", employee=" + employee +
                ", manager=" + manager +
                ", statusName='" + statusName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return id == that.id && amount == that.amount && Objects.equals(dateSubmitted, that.dateSubmitted) && Objects.equals(dateResolved, that.dateResolved) && Objects.equals(description, that.description) && Objects.equals(employee, that.employee) && Objects.equals(manager, that.manager) && Objects.equals(statusName, that.statusName) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, dateSubmitted, dateResolved, description, employee, manager, statusName, typeName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
