package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    PieChart pcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intRecieved = getIntent();
        int correct = intRecieved.getIntExtra("correct",0);
        int wrong = intRecieved.getIntExtra("wrong",0);

        pcResult = (PieChart)findViewById(R.id.pieChart);
        pcResult.setUsePercentValues(true);
        pcResult.getDescription().setEnabled(false);
        pcResult.setExtraOffsets(5,10,5,5);



        pcResult.setDragDecelerationFrictionCoef(0.95f);
        pcResult.setDrawHoleEnabled(true);
        pcResult.setHoleColor(Color.WHITE);
        pcResult.setTransparentCircleRadius(61f);
        pcResult.setCenterText("Result");
        pcResult.setCenterTextSize(20f);
        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(correct,"Wrong"));
        yValues.add(new PieEntry(wrong,"Correct"));


        PieDataSet dataSet = new PieDataSet(yValues,"Result");
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(3f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(25f);
        data.setValueTextColor(Color.YELLOW);

        pcResult.setData(data);
    }
}
