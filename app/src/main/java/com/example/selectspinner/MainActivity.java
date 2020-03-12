package com.example.selectspinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView selected;
    private Spinner spinner;
    Button btn;
    FirebaseDatabase database;
    DatabaseReference reference;
    Member member;
    int maxid =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected = findViewById(R.id.Selected_month);
        spinner =findViewById(R.id.spinner);
        btn =findViewById(R.id.btn_save);
        member= new Member();
        reference = database.getInstance().getReference().child("Spinner");

        List<String> Categories = new ArrayList<>();
        Categories.add(0,"choose month");
        Categories.add("jan");
        Categories.add("Fab");
        Categories.add("Mar");
        Categories.add("Mar");
        Categories.add("Mar");
        Categories.add("Mar");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(dataAdapter);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Category"))
                {

                }
                else
                {
                    selected.setText(parent.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (int)dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setSpinner(spinner.getSelectedItem().toString());
                Toast.makeText(MainActivity.this, "value stored ", Toast.LENGTH_SHORT).show();

                reference.child(String.valueOf(maxid+1)).setValue(member);
            }
        });
    }

}
