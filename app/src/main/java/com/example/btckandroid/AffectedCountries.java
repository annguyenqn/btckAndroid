package com.example.btckandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {
 EditText edtSearch;
 ListView listView ;
 public  static List<CountryModel> countryModelList = new ArrayList<>();
 CountryModel countryModel;
 MyCustomAdapter myCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
         edtSearch = findViewById(R.id.edtSearch);
         listView = findViewById(R.id.listView);
        fetchData();
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/countries/";
        //simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0 ; i<jsonArray.length();i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        String countryName = jsonObject.getString("country");
                        String   cases= jsonObject.getString("cases");
                        String   recovered= jsonObject.getString("recovered");
                        String   critical= jsonObject.getString("critical");
                        String    active= jsonObject.getString("active");
                        String  todayCases= jsonObject.getString("todayCases");
                        String  deaths= jsonObject.getString("deaths");
                        String   todayDeaths= jsonObject.getString("todayDeaths");


                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");

                       countryModel = new CountryModel(flagUrl, countryName,cases, todayCases,deaths, todayDeaths,recovered,active,  critical);
                       countryModelList.add(countryModel);
                    }
                    myCustomAdapter = new MyCustomAdapter(AffectedCountries.this,countryModelList);
                    listView.setAdapter(myCustomAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AffectedCountries.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request); //Đoạn này làm nó văng khỏi app
    }
}