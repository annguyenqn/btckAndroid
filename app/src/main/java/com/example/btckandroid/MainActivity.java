package com.example.btckandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tvCases , tvRecovered, tvCritical, tvActive, tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries;
    //SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Database
        database = new Database(this, "LuuThongTin.sqlite", null, 1);

        //Create Table
        database.QueryData("CREATE TABLE IF NOT EXISTS ThongTin(Id INTEGER PRIMARY KEY AUTOINCREMENT, currentDay VARCHAR(200) , country VARCHAR(200), cases VARCHAR(200), recovered VARCHAR(200), critical VARCHAR(200), active VARCHAR(200), todayCases VARCHAR(200), totalDeaths VARCHAR(200), todayDeaths VARCHAR(200))");



        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);;
        tvActive = findViewById(R.id.tvActive);;
        tvTodayCases = findViewById(R.id.tvTodayCases);;
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);;
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);;;
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);;;
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);
        fetchData();
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all/";
        //simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();
                    //  simpleArcLoader.stop();
                    //  simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);



                } catch (JSONException e) {
                    e.printStackTrace();
                    //  simpleArcLoader.stop();
                    //  simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  simpleArcLoader.stop();
                //  simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request); //Đoạn này làm nó văng khỏi app
    }

    public void goTrackCountries(View view) {


       startActivity(new Intent(getApplicationContext(),AffectedCountries.class));

    }
}