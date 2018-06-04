package com.thien.rssredaer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView,recyclerView1,recyclerView2;
    String[] address = {"https://www.24h.com.vn/upload/rss/tintuctrongngay.rss",
            "https://vnexpress.net/rss/tin-moi-nhat.rss",
            "https://tinhte.vn/rss"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);


        //Call Read rss asyntask to fetch rss
        ReadRss readRss = new ReadRss(this, recyclerView);
        readRss.execute(address[0],"24h");

        ReadRss readRss1 = new ReadRss(this, recyclerView1);
        readRss1.execute(address[1],"vnexpress");

        ReadRss readRss2 = new ReadRss(this, recyclerView2);
        readRss2.execute(address[2],"kenh14");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView1.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.GONE);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_24h:
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView1.setVisibility(View.GONE);
                                recyclerView2.setVisibility(View.GONE);
                                break;
                            case R.id.action_vnexpress:
                                recyclerView.setVisibility(View.GONE);
                                recyclerView1.setVisibility(View.VISIBLE);
                                recyclerView2.setVisibility(View.GONE);
                                break;
                            case R.id.action_tinhte:
                                recyclerView.setVisibility(View.GONE);
                                recyclerView1.setVisibility(View.GONE);
                                recyclerView2.setVisibility(View.VISIBLE);
                                break;
                        }
                        return false;
                    }
                });


    }

}
