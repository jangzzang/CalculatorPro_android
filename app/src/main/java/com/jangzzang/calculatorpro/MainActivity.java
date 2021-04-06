package com.jangzzang.calculatorpro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button button;
    //ImageView imageView_normal_cal,imageView_unit_cal, gold_cal, dis_cal, money_cal, school_cal;
    public static final int NORMAL_CALCULATOR = 0;
    public static final int UNIT_CALCULATOR = 1;
    public static final int GOLD_CALCULATOR = 2;
    public static final int DISCOUNT_CALCULATOR = 3;
    public static final int MONEY_CALCULATOR = 4;
    public static final int SCHOOL_CALCULATOR = 5;

    double don = 0.0;
    double gram = 0.0;

    String p1;
    String p2;
    String usa;
    String eur;
    String jap;
    String chi;

    RecyclerView recyclerView;
    ArrayList<School_item> list = new ArrayList<>();
    School_Adapter adapter;

    int remove;

    TextView total1;
    TextView total2;

    Button normal;
    Button unit;
    Button gold;
    Button discount;
    Button money;
    Button school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        normal = findViewById(R.id.normalicon);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(0);
            }
        });

        /*imageView_unit_cal = findViewById(R.id.imageView_unit_cal);
        imageView_unit_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(1);
            }
        });*/

        unit = findViewById(R.id.uniticon);
        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(1);
            }
        });

        gold = findViewById(R.id.goldicon);
        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(2);
            }
        });

        discount = findViewById(R.id.discounticon);
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(3);
            }
        });

        money = findViewById(R.id.moneyicon);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(4);
            }
        });

        school = findViewById(R.id.schoolicon);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomSheetDialog(5);
            }
        });

        new Parsing().execute();

    }

    private class Parsing extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc1 = Jsoup.connect("https://finance.naver.com/marketindex/goldDetail.nhn").get(); //파싱할 사이트 주소 이동
                Elements links1 = doc1.select("div.input_area").get(0).select("input");
                p1 = links1.attr("value");

                Document doc2 = Jsoup.connect("https://finance.naver.com/marketindex/goldDetail.nhn").get(); //파싱할 사이트 주소 이동
                Elements links2 = doc2.select("div.input_area").get(1).select("input");
                p2 = links2.attr("value");

                Document doc3 = Jsoup.connect("https://finance.naver.com/marketindex/?tabSel=exchange#tab_section").get();
                Elements links3 = doc3.select("div.selectbox-noscript").select("select.selectbox-source");
                usa = links3.select("option").get(2).attr("value");

                Document doc4 = Jsoup.connect("https://finance.naver.com/marketindex/?tabSel=exchange#tab_section").get();
                Elements links4 = doc3.select("div.selectbox-noscript").select("select.selectbox-source");
                eur = links4.select("option").get(3).attr("value");

                Document doc5 = Jsoup.connect("https://finance.naver.com/marketindex/?tabSel=exchange#tab_section").get();
                Elements links5 = doc3.select("div.selectbox-noscript").select("select.selectbox-source");
                jap = links5.select("option").get(4).attr("value");

                Document doc6 = Jsoup.connect("https://finance.naver.com/marketindex/?tabSel=exchange#tab_section").get();
                Elements links6 = doc3.select("div.selectbox-noscript").select("select.selectbox-source");
                chi = links6.select("option").get(5).attr("value");

            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }
    }

    int bracket_open = 0;
    int number = 0;
    int[] dis_number = new int[2];
    int[] dis_dot = new int[2];
    int[] dis_dot_check = new int[2];
    int sign = 0;
    int ddot = 0;
    int ddot_check = 0;
    int status = 0;
    int op = 1;

    private void setBottomSheetDialog(int type){

        TextView[] dis_text = new TextView[3];
        TextView[] unit_text = new TextView[2];

        Spinner[] spinners = new Spinner[2];

        TextView textView1;
        TextView textView2;
        TextView textView3;
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
        Button dot;
        Button clear;
        Button bracket;
        Button rest;
        Button share;
        Button mul;
        Button minus;
        Button plus;
        Button result;

        Button del;
        Button move;
        Button change;


        View v;
        BottomSheetBehavior mBehavior;

        switch (type){
            case NORMAL_CALCULATOR :
                bracket_open = 0;
                number = 0;
                sign = 0;
                ddot = 0;
                ddot_check = 0;

                BottomSheetDialog normal_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
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
                dot = v.findViewById(R.id.dot);
                clear = v.findViewById(R.id.clear);
                del = v.findViewById(R.id.del);
                //bracket = v.findViewById(R.id.bracket);
                //rest = v.findViewById(R.id.rest);   //percent
                share = v.findViewById(R.id.share);
                mul = v.findViewById(R.id.mul);
                minus = v.findViewById(R.id.minus);
                plus = v.findViewById(R.id.plus);
                result = v.findViewById(R.id.result);

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView1.setText("");
                        textView2.setText("");
                        number = 0;
                        sign = 0;
                        ddot = 0;
                        bracket_open = 0;
                        ddot_check = 0;
                    }
                });


                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text0 = textView1.getText().toString();
                        if(text0.length() > 1){
                            String text00 = text0.substring(0, text0.length() - 1);

                            if(text00.charAt(text00.length()-1) == ' '){
                                text00 = text0.substring(0, text0.length() - 2);
                            }

                            textView1.setText(text00);
                            textView2.setText("");

                            if(text00.contains(".")){
                                ddot_check = 1;
                            }
                            else{
                                ddot_check = 0;
                            }

                            if(text00.length() > 0 && text00.charAt(text00.length()-1) == '.'){
                                ddot = 1;
                            }
                            else{
                                ddot = 0;
                            }

                            if(text00.length() > 0 && (text00.charAt(text00.length()-1) == '+' || text00.charAt(text00.length()-1) == '-' || text00.charAt(text00.length()-1) == '*' || text00.charAt(text00.length()-1) == '/')){
                                number = 0;
                                sign = 1;
                                ddot_check = 0;
                                ddot = 0;
                            }
                            else if(text00.length() == 0){
                                bracket_open = 0;
                                number = 0;
                                sign = 0;
                                ddot = 0;
                                ddot_check = 0;
                            }
                            else{
                                number = 1;
                                sign = 0;
                            }



                        }
                        else if(text0.length() == 1){
                            textView1.setText("");
                            textView2.setText("");
                            bracket_open = 0;
                            number = 0;
                            sign = 0;
                            ddot = 0;
                            ddot_check = 0;
                        }
                    }
                });


                /*bracket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && sign == 0){   //처음 시작
                            String txt = textView1.getText().toString();
                            txt += " (";
                            textView1.setText(txt);
                            bracket_open++;
                            number = 0;
                            sign = 1;
                        }
                        else {
                            if((number == 1 || number == -1 || number == 2) && sign == 0 && bracket_open > 0){
                                String txt = textView1.getText().toString();
                                txt += " )";
                                textView1.setText(txt);
                                bracket_open--;
                                number = 2;
                                sign = 0;
                            }
                            else if(number == 0 && sign == 1){
                                String txt = textView1.getText().toString();
                                txt += " (";
                                textView1.setText(txt);
                                bracket_open++;
                                number = 0;
                                sign = 1;
                            }
                        }
                    }
                });*/




                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sign == 0 && (number == 1 || number == -1 || number == 2)){
                            String txt = textView1.getText().toString();
                            txt += " /";
                            textView1.setText(txt);
                            number = 0;
                            sign = 1;
                            ddot_check = 0;
                            ddot = 0;
                        }
                    }
                });
                mul.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sign == 0 && (number == 1 || number == -1 || number == 2)){
                            String txt = textView1.getText().toString();
                            txt += " *";
                            textView1.setText(txt);
                            number = 0;
                            sign = 1;
                            ddot_check = 0;
                            ddot = 0;
                        }
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sign == 0 && (number == 1 || number == -1 || number == 2)){
                            String txt = textView1.getText().toString();
                            txt += " -";
                            textView1.setText(txt);
                            number = 0;
                            sign = 1;
                            ddot_check = 0;
                            ddot = 0;
                        }
                    }
                });
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sign == 0 && (number == 1 || number == -1 || number == 2)){
                            String txt = textView1.getText().toString();
                            txt += " +";
                            textView1.setText(txt);
                            number = 0;
                            sign = 1;
                            ddot_check = 0;
                            ddot = 0;
                        }
                    }
                });



                result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number != 0){
                            if(sign == 1 || bracket_open != 0){
                                Toast.makeText(v.getContext(), "완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String txt = textView1.getText().toString();
                                String result = getCalculate(txt);

                                if(remove == -1){
                                    int len = result.length();
                                    int cnt = 0;
                                    for(int i=len - 1;i>=0;i--){
                                        if(result.charAt(i) == '0'){
                                            cnt++;
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                    String result2 = result.substring(0,len-cnt);
                                    textView2.setText(result2);
                                }
                                else{
                                    textView2.setText(result);
                                }
                            }
                        }
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ddot_check == 0 && number == 1){
                            ddot_check = 1;
                            ddot = 1;
                            number = 0;
                            sign = 1;
                            String txt = textView1.getText().toString();
                            txt += ".";
                            textView1.setText(txt);
                        }
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "1";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 1";
                            textView1.setText(txt);
                        }
                        else if(number == 1 || ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "1";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 1";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "2";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 2";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "2";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 2";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "3";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 3";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "3";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 3";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "4";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 4";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "4";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 4";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "5";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 5";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "5";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 5";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "6";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 6";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "6";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 6";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "7";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 7";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "7";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 7";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "8";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 8";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "8";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 8";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == -1 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "9";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 9";
                            textView1.setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "9";
                            textView1.setText(txt);
                        }
                        else if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += " 9";
                            textView1.setText(txt);
                        }
                        ddot = 0;
                        number = 1;
                        sign = 0;
                    }
                });
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            number = -1;
                            String txt = textView1.getText().toString();
                            txt += " 0";
                            textView1.setText(txt);
                        }
                        else if(number == 2){
                            String txt = textView1.getText().toString();
                            txt += " * 0";
                            textView1.setText(txt);
                            sign = 0;
                            number = -1;
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "0";
                            textView1.setText(txt);
                            sign = 0;
                            number = 1;
                        }
                        ddot = 0;
                    }
                });

                normal_dialog.setContentView(v);

                mBehavior =BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                normal_dialog.show();
                break;

            case UNIT_CALCULATOR :
                number = 0;
                status = 0;
                op = 1;
                ddot = 0;
                ddot_check = 0;

                BottomSheetDialog unit_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_unit_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_unit_cal));
                unit_dialog.setContentView(v);

                unit_text[0] = v.findViewById(R.id.textview1);
                unit_text[1] = v.findViewById(R.id.textview2);
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
                dot = v.findViewById(R.id.dot);
                result = v.findViewById(R.id.result);
                del = v.findViewById(R.id.del);
                move = v.findViewById(R.id.gonormal);
                change = v.findViewById(R.id.changing);

                LinearLayout uplayout2 = v.findViewById(R.id.uplayout);
                LinearLayout downlayout2 = v.findViewById(R.id.downlayout);

                spinners[0] = v.findViewById(R.id.unit_sp1);
                spinners[1] = v.findViewById(R.id.unit_sp2);



                move.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setBottomSheetDialog(0);
                    }
                });

                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int p1 = spinners[0].getSelectedItemPosition();
                        int p2 = spinners[1].getSelectedItemPosition();

                        spinners[0].setSelection(p2);
                        spinners[1].setSelection(p1);

                        unit_text[0].setText("");
                        unit_text[1].setText("");
                        ddot = 0;
                        ddot_check = 0;
                        number = 0;
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ddot_check == 0 && number == 1){
                            ddot_check = 1;
                            ddot = 1;
                            number = 0;
                            String txt = unit_text[0].getText().toString();
                            txt += ".";
                            unit_text[0].setText(txt);
                        }
                    }
                });


                result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sel1 = spinners[0].getSelectedItem().toString();
                        String sel2 = spinners[1].getSelectedItem().toString();

                        if(number != 0 && ddot == 0){
                            String num1 = unit_text[0].getText().toString();
                            Double n1 = Double.parseDouble(num1);

                            if(sel1.equals("밀리미터(mm)")){
                                if(sel2.equals("밀리미터(mm)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("센티미터(cm)")){
                                    Double n2 = n1 / 10;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("미터(m)")){
                                    Double n2 = n1 / 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로미터(km)")){
                                    Double n2 = n1 / 1000000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("인치(in)")){
                                    Double n2 = n1 * 0.0393701;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("피트(ft)")){
                                    Double n2 = n1 * 0.00328084;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("센티미터(cm)")){
                                if(sel2.equals("밀리미터(mm)")){
                                    Double n2 = n1 * 10;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("센티미터(cm)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("미터(m)")){
                                    Double n2 = n1 / 100;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로미터(km)")){
                                    Double n2 = n1 / 100000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("인치(in)")){
                                    Double n2 = n1 * 0.393701;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("피트(ft)")){
                                    Double n2 = n1 * 0.0328084;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("미터(m)")){
                                if(sel2.equals("밀리미터(mm)")){
                                    Double n2 = n1 * 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("센티미터(cm)")){
                                    Double n2 = n1 * 100;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("미터(m)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로미터(km)")){
                                    Double n2 = n1 / 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("인치(in)")){
                                    Double n2 = n1 * 39.3701;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("피트(ft)")){
                                    Double n2 = n1 * 3.28084;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("킬로미터(km)")){
                                if(sel2.equals("밀리미터(mm)")){
                                    Double n2 = n1 * 1000000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("센티미터(cm)")){
                                    Double n2 = n1 * 100000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("미터(m)")){
                                    Double n2 = n1 * 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로미터(km)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("인치(in)")){
                                    Double n2 = n1 * 39370.1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("피트(ft)")){
                                    Double n2 = n1 * 3280.84;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("인치(in)")){
                                if(sel2.equals("밀리미터(mm)")){
                                    Double n2 = n1 * 25.4;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("센티미터(cm)")){
                                    Double n2 = n1 * 2.54;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("미터(m)")){
                                    Double n2 = n1 * 0.0254;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로미터(km)")){
                                    Double n2 = n1 * 0.0000254;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("인치(in)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("피트(ft)")){
                                    Double n2 = n1 * 0.0833333;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("피트(ft)")){
                                if(sel2.equals("밀리미터(mm)")){
                                    Double n2 = n1 * 304.8;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("센티미터(cm)")){
                                    Double n2 = n1 * 30.48;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("미터(m)")){
                                    Double n2 = n1 * 0.3048;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로미터(km)")){
                                    Double n2 = n1 * 0.0003048;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("인치(in)")){
                                    Double n2 = n1 * 12;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("피트(ft)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("밀리그램(mg)")){
                                if(sel2.equals("밀리그램(mg)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("그램(g)")){
                                    Double n2 = n1 * 0.001;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로그램(kg)")){
                                    Double n2 = n1 * 0.000001;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("톤(t)")){
                                    Double n2 = n1 * 0.000000001;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("온즈(oz)")){
                                    Double n2 = n1 * 0.000035274;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("파운드(lb)")){
                                    Double n2 = n1 * 0.00000220462;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("그램(g)")){
                                if(sel2.equals("밀리그램(mg)")){
                                    Double n2 = n1 * 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("그램(g)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로그램(kg)")){
                                    Double n2 = n1 * 0.001;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("톤(t)")){
                                    Double n2 = n1 * 0.000001;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("온즈(oz)")){
                                    Double n2 = n1 * 0.035274;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("파운드(lb)")){
                                    Double n2 = n1 * 0.00220462;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("킬로그램(kg)")){
                                if(sel2.equals("밀리그램(mg)")){
                                    Double n2 = n1 * 1000000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("그램(g)")){
                                    Double n2 = n1 * 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로그램(kg)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("톤(t)")){
                                    Double n2 = n1 * 0.001;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("온즈(oz)")){
                                    Double n2 = n1 * 35.274;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("파운드(lb)")){
                                    Double n2 = n1 * 2.20462;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("톤(t)")){
                                if(sel2.equals("밀리그램(mg)")){
                                    Double n2 = n1 * 1000000000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("그램(g)")){
                                    Double n2 = n1 * 1000000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로그램(kg)")){
                                    Double n2 = n1 * 1000;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("톤(t)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("온즈(oz)")){
                                    Double n2 = n1 * 35274;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("파운드(lb)")){
                                    Double n2 = n1 * 2204.62;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("온즈(oz)")){
                                if(sel2.equals("밀리그램(mg)")){
                                    Double n2 = n1 * 28349.5;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("그램(g)")){
                                    Double n2 = n1 * 28.3495;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로그램(kg)")){
                                    Double n2 = n1 * 0.0283495;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("톤(t)")){
                                    Double n2 = n1 * 0.0000283495;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("온즈(oz)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("파운드(lb)")){
                                    Double n2 = n1 * 0.0625;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("파운드(lb)")){
                                if(sel2.equals("밀리그램(mg)")){
                                    Double n2 = n1 * 453592;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("그램(g)")){
                                    Double n2 = n1 * 453.592;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("킬로그램(kg)")){
                                    Double n2 = n1 * 0.453592;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("톤(t)")){
                                    Double n2 = n1 * 0.000453592;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("온즈(oz)")){
                                    Double n2 = n1 * 16;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("파운드(lb)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("섭씨(C)")){
                                if(sel2.equals("섭씨(C)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("화씨(F)")){
                                    Double n2 = (n1 * 9/5) + 32;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("절대온도(K)")){
                                    Double n2 = n1 + 273.15;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("화씨(F)")){
                                if(sel2.equals("섭씨(C)")){
                                    Double n2 = (n1 - 32) * 5 / 9;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("화씨(F)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("절대온도(K)")){
                                    Double n2 = (n1 - 32) * 5 / 9 + 273.15;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                            else if(sel1.equals("절대온도(K)")){
                                if(sel2.equals("섭씨(C)")){
                                    Double n2 = n1 - 273.15;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("화씨(F)")){
                                    Double n2 = (n1 - 273.15) * 9/5 + 32;
                                    unit_text[op].setText(n2+"");
                                }
                                else if(sel2.equals("절대온도(K)")){
                                    Double n2 = n1;
                                    unit_text[op].setText(n2+"");
                                }
                            }
                        }
                    }
                });

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text0 = unit_text[0].getText().toString();
                        if(text0.length() > 1){
                            String text00 = text0.substring(0, text0.length()-1);
                            unit_text[0].setText(text00);
                            unit_text[1].setText("");

                            if(text00.contains(".")){
                                ddot_check = 1;
                            }
                            else{
                                ddot_check = 0;
                            }

                            if(text00.charAt(text00.length()-1) == '.'){
                                ddot = 1;
                            }
                            else{
                                ddot = 0;
                            }
                        }
                        else if(text0.length() == 1){
                            unit_text[0].setText("");
                            unit_text[1].setText("");
                            number = 0;
                        }
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "1";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "1";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "1";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "2";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "2";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "2";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "3";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "3";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "3";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "4";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "4";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "4";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "5";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "5";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "5";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "6";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "6";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "6";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "7";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "7";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "7";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "8";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "8";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "8";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "9";
                            unit_text[status].setText(txt);
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "9";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "9";
                            unit_text[status].setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = unit_text[status].getText().toString();
                            txt += "0";
                            unit_text[status].setText(txt);
                            number = -1;
                        }
                        else if(number == -1){
                            String txt = unit_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "0";
                            unit_text[status].setText(txt);
                        }
                        else if(number == 1|| ddot == 1){
                            String txt = unit_text[status].getText().toString();
                            txt += "0";
                            unit_text[status].setText(txt);
                            number = 1;
                        }
                        ddot = 0;
                    }
                });




                mBehavior = BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                unit_dialog.show();
                break;

            case GOLD_CALCULATOR:
                number = 0;
                ddot = 0;
                ddot_check = 0;

                BottomSheetDialog gold_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_gold_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_gold_cal));

                Spinner spinner = v.findViewById(R.id.gold_spinner);

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
                dot = v.findViewById(R.id.dot);
                result = v.findViewById(R.id.result);
                del = v.findViewById(R.id.del);
                move = v.findViewById(R.id.gonormal);
                clear = v.findViewById(R.id.clear);

                move.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setBottomSheetDialog(0);
                    }
                });

                result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str1 = textView1.getText().toString();
                        if(number == 0){
                            str1 = "0";
                        }
                        Double num1 = Double.parseDouble(str1);
                        String str = spinner.getSelectedItem().toString();
                        double tgram = Double.parseDouble(p1);

                        String od = p2.replace(",","");
                        double onedon = Double.parseDouble(od);

                        if(str.equals("돈")){
                            double res = num1 * onedon;
                            textView2.setText(String.format("%.2f", res)+" 원");
                           // textView2.setText(Double.toString(res)+" 원");
                        }
                        else if(str.equals("그램")){
                            double res = num1 * onedon / tgram;
                            textView2.setText(String.format("%.2f", res)+" 원");

                        }
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ddot_check == 0 && number == 1){
                            ddot_check = 1;
                            ddot = 1;
                            number = 0;
                            String txt = textView1.getText().toString();
                            txt += ".";
                            textView1.setText(txt);
                        }
                    }
                });

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text0 = textView1.getText().toString();
                        if(text0.length() > 1){
                            String text00 = text0.substring(0, text0.length()-1);
                            textView1.setText(text00);
                            textView2.setText("");

                            if(text00.contains(".")){
                                ddot_check = 1;
                            }
                            else{
                                ddot_check = 0;
                            }

                            if(text00.charAt(text00.length()-1) == '.'){
                                ddot = 1;
                            }
                            else{
                                ddot = 0;
                            }
                        }
                        else if(text0.length() == 1){
                            textView1.setText("");
                            textView2.setText("");
                            number = 0;
                        }
                    }
                });

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView1.setText("");
                        textView2.setText("");
                        number = 0;
                        ddot = 0;
                        ddot_check = 0;
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if(number == 0){
                           String txt = textView1.getText().toString();
                           txt += "1";
                           textView1.setText(txt);
                       }
                       else if(number == -1){
                           String txt = textView1.getText().toString();
                           txt = txt.substring(0,txt.length()-1) + "1";
                           textView1.setText(txt);
                       }
                       else if(number == 1){
                           String txt = textView1.getText().toString();
                           txt += "1";
                           textView1.setText(txt);
                       }
                       number = 1;
                       ddot = 0;
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "2";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "2";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "2";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "3";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "3";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "3";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "4";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "4";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "4";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "5";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "5";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "5";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "6";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "6";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "6";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "7";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "7";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "7";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "8";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "8";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "8";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "9";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "9";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "9";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt += "0";
                            textView1.setText(txt);
                            number = -1;
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "0";
                            textView1.setText(txt);
                        }
                        else if(number == 1 || ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "0";
                            textView1.setText(txt);
                            number = 1;
                        }
                        ddot = 0;
                    }
                });
                gold_dialog.setContentView(v);

                mBehavior = BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                gold_dialog.show();
                break;

            case DISCOUNT_CALCULATOR:
                number = 0;
                dis_number[0] = 0;
                dis_number[1] = 0;
                dis_dot[0] = 0;
                dis_dot[1] = 0;
                dis_dot_check[0] = 0;
                dis_dot_check[1] = 0;
                status = 0;
                BottomSheetDialog discount_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_discount_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_discount_cal));

                dis_text[0] = v.findViewById(R.id.textview1);
                dis_text[1] = v.findViewById(R.id.textview2);
                dis_text[2] = v.findViewById(R.id.textview3);
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
                dot = v.findViewById(R.id.dot);
                result = v.findViewById(R.id.result);
                del = v.findViewById(R.id.del);
                //move = v.findViewById(R.id.gonormal);
                clear = v.findViewById(R.id.clear);
                change = v.findViewById(R.id.changing);

                LinearLayout uplayout = v.findViewById(R.id.uplayout);
                LinearLayout downlayout = v.findViewById(R.id.downlayout);

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dis_text[0].setText("");
                        dis_text[1].setText("");
                        dis_text[2].setText("");
                        number = 0;
                        dis_number[0] = 0;
                        dis_number[1] = 0;
                        dis_dot[0] = 0;
                        dis_dot[1] = 0;
                        dis_dot_check[0] = 0;
                        dis_dot_check[1] = 0;
                    }
                });

                change.setOnClickListener(new View.OnClickListener() {    // down
                    @Override
                    public void onClick(View v) {
                        if(status == 0){
                            status = 1;
                            uplayout.setBackgroundResource(R.drawable.notfocus);
                            downlayout.setBackgroundResource(R.drawable.focus);
                        }
                        else{
                            status = 0;
                            uplayout.setBackgroundResource(R.drawable.focus);
                            downlayout.setBackgroundResource(R.drawable.notfocus);
                        }
                    }
                });

                result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[0] != 0 && dis_number[1] != 0){
                            double orig = Double.parseDouble(dis_text[0].getText().toString());
                            double perc = Double.parseDouble(dis_text[1].getText().toString());

                            double result = orig - (orig * perc / 100);

                            dis_text[2].setText(String.format("%.2f", result));
                            //dis_text[2].setText(Double.toString(result));
                        }
                    }
                });

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text0 = dis_text[status].getText().toString();
                        if(text0.length() > 1){
                            String text00 = text0.substring(0, text0.length()-1);
                            dis_text[status].setText(text00);
                            dis_text[2].setText("");

                            if(text00.contains(".")){
                                dis_dot_check[status] = 1;
                                //ddot_check = 1;
                            }
                            else{
                                dis_dot_check[status] = 0;
                            }

                            if(text00.charAt(text00.length()-1) == '.'){
                                dis_dot[status] = 1;
                            }
                            else{
                                dis_dot[status] = 1;
                            }
                        }
                        else if(text0.length() == 1){
                            dis_text[status].setText("");
                            dis_number[status] = 0;
                            dis_dot[status] = 0;
                            dis_dot_check[status] = 0;
                            dis_text[2].setText("");
                        }
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_dot_check[status] == 0 && dis_number[status] == 1){
                            dis_dot_check[status] = 1;
                            dis_dot[status] = 1;
                            dis_number[status] = 0;
                            String txt = dis_text[status].getText().toString();
                            txt += ".";
                            dis_text[status].setText(txt);
                        }
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "1";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "1";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "1";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "2";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "2";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "2";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "3";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "3";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "3";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "4";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "4";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "4";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "5";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "5";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "5";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "6";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "6";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "6";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "7";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "7";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "7";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "8";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "8";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "8";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "9";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "9";
                            dis_text[status].setText(txt);
                        }
                        else if(dis_number[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "9";
                            dis_text[status].setText(txt);
                        }
                        dis_number[status] = 1;
                        dis_dot[status] = 0;
                    }
                });
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dis_number[status] == 0 && dis_dot[status] == 0){
                            String txt = dis_text[status].getText().toString();
                            txt += "0";
                            dis_text[status].setText(txt);
                            dis_number[status] = -1;
                        }
                        else if(dis_number[status] == -1){
                            String txt = dis_text[status].getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "0";
                            dis_text[status].setText(txt);
                            dis_number[status] = 1;
                        }
                        else if(dis_number[status] == 1 || dis_dot[status] == 1){
                            String txt = dis_text[status].getText().toString();
                            txt += "0";
                            dis_text[status].setText(txt);
                            dis_number[status] = 1;
                        }
                        dis_dot[status] = 0;
                    }
                });



                discount_dialog.setContentView(v);

                mBehavior = BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                discount_dialog.show();
                break;

            case MONEY_CALCULATOR:
                number = 0;
                ddot = 0;
                ddot_check = 0;

                BottomSheetDialog money_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_money_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_money_cal));
                money_dialog.setContentView(v);

                Double us = Double.parseDouble(usa);
                Double er = Double.parseDouble(eur);
                Double jp = Double.parseDouble(jap);
                Double ch = Double.parseDouble(chi);

                Spinner spinner1 = v.findViewById(R.id.money_sp1);
                Spinner spinner2 = v.findViewById(R.id.money_sp2);
                spinner2.setSelection(1);

                textView1 = v.findViewById(R.id.textview1);
                textView2 = v.findViewById(R.id.textview2);
                textView3 = v.findViewById(R.id.textview3);
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
                dot = v.findViewById(R.id.dot);
                result = v.findViewById(R.id.result);
                del = v.findViewById(R.id.del);
                move = v.findViewById(R.id.gonormal);
                change = v.findViewById(R.id.changing);

                move.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setBottomSheetDialog(0);
                    }
                });

                result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str1 = spinner1.getSelectedItem().toString();
                        String str2 = spinner2.getSelectedItem().toString();

                        if(number != 0 && ddot != 1) {
                            if (str1.equals("한국")) {
                                if (str2.equals("한국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    Double m2 = m1;
                                    textView2.setText(m2 + "");
                                    textView3.setText("원");
                                } else if (str2.equals("미국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 / us);
                                    textView2.setText(m2 + "");
                                    textView3.setText("달러");
                                } else if (str2.equals("유럽")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 / er);
                                    textView2.setText(m2 + "");
                                    textView3.setText("유로");
                                } else if (str2.equals("일본")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 / jp);
                                    textView2.setText(m2 + "");
                                    textView3.setText("엔");
                                } else if (str2.equals("중국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 / ch);
                                    textView2.setText(m2 + "");
                                    textView3.setText("위안");
                                }
                            } else if (str1.equals("미국")) {
                                if (str2.equals("한국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * us);
                                    textView2.setText(m2+"");
                                    textView3.setText("원");
                                } else if (str2.equals("미국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    Double m2 = m1;
                                    textView2.setText(m2 + "");
                                    textView3.setText("달러");
                                } else if (str2.equals("유럽")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * us / er);
                                    textView2.setText(m2+"");
                                    textView3.setText("유로");
                                } else if (str2.equals("일본")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * us / jp);
                                    textView2.setText(m2 + "");
                                    textView3.setText("엔");
                                } else if (str2.equals("중국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * us / ch);
                                    textView2.setText(m2 + "");
                                    textView3.setText("위안");
                                }
                            } else if (str1.equals("유럽")) {
                                if (str2.equals("한국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * er);
                                    textView2.setText(m2 + "");
                                    textView3.setText("원");
                                } else if (str2.equals("미국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * er / us);
                                    textView2.setText(m2 + "");
                                    textView3.setText("달러");
                                } else if (str2.equals("유럽")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    Double m2 = m1;
                                    textView2.setText(m2 + "");
                                    textView3.setText("유로");
                                } else if (str2.equals("일본")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * er / jp);
                                    textView2.setText(m2 + "");
                                    textView3.setText("엔");
                                } else if (str2.equals("중국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * er / ch);
                                    textView2.setText(m2 + "");
                                    textView3.setText("위안");
                                }
                            } else if (str1.equals("일본")) {
                                if (str2.equals("한국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * jp);
                                    textView2.setText(m2 + "");
                                    textView3.setText("원");
                                } else if (str2.equals("미국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * jp / us);
                                    textView2.setText(m2 + "");
                                    textView3.setText("달러");
                                } else if (str2.equals("유럽")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * jp / er);
                                    textView2.setText(m2 + "");
                                    textView3.setText("유로");
                                } else if (str2.equals("일본")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    Double m2 = m1;
                                    textView2.setText(m2 + "");
                                    textView3.setText("엔");
                                } else if (str2.equals("중국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * jp / ch);
                                    textView2.setText(m2 + "");
                                    textView3.setText("위안");
                                }
                            } else if (str1.equals("중국")) {
                                if (str2.equals("한국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * ch);
                                    textView2.setText(m2 + "");
                                    textView3.setText("원");
                                } else if (str2.equals("미국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * ch / us);
                                    textView2.setText(m2 + "");
                                    textView3.setText("달러");
                                } else if (str2.equals("유럽")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * ch / er);
                                    textView2.setText(m2 + "");
                                    textView3.setText("유로");
                                } else if (str2.equals("일본")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    String m2 = String.format("%.2f", m1 * ch / jp);
                                    textView2.setText(m2 + "");
                                    textView3.setText("엔");
                                } else if (str2.equals("중국")) {
                                    String s1 = textView1.getText().toString();
                                    Double m1 = Double.parseDouble(s1);
                                    Double m2 = m1;
                                    textView2.setText(m2 + "");
                                    textView3.setText("위안");
                                }
                            }
                        }
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ddot_check == 0 && number == 1){
                            ddot_check = 1;
                            ddot = 1;
                            number = 0;
                            String txt = textView1.getText().toString();
                            txt += ".";
                            textView1.setText(txt);
                        }
                    }
                });

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text0 = textView1.getText().toString();
                        if(text0.length() > 1){
                            String text00 = text0.substring(0, text0.length()-1);
                            textView1.setText(text00);
                            textView2.setText("");
                            textView3.setText("");

                            if(text00.contains(".")){
                                ddot_check = 1;
                            }
                            else{
                                ddot_check = 0;
                            }

                            if(text00.charAt(text00.length()-1) == '.'){
                                ddot = 1;
                            }
                            else{
                                ddot = 0;
                            }
                        }
                        else if(text0.length() == 1){
                            textView1.setText("");
                            textView2.setText("");
                            textView3.setText("");
                            number = 0;
                        }
                    }
                });

                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int p1 = spinner1.getSelectedItemPosition();
                        int p2 = spinner2.getSelectedItemPosition();

                        spinner1.setSelection(p2);
                        spinner2.setSelection(p1);

                        textView1.setText("");
                        textView2.setText("");
                        textView3.setText("");
                        number = 0;
                        ddot = 0;
                        ddot_check = 0;
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "1";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "1";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "1";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "2";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "2";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "2";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "3";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "3";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "3";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "4";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "4";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "4";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "5";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "5";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "5";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "6";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "6";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "6";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "7";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "7";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "7";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "8";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "8";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "8";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0){
                            String txt = textView1.getText().toString();
                            txt += "9";
                            textView1.setText(txt);
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "9";
                            textView1.setText(txt);
                        }
                        else if(number == 1){
                            String txt = textView1.getText().toString();
                            txt += "9";
                            textView1.setText(txt);
                        }
                        number = 1;
                        ddot = 0;
                    }
                });
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number == 0 && ddot == 0){
                            String txt = textView1.getText().toString();
                            txt += "0";
                            textView1.setText(txt);
                            number = -1;
                        }
                        else if(number == -1){
                            String txt = textView1.getText().toString();
                            txt = txt.substring(0,txt.length()-1) + "0";
                            textView1.setText(txt);
                        }
                        else if(number == 1 || ddot == 1){
                            String txt = textView1.getText().toString();
                            txt += "0";
                            textView1.setText(txt);
                            number = 1;
                        }
                        ddot = 0;
                    }
                });


                mBehavior = BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                money_dialog.show();
                break;

            case SCHOOL_CALCULATOR:
                list.clear();
                number = 0;
                BottomSheetDialog school_dialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_school_cal,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer_school_cal));
                total1 = v.findViewById(R.id.total1);
                total2 = v.findViewById(R.id.total2);
                recyclerView = v.findViewById(R.id.school_rcv);
                LinearLayoutManager manager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);


                list.add(new School_item(null,null,null,2));
                adapter = new School_Adapter(list, v.getContext());
                adapter.setOnPlusClickListener(new School_Adapter.OnPlusClickListener() {
                    @Override
                    public void onPlusClick(View v, int position) {
                        list.remove(position);
                        Intent intent = new Intent(v.getContext(), PopupActivity.class);
                        intent.putExtra("pos", position);
                        startActivityForResult(intent, 1);

                        //list.add(new School_item(1));
                        //list.add(new School_item(2));
                        //adapter.notifyItemRangeChanged(position, 2);
                    }
                });
                adapter.setOnOtherClickListener(new School_Adapter.OnOtherClickListener() {
                    @Override
                    public void onOtherClick(View v, int position) {
                        /*String name = list.get(position).getName();
                        String score1 = list.get(position).getScore1();
                        Double d1 = Double.parseDouble(score1);
                        String score2 = list.get(position).getScore2();
                        Double d2 = Double.parseDouble(score2);

                        String t1 = total1.getText().toString();
                        Double d3 = Double.parseDouble(t1);

                        String t2 = total2.getText().toString();
                        Double d4 = Double.parseDouble(t2);

                        Double res = d3 - d1;
                        total1.setText(String.format("%.1f", res));


                        if(res == 0){
                            Double res2 = res;
                            total2.setText(String.format("%.2f", res2));
                        }
                        else{
                            Double res2 = ((d3*d4) - (d1*d2))/res;
                            total2.setText(String.format("%.2f", res2));
                        }*/

                        double total111 = 0.0;
                        double total222 = 0.0;

                        list.remove(position);

                        for(int i=0;i<list.size() - 1;i++){
                            String s1 = list.get(i).getScore1();
                            String s2 = list.get(i).getScore2();

                            Double n1 = Double.parseDouble(s1);
                            Double n2 = Double.parseDouble(s2);

                            total111 += n1;
                            total222 += n1 * n2;
                        }

                        total1.setText(String.format("%.2f", total111));

                        Double ans = 0.0;
                        if(total111 == 0){
                            ans = 0.0;
                        }
                        else{
                            ans = total222 / total111;
                        }

                        total2.setText(String.format("%.2f", ans));

                        adapter.notifyItemRemoved(position);
                    }
                });
                recyclerView.setAdapter(adapter);




                school_dialog.setContentView(v);

                mBehavior = BottomSheetBehavior.from((View)v.getParent());
                mBehavior.setPeekHeight(4000);
                school_dialog.show();
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("pos", 0);
                String name = data.getStringExtra("name");
                String score1 = data.getStringExtra("score1");
                String score2 = data.getStringExtra("score2");

                list.add(new School_item(name, score1, score2, 1));
                list.add(new School_item(null, null, null, 2));
                adapter.notifyItemRangeChanged(pos, 2);

                double total11 = 0.0;
                double total22 = 0.0;

                for(int i=0;i<list.size() - 1;i++){
                    String s1 = list.get(i).getScore1();
                    String s2 = list.get(i).getScore2();

                    Double n1 = Double.parseDouble(s1);
                    Double n2 = Double.parseDouble(s2);

                    total11 += n1;
                    total22 += n1 * n2;
                }

                total1.setText(String.format("%.2f", total11));

                Double ans = total22 / total11;

                total2.setText(String.format("%.2f", ans));


                /*String t1 = total1.getText().toString();
                Double n1 = Double.parseDouble(t1);

                String t2 = score1;
                Double n2 = Double.parseDouble(score1);

                Double res = n1 + n2;
                //total1.setText(res+"");
                total1.setText(String.format("%.2f", res));

                //현재 평점
                String t3 = total2.getText().toString();

                *//*if(score2.equals("PASS")){
                    Double n3 = Double.parseDouble(t3);

                    String t4 = t3;
                    Double n4 = Double.parseDouble(t4);

                    Double res2 = (n3 * n1 + n4 * n2)/res;
                    //total2.setText(res2+"");
                    total2.setText(String.format("%.2f", res2));
                }
                else if(score2.equals("FAIL")){

                    Double n3 = Double.parseDouble(t3);

                    String t4 = "0.0";
                    Double n4 = Double.parseDouble(t4);

                    Double res2 = (n3 * n1 + n4 * n2)/res;
                    //total2.setText(res2+"");
                    total2.setText(String.format("%.2f", res2));
                }
                else{


                }*//*

                Double n3 = Double.parseDouble(t3);

                String t4 = score2;
                Double n4 = Double.parseDouble(t4);

                Double res2 = (n3 * n1 + n4 * n2)/res;
                //total2.setText(res2+"");
                total2.setText(String.format("%.2f", res2));*/

            }
        }
    }







    private String getCalculate(String content) {

        char[] operationCode = {'+', '-', '*', '/', '(', ')'}; //연산 부호

        ArrayList<String> postfixList = new ArrayList<String>(); //후위표기법으로 변환 후 저장 할 ArrayList
        Stack<Character> opStack = new Stack<Character>(); // 연산 부호 우선순위처리 하며 후위 표기법으로 변경하는 Stack
        Stack<String> calculatorStack = new Stack<String>(); //후위 표기법을 계산하는 Stack

        int index = 0;//content.substring() 인수

        for (int i = 0; i < content.length(); i++) {
            for (int j = 0; j < operationCode.length; j++) {
                if (content.charAt(i) == operationCode[j]) { //문자열과 연산 부호 비교

                    //postfixList에 연산 부호가 나오기 전까지의 숫자를 담는다(공백제거)
                    postfixList.add(content.substring(index, i).trim().replace("(", "").replace(")", ""));
                    if (content.charAt(i) == '(') {
                        if (content.charAt(i) == ')') {//우 괄호가 나오면 좌 괄호가 나오거나 스택에 비어있을때 까지 pop하여 list에 저장
                            while (true) {
                                postfixList.add(opStack.pop().toString());
                                if (opStack.pop() == '(' || opStack.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }

                    if (opStack.isEmpty()) { //opStack이 비어 있을 경우
                        opStack.push(operationCode[j]); //연산 부호 저장
                    } else { //opStack이 비어 있지 않을 경우
                        if (opOrder(operationCode[j]) > opOrder(opStack.peek())) { //우선 순위 비교
                            opStack.push(operationCode[j]); //스택에 top 값 보다 높은 우선순위이면 그대로 저장
                        } else if (opOrder(operationCode[j]) <= opOrder(opStack.peek())) {//우선 순위 비교
                            postfixList.add(opStack.peek().toString());//스택에 있는 값이 우선순위가 같거나 작을 경우 list에 저장
                            opStack.pop();//스택 제거
                            opStack.push(operationCode[j]);//높은 우선순위 연산 부호 스택에 저장
                        }
                    }
                    index = i + 1;// 다음 순서 처리
                }
            }
        }
        postfixList.add(content.substring(index, content.length()).trim().replace("(", "").replace(")", "")); //마지막 숫자 처리

        if (!opStack.isEmpty()) { //Stack에 남아있는 연산 모두 postfixList에 추가
            for (int i = 0; i < opStack.size();) {
                postfixList.add(opStack.peek().toString());
                opStack.pop();
            }
        }

        //list에 공백, 괄호 제거
        for (int i = 0; i < postfixList.size(); i++) {
            if (postfixList.get(i).equals("")) {
                postfixList.remove(i);
                i = i - 1;
            } else if (postfixList.get(i).equals("(")) {
                postfixList.remove(i);
                i = i - 1;
            } else if (postfixList.get(i).equals(")")) {
                postfixList.remove(i);
                i = i - 1;
            }
        }

        System.out.println(postfixList);

        opStack.clear(); //Stack 비우기

        //postfixList를 calculatorStack에 저장하면서 후위연산 처리
        for (int i = 0; i < postfixList.size(); i++) {
            calculatorStack.push(postfixList.get(i));
            for (int j = 0; j < operationCode.length; j++) {
                if (postfixList.get(i).charAt(0) == operationCode[j]) { //연산 부호 비교
                    calculatorStack.pop(); //stack에 저장된 연산 부호 제거
                    double s2, s1; //stack에서 pop 되는 값들을 저장할 변수
                    String rs; // 연산 처리 후 문자열로 변환 후 stack에 저장할 변수

                    s2 = Double.parseDouble(calculatorStack.pop()); //스택에서 pop하여 문자열을 숫자로 형변환
                    s1 = Double.parseDouble(calculatorStack.pop());

                    //연산 부호에 해당하는 산술 처리 후 stack에 저장
                    switch (operationCode[j]) {
                        case '+':
                            rs = String.valueOf(s1 + s2);
                            calculatorStack.push(rs);
                            break;
                        case '-':
                            rs = String.valueOf(s1 - s2);
                            calculatorStack.push(rs);
                            break;
                        case '*':
                            rs = String.valueOf(s1 * s2);
                            calculatorStack.push(rs);
                            break;
                        case '/':
                            rs = String.valueOf(s1 / s2);
                            calculatorStack.push(rs);
                            break;
                    }
                }
            }
        }

        double re = Double.parseDouble(calculatorStack.peek()); //Stack Top 데이터
        String result = String.format("%.10f", re); //소수점 10째짜리

        //정수 부분 자리 구하기
        int num = 0;
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '.') {
                num = i;
                break;
            }
        }

        //정수부분
        String mok = result.substring(0, num);

        //나머지 연산
        double divde = Double.parseDouble(result) % Double.parseDouble(mok);

        remove = -1;
        //나머지가 0이면 소수점 자릿 수 안보이게
        if (divde == 0) {
            result = String.format("%.0f", re);
            remove = 1;
        }

        return result;
    }

    public int opOrder(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

}