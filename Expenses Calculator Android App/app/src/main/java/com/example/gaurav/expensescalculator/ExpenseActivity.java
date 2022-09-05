package com.example.gaurav.expensescalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {

    List<Expense> expenses;
    List<Month> database;
    Adapter adapter;
    private Month month;

    class Adapter extends BaseAdapter {
        Context context;
        List<Expense> items;

        public Adapter(Context context, List<Expense> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_expense, parent, false);
            }

            ((TextView)convertView.findViewById(R.id.item_expense_amount)).setText(String.valueOf(items.get(position).amount));
            ((TextView)convertView.findViewById(R.id.item_expense_label)).setText(items.get(position).label);
            ((CheckBox)convertView.findViewById(R.id.item_expense_isRegular)).setChecked(items.get(position).isRegular);

            ((Button)convertView.findViewById(R.id.item_expense_edit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExpenseActivity.this, FormExpenseActivity.class);
                    intent.putExtra("expense", items.get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, 2);
                }
            });

            ((Button)convertView.findViewById(R.id.item_expense_delete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expenses.remove(position);
                    adapter.notifyDataSetChanged();
                    saveDatabase();
                    updateBalance();
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button fab = (Button) findViewById(R.id.activity_expense_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this, FormExpenseActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        loadDatabase();

        month = database.get(getIntent().getIntExtra("position", 0));
        expenses = database.get(getIntent().getIntExtra("position", 0)).expenses;

        setTitle(String.format("%d/%d", month.month, month.year));

        ListView view = ((ListView)findViewById(R.id.activity_expense_list));
        adapter = new Adapter(this, expenses);
        view.setAdapter(adapter);
        updateBalance();
    }

    private void updateBalance() {
        int sum = 0;
        for (Expense e : expenses) {
            sum += e.amount;
        }
        ((TextView)findViewById(R.id.activity_expense_income)).setText(String.valueOf(month.income));
        ((TextView)findViewById(R.id.activity_expense_balance)).setText(String.valueOf(month.income - sum));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            expenses.add((Expense) data.getSerializableExtra("expense"));
            adapter.notifyDataSetChanged();
            saveDatabase();
            updateBalance();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            expenses.set(data.getIntExtra("position", -1), (Expense) data.getSerializableExtra("expense"));
            adapter.notifyDataSetChanged();
            saveDatabase();
            updateBalance();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public List<Month> loadDatabase() {
        try {
            FileInputStream fi = new FileInputStream(new File(getFilesDir(),"database.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            database = (List<Month>) oi.readObject();

            oi.close();
            fi.close();
        } catch (IOException | ClassNotFoundException ignored) {
            database = new ArrayList<>();
        }

        return database;
    }

    public void saveDatabase() {
        try {
            FileOutputStream f = new FileOutputStream(new File(getFilesDir(), "database.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(database);

            o.close();
            f.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
