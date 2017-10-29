package viki_android.linzn.de.viki_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {
    public TextView vikiInput;
    public TextView vikiOutput;
    public WebView vikiLogo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setupInPutView();
        setupOutPutView();
        setupWebView();
        setupVikiMode();
    }

    private void setupOutPutView() {
        vikiOutput = (TextView) findViewById(R.id.vikiOutput);
    }

    private void setupInPutView() {
        vikiInput = (TextView) findViewById(R.id.vikiInput);
    }

    private void setupWebView() {
        vikiLogo = (WebView) findViewById(R.id.webclient1);
        vikiLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                vikiOutput.setText("I´m listening...");
                return false;
            }
        });
        vikiLogo.loadUrl("http://viki.lan");
    }

    private void setupVikiMode() {

    }

}
