package com.ibnkhaldoun.studentside.Utilities;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;



public final class ActivityUtilities {

    //this method makes the bar transparent
    public static void changeStatusBarColor(Window windows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            windows.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
