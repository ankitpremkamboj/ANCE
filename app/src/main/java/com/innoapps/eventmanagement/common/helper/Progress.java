package com.innoapps.eventmanagement.common.helper;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import com.innoapps.eventmanagement.R;


public class Progress {
//
    static ProgressDialog dialog;

    public static void start(Context context){
        if(dialog!=null)
            dialog.dismiss();

            dialog=new ProgressDialog(context,ProgressDialog.THEME_HOLO_LIGHT);

        try
        {
            dialog.show();
        }
        catch(WindowManager.BadTokenException e)
        {

        }
        dialog.setIndeterminate(true);

        dialog.setContentView(R.layout.progressdialog);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

    }

    public static void stop()
    {
        if(dialog!=null){
            dialog.dismiss();
        }
    }
}
