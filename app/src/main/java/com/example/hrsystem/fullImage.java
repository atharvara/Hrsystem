package com.example.hrsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class fullImage extends AppCompatActivity {
    String url;
    Bitmap bitmap;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img=(ImageView) findViewById(R.id.imageView);
        url = getIntent().getStringExtra("imageurl");
        ImageLoadTask obj = new ImageLoadTask(url, img);
        obj.execute();
    }

    class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL connection = new URL(url);
                InputStream input = connection.openStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 400, 400, true);
                return resized;
            } catch (Exception e) {
                new Thread() {
                    public void run() {
                        fullImage.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }.start();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }


}