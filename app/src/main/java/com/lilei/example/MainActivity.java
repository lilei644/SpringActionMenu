package com.lilei.example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lilei.springactionmenu.ActionMenu;

public class MainActivity extends AppCompatActivity {

    private ActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionMenu = (ActionMenu) findViewById(R.id.actionMenu);

        // add menu items
        actionMenu.addView(R.drawable.search, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenu.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenu.addView(R.drawable.write);
    }


    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }
}
