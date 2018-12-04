package bsi.mpoo.istock.services;

import android.util.Patterns;
import android.widget.EditText;

public class Validations {

    public boolean name(String name){
        if (!name.isEmpty() && name.matches("[/sA-zÀ-ý]+")){
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

    public boolean phone(String phone){
        if (!phone.isEmpty() && phone.matches("[0-9]{12}")){
            return true;
        }
        return false;
    }
}
