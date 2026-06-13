package com.offjson.ll1;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;


public class NewsDetailsActivity extends AppCompatActivity {
    
	WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_activity);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        wv=findViewById(R.id.wv);
		
		if(getSupportActionBar()!=null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		String url=getIntent().getStringExtra("url");
		String title=getIntent().getStringExtra("title");
		
		if(title != null && !title.isEmpty()){
			setTitle(title);
			
		}else{
			
			setTitle("သတင်း အသ​ေး");
		}
		
		WebSettings wb=wv.getSettings();
		wb.setJavaScriptEnabled(true);
		wb.setLoadWithOverviewMode(true);
		wb.setUseWideViewPort(true);
		
		wv.setWebViewClient(new WebViewClient());
		// URL ကို load လုပ်ခြင်း
        if (url != null && !url.isEmpty()) {
            wv.loadUrl(url);
        } else {
            // URL မရှိလျှင် error message ပြခြင်း
            wv.loadData(
                "<html><body><h3>Error</h3><p>URL မရှိပါ။</p></body></html>",
                "text/html",
                "UTF-8"
            );
        }
    }

    /**
     * Toolbar ပေါ်က back button နှိပ်လျှင် ပြန်သွားရန်
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * WebView ထဲမှာ back button နှိပ်လျှင် နောက်စာမျက်နှာသို့ ပြန်သွားရန်
     */
    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();  // WebView ထဲက နောက်စာမျက်နှာသို့ ပြန်သွားရန်
        } else {
            super.onBackPressed();  // Activity ကိုပိတ်ရန်
        }
    }
	}
    
