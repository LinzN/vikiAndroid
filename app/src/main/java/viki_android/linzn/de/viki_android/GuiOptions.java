package viki_android.linzn.de.viki_android;


import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class GuiOptions {
    private MainActivity mainActivity;
    private UUID uuidForInfoView;

    public GuiOptions(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public synchronized void setGuiData(String osVersion) {
        new Handler(Looper.getMainLooper()).post(() -> {
            mainActivity.osVersion.setText(osVersion);
        });
    }

    /* Change Gui state things */
    public synchronized void setInfoView(int time, String text, boolean setWorking) {
        Handler handler = new Handler(Looper.getMainLooper());
        UUID refInfoViewUUID = UUID.randomUUID();
        this.uuidForInfoView = refInfoViewUUID;
        handler.post(() -> {
            if (setWorking) {
                this.setGuiWorking(true);
            }
            mainActivity.infoView.setText(text);
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (setWorking) {
                        setGuiWorking(false);
                    }
                    if (uuidForInfoView == refInfoViewUUID) {
                        mainActivity.infoView.setText("");
                    }
                });
            }
        }, time * 1000);
    }

    public synchronized void setGuiOnline(boolean value) {
        if (this.mainActivity.isDestroyed == value) {
            if (value) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    mainActivity.isDestroyed = false;
                    mainActivity.imageSwitcher.setBackgroundColor(Color.GREEN);
                    mainActivity.circularRing1.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                    mainActivity.circularRing1.setBackgroundColor(mainActivity.getResources().getColor(R.color.ringOrange));
                    mainActivity.circularRing2.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                    mainActivity.circularRing3.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                    mainActivity.currentRingSpeed1 = mainActivity.fixedRingSpeed1;
                    mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2;
                });
            } else {
                new Handler(Looper.getMainLooper()).post(() -> {
                    mainActivity.isDestroyed = true;
                    mainActivity.imageSwitcher.setBackgroundColor(Color.RED);
                    mainActivity.circularRing1.setColor(Color.GRAY);
                    mainActivity.circularRing1.setBackgroundColor(Color.GRAY);
                    mainActivity.circularRing2.setColor(Color.GRAY);
                    mainActivity.circularRing3.setColor(Color.GRAY);
                    mainActivity.currentRingSpeed1 = mainActivity.fixedRingSpeed1 * -0.1F;
                    mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2 * -0.1F;
                });
            }
        }
    }

    public synchronized void setGuiWorking(boolean value) {
        if (!mainActivity.isDestroyed) {
            if (value) {
                mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2 * 2.5F;
            } else {
                mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2;
            }
        }
    }


}
