package com.example.gaurav.expensescalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    List<Month> database;
    Adapter adapter;

    class Adapter extends BaseAdapter {
        Context context;
        List<Month> items;

        public Adapter(Context context, List<Month> items) {
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
                convertView = inflater.inflate(R.layout.item_month, parent, false);
            }

            ((TextView)convertView.findViewById(R.id.item_month_month)).setText(String.valueOf(items.get(position).month));
            ((TextView)convertView.findViewById(R.id.item_month_year)).setText(String.valueOf(items.get(position).year));
            ((TextView)convertView.findViewById(R.id.item_month_income)).setText(String.valueOf(items.get(position).income));

            ((Button)convertView.findViewById(R.id.item_month_edit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FormMonthActivity.class);
                    intent.putExtra("month", items.get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, 2);
                }
            });

            ((Button)convertView.findViewById(R.id.item_month_delete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.remove(position);
                    adapter.notifyDataSetChanged();
                    saveDatabase();
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fab = (Button) findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormMonthActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        loadDatabase();

        ListView view = ((ListView)findViewById(R.id.activity_main_list));
        adapter = new Adapter(this, database);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("month", database.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            database.add((Month) data.getSerializableExtra("month"));
            adapter.notifyDataSetChanged();
            saveDatabase();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Month month = database.get(data.getIntExtra("position", -1));
            month.month = ((Month) data.getSerializableExtra("month")).month;
            month.year = ((Month) data.getSerializableExtra("month")).year;
            month.income = ((Month) data.getSerializableExtra("month")).income;
            adapter.notifyDataSetChanged();
            saveDatabase();
        } else if (requestCode == 3) {
            loadDatabase();

            ListView view = ((ListView)findViewById(R.id.activity_main_list));
            adapter = new Adapter(this, database);
            view.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
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
