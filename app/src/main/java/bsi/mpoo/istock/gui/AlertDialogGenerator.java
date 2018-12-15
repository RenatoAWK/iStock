package bsi.mpoo.istock.gui;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import bsi.mpoo.istock.R;

public class AlertDialogGenerator {
    private Activity activity;
    private String message;
    private boolean closeAfter;

    public AlertDialogGenerator(Activity activity, String message, boolean closeAfter) {
        this.activity = activity;
        this.message = message;
        this.closeAfter = closeAfter;
    }

    public void invoke() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setPositiveButton(activity.getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (closeAfter){
                    activity.finish();
                }
            }
        });
        builder.show();
    }
}
