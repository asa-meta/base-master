package com.asa.meta.helpers.toast;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.meta.helpers.R;
import com.asa.meta.helpers.Utils;


public class ToastUtils {

    private static Toast toast;

    public static void showToast(int resId) {
        showToast(Utils.context.getString(resId));
    }

    public static synchronized void showToast(String message) {
        try {
            View layout = LayoutInflater.from(Utils.context).inflate(R.layout.ui_custom_toast, null);
            TextView textview = layout.findViewById(R.id.toast_message);
            textview.setText(message);
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = new Toast(Utils.context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } catch (Exception e) {
            Toast.makeText(Utils.context, message, Toast.LENGTH_LONG).show();
        }
    }
}