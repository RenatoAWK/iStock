package bsi.mpoo.istock.gui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.client.ClientServices;
import bsi.mpoo.istock.services.product.ProductServices;
import bsi.mpoo.istock.services.user.UserServices;

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

    public void invokeShare(final String extraMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(message);
        builder.setMessage(extraMessage);
        builder.setPositiveButton(activity.getString(R.string.share), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (closeAfter){
                    activity.finish();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, extraMessage);
                    intent.setType(Constants.Activity.TEXT_PLAIN);
                    activity.startActivity(intent);

                }
            }
        });
        builder.setNegativeButton(activity.getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (closeAfter){
                    activity.finish();
                }
            }
        });
        builder.show();
    }

    public void invokeDeleteChoose(final Object object){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setPositiveButton(activity.getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (object instanceof User){
                    UserServices userServices = new UserServices(activity.getApplicationContext());
                    try {
                        userServices.disableUser((User) object);
                    } catch (Exception e) {
                        showUnknowError();
                    }
                } else if (object instanceof Client){
                    ClientServices clientServices = new ClientServices(activity.getApplicationContext());
                    try {
                        clientServices.disableClient((Client) object, Session.getInstance().getAdministrator());
                    } catch (Exception e) {
                        showUnknowError();
                    }
                } else if (object instanceof Product) {
                    ProductServices productServices = new ProductServices(activity.getApplicationContext());
                    try {
                        productServices.disableProduct((Product) object, Session.getInstance().getAdministrator());
                    } catch (Exception e) {
                        showUnknowError();
                    }
                }
                if (closeAfter){
                    activity.finish();
                }
            }
            private void showUnknowError() {
                new AlertDialogGenerator(activity, activity.getString(R.string.unknow_error), false).invoke();
            }
        });
        builder.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
