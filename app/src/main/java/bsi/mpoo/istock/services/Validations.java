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
        if (!name.isEmpty() && name.matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")){
            return true;
        }
        return false;
    }

    public boolean email(String email){
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public boolean password(String password){
        if (password.length() >= 5 && password.length() <= 10){
            return true;
        }
        return false;
    }

    public boolean passwordEquals(String password, String passwordConfirmation){
        if (password.equals(passwordConfirmation)){
            return true;
        }
        return false;
    }

    public boolean editValidate(EditText editText){
        if (editText != null && !editText.getText().toString().trim().equals("")){
            return true;
        }
        return false;
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
        if (!phone.isEmpty() && !phone.matches("^[0-9]")){
            return true;
        }
        return false;
    }

    public boolean price(String price) {
        try {
            BigDecimal bigDecimal = new BigDecimal(price);
            if (bigDecimal.compareTo(new BigDecimal("0"))>0){
                return true;
            }
            throw new Exception();

        }catch (Exception error){
            return false;
        }
    }

    public boolean minimum(String minimum) {
        if (minimum.isEmpty()){
            return true;
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(minimum);
            if (bigDecimal.compareTo(new BigDecimal("0"))>=0){
                return true;
            }
            throw new Exception();

        }catch (Exception error){
            return false;
        }

    }
}
