package com.example.btckandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class chi_tiet extends AppCompatActivity {

    private TextView tvCountry1,tvCases1,tvRecovered1,tvCritical1,tvActive1,tvTodayCases1,tvTotalDeaths1,tvTodayDeaths1,textViewDate1;

    private Database database = new Database(this, "LuuThongTin.sqlite", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        anhXa();

        //Tạo, lấy intent
        Intent intent = getIntent();
        String id =(String) intent.getSerializableExtra("id");

        //Lấy dữ liệu
        Cursor one_line_data = database.GetData("SELECT * FROM ThongTin WHERE Id ='"+id+"'");
        one_line_data.moveToNext();

        Toast.makeText(this, one_line_data.getString(1), Toast.LENGTH_SHORT).show();

        textViewDate1.setText(one_line_data.getString(1));
        tvCountry1.setText(one_line_data.getString(2));
        tvCases1.setText(one_line_data.getString(3));
        tvRecovered1.setText(one_line_data.getString(4));
        tvCritical1.setText(one_line_data.getString(5));
        tvActive1.setText(one_line_data.getString(6));
        tvTodayCases1.setText(one_line_data.getString(7));
        tvTotalDeaths1.setText(one_line_data.getString(8));
        tvTodayDeaths1.setText(one_line_data.getString(9));
    }


    void anhXa(){
        textViewDate1 = findViewById(R.id.text_view_date1);
        tvCountry1 = findViewById(R.id.tvCountry1);
        tvCases1 = findViewById(R.id.tvCases1);
        tvRecovered1 = findViewById(R.id.tvRecovered1);
        tvCritical1 = findViewById(R.id.tvCritical1);
        tvActive1 = findViewById(R.id.tvActive1);
        tvTodayCases1 = findViewById(R.id.tvTodayCases1);
        tvTotalDeaths1 = findViewById(R.id.tvDeaths1);
        tvTodayDeaths1 = findViewById(R.id.tvTodayDeaths1);
    }
}