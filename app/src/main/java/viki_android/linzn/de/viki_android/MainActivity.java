package viki_android.linzn.de.viki_android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import de.linzn.vikiAndroid.VikiAndroid;

public class MainActivity extends Activity {
    private static VikiAndroid vikiAndroid;
    public TextView vikiInput;
    public TextView vikiOutput;
    public ImageView vikiImage;
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
        test();
        setupVikiMode();
    }

    private void setupOutPutView() {
        vikiOutput = (TextView) findViewById(R.id.vikiOutput);
    }

    private void setupInPutView() {
        vikiInput = (TextView) findViewById(R.id.vikiInput);
    }

    private void setupWebView() {
        vikiImage = findViewById(R.id.vikiLogo);
        vikiImage.setOnClickListener(view -> vikiOutput.setText("I´m listening..."));
        imageSwitcher = findViewById(R.id.connectionStatus);
    }

    private void setupVikiMode() {
        vikiAndroid = new VikiAndroid(this);
        vikiAndroid.start_VikiAndroid();
    }

    private void test() {
        CircularProgressBar circularProgressBar1 = (CircularProgressBar) findViewById(R.id.ring1);
        circularProgressBar1.setProgressWithAnimation(70, 5000); // Default duration = 1500ms
        circularProgressBar1.setRotation(20);

        CircularProgressBar circularProgressBar2 = (CircularProgressBar) findViewById(R.id.ring2);
        circularProgressBar2.setProgressWithAnimation(60, 10000); // Default duration = 1500ms
        circularProgressBar2.setBackgroundColor(Color.TRANSPARENT);
        circularProgressBar2.setRotation(60);

        CircularProgressBar circularProgressBar3 = (CircularProgressBar) findViewById(R.id.ring3);
        circularProgressBar3.setProgressWithAnimation(100, 1000); // Default duration = 1500ms
        circularProgressBar3.setBackgroundColor(Color.TRANSPARENT);
        circularProgressBar3.setRotation(90);
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
