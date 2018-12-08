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

    private boolean isUserRegistered(User user){

        User searchedUser = userDAO.getUserbyEmail(user);

        if (searchedUser == null) {
            return false;
        }
        return true;
    }

    public void registerUser(User user) throws Exception {

        if (isUserRegistered(user)){
            throw new EmailAlreadyRegistered();
        }
        else {

            user.setPassword(Encryption.encrypt(user.getPassword()));
            userDAO.insertUser(user);

        }

    }

    public User login(User user){

        User searchedUser = userDAO.getUserbyEmail(user);
        if (searchedUser != null){
            if (searchedUser.getPassword().equals(Encryption.encrypt(user.getPassword()))){
                return searchedUser;
            }
        }
        return searchedUser;

    }

}
