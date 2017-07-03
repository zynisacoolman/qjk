package com.qijukeji.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/4/13.
 */

public class BuilderShow {
    private static void builderShou(Context context, String title, String mssage, String positiveButton, String negativeButton, LeaveMyDialogListener onclick, EditText editText) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(editText)
                .setMessage(mssage)
                .setPositiveButton(positiveButton, onclick)
                .setNegativeButton(negativeButton, null)
                .show();
    }

    public interface LeaveMyDialogListener extends DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which);
    }

    /**
     * 输入框
     *
     * @param context
     * @param title
     * @param positiveButton
     * @param negativeButton
     * @param onclick
     * @param editText
     */
    public static void builderShou(Context context, String title, String positiveButton, String negativeButton, LeaveMyDialogListener onclick, EditText editText) {
        builderShou(context, title, null, positiveButton, negativeButton, onclick, editText);
    }

    /**
     * 选择框
     *
     * @param context
     * @param title
     * @param mssage
     * @param positiveButton
     * @param negativeButton
     * @param onclick
     */
    public static void builderShou(Context context, String title, String mssage, String positiveButton, String negativeButton, LeaveMyDialogListener onclick) {
        builderShou(context, title, mssage, positiveButton, negativeButton, onclick, null);
    }
}
