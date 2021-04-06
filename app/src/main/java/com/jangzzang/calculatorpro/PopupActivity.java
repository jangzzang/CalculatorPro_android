package com.jangzzang.calculatorpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PopupActivity extends Activity {

    EditText e11;
    EditText e22;
    EditText e33;
    Button complete;

    Double Total1;
    Double Total2;
    Double Total3;

    int pos;

    Spinner spinner1;
    Spinner spinner2;

    AdapterSpinner1 adapterSpinner1;
    AdapterSpinner1 adapterSpinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        List<String> data2 = new ArrayList<>();
        data2.add("학점");
        data2.add("10");
        data2.add("9");
        data2.add("8");
        data2.add("7");
        data2.add("6");
        data2.add("5");
        data2.add("4");
        data2.add("3");
        data2.add("2");
        data2.add("1");


        List<String> data = new ArrayList<>();
        data.add("점수");
        data.add("4.5");
        data.add("4.3");
        data.add("4.0");
        data.add("3.7");
        data.add("3.5");
        data.add("3.3");
        data.add("3.0");
        data.add("2.7");
        data.add("2.5");
        data.add("2.3");
        data.add("2.0");
        data.add("1.7");
        data.add("1.5");
        data.add("1.3");
        data.add("1.0");
        data.add("0.7");
        data.add("0.0");

        spinner1 = findViewById(R.id.spinner1);

        adapterSpinner1 = new AdapterSpinner1(this, data2);

        spinner1.setAdapter(adapterSpinner1);



        spinner2 = findViewById(R.id.spinner2);

        adapterSpinner2 = new AdapterSpinner1(this, data);

        spinner2.setAdapter(adapterSpinner2);


        Intent intent = getIntent();
        pos = intent.getIntExtra("pos", 0);

        e11 = findViewById(R.id.e11);
        //e22 = findViewById(R.id.e22);
        //e33 = findViewById(R.id.e33);
        complete = findViewById(R.id.complete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = e11.getText().toString();
                String score1 = spinner1.getSelectedItem().toString();
                String score2 = spinner2.getSelectedItem().toString();

                if(name.equals("") || score1.equals("학점") || score2.equals("점수")){
                    Toast.makeText(PopupActivity.this, "작성되지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("score1", score1);
                intent.putExtra("score2", score2);
                intent.putExtra("pos", pos);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


}