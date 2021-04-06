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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


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
                String score1 = e22.getText().toString();
                String score2 = e33.getText().toString();


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