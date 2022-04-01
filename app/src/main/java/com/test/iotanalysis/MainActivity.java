package com.test.iotanalysis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.txusballesteros.SnakeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Switch switch1, switch2, switch3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Boolean status = true;

    private float[] values = new float[] { 60, 70, 80, 90, 100,
            150, 150, 160, 170, 175, 180,
            170, 140, 130, 110, 90, 80, 60,170, 140, 130, 110, 90, 80, 60,170, 140, 130, 110, 90, 80, 60};
    private TextView text;
    private SnakeView snakeView;
    private int position = 0;
    private boolean stop = false;

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = findViewById(R.id.chart1);

        // background color
        chart.setBackgroundColor(Color.WHITE);

        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners
        chart.setDrawGridBackground(false);


        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        setData(45, 180);
        /*final SnakeView snakeView = (SnakeView)findViewById(R.id.snake);
        snakeView.setMinValue(0);
        snakeView.setMaxValue(100);*/

        //getWindow().setBackgroundDrawable(null);
        text = (TextView)findViewById(R.id.text);
        snakeView = (SnakeView)findViewById(R.id.snake);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/");

        switch1 = findViewById(R.id.led1);
        switch2 = findViewById(R.id.led2);
        switch3 = findViewById(R.id.led3);

        Button activity1, activity2, activity3;

        activity1 = findViewById(R.id.led1Activity);
        activity2 = findViewById(R.id.led2Activity);
        activity3 = findViewById(R.id.led3Activity);

        activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="led_1";
                Intent i = new Intent(MainActivity.this, ActivityActivity.class);
                i.putExtra("led",value);
                startActivity(i);
            }
        });
        activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="led_2";
                Intent i = new Intent(MainActivity.this, ActivityActivity.class);
                i.putExtra("led",value);
                startActivity(i);
            }
        });
        activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="led_3";
                Intent i = new Intent(MainActivity.this, ActivityActivity.class);
                i.putExtra("led",value);
                startActivity(i);
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/activity/");

        rootRef.child("led_1/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String time = shot.child("time").getValue().toString();
                    String status = shot.child("status").getValue().toString();

                    if (status.equals("true"))
                        count++;
                }

                TextView led_1_tv = findViewById(R.id.led1_tv);
                led_1_tv.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rootRef.child("led_2/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String time = shot.child("time").getValue().toString();
                    String status = shot.child("status").getValue().toString();

                    if (status.equals("true"))
                        count++;
                }

                TextView led_1_tv = findViewById(R.id.led2_tv);
                led_1_tv.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rootRef.child("led_3/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String time = shot.child("time").getValue().toString();
                    String status = shot.child("status").getValue().toString();

                    if (status.equals("true"))
                        count++;
                }

                TextView led_1_tv = findViewById(R.id.led3_tv);
                led_1_tv.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                databaseReference.child("led_1").setValue(String.valueOf(b ? true : false));

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/activity/led_1/");
                String key = rootRef.push().getKey();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                rootRef.child(key).setValue(new Data(String.valueOf(b), currentDateandTime));

                status = false;
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                databaseReference.child("led_2").setValue(String.valueOf(b ? true : false));

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/activity/led_2/");
                String key = rootRef.push().getKey();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                rootRef.child(key).setValue(new Data(String.valueOf(b), currentDateandTime));

                status = false;
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                databaseReference.child("led_3").setValue(String.valueOf(b ? true : false));

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/activity/led_3/");
                String key = rootRef.push().getKey();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                rootRef.child(key).setValue(new Data(String.valueOf(b), currentDateandTime));

                status = false;
            }
        });

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean s1 = true, s2 = false, s3 = true;

                if(snapshot.child("led_1").getValue(String.class).toString().equals("true"))
                    s1 = true;
                else
                    s1 = false;

                if(snapshot.child("led_2").getValue(String.class).toString().equals("true"))
                    s2 = true;
                else
                    s2 = false;

                if(snapshot.child("led_3").getValue(String.class).toString().equals("true"))
                    s3 = true;
                else
                    s3 = false;


                switch1.setChecked(s1);
                switch2.setChecked(s2);
                switch3.setChecked(s3);

                //status = true;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        stop = false;
        generateValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop = true;
    }
    private void generateValue() {

        for(float f: values )

        snakeView.addValue(f);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.common_full_open_on_phone)));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.common_full_open_on_phone);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

}