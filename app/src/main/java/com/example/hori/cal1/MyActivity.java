package com.example.hori.cal1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.text.DecimalFormat;


public class MyActivity extends Activity {


    private int value;
    private int currentValue;
    private int divergence2 = 0;
    private boolean calculated = false;
    private String operator = null;
    private String period = null;
    private SQLiteDatabase historyDB;
    double Operand,PrimaryOperand,result,FirstResult,i;
    double SecondOperand = 0;
    double addOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SQLiteOpenHelper helper = new HistorySQLiteOpenHelper(getApplicationContext());
        historyDB = helper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickNumberButton(View view) {

        if (addOperand != 0){
            EditText editText = (EditText) findViewById(R.id.editText);
            Button button = (Button) view;
            editText.setText("");
            editText.append(button.getText());
            addOperand = 0;
        }
        else {
            EditText editText = (EditText) findViewById(R.id.editText);
            Button button = (Button) view;
            editText.append(button.getText());
        }

    }

    public void onClickPlusButton(View view) {
        try {
            EditText text = (EditText) findViewById(R.id.editText);

            if (PrimaryOperand == 0) {
                PrimaryOperand = Double.valueOf(text.getText().toString());
                text.setText("");
            } else if(PrimaryOperand != 0) {
                addOperand = Double.valueOf(text.getText().toString());
                if (operator == "+") {
                    PrimaryOperand = PrimaryOperand + addOperand;
                }else if(operator == "-"){
                    PrimaryOperand = PrimaryOperand - addOperand;
                }else if (operator == "*"){
                    PrimaryOperand = PrimaryOperand * addOperand;
                }else if (operator == "/"){
                    PrimaryOperand = PrimaryOperand / addOperand;
                }else{return;}
                //小数点以下0の場合intにキャスト
                if (PrimaryOperand % 1 == 0) {
                    value = (int) PrimaryOperand;
                    text.setText(Integer.toString(value));
                } else {
                    text.setText(Double.toString(PrimaryOperand));
                }

            }
            operator = "+";
            period = null;
            divergence2 = 0;
        }
        catch (NumberFormatException e){
            return;
        }
    }

    public void onClickMinusButton(View view) {
        try {
            EditText text = (EditText) findViewById(R.id.editText);

            if(PrimaryOperand == 0){
                PrimaryOperand = Double.valueOf(text.getText().toString());
                text.setText("");
            }else if(PrimaryOperand != 0){
                addOperand = Double.valueOf(text.getText().toString());
                if (operator == "+") {
                    PrimaryOperand = PrimaryOperand + addOperand;
                }else if(operator == "-"){
                    PrimaryOperand = PrimaryOperand - addOperand;
                }else if (operator == "*"){
                    PrimaryOperand = PrimaryOperand * addOperand;
                }else if (operator == "/"){
                    PrimaryOperand = PrimaryOperand / addOperand;
                }else{return;}
                //小数点以下0の場合intにキャスト
                if (PrimaryOperand % 1 == 0) {
                    value = (int) PrimaryOperand;
                    text.setText(Integer.toString(value));
                } else {
                    text.setText(Double.toString(PrimaryOperand));
                }
            }
            operator = "-";
            period = null;
            divergence2 = 0;
        }
        catch (NumberFormatException e){
            return;
        }
    }

    public void onClickMultiplicationButton(View view) {
        try {
            EditText text = (EditText) findViewById(R.id.editText);

            if(PrimaryOperand == 0){
                PrimaryOperand = Double.valueOf(text.getText().toString());
                text.setText("");
            }else if(PrimaryOperand != 0){
                addOperand = Double.valueOf(text.getText().toString());
                if (operator == "+") {
                    PrimaryOperand = PrimaryOperand + addOperand;
                }else if(operator == "-"){
                    PrimaryOperand = PrimaryOperand - addOperand;
                }else if (operator == "*"){
                    PrimaryOperand = PrimaryOperand * addOperand;
                }else if (operator == "/"){
                    PrimaryOperand = PrimaryOperand / addOperand;
                }else{return;}
                //小数点以下0の場合intにキャスト
                if (PrimaryOperand % 1 == 0) {
                    value = (int) PrimaryOperand;
                    text.setText(Integer.toString(value));
                } else {
                    text.setText(Double.toString(PrimaryOperand));
                }
            }
            operator = "*";
            period = null;
            divergence2 = 0;
        }
        catch (NumberFormatException e){
            return;
        }
    }

