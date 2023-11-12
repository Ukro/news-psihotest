package com.paresh.webviewclientdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class WebViewClientDemoActivity extends Activity {
    /** Called when the activity is first created. */
	
	WebView web;
	ProgressBar progressBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        web = (WebView) findViewById(R.id.webview01);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        web.setWebChromeClient(new WebChromeClient() {

            // this will be called on page loading progress

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                //super.onProgressChanged(view, newProgress);


                progressBar.setProgress(newProgress);

                // hide the progress bar if the loading is complete

                if (newProgress == 100) {

                    /* call after laoding splash.html  */
                    web.loadUrl("javascript:_fully_loaded()");
                    web.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }

            }

        });

        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setInitialScale(80);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl("file:///android_asset/index.html");
        //web.loadUrl("https://coronavirus.jhu.edu/map");
        //web.loadUrl("https://www.arcgis.com/apps/opsdashboard/index.html#/85320e2ea5424dfaaa75ae62e5c06e61");
        //web.loadUrl("https://m-covid19.rnbo.gov.ua/map");




        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    
    public class myWebClient extends WebViewClient
    {


    	@Override
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		// TODO Auto-generated method stub
    		super.onPageStarted(view, url, favicon);
    	}
    	
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		// TODO Auto-generated method stub
    		
    		view.loadUrl(url);
    		return true;
    		
    	}
    	
    	@Override
    	public void onPageFinished(WebView view, String url) {
    		// TODO Auto-generated method stub
    		super.onPageFinished(view, url);
    		
    		progressBar.setVisibility(View.GONE);
    	}
    }
    
    // To handle "Back" key press event for WebView to go back to previous screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}