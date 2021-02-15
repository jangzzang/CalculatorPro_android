package com.jangzzang.calculatorpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView imageView_normal_cal,imageView_unit_cal, gold_cal, dis_cal, money_cal, school_cal;
    public static final int NORMAL_CALCULATOR = 0;
    public static final int UNIT_CALCULATOR = 1;
    public static final int GOLD_CALCULATOR = 2;
    public static final int DISCOUNT_CALCULATOR = 3;
    public static final int MONEY_CALCULATOR = 4;
    public static final int SCHOOL_CALCULATOR = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView_normal_cal = findViewById(R.id.imageView_normal_cal);
        imageView_normal_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(0);
            }
        });

        imageView_unit_cal = findViewById(R.id.imageView_unit_cal);
        imageView_unit_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(1);
            }
        });

        gold_cal = findViewById(R.id.imageView_gold_cal);
        gold_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(2);
            }
        });

        dis_cal = findViewById(R.id.imageView_discount_cal);
        dis_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(3);
            }
        });

        money_cal = findViewById(R.id.imageView_money_cal);
        money_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(4);
            }
        });

        school_cal = findViewById(R.id.imageView_school_cal);
        school_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(5);
            }
        });




    }



    private void setBottomSheetDialog(int type){

        TextView textView1;
        TextView textView2;
        Button btn1;
        Button btn2;
        Button btn3;
        Button btn4;
        Button btn5;
        Button btn6;
        Button btn7;
        Button btn8;
        Button btn9;
        Button btn0;
        Button clear;
        Button bracket;
        Button rest;
        Button share;
        Button mul;
        Button minus;
        Button plus;
        Button result;



        View v;
        BottomSheetBehavior mBehavior;

        switch (type){
            case NORMAL_CALCULATOR :
                BottomSheetDialog normal_dialog = new BottomSheetDialog(
                        MainActivity.this,R.style.BottomSheetDialogTheme
                );
                v  = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_default_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer));

                textView1 = v.findViewById(R.id.textview1);
                textView2 = v.findViewById(R.id.textview2);
                btn1 = v.findViewById(R.id.one);
                btn2 = v.findViewById(R.id.two);
                btn3 = v.findViewById(R.id.three);
                btn4 = v.findViewById(R.id.four);
                btn5 = v.findViewById(R.id.five);
                btn6 = v.findViewById(R.id.six);
                btn7 = v.findViewById(R.id.seven);
                btn8 = v.findViewById(R.id.eight);
                btn9 = v.findViewById(R.id.nine);
                btn0 = v.findViewById(R.id.zero);
                clear = v.findViewById(R.id.clear);
                bracket = v.findViewById(R.id.bracket);
                rest = v.findViewById(R.id.rest);   //percent
                share = v.findViewById(R.id.share);
                mul = v.findViewById(R.id.mul);
                minus = v.findViewById(R.id.minus);
                plus = v.findViewById(R.id.plus);
                result = v.findViewById(R.id.result);



                normal_dialog.setContentView(v);

                mBehavior =BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                normal_dialog.show();
                break;

            case UNIT_CALCULATOR :
                BottomSheetDialog unit_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_unit_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_unit_cal));
                unit_dialog.setContentView(v);

                mBehavior = BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                unit_dialog.show();
                break;

        }

    }
}