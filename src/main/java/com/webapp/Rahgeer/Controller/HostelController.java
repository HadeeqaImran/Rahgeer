package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.HostWrapper;
import com.webapp.Rahgeer.DBWrapper.HostelWrapper;
import com.webapp.Rahgeer.DBWrapper.HotelWrapper;
import com.webapp.Rahgeer.DBWrapper.PlaceWrapper;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Host;
import com.webapp.Rahgeer.Model.Place;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.ServletWebRequest;

import static java.lang.Float.parseFloat;

@Controller
public class HostelController {
    DBConnection db = new DBConnection();
    HostWrapper hostWrapper = new HostWrapper(db);
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    HostelWrapper hostelWrapper = new HostelWrapper(db);

    @GetMapping("/addHostelButton/{username}")
    public String addHostelButton(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "addhostel";
    }

    @PostMapping("/addHostel/{username}")
    public String addHostel(@PathVariable("username") String username, @RequestParam String name, @RequestParam String description, @RequestParam String city, @RequestParam String address, @RequestParam int noOfrooms, @RequestParam int beds, @RequestParam float cost,  @RequestParam float breakfastcost, @RequestParam float lunchcost, @RequestParam float dinnercost, Model model, ServletWebRequest request){
        placeWrapper.addPlace(name, description, address, username, (float) 0, city);
        Place place = placeWrapper.getPlace(username, name, city);
        hostelWrapper.addHostel(place.getPlaceID(), noOfrooms, noOfrooms, cost, beds);
        if (request.getParameter("mealscheck") != null) {
            placeWrapper.addPlaceService(place.getPlaceID(), 3);
            placeWrapper.addPlaceMealPrices(place.getPlaceID(), breakfastcost, lunchcost, dinnercost);
        }
        if (request.getParameter("wificheck") != null) {
            placeWrapper.addPlaceService(place.getPlaceID(), 4);
        }
        if (request.getParameter("parkingcheck") != null) {
            placeWrapper.addPlaceService(place.getPlaceID(), 6);
        }
        if (request.getParameter("laundrycheck") != null) {
            placeWrapper.addPlaceService(place.getPlaceID(), 5);
        }

        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "managePlaces";
    }

}
