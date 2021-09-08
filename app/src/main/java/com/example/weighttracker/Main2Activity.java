package com.example.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    BarChart barChart;
    BarData barData;
    BarDataSet bardataSet;
    ArrayList barEntries;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myDb = new DatabaseHelper(this);
        barChart = (BarChart)findViewById(R.id.Barchart);
        getEntries();

        bardataSet = new BarDataSet(barEntries,"Data Set");
        barData = new BarData(bardataSet);
        barChart.setData(barData);
        bardataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        bardataSet.setValueTextColor(Color.BLACK);
        bardataSet.setValueTextSize(16f);
    }

    public void getEntries(){
        barEntries = new ArrayList<>();
        Cursor cr = myDb.getallData();
        if(cr.getCount()==0){
            barEntries.add(new BarEntry(0f,0));
        }else{

            float f = 1f;
            while(cr.moveToNext()){
                double weight = cr.getDouble(2);
                float m = (float)weight;
                barEntries.add(new BarEntry(f,m));
                f+=1f;



            }



        }

    }
}
