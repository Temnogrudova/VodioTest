package com.temnogrudova.vodiotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<ArrayList<String>> imageIDs;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        imageIDs = new ArrayList<ArrayList<String>>();
        ArrayList<String> s1 = new ArrayList<String>();
        s1.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number9.jpg");
        s1.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number6.jpg");
        s1.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number6.jpg");
        ArrayList<String> s2 = new ArrayList<String>();
        s2.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number1.jpg");
        s2.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number6.jpg");
        s2.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number6.jpg");
        s2.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number6.jpg");
        ArrayList<String> s3 = new ArrayList<String>();
        s3.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number7.jpg");
        s3.add("http://www.kidsmathgamesonline.com/images/pictures/numbers600//number7.jpg");
        imageIDs.add(s1);
        imageIDs.add(s2);
        imageIDs.add(s3);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new MainListAdapter(this));
    }

    public class MainListAdapter extends BaseAdapter
    {
        private Context context;

        public MainListAdapter(Context c)
        {
            context = c;
        }

        //---returns the number of images---
        public int getCount() {
            return imageIDs.size();
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        //---returns the ID of an item---
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            HorizontalScrollView scrollView = (HorizontalScrollView) inflater.inflate(R.layout.pattern, null, false);
            final LinearLayout l = (LinearLayout) scrollView.findViewById(R.id.myL);

            for (int i = 0; i< imageIDs.get(position).size();i++){
                 final ImageView imageView = new ImageView(context);


                AsyncTask<String, Void, Bitmap> t = new AsyncTask<String, Void, Bitmap>() {
                    protected Bitmap doInBackground(String... p) {
                        Bitmap bm = null;
                        try {
                            String s = p[0];
                            URL aURL = new URL(s);
                            URLConnection conn = aURL.openConnection();
                            conn.setUseCaches(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);
                            bm = BitmapFactory.decodeStream(bis);
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return bm;
                    }

                    protected void onPostExecute(final Bitmap bm) {
                        try {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            Drawable drawable = new BitmapDrawable(context.getResources(),bm);
                            imageView.setImageDrawable(drawable);
                            l.addView(imageView);
                        }catch (NullPointerException e){
                            imageView.setImageResource(R.drawable.pic1);
                        }


                    }
                };
                t.execute(imageIDs.get(position).get(i));

            }
            return scrollView;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
