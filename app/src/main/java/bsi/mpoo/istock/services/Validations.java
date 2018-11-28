package bsi.mpoo.istock.services;

import android.util.Patterns;
import android.widget.EditText;

public class Validations {

    public boolean companyName(String company){
        if (!company.isEmpty())
            return true;
        return false;
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
}
