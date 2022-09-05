package com.example.gaurav.expensescalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;

public class FormExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_expense);

        Expense expense = (Expense) getIntent().getSerializableExtra("expense");
        if (expense != null) {
            ((EditText)findViewById(R.id.form_expense_amount)).setText(String.valueOf(expense.amount));
            ((EditText)findViewById(R.id.form_expense_label)).setText(expense.label);
            ((CheckBox)findViewById(R.id.form_expense_isRegular)).setChecked(expense.isRegular);
        } else {
            ((EditText)findViewById(R.id.form_expense_amount)).setText("0");
            ((EditText)findViewById(R.id.form_expense_label)).setText("");
            ((CheckBox)findViewById(R.id.form_expense_isRegular)).setChecked(true);
        }

        ((Button)findViewById(R.id.form_expense_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        ((Button)findViewById(R.id.form_expense_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int amount = Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.form_expense_amount)).getText()));
                    String label = String.valueOf(((EditText) findViewById(R.id.form_expense_label)).getText());
                    boolean isRegular = ((CheckBox) findViewById(R.id.form_expense_isRegular)).isChecked();

                    Intent intent = new Intent();
                    intent.putExtra("expense", new Expense(amount, label, isRegular));
                    intent.putExtra("position", getIntent().getIntExtra("position", -1));
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
        });
    }
}
