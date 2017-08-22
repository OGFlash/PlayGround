package com.example.owner.testtwo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Owner on 7/20/2017.
 */

public class StandAloneToolBar extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        mtoolbar.setTitle("StandAlonToolBar");
        mtoolbar.setSubtitle("by Andrew King");

        mtoolbar.inflateMenu(R.menu.menu_main);
        mtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String title = (String) item.getTitle();
                Toast.makeText(StandAloneToolBar.this, title + " Was Selected", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