    public void onClickDivisionButton(View view) {
        try {
            EditText text = (EditText) findViewById(R.id.editText);

            if(PrimaryOperand == 0){
                PrimaryOperand = Double.valueOf(text.getText().toString());
                text.setText("");
            }else if(PrimaryOperand != 0){
                addOperand = Double.valueOf(text.getText().toString());
                if (operator == "+") {
                    PrimaryOperand = PrimaryOperand + addOperand;
                }else if(operator == "-"){
                    PrimaryOperand = PrimaryOperand - addOperand;
                }else if (operator == "*"){
                    PrimaryOperand = PrimaryOperand * addOperand;
                }else if (operator == "/"){
                    PrimaryOperand = PrimaryOperand / addOperand;
                }else{return;}
                //小数点以下0の場合intにキャスト
                if (PrimaryOperand % 1 == 0) {
                    value = (int) PrimaryOperand;
                    text.setText(Integer.toString(value));
                } else {
                    text.setText(Double.toString(PrimaryOperand));
                }
            }
            operator = "/";
            period = null;
            divergence2 = 0;
        }
        catch (NumberFormatException e){
            return;
        }
    }

    public void onClickClearButton(View view) {
        EditText text = (EditText) findViewById(R.id.editText);

        value = 0;
        currentValue = 0;
        Operand = 0;
        PrimaryOperand = 0;
        SecondOperand = 0;
        operator = null;
        period = null;
        calculated = false;

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("");
    }

    public void onClickEqualButton(View view){
        try {

            if (divergence2 == 0) {

                EditText text = (EditText) findViewById(R.id.editText);
                SecondOperand = Double.valueOf(text.getText().toString());


                if (operator == "+") {
                        FirstResult = PrimaryOperand + SecondOperand;
                    //小数点以下が0ならintにキャストしてからstrで表示
                    if (FirstResult % 1 == 0) {
                        value = (int) FirstResult;
                        text.setText(Integer.toString(value));
                    } else {
                        text.setText(Double.toString(FirstResult));
                    }
                } else if (operator == "-") {
                    FirstResult = PrimaryOperand - SecondOperand;
                    if (FirstResult % 1 == 0) {
                        value = (int) FirstResult;
                        text.setText(Integer.toString(value));
                    } else {
                        text.setText(Double.toString(FirstResult));
                    }
                } else if (operator == "*") {
                    FirstResult = PrimaryOperand * SecondOperand;
                    if (FirstResult % 1 == 0) {
                        value = (int) FirstResult;
                        text.setText(Integer.toString(value));
                    } else {
                        text.setText(Double.toString(FirstResult));
                    }
                } else if (operator == "/") {
                    FirstResult = PrimaryOperand / SecondOperand;
                    if (FirstResult % 1 == 0) {
                        value = (int) FirstResult;
                        text.setText(Integer.toString(value));
                    } else {
                        text.setText(Double.toString(FirstResult));
                    }
                } else if (operator == null) {
                    return;
                } else {
                    return;
                }
                divergence2 = 1;

                result = Double.valueOf(text.getText().toString());

                calculated = true;

                ContentValues values = new ContentValues();
                values.put("formula", PrimaryOperand + operator + SecondOperand + "=" + result);

                historyDB.insert("history", null, values);

                //二回目以降のイコールボタンが押された場合
            } else if (divergence2 == 1) {
                    EditText text = (EditText) findViewById(R.id.editText);
                    if (operator == "+") {
                        result = i + SecondOperand;
                        if (result % 1 == 0) {
                            value = (int) result;
                            text.setText(Integer.toString(value));
                        } else {
                            text.setText(Double.toString(result));
                        }
                    } else if (operator == "-") {
                        result = i - SecondOperand;
                        if (result % 1 == 0) {
                            value = (int) result;
                            text.setText(Integer.toString(value));
                        } else {
                            text.setText(Double.toString(result));
                        }
                    } else if (operator == "*") {
                        result = i * SecondOperand;
                        if (result % 1 == 0) {
                            value = (int) result;
                            text.setText(Integer.toString(value));
                        } else {
                            text.setText(Double.toString(result));
                        }
                    } else if (operator == "/") {
                        result = i / SecondOperand;
                        if (result % 1 == 0) {
                            value = (int) result;
                            text.setText(Integer.toString(value));
                        } else {
                            text.setText(Double.toString(result));
                        }
                    } else if (operator == null) {
                        return;
                    } else {
                        return;
                    }

                    calculated = true;

                    ContentValues values = new ContentValues();
                    values.put("formula", i + operator + SecondOperand + "=" + result);
                    historyDB.insert("history", null, values);
            }
            EditText text = (EditText) findViewById(R.id.editText);
            i = Double.parseDouble(text.getText().toString());
            addOperand = 0;
            period = null;
        }
        catch (NumberFormatException e){
            return;
        }
    }

    public void onClickHistoryButton(View view) {

        switch (view.getId()){
            case R.id.button_history:
                Intent intent = new Intent(this,History.class);
                startActivity(intent);
                break;
        }
    }

    public void onClickPeriodButton(View view) {
        if(period == null) {
            EditText text = (EditText) findViewById(R.id.editText);
            text.setText(text.getText() + ".");
            period = ".";
        }
        else{return;}
    }

}




