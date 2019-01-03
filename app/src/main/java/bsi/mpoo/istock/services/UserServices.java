package bsi.mpoo.istock.services;

import android.content.Context;

import java.util.ArrayList;

import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.data.user.UserDAO;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Exceptions.EmailAlreadyRegistered;

public class UserServices {

    private UserDAO userDAO;

    public UserServices(Context context){
        this.userDAO = new UserDAO(context);
    }

    private boolean isUserRegistered(User user){
        User searchedUser = userDAO.getUserByEmail(user);
        return searchedUser != null;
    }

    public void registerUser(User user) throws Exception {

        if (isUserRegistered(user)){
            throw new EmailAlreadyRegistered();
        } else {
            user.setPassword(Encryption.encrypt(user.getPassword()));
            userDAO.insertUser(user);
        }
    }

    public User login(User user){
        User searchedUser = userDAO.getUserByEmail(user);
        if (searchedUser != null){
            if (searchedUser.getPassword().equals(Encryption.encrypt(user.getPassword()))){
                return searchedUser;
            } else {
              return null;
            }
        }
        return null;
    }

    public ArrayList<User> getAcitiveUsersAsc(User user){
        return (ArrayList<User>) userDAO.getActiveUsersByAdmId(user,Contract.ASC);
    }

    public ArrayList<User> getAcitiveUsersDesc(User user){
        return (ArrayList<User>) userDAO.getActiveUsersByAdmId(user,Contract.DESC);
    }

    public ArrayList<User> getUsersAsc(User user){
        return (ArrayList<User>) userDAO.getUsersByAdmId(user, Contract.ASC);
    }

    public ArrayList<User> getUsersDesc(User user){
        return (ArrayList<User>) userDAO.getUsersByAdmId(user, Contract.DESC);
    }

}
