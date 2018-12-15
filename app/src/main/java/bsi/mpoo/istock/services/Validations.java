package bsi.mpoo.istock.services;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import java.math.BigDecimal;

import bsi.mpoo.istock.R;

public class Validations {

    private Context context;

    public Validations(Context context){
        this.context = context;
    }

    public boolean name(String name){
        return !name.isEmpty() && name.matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$");
    }

    public boolean email(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean password(String password){
        return password.length() >= 5 && password.length() <= 10;
    }

    public boolean passwordEquals(String password, String passwordConfirmation){
        return password.equals(passwordConfirmation);
    }

    public boolean editValidate(EditText editText){
        return editText != null && !editText.getText().toString().trim().equals("");
    }

    public boolean editValidate(EditText...editTexts){
        boolean valid = true;
        for (EditText editText: editTexts){
            if (!editValidate(editText)){
                editText.setError(context.getString(R.string.requiredField));
                valid = false;
            }
        }
        return valid;
    }

    public boolean phone(String phone){
        return !phone.isEmpty() && !phone.matches("^[0-9]");
    }

    public boolean price(String price) {
        BigDecimal bigDecimal = new BigDecimal(price);
        return bigDecimal.compareTo(new BigDecimal("0")) > 0;
    }

    public boolean quantity(String quantity){
        long number = Long.parseLong(quantity);
        return number >= 0;
    }

    public boolean minimum(String minimum) {
        if (minimum.isEmpty()){
            return true;
        }
        long number = Long.parseLong(minimum);
        return number > 0;

    }
}
