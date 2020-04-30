package com.example.a6;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    DataBaseManagement DB;
    TextView balance;
    EditText Day, Price, Item, Month, Year, Day1, Day2, Year1, Year2, Month1, Month2, Amount1, Amount2;
    Button Add, Sub, Search, Clear;
    TableLayout history;
    DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DB = new DataBaseManagement(this);
        balance = (TextView) findViewById(R.id.balance);
        Day = (EditText) findViewById(R.id.Day);
        Month = (EditText) findViewById(R.id.Month);
        Year =(EditText) findViewById(R.id.Year);
        Day1 = (EditText) findViewById(R.id.Day1);
        Day2 = (EditText) findViewById(R.id.Day2);
        Month1 = (EditText) findViewById(R.id.Month1);
        Month2 = (EditText) findViewById(R.id.Month2);
        Year1 =(EditText) findViewById(R.id.Year1);
        Year2 =(EditText) findViewById(R.id.Year2);
        Price = (EditText) findViewById(R.id.Price);
        Amount1 = (EditText) findViewById(R.id.Amount1);
        Amount2 = (EditText) findViewById(R.id.Amount2);
        Item = (EditText) findViewById(R.id.Item);
        Add = (Button) findViewById(R.id.Add);
        Sub = (Button) findViewById(R.id.Sub);
        Search = (Button) findViewById(R.id.Search);
        Clear = (Button) findViewById(R.id.Clear);
        history = (TableLayout) findViewById(R.id.tableHistory);
        add();
        Get();
        Search();
        Clearbt();
    }

    //set the button that can record the information we enter to the new table.
    public void add(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString());
                        TransModel mo = new TransModel();
                        String day = Day.getText().toString();
                        String month = Month.getText().toString();
                        String year = Year.getText().toString();
                        mo.mDate =  CreateDate(day, month, year);
                        mo.mItem = Item.getText().toString();
                        mo.mPrice = price;
                        boolean result = DB.add(mo);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        Get();
                        MainActivity.this.Day.setText("");
                        MainActivity.this.Year.setText("");
                        MainActivity.this.Month.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                        MainActivity.this.Day1.setText("");
                        MainActivity.this.Day2.setText("");
                        MainActivity.this.Month1.setText("");
                        MainActivity.this.Month2.setText("");
                        MainActivity.this.Year1.setText("");
                        MainActivity.this.Year2.setText("");
                        MainActivity.this.Amount1.setText("");
                        MainActivity.this.Amount2.setText("");
                    }
                }
        );

        Sub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = -1 * Double.parseDouble(Price.getText().toString());
                        TransModel mo = new TransModel();
                        String day = Day.getText().toString();
                        String month = Month.getText().toString();
                        String year = Year.getText().toString();
                        mo.mDate =  CreateDate(day, month, year);
                        mo.mItem = Item.getText().toString();
                        mo.mPrice = price;
                        boolean result = DB.add(mo);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        Get();
                        MainActivity.this.Day.setText("");
                        MainActivity.this.Year.setText("");
                        MainActivity.this.Month.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                        MainActivity.this.Day1.setText("");
                        MainActivity.this.Day2.setText("");
                        MainActivity.this.Month1.setText("");
                        MainActivity.this.Month2.setText("");
                        MainActivity.this.Year1.setText("");
                        MainActivity.this.Year2.setText("");
                        MainActivity.this.Amount1.setText("");
                        MainActivity.this.Amount2.setText("");

                    }
                }
        );
    }

    // use the cursor way to find the data.
    public void Get(){
        Cursor result = DB.get();
        set(result, false);
    }


    //to set the data which we will show in the history.
    public void set(Cursor result, boolean filtered) {
        if (result == null) {
            return;
        }

        clear();
        StringBuffer str = new StringBuffer();
        Double balance = 0.0;

        while (result.moveToNext()) {
            TableRow st = new TableRow(this);
            TableRow.LayoutParams columnLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            columnLayout.weight = 1;

            TextView date = new TextView(this);
            date.setLayoutParams(columnLayout);
            date.setText(result.getString(2));
            st.addView(date);

            TextView priceView = new TextView(this);
            priceView.setLayoutParams(columnLayout);
            priceView.setText(result.getString(3));
            st.addView(priceView);

            TextView item = new TextView(this);
            item.setLayoutParams(columnLayout);
            item.setText(result.getString(1));
            st.addView(item);

            history.addView(st, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


            double price = Double.parseDouble(result.getString(3));
            balance += price;

        }
        if (!filtered) {
            MainActivity.this.balance.setText("Current Balance: $" + df.format(balance));
        }
    }

    //clean the view.
    public void clear(){
        int count = history.getChildCount();
        for (int i = 1; i < count; i++) {
            history.removeViewAt(1);
        }
    }
    //set the button to get the information we type in the search place.
    public void Search() {
        Search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amount1 = Amount1.getText().toString();
                        String amount2 = Amount2.getText().toString();
                        String day = Day1.getText().toString();
                        String month = Month1.getText().toString();
                        String year = Year1.getText().toString();
                        String D1 = CreateDate(day, month, year);
                        day = Day2.getText().toString();
                        month = Month2.getText().toString();
                        year = Year2.getText().toString();
                        String D2 = CreateDate(day, month, year);


                        Cursor result = DB.pullData(amount1, amount2, D1, D2);
                        set(result, true);
                    }
                }
        );
    }
    //set the button to clear the information we type in the search place.
    public void Clearbt(){
        Clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.Day.setText("");
                        MainActivity.this.Year.setText("");
                        MainActivity.this.Month.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                        MainActivity.this.Day1.setText("");
                        MainActivity.this.Day2.setText("");
                        MainActivity.this.Month1.setText("");
                        MainActivity.this.Month2.setText("");
                        MainActivity.this.Year1.setText("");
                        MainActivity.this.Year2.setText("");
                        MainActivity.this.Amount1.setText("");
                        MainActivity.this.Amount2.setText("");
                        Get();
                    }
                }
        );
    }
    //create the date formal.
    public String CreateDate (String day, String month, String year) {
        return month + "-" + day + "-" + year;
    }


}