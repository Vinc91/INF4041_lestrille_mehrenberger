package org.esiea.mehrenberger_lestrille.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by pierremehrenberger on 05/12/2016.
 */

public class WebActivity extends AppCompatActivity {

    private static WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        //Permet de relier notre site web à notre appli mobile
        browser = (WebView) findViewById(R.id.webView);
        String url = "https://mysneakersfeed.herokuapp.com";
        browser.getSettings().getJavaScriptEnabled();
        browser.loadUrl(url);

    }
}