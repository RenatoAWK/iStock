package bsi.mpoo.istock.services;

import android.content.Context;

import bsi.mpoo.istock.data.UserDAO;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Exceptions.EmailAlreadyRegistered;

public class UserServices {

    private UserDAO userDAO;

    public UserServices(Context context){

        this.userDAO = new UserDAO(context);

    }

    private boolean isUserRegistered(String email){

        User searchedUser = userDAO.getUserEmail(email.toUpperCase());

        if (searchedUser == null) {
            return false;
        }
        return true;
    }

    public void registerUser(User user) throws Exception {

        if (isUserRegistered(user.getEmail())){
            throw new EmailAlreadyRegistered();
        }
        else {

            user.setPassword(Encryption.encrypt(user.getPassword()));
            userDAO.insertUser(user);

        }

    }

    public User login(String email, String password){

        User searchedUser = userDAO.getUserEmail(email);
        if (searchedUser != null){
            if (searchedUser.getPassword().equals(Encryption.encrypt(password))){
                return searchedUser;
            }
            else {
                searchedUser = null;
            }
        }
        return searchedUser;

    }

}
