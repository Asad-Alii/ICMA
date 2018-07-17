package com.asad.icma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ClassStatusActivity extends AppCompatActivity {

    Button endclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classstatus);

        endclass = findViewById(R.id.endClass_btn);
        endclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), TodaysClassActivity.class);
                startActivity(in);
            }
        });
    }
}
