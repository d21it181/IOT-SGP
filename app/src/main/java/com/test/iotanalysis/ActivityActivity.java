package com.test.iotanalysis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        String led = getIntent().getExtras().getString("led");
        //Toast.makeText(this, led, Toast.LENGTH_SHORT).show();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/activity/");

        rootRef.child(led+"/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Data> items = new ArrayList<>();
                items = new ArrayList<>();
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String time = shot.child("time").getValue().toString();
                    String status = shot.child("status").getValue().toString();

                    items.add(new Data(status, time));
                }

                RecyclerView recyclerView2 = findViewById(R.id.rv);
                recyclerView2.setLayoutManager(new GridLayoutManager(ActivityActivity.this, 1));

                /*DividerItemDecoration itemDecorator = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.divider));
                recyclerView2.addItemDecoration(itemDecorator);*/

                ActivityAdapter activityAdapter = new ActivityAdapter(ActivityActivity.this, items);
                recyclerView2.setAdapter(activityAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}