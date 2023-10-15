package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.HostWrapper;
import com.webapp.Rahgeer.DBWrapper.PlaceWrapper;
import com.webapp.Rahgeer.DBWrapper.RestaurantWrapper;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Host;
import com.webapp.Rahgeer.Model.Place;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestaurantController {
    DBConnection db = new DBConnection();
    HostWrapper hostWrapper = new HostWrapper(db);
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    RestaurantWrapper restaurantWrapper = new RestaurantWrapper(db);

    @GetMapping("/addRestaurantButton/{username}")
    public String addRestaurantButton(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "addrestaurant";
    }

    @PostMapping("/addRestaurant/{username}")
    public String addRestaurant(@PathVariable("username") String username, @RequestParam String name, @RequestParam String description, @RequestParam String city, @RequestParam String address, @RequestParam int noOfrooms, Model model){
        placeWrapper.addPlace(name, description, address, username, (float) 0, city);
        Place place = placeWrapper.getPlace(username, name, city);
        restaurantWrapper.addRestaurant(place.getPlaceID(), noOfrooms);
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "managePlaces";
    }
}
