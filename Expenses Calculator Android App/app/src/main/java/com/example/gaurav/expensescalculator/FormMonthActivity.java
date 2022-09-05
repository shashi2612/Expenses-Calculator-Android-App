package com.example.gaurav.expensescalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

public class FormMonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_month);

        Month month = (Month) getIntent().getSerializableExtra("month");
        if (month != null) {
            ((EditText)findViewById(R.id.form_month_month)).setText(String.valueOf(month.month));
            ((EditText)findViewById(R.id.form_month_year)).setText(String.valueOf(month.year));
            ((EditText)findViewById(R.id.form_month_income)).setText(String.valueOf(month.income));
        } else {
            ((EditText)findViewById(R.id.form_month_month)).setText(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
            ((EditText)findViewById(R.id.form_month_year)).setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            ((EditText)findViewById(R.id.form_month_income)).setText(String.valueOf(0));
        }

        ((Button)findViewById(R.id.form_month_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        ((Button)findViewById(R.id.form_month_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int month = Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.form_month_month)).getText()));
                    int year = Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.form_month_year)).getText()));
                    int income = Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.form_month_income)).getText()));

                    if (month < 1 || month > 12) {
                        throw new Exception();
                    }

                    Intent intent = new Intent();
                    intent.putExtra("month", new Month(month, year, income, new ArrayList<Expense>()));
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
