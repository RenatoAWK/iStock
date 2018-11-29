package bsi.mpoo.istock.services;

import android.content.Context;

import bsi.mpoo.istock.data.UserDAO;
import bsi.mpoo.istock.domain.User;

public class UserServices {

    private UserDAO userDAO;

    public UserServices(Context context){

        this.userDAO = new UserDAO(context);

    }

    public boolean isUserRegistered(String email){

        User searchedUser = userDAO.getUserEmail(email.toUpperCase());

        if (searchedUser == null){
            return false;
        }
        return true;
    }

    public void registerUser(String name, String email, String password, String type,
                             String status, String company, long administrator)
                            throws Exception {

        if (isUserRegistered(email)){
            throw new Exception("Email já cadastrado");
        }
        else {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(Encryption.encrypt(password));
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
