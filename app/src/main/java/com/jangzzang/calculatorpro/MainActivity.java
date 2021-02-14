package com.jangzzang.calculatorpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView imageView_normal_cal,imageView_unit_cal;
    public static final int NORMAL_CALCULATOR = 0;
    public static final int UNIT_CALCULATOR = 1;
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




    }



    private void setBottomSheetDialog(int type){
        View bottomSheetView;
        BottomSheetBehavior mBehavior;
        switch (type){
            case NORMAL_CALCULATOR :
                BottomSheetDialog normal_dialog = new BottomSheetDialog(
                        MainActivity.this,R.style.BottomSheetDialogTheme
                );
                bottomSheetView  = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_default_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer));
                normal_dialog.setContentView(bottomSheetView);

                mBehavior =BottomSheetBehavior.from((View)bottomSheetView.getParent());
                mBehavior.setPeekHeight(4000);
                normal_dialog.show();
                break;

            case UNIT_CALCULATOR :
                BottomSheetDialog unit_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_unit_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_unit_cal));
                unit_dialog.setContentView(bottomSheetView);

                mBehavior = BottomSheetBehavior.from((View)bottomSheetView.getParent());
                mBehavior.setPeekHeight(4000);

                unit_dialog.show();

                break;

        }

    }
}