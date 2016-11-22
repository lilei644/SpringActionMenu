package com.lilei.example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

public class MainActivity extends AppCompatActivity {

    private ActionMenu actionMenuTop;
    private ActionMenu actionMenuBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionMenuTop = (ActionMenu) findViewById(R.id.actionMenuTop);
        actionMenuBottom = (ActionMenu) findViewById(R.id.actionMenuBottom);

        // add menu items
        actionMenuTop.addView(R.drawable.search, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenuTop.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenuTop.addView(R.drawable.write);

        actionMenuBottom.addView(R.drawable.search, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenuBottom.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenuBottom.addView(R.drawable.write);


        actionMenuBottom.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                showMessage("Click " + index);
            }

            @Override
            public void onAnimationEnd(boolean isOpen) {
         //       showMessage("animation end : " + isOpen);
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
