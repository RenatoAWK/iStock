package bsi.mpoo.istock.services;

import android.util.Patterns;
import android.widget.EditText;

public class Validations {

    public boolean companyName(String company){
        return true;//
    }

    public boolean name(String name){
        return true; //
    }

    public boolean email(String email){
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public boolean password(String password){
        return true; //
    }

    public boolean passwordEquals(String password, String passwordConfirmation){
        if (password.equals(passwordConfirmation)){
            return true;
        }
        return false;
    }

    public boolean editValidate(EditText editText){
        if (editText != null && !editText.getText().toString().equals("")){
            return true;
        }
        return false;
    }
}
