package viki_android.linzn.de.viki_android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import de.linzn.vikiAndroid.VikiAndroid;

public class MainActivity extends Activity {
    private static VikiAndroid vikiAndroid;
    public TextView vikiInput;
    public TextView vikiOutput;
    public WebView vikiLogo;
    public ImageSwitcher imageSwitcher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        vikiLogo.setOnTouchListener((view, motionEvent) -> {
            vikiOutput.setText("I´m listening...");
            return false;
        });
        vikiLogo.loadUrl("http://viki.lan");
        imageSwitcher = findViewById(R.id.connectionStatus);
    }

    private void setupVikiMode() {
        vikiAndroid = new VikiAndroid(this);
        vikiAndroid.start_VikiAndroid();
    }

    public void updateOutputView(int time, String text) {
        new Thread(() -> {
            new Handler(Looper.getMainLooper()).post(() -> this.vikiOutput.setText(text));
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(() -> this.vikiOutput.setText(""));
        }).start();
    }

}
