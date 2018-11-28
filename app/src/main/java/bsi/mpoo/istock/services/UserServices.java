package bsi.mpoo.istock.services;

import android.content.Context;
import android.util.Log;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.data.UserDAO;
import bsi.mpoo.istock.domain.User;

public class UserServices {

    private UserDAO userDAO;

    public UserServices(Context context){

        this.userDAO = new UserDAO(context);

    }

    public boolean isUserRegistred(String email){

        User searchedUser = userDAO.getUserEmail(email.toUpperCase());

        if (searchedUser == null){
            return false;
        }
        return true;
    }

    public void registerUser(String name, String email, String password, String type, String status, String company, long administrator) {

        if (isUserRegistred(email)){
            // throw
        }
        else {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password); // Precisa passar pela criptografia antes
            user.setType(type);
            user.setStatus(status);
            user.setCompany(company);
            user.setAdministrator(administrator);

            userDAO.insertUser(user);

        }

    }

    public User login(String email, String password){

        User searchedUser = userDAO.getUserEmail(email);
        if (searchedUser != null){
            if (searchedUser.getPassword().equals(password)){ //Precisa passar pela criptografia
                return searchedUser;
            }
            else {
                searchedUser = null;
            }
        }
        return searchedUser;

    }

}
