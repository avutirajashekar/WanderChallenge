package com.wander.controller;

import com.wander.model.UserModel;
import com.wander.service.UserService;
import com.wander.utility.Utility;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Rajashekar Avuti
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Utility utility;

    @RequestMapping("/")
    public String index(HttpServletRequest request, @CookieValue(value = "wander", required = false) String encodedCookie, ModelMap model) {
        try {
            if (encodedCookie != null) {
                String decodeValue = utility.getDecodeId(encodedCookie);
                JSONObject jSONObject = new JSONObject(decodeValue);
                model.addAttribute("username", jSONObject.getString("username"));
            }
        } catch (Exception e) {
            System.out.println("Exception at index() in UserController.java : " + e.getMessage());
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public @ResponseBody
    UserModel registerUser(@ModelAttribute("UserModel") UserModel userModel) {
        try {
            String userStatus = userService.validateUser(userModel);
//            System.out.println(" ===================== " + rStatus);
            if (userStatus.equals("user")) {
                userModel.setErrStatus(0);
                userModel.setErrMessage("Name should contain minimum 3 characters.");
            } else if (userStatus.equals("mobile")) {
                userModel.setErrStatus(0);
                userModel.setErrMessage("Please enter valid mobile number.");
            } else if (userStatus.equals("email")) {
                userModel.setErrStatus(0);
                userModel.setErrMessage("Please enter valid email address.");
            } else if (userStatus.equals("password")) {
                userModel.setErrStatus(0);
                userModel.setErrMessage("Please enter password.");
            } else if (userStatus.equals("success")) {
                userModel = userService.registerUser(userModel);
                userModel.setEncodeString(utility.getStringEncode(userModel.getUser_id() + "-" + userModel.getMobile_no()));
                userModel.setErrMessage("Otp has been sent to your registered mobile number.");
            } else {
                userModel.setErrStatus(0);
                userModel.setErrMessage("Invalid user details. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Exception at registerUser() in UserController.java : " + e.getMessage());
            e.printStackTrace();
        }
        return userModel;
    }

}
