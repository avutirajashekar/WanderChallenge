package com.wander.service;

import com.wander.dao.UserDao;
import com.wander.model.UserModel;
import com.wander.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rajashekar avuti
 */
public class UserService {

    @Autowired
    private Utility utility;

    @Autowired
    private UserDao userDao;

    public String validateUser(UserModel userModel) {
        String status = "success";
        if (userModel.getUser_name() == null && userModel.getUser_name().equalsIgnoreCase("") && userModel.getUser_name().length() < 3) {
            status = "user";
        } else if (userModel.getMobile_no() == null && userModel.getMobile_no().equalsIgnoreCase("") && !utility.validateMobileNumber(userModel.getMobile_no())) {
            status = "mobile";
        } else if (userModel.getEmail_id() == null && userModel.getEmail_id().equalsIgnoreCase("") && !utility.validateEmail(userModel.getEmail_id())) {
            status = "email";
        }
        return status;
    }
    
     public UserModel registerUser(UserModel userModel) {
        return userDao.registerUser(userModel);
    }
}
