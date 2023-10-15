package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.PlaceWrapper;
import com.webapp.Rahgeer.DBWrapper.UserWrapper;
import com.webapp.Rahgeer.DBWrapper.ReportWrapper;
import com.webapp.Rahgeer.DBWrapper.AdminWrapper;
import com.webapp.Rahgeer.DBWrapper.HostWrapper;
import com.webapp.Rahgeer.DBWrapper.HostelWrapper;

import com.webapp.Rahgeer.DBWrapper.HotelWrapper;

import com.webapp.Rahgeer.DBWrapper.RestaurantWrapper;
import com.webapp.Rahgeer.DBWrapper.TouristWrapper;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;
import com.webapp.Rahgeer.OTPScript.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    DBConnection db = new DBConnection();
    AdminWrapper adminWrapper = new AdminWrapper(db);
    UserWrapper userWrapper = new UserWrapper(db);
    MailSender mailSender = new MailSender();
    TouristWrapper touristWrapper = new TouristWrapper(db);
    HostWrapper hostWrapper = new HostWrapper(db);
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    HostelWrapper hostelWrapper = new HostelWrapper(db);
    HotelWrapper hotelWrapper = new HotelWrapper(db);
    RestaurantWrapper restaurantWrapper = new RestaurantWrapper(db);

    ReportWrapper reportWrapper = new ReportWrapper(db);

    @GetMapping("/home/{username}")
    public String home(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "homeAdmin";
    }

    @GetMapping("/report/{username}")
    public String report(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "adminReportpage1";
    }

    @GetMapping("/PlacesOnReport/{username}")
    public String PlacesOnReport(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "adminReportPlaces";
    }

    @GetMapping("/reportHotels/{username}")
    public String reportHotels(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Hotel> hotels = hotelWrapper.getAllHotels();
        System.out.println("Hotels: " + hotels.size());
        for (Hotel h: hotels){
            h.setPlace(placeWrapper.getPlace(h.getHotelID()));
        }
        Hotel[] hotelsArray = new Hotel[hotels.size()];
        hotelsArray = hotels.toArray(hotelsArray);
        model.addAttribute("hotels", hotelsArray);
        return "reportHotels";
    }

    @GetMapping("/reportHostel/{username}")
    public String reportHostel(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Hostel> hostels = hostelWrapper.getAllHostels();
        System.out.println("Hostels: " + hostels.size());
        for (Hostel h: hostels){
            h.setPlace(placeWrapper.getPlace(h.getHostelID()));
        }
        Hostel[] hostelsArray = new Hostel[hostels.size()];
        hostelsArray = hostels.toArray(hostelsArray);
        model.addAttribute("hostels", hostelsArray);
        return "reportHostels";
    }

    @GetMapping("/reportRestaurants/{username}")
    public String reportRestaurants(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Restaurant> res = restaurantWrapper.getAllRestaurants();
        System.out.println("Restaurants: " + res.size());
        Restaurant[] resArray = new Restaurant[res.size()];
        resArray = res.toArray(resArray);
        model.addAttribute("restaurants", resArray);
        return "reportRestaurants";
    }

    @GetMapping("/MonthlyReport/{username}")
    public String MonthlyReport(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "reportTimeline";
    }

    @GetMapping("/userCategories/{username}")
    public String userCategories(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "adminUsers";
    }


    @GetMapping("/listOfTourists/{username}")
    public String listOfTourists(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Tourist> tourists = touristWrapper.getAllUnblockedTourists();
        Tourist[] touristsArray = new Tourist[tourists.size()];
        touristsArray = tourists.toArray(touristsArray);
        model.addAttribute("tourists", touristsArray);
        return "adminTouristsList";
    }

    @GetMapping("/listOfHosts/{username}")
    public String listOfHosts(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Host> hosts = hostWrapper.getAllUnblockedHosts();
        Host[] hostsArray = new Host[hosts.size()];
        hostsArray = hosts.toArray(hostsArray);
        model.addAttribute("hosts", hostsArray);
        return "adminHostsList";
    }

    @GetMapping("/listOfBlockedTourists/{username}")
    public String listOfBlockedTourists(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Host> hosts = hostWrapper.getBlockedHosts();
        Host[] hostsArray = new Host[hosts.size()];
        hostsArray = hosts.toArray(hostsArray);
        model.addAttribute("hosts", hostsArray);

        List<Tourist> tourists = touristWrapper.getBlockedTourists();
        Tourist[] touristsArray = new Tourist[tourists.size()];
        touristsArray = tourists.toArray(touristsArray);
        model.addAttribute("tourists", touristsArray);
        return "adminBlockedUsersList";
    }


    @GetMapping("/updateAdminProfile/{username}")
    public String updateAdminProfile(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "adminUpdateProfile";
    }

    @PostMapping("/updateAdminProfile/{email}")
    public String updateAdminProfile(@PathVariable("email") String email, @RequestParam String name, Model model){
        Admin admin = adminWrapper.getAdminByEmail(email);
        adminWrapper.updateName(admin.getUsername(), name);
        model.addAttribute("admin", admin);
        return "homeAdmin";
    }

    @GetMapping("/touristList/{username}")
    public String touristList(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        List<Tourist> tourists = touristWrapper.getAllUnblockedTourists();
        //Tourist[] touristArray = new Tourist[tourists.size()];
        //touristArray = tourists.toArray(touristArray);
        model.addAttribute("tourists", tourists);
        return "adminTouristsList";
    }

    //Change Password
    @GetMapping("/updateAdminPass/{email}")
    public String updateAdminPass(@PathVariable("email") String email, Model model){
        User user = userWrapper.getUserByEmail(email);
        Integer otp = userWrapper.generateOTP();
        mailSender.sendMail(email, "One Time Password", "Your OTP is " + otp);
        model.addAttribute("email", email);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("msg", "OTP sent to your email");
        model.addAttribute("otpcode", otp);
        return "changePassword";
    }

    //Probably Discarded
    @GetMapping("/changePassword/{username}")
    public String changePassword(@PathVariable("username") String username, Model model){
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "adminUpdatePassword";
    }

    //Blocking Users

    /*<form th:action="@{/deletePlace/} + ${place.placeOwner} + '/' + ${place.placeID}" method="get">
    @GetMapping("/deletePlace/{username}/{placeID}")
    public String placeProfile(@PathVariable("placeID") int placeID, @PathVariable("username") String username, Model model){
        Place place = placeWrapper.getPlace(placeID);
        placeWrapper.deletePlaceService(placeID);
        placeWrapper.deletePlaceMealPrices(placeID);
        hostelWrapper.deleteHostel(placeID);
        restaurantWrapper.deleteRestaurant(placeID);
        hotelWrapper.deleteHotel(placeID);
        placeWrapper.deletePlace(placeID);
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "managePlaces";
    }*/
    @GetMapping("/blockUser/{username}/{email}")
    public String blockUser(@PathVariable("username") String username, @PathVariable("email") String email, Model model){

        User user = userWrapper.getUserByEmail(email);
        userWrapper.blockUser(user.getUsername());
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "redirect:/userCategories/" + username;
    }

    @GetMapping("/removeBlockedUser/{username}/{email}")
    public String removeBlockedUser(@PathVariable("username") String username, @PathVariable("email") String email, Model model){

        User user = userWrapper.getUserByEmail(email);
        userWrapper.removeBlockedUser(user.getUsername());
        Admin admin = adminWrapper.getAdmin(username);
        model.addAttribute("admin", admin);
        return "redirect:/userCategories/" + username;
    }

    //New Added
    @PostMapping("/generateReport")
    public String generateReport(@RequestParam int month, @RequestParam int year, Model model){
        model.addAttribute("month", month);
        model.addAttribute("year", month);
        return "placesWithTimeline";
    }


    @PostMapping("/generateHotelReport/{month}/{year}")
    public String generateHotelReport(@PathVariable("month") int month, @PathVariable("year") int year, Model model) {
        List<Report> reports = reportWrapper.getHotelDetails(month, year);
        model.addAttribute("hotels", reports);
        return "page4-hotel";
    }

    @PostMapping("/generateHostelReport/{month}/{year}")
    public String generateHostelReport(@PathVariable("month") int month, @PathVariable("year") int year, Model model) {
        List<Report> reports = reportWrapper.getHostelDetails(month, year);
        model.addAttribute("hostels", reports);
        return "page4-hostel";
    }

    @PostMapping("/generateRestaurantReport/{month}/{year}")
    public String generateRestaurantReport(@PathVariable("month") int month, @PathVariable("year") int year, Model model) {
        List<Report> reports = reportWrapper.getRestaurantDetails(month, year);
        model.addAttribute("hostels", reports);
        return "page4-rest";
    }

}