package com.wander.dao;

import com.wander.model.UserModel;
import com.wander.utility.Utility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Rajashekar avuti
 */


public class UserDao {

    @Autowired(required = true)
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private Utility utility;

    public UserModel registerUser(UserModel userModel) {
        List<UserModel> userModels = null;
        MapSqlParameterSource parameters = null;
        Map<String, Object> params = null;
        String queryString = null;
        try {
            queryString = "SELECT *  FROM user_register WHERE mobile_no = :mobile_no LIMIT :limit;";
            params = new HashMap<>();
            params.put("mobile_no", userModel.getMobile_no());
            params.put("limit", 1);
            userModels = namedParameterJdbcTemplate.query(queryString, params, new BeanPropertyRowMapper(UserModel.class));
            if (userModels != null && userModels.size() > 0) {
                userModel = userModels.get(0) != null && userModels.size() > 0 ? userModels.get(0) : null;
//                userModel.setErrStatus(0);
//                userModel.setErrMessage("User already exists. Please try again with different mobile number");
            } else {
                queryString = "INSERT INTO user_register (user_name, mobile_no, email_id, password, reg_date, status) VALUES(:user_name, :mobile_no, :email_id, :password, UNIX_TIMESTAMP(), :status);";
                parameters = new MapSqlParameterSource();
                parameters.addValue("user_name", userModel.getUser_name());
                parameters.addValue("mobile_no", userModel.getMobile_no());
                parameters.addValue("email_id", userModel.getEmail_id());
                parameters.addValue("password", userModel.getPassword());
                parameters.addValue("status", 1);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                namedParameterJdbcTemplate.update(queryString, parameters, keyHolder, new String[]{"user_id"});
                Number userId = keyHolder.getKey();
                userModel.setUser_id(userId.intValue());
                userModel.setErrStatus(1);
//                userModel.setErrMessage("User registered successfully.");
            }
        } catch (Exception e) {
            System.out.println("Exception at registerUser() in UserDao.java : " + e.getMessage());
//            e.printStackTrace();
        }
        return userModel;
    }
}
