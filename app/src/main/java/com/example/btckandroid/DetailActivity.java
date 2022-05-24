package com.example.btckandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    private  int positionCountry;
    private TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,textViewDate;
    private ListView listView2;
    private int point=1;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> listShowDay = new ArrayList<>();

    private Database database = new Database(this, "LuuThongTin.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);
        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);
        getSupportActionBar().setTitle("Details of "+AffectedCountries.countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCountry = findViewById(R.id.tvCountry);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        listView2 = findViewById(R.id.listView2);

        //Ánh xạ adapter vào listShowDay
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listShowDay);



        tvCountry.setText(AffectedCountries.countryModelList.get(positionCountry).getCountry());
        tvCases.setText(AffectedCountries.countryModelList.get(positionCountry).getCases());
        tvRecovered.setText(AffectedCountries.countryModelList.get(positionCountry).getRecovered());
        tvCritical.setText(AffectedCountries.countryModelList.get(positionCountry).getCritical());
        tvActive.setText(AffectedCountries.countryModelList.get(positionCountry).getActive());
        tvTodayCases.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(AffectedCountries.countryModelList.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayDeaths());

        load_ListView_Data();

        //Chỉ định ContextMenu áp dụng vào đâu
        registerForContextMenu(listView2);

        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                point = i;
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void load_ListView_Data(){

        Cursor tableThongTin = database.GetData("SELECT * FROM ThongTin WHERE country = '"+tvCountry.getText()+"'");
        listShowDay.clear();
        while (tableThongTin.moveToNext()){
            String day = tableThongTin.getString(1);
            listShowDay.add(day);
        }

        listView2.setAdapter(adapter);
    }

    //Nút lưu
    public void Add(View view) {
        Cursor check = database.GetData("SELECT * FROM ThongTin WHERE currentDay = '"+textViewDate.getText()+"' AND country = '"+tvCountry.getText()+"'");
        if(check.getCount() == 0) {
            //Insert Data
            database.QueryData("INSERT INTO ThongTin VALUES(null, '" + textViewDate.getText() + "', '" + tvCountry.getText() + "', '" + tvCases.getText() + "', '" + tvRecovered.getText() + "', '" + tvCritical.getText() + "', '" + tvActive.getText() + "', '" + tvTodayCases.getText() + "', '" + tvTotalDeaths.getText() + "', '" + tvTodayDeaths.getText() + "')");
            load_ListView_Data();
            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Dữ liệu hôm nay đã được lưu", Toast.LENGTH_SHORT).show();
    }

    //Khai báo ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    //Tạo và xử lý sự kiện các Item trong ContextMenu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String currentDay = listView2.getItemAtPosition(point).toString();
        switch (item.getItemId())
        {
            //Xử lý sự kiện nhấn Item xóa trong ContextMenu
            case R.id.item_Xoa:
            {
                database.QueryData("DELETE FROM ThongTin WHERE currentDay = '"+currentDay+"' AND country = '"+tvCountry.getText()+"'");
                load_ListView_Data();
                Toast.makeText(this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                break;
            }
            //Xử lý sự kiện nhấn Item xem trong ContextMenu
            case R.id.item_Xem:
            {
                //Lấy dữ liệu
                Cursor id = database.GetData("SELECT id FROM ThongTin WHERE currentDay = '"+currentDay+"' AND country = '"+tvCountry.getText()+"'");
                id.moveToNext();
                //Tạo, gán dữ liệu và chạy intent
                Intent intent = new Intent(DetailActivity.this, chi_tiet.class);
                intent.putExtra("id", id.getString(0));
                startActivity(intent);
            }
        }
        return super.onContextItemSelected(item);
    }
}