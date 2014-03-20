package com.nu.media.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/**
 * Helper for retrieve image from html content and return it for use with 
 * Spanned text and display it on textView asynchronously
 * <br>
 * Usage:<br>
 * {@code AqueryImageParser p = new AqueryImageParser(textViewTarget, getActivity());}<br>
 * {@code Spanned htmlSpan = Html.fromHtml(htmlTextSource, p, null);}<br>
 * {@code textViewTarget.setText(htmlSpan); }
 * @author Aziz Muhammad
 */
public class AqueryImageParser implements ImageGetter {
    private Context c;
    private TextView container;
	private AQuery aq;
//	private int imageProgress;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param TextView
     * @param Context
     * @param Resources image progress
     */
    public AqueryImageParser(TextView t, Context c, int progress) {
        this.c = c;
        this.container = t;
        this.aq = new AQuery(c);
//        this.imageProgress = progress;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();
        // request image
        new AqueryImageGetter(source, urlDrawable);
        
        
        
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
    
    private class AqueryImageGetter {
    	URLDrawable urlDrawable;
    	
    	public AqueryImageGetter(String url, URLDrawable urlDrawable){
    		this.urlDrawable = urlDrawable;
    		requestImage(url);
    	}
		private void requestImage(String url) {
			
//			AjaxCallback<Bitmap> cb = new AjaxCallback<Bitmap>(){
//				@Override
//				public void callback(String url, Bitmap bmp, AjaxStatus status) {
//					if (bmp == null) {
//						return;
//					}										
//					Drawable draw = new BitmapDrawable(bmp);
//					draw  = scaleImage(draw);
//					setImageDrawable(draw);
//				}
//			};
//			//set type callback
//			cb.type(Bitmap.class);
//			cb.url(url);
//			//proceed
//			aq.ajax(cb);
//			aq.id(R.id.button2).image(url, true, true, 0, 0, new BitmapAjaxCallback(){
//
//		        @Override
//		        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
//		        	if (bm == null) {
//						return;
//					}										
//					Drawable draw = new BitmapDrawable(bm);
//					draw  = scaleImage(draw);
//					setImageDrawable(draw);		                
//		        }
//		        
//		});
			aq.ajax(url, Bitmap.class, 0, new AjaxCallback<Bitmap>(){
				@Override
				public void callback(String url, Bitmap bmp, AjaxStatus status) {
					if (bmp == null) {
						return;
					}										
					Drawable draw = new BitmapDrawable(bmp);
					draw  = scaleImage(draw);
					setImageDrawable(draw);
				}
			});
		}
		
		protected void setImageDrawable(Drawable draw) {
			//set bitmap bound according to the result from HTTP call
			draw.setBounds(0, 0, 0 + draw.getIntrinsicWidth(), 0 + draw.getIntrinsicHeight()); 
			
			// set the correct bound according to the result from HTTP call
			urlDrawable.setBounds(0, 0, 0 + draw.getIntrinsicWidth(), 0 + draw.getIntrinsicHeight());  

            // change the reference of the current drawable to the result 
            // from the HTTP call 
            urlDrawable.drawable = draw; 

            // redraw the image by invalidating the container 
            AqueryImageParser.this.container.invalidate();

            // For ICS
            AqueryImageParser.this.container.setHeight(AqueryImageParser.this.container.getHeight() + draw.getIntrinsicHeight());

            // Pre ICS
            AqueryImageParser.this.container.setEllipsize(null);
		}
		/**
		 * Resizie image drawable based on screen width
		 * @param image
		 * @return
		 */
		private Drawable scaleImage (Drawable image) {
        	WindowManager win = ((Activity) c).getWindowManager(); 
        	Display d  = win.getDefaultDisplay(); 
        	d = win.getDefaultDisplay(); 
        	int padding = 25;	// padding
        	int width = d.getWidth() - padding; // Width of the actual device 
//        	int height = d.getHeight()- padding; // height of the actual device 

        	Bitmap b = ((BitmapDrawable)image).getBitmap();
        	int h = image.getIntrinsicHeight();
        	int w = image.getIntrinsicWidth();
        	
        	//if image too large according to screen, resize it
        	if (w > width) {
        		h=width*h/w;
            	w=width;
			}
        	
        	Bitmap bitmapResized = Bitmap.createScaledBitmap(b, w, h, false);
        	image = new BitmapDrawable(c.getResources(), bitmapResized);
        	return image;
        }
    }
}
