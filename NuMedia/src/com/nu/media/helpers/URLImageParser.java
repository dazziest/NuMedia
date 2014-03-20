package com.nu.media.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Helper for retrieve image from html content and return it for use with 
 * Spanned text and display it on textView asynchronously
 * <br>
 * Usage:<br>
 * {@code URLImageParser p = new URLImageParser(textViewTarget, getActivity());}<br>
 * {@code Spanned htmlSpan = Html.fromHtml(htmlTextSource, p, null);}<br>
 * {@code textViewTarget.setText(htmlSpan); }
 * @author muhammad_a
 */
public class URLImageParser implements ImageGetter {
    Context c;
    TextView container;
    float density;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(TextView t, Context c) {
        this.c = c;
        this.container = t;
        this.density = c.getResources().getDisplayMetrics().density;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask( urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }
    
    private class URLDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if(drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    private class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
        	// set the correct bound according to the result from HTTP call         	
            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 + result.getIntrinsicHeight());  

            // change the reference of the current drawable to the result 
            // from the HTTP call 
            urlDrawable.drawable = result; 

            // redraw the image by invalidating the container 
            URLImageParser.this.container.invalidate();

            // For ICS
            URLImageParser.this.container.setHeight(URLImageParser.this.container.getHeight() + result.getIntrinsicHeight());

            // Pre ICS
            URLImageParser.this.container.setEllipsize(null);
        }
        
        public Drawable scaleImage (Drawable image) {
        	
        	WindowManager win = ((Activity) c).getWindowManager(); 
        	Display d  = win.getDefaultDisplay(); 
        	d = win.getDefaultDisplay(); 
        	int width = d.getWidth()-25; // Width of the actual device 
//        	int height = d.getHeight(); // height of the actual device 

        	Bitmap b = ((BitmapDrawable)image).getBitmap();
        	int h = image.getIntrinsicHeight();
        	int w = image.getIntrinsicWidth();
        	h=width*h/w;
        	w=width;
//        	width = Math.round(image.getIntrinsicWidth() / ratio);
//        	height = Math.round(image.getIntrinsicHeight() / ratio);
            Bitmap bitmapResized = Bitmap.createScaledBitmap(b, w, h, false);
            image = new BitmapDrawable(c.getResources(), bitmapResized);
            return image;

        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");
                drawable  = scaleImage(drawable);
                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 + drawable.getIntrinsicHeight()); 
                return drawable;
            } catch (Exception e) {
                return null;
            } 
        }
        
        

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);
            return response.getEntity().getContent();
        }
    }
}
