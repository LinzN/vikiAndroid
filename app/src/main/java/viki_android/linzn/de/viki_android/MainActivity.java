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
import de.linzn.vikiAndroid.listeners.LogoClickListener;

public class MainActivity extends Activity {
    public VikiAndroid vikiAndroid;
    public TextView speechInputView, infoView, osVersion;
    public ImageView vikiImage;
    public ImageSwitcher imageSwitcher;
    public CircularProgressBar circularProgressBar1, circularProgressBar2, circularProgressBar3;
    public Float ringSpeed1, ringSpeed2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setupGui();
        this.setupInternalKernel();
    }

    /* Gui things */
    private void setupGui() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setContentView(R.layout.activity_main);
        this.infoView = findViewById(R.id.infoView);
        this.speechInputView = findViewById(R.id.speechInputView);
        this.osVersion = findViewById(R.id.osVersion);
        this.setupLogo();
        this.setupInternalKernel();
    }

    private synchronized void setupLogo() {
        this.vikiImage = findViewById(R.id.vikiLogo);
        this.vikiImage.setOnClickListener(new LogoClickListener(this));
        this.imageSwitcher = findViewById(R.id.connectionStatus);
        this.circularProgressBar1 = findViewById(R.id.ring1);
        this.circularProgressBar1.setProgressWithAnimation(70, 10000); // Default duration = 1500ms
        this.circularProgressBar2 = findViewById(R.id.ring2);
        this.circularProgressBar2.setProgressWithAnimation(60, 10000); // Default duration = 1500ms
        this.circularProgressBar2.setBackgroundColor(Color.TRANSPARENT);
        this.circularProgressBar3 = findViewById(R.id.ring3);
        this.circularProgressBar3.setProgressWithAnimation(100, 10000); // Default duration = 1500ms
        this.circularProgressBar3.setBackgroundColor(Color.TRANSPARENT);
        this.ringSpeed1 = 1.5F;
        this.ringSpeed2 = -0.8F;

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Handler(Looper.getMainLooper()).post(() -> this.circularProgressBar1.setRotation(this.circularProgressBar1.getRotation() + this.ringSpeed1));
                new Handler(Looper.getMainLooper()).post(() -> this.circularProgressBar2.setRotation(this.circularProgressBar2.getRotation() + this.ringSpeed2));
            }
        }).start();
    }

    /* Change Gui state things */
    public synchronized void setInfoView(int time, String text) {
        new Thread(() -> {
            new Handler(Looper.getMainLooper()).post(() -> this.infoView.setText(text));
            CharSequence charNew = this.infoView.getText();
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(() -> this.infoView.setText(""));

        }).start();
    }

    public synchronized void setGuiOnline(boolean value) {
        if (value) {
            new Handler(Looper.getMainLooper()).post(() -> {
                this.imageSwitcher.setBackgroundColor(Color.GREEN);
                this.circularProgressBar1.setColor(this.getResources().getColor(R.color.ringOrange));
                this.circularProgressBar1.setBackgroundColor(this.getResources().getColor(R.color.ringOrange));
                this.circularProgressBar2.setColor(this.getResources().getColor(R.color.ringOrange));
                this.circularProgressBar3.setColor(this.getResources().getColor(R.color.ringOrange));
                this.ringSpeed1 = 1.5F;
                this.ringSpeed2 = -0.8F;
            });
        } else {
            new Handler(Looper.getMainLooper()).post(() -> {
                this.imageSwitcher.setBackgroundColor(Color.RED);
                this.circularProgressBar1.setColor(Color.GRAY);
                this.circularProgressBar1.setBackgroundColor(Color.GRAY);
                this.circularProgressBar2.setColor(Color.GRAY);
                this.circularProgressBar3.setColor(Color.GRAY);
                this.ringSpeed1 = 0.3F;
                this.ringSpeed2 = -0.1F;
            });
        }
    }

    public synchronized void setGuiData(String osVersion) {
        new Handler(Looper.getMainLooper()).post(() -> {
            this.osVersion.setText(osVersion);
        });
    }

    /* Setup internal things */
    private void setupInternalKernel() {
        vikiAndroid = new VikiAndroid(this);
        vikiAndroid.start_VikiAndroid();
    }

}
