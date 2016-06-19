package com.viveksb007.livewallpaper;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

public class GIFWallpaperService extends WallpaperService {


    private float scaleX = 3f, scaleY = 3f;
    private int x = 0, y = 0;
    private String loadGIF = "classicspiral.gif";

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public Engine onCreateEngine() {
        try {
            SharedPreferences preferences = getSharedPreferences("params",MODE_PRIVATE);
            scaleX = preferences.getFloat("scaleX",3f);
            scaleY = preferences.getFloat("scaleY",3f);
            x = preferences.getInt("x",0);
            y = preferences.getInt("y",0);
            loadGIF = preferences.getString("loadGIF","classicspiral.gif");
            Movie movie = Movie.decodeStream(getResources().getAssets().open(loadGIF));
            return new GIFWallpaperEngine(movie);
        } catch (IOException e) {
            Log.d("GIF", "Could not LOAD GIF");
            return null;
        }
    }

    private class GIFWallpaperEngine extends WallpaperService.Engine {
        private final int frameDuration = 20;

        private SurfaceHolder holder;
        private Movie movie;
        private boolean visible;
        private Handler handler;

        public GIFWallpaperEngine(Movie movie) {
            this.movie = movie;
            handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        private Runnable drawGIF = new Runnable() {
            public void run() {
                draw();
            }
        };

        private void draw() {
            if (visible) {
                Canvas canvas = holder.lockCanvas();
                canvas.save();
                // Adjust size and position so that
                // the image looks good on your screen
                canvas.scale(scaleX, scaleY);
                movie.draw(canvas, x, y);
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                handler.removeCallbacks(drawGIF);
                handler.postDelayed(drawGIF, frameDuration);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawGIF);
            } else {
                handler.removeCallbacks(drawGIF);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawGIF);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawGIF);
        }
    }

}
