package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.*;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;
import com.webapp.Rahgeer.OTPScript.MailSender;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Controller
public class UserController {
    DBConnection db = new DBConnection();
    UserWrapper userWrapper = new UserWrapper(db);
    HostWrapper hostWrapper = new HostWrapper(db);
    TouristWrapper touristWrapper = new TouristWrapper(db);
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    HotelWrapper hotelWrapper = new HotelWrapper(db);
    HostelWrapper hostelWrapper = new HostelWrapper(db);
    AdminWrapper adminWrapper = new AdminWrapper(db);

    MailSender mailSender = new MailSender();
    @PostMapping("/loginUser")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model){
        if (userWrapper.isBlocked(username)){
            model.addAttribute("msg", "Your account is blocked");
            return "login";
        }
        if (userWrapper.validateUser(username,password)){
            model.addAttribute("msg", "Login successful");
            if (hostWrapper.hostAgainstUsername(username)){
                Host host = hostWrapper.getHost(username);
                model.addAttribute("host", host);
                List<Place> places = placeWrapper.getPlacesOfOwner(username);
                Place[] placesArray = new Place[places.size()];
                placesArray = places.toArray(placesArray);
                model.addAttribute("places", placesArray);
                return "homeHost";
            }
            else if (touristWrapper.touristAgainstUsername(username)){
                Tourist tourist = touristWrapper.getTourist(username);
                model.addAttribute("tourist", tourist);
                List<Place> places = placeWrapper.getAllPlaces();
                Place[] placesArray = new Place[places.size()];
                placesArray = places.toArray(placesArray);
                model.addAttribute("places", placesArray);
                return "homeTourist";
            } else if (adminWrapper.adminAgainstUsername(username)){
                Admin admin = adminWrapper.getAdmin(username);
                model.addAttribute("admin", admin);
                return "homeAdmin";
            } else{
                model.addAttribute("msg", "User not found");
                return "login";
            }
        }
        else{
            model.addAttribute("msg", "Login failed");
            return "login";
        }
    }

    @GetMapping("/forgetPassword")
    @PostMapping("/forgetPassword")
    public String forgetPassword(Model model){
        model.addAttribute("msg", "");
        return "resetpass";
    }

    @PostMapping("/sendOTP")
    public String sendOTP(@RequestParam String email, Model model) {
        User user = userWrapper.getUserByEmail(email);
        if (user == null) {
            model.addAttribute("msg", "No user found with this email");
            return "resetpass";
        } else {
            Integer otp = userWrapper.generateOTP();
            mailSender.sendMail(email, "One Time Password", "Your OTP is " + otp);
            model.addAttribute("email", email);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("msg", "OTP sent to your email");
            model.addAttribute("otpcode", otp);
            return "changePassword";
        }
    }

    @PostMapping("/changePass/{email}/{otpcode}")
    public String changePass(@PathVariable("email") String email, @RequestParam String password, @RequestParam String confirmpassword, @RequestParam Integer otp, Model model, @PathVariable("otpcode") Integer otpcode){
        if (password.equals(confirmpassword)) {
            if (otp.equals(otpcode)) {
                User user = userWrapper.getUserByEmail(email);
                userWrapper.updatePassword(user.getUsername(), password);
                model.addAttribute("msg", "Password changed successfully");
                return "redirect:/login";
            } else {
                model.addAttribute("msg", "OTP is incorrect");
                return "changePassword";
            }
        } else {
            model.addAttribute("msg", "Passwords do not match");
            return "changePassword";
        }
    }

}
