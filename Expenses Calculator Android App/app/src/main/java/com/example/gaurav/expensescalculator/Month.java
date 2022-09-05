package com.example.gaurav.expensescalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Month implements Serializable {
    public int month;
    public int year;
    public int income;
    public List<Expense> expenses;

    public Month(int month, int year, int income, List<Expense> expenses) {
        this.month = month;
        this.year = year;
        this.income = income;
        this.expenses = expenses;
    }
}
