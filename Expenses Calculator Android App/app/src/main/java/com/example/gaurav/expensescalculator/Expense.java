package com.example.gaurav.expensescalculator;

import java.io.Serializable;

public class Expense implements Serializable {
    public int amount;
    public String label;
    public boolean isRegular;

    public Expense(int amount, String label, boolean isRegular) {
        this.amount = amount;
        this.label = label;
        this.isRegular = isRegular;
    }
}
