package com.viveksb007.livewallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends Activity {

    RadioGroup mLiveWallpaperChoice;
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mLiveWallpaperChoice = (RadioGroup) findViewById(R.id.rgLW);
        Button changeLW = (Button) findViewById(R.id.btnChangeLW);
        preferences = getSharedPreferences("params", MODE_PRIVATE);
        changeLW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                Intent intent;
                switch (mLiveWallpaperChoice.getCheckedRadioButtonId()) {
                    case R.id.rbSpiral:
                        intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        editor.putFloat("scaleX", 1.7f);
                        editor.putFloat("scaleY", 2.55f);
                        editor.putInt("x", -15);
                        editor.putInt("y", 0);
                        editor.putString("loadGIF", "classicspiral.gif");
                        editor.commit();
                        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getApplicationContext(), GIFWallpaperService.class));
                        destroyWallpaper();
                        startActivity(intent);
                        break;
                    case R.id.rbRasengan:
                        intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        editor.putFloat("scaleX", 3f);
                        editor.putFloat("scaleY", 5f);
                        editor.putInt("x", -25);
                        editor.putInt("y", 10);
                        editor.putString("loadGIF", "rasengan.gif");
                        editor.commit();
                        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getApplicationContext(), GIFWallpaperService.class));
                        destroyWallpaper();
                        startActivity(intent);
                        break;
                    case R.id.rbRasengan1:
                        intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        editor.putFloat("scaleX", 3f);
                        editor.putFloat("scaleY", 6f);
                        editor.putInt("x", 0);
                        editor.putInt("y", 10);
                        editor.putString("loadGIF", "rasengan1.gif");
                        editor.commit();
                        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getApplicationContext(), GIFWallpaperService.class));
                        destroyWallpaper();
                        startActivity(intent);
                        break;
                    case R.id.rbCat:
                        intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        editor.putFloat("scaleX", 2.2f);
                        editor.putFloat("scaleY", 5f);
                        editor.putInt("x", -50);
                        editor.putInt("y", 0);
                        editor.putString("loadGIF", "cat.gif");
                        editor.commit();
                        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getApplicationContext(), GIFWallpaperService.class));
                        destroyWallpaper();
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Check Radio Button First", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void destroyWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
