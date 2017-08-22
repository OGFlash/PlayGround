package com.example.owner.testtwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Owner on 7/20/2017.
 */

public class ActionToolBar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstantstate){
        super.onCreate(saveInstantstate);
        setContentView(R.layout.activity_toolbar);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setTitle("Action Bar ToolBar");
        mToolBar.setSubtitle("By Andrew Michael King");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()){
            case R.id.save_menu:
                msg = "Save!";
                break;
            case R.id.settings_menu:
                msg = "Settings!";
                break;
            case R.id.message_menu:
                msg = "message!";
                break;
            case R.id.camera_menu:
                msg = "camera!";
                break;
            case R.id.share_menu:
                msg = "share!";
                break;
            case R.id.nav_share:
                msg = "nav share!";
                break;
            case R.id.nav_send:
                msg = "nav send!";
                break;
            case R.id.communication_menu:
                msg = "Communication";
                break;
            default:
                msg = "Not Found";
                break;
        }
        Toast.makeText(this, msg + "Clicked !", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
