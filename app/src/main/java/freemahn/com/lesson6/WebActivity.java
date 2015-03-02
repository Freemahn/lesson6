package freemahn.com.lesson6;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Freemahn on 21.10.2014.
 */
public class WebActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webView);
        setTitle(getIntent().getStringExtra("title"));
        WebView wv = (WebView) findViewById(R.id.web_view);
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.loadDataWithBaseURL(null, getIntent().getStringExtra("description"), "text/html", "en_US", null);

    }
}
