package com.lilei.example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

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


        actionMenu.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                showMessage("Click " + index);
            }

            @Override
            public void onAnimationEnd(boolean isOpen) {
                showMessage("animation end : " + isOpen);
            }
        });
    }


    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }

    private void showMessage(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
