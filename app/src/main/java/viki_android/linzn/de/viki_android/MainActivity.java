package viki_android.linzn.de.viki_android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
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

import java.util.Timer;
import java.util.TimerTask;

import de.linzn.vikiAndroid.VikiAndroid;
import de.linzn.vikiAndroid.listeners.LogoClickListener;

public class MainActivity extends Activity {
    public VikiAndroid vikiAndroid;
    public TextView speechInputView, infoView, osVersion;
    public ImageView vikiImage;
    public ImageSwitcher imageSwitcher;
    public CircularProgressBar circularRing1, circularRing2, circularRing3;

    public Float currentRingSpeed1, currentRingSpeed2;
    public Float fixedRingSpeed1 = 1.5F, fixedRingSpeed2 = -0.8F;
    public boolean isDestroyed = false;
    public GuiOptions guiOptions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.guiOptions = new GuiOptions(this);
        this.setupGui();
        this.setupInternalKernel();
    }

    /* Gui things */
    private void setupGui() {
        this.setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.infoView = findViewById(R.id.infoView);
        this.speechInputView = findViewById(R.id.speechInputView);
        this.osVersion = findViewById(R.id.osVersion);
        this.vikiImage = findViewById(R.id.vikiLogo);
        this.imageSwitcher = findViewById(R.id.connectionStatus);
        this.circularRing1 = findViewById(R.id.ring1);
        this.circularRing2 = findViewById(R.id.ring2);
        this.circularRing3 = findViewById(R.id.ring3);
        this.setupLogo();
    }

    private synchronized void setupLogo() {
        this.vikiImage.setOnClickListener(new LogoClickListener(this));

        this.circularRing1.setProgressWithAnimation(70, 10000);

        this.circularRing2.setProgressWithAnimation(60, 10000);
        this.circularRing2.setBackgroundColor(Color.TRANSPARENT);

        this.circularRing3.setProgressWithAnimation(100, 10000);
        this.circularRing3.setBackgroundColor(Color.TRANSPARENT);

        this.currentRingSpeed1 = this.fixedRingSpeed1;
        this.currentRingSpeed2 = this.fixedRingSpeed2;

        Handler handler = new Handler(Looper.getMainLooper());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    circularRing1.setRotation(circularRing1.getRotation() + currentRingSpeed1);
                    circularRing2.setRotation(circularRing2.getRotation() + currentRingSpeed2);
                });
            }
        }, 0, 10);
    }



    /* Setup internal things */
    private void setupInternalKernel() {
        vikiAndroid = new VikiAndroid(this);
        vikiAndroid.start_VikiAndroid();
    }

}
