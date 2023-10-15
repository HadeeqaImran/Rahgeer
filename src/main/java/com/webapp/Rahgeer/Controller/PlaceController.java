package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.*;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

@Controller
public class PlaceController {

    DBConnection db = new DBConnection();
    UserWrapper userWrapper = new UserWrapper(db);
    HostWrapper hostWrapper = new HostWrapper(db);
    TouristWrapper touristWrapper = new TouristWrapper(db);
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    HostelWrapper hostelWrapper = new HostelWrapper(db);
    RestaurantWrapper restaurantWrapper = new RestaurantWrapper(db);
    HotelWrapper hotelWrapper = new HotelWrapper(db);

    @GetMapping("/addPlaceButton/{username}")
    public String addPlaceButton(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "addPlace";
    }

    @GetMapping("deletePlaceButton/{username}")
    public String deletePlaceButton(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        List<Place> places = placeWrapper.getPlacesOfOwner(username);
        Place[] placesArray = new Place[places.size()];
        placesArray = places.toArray(placesArray);
        model.addAttribute("places", placesArray);
        model.addAttribute("host", host);
        return "removePlace";
    }

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
    }

    @GetMapping("/viewPlaceHost/{placeID}")
    public String viewPlaceHost(@PathVariable("placeID") int placeID, Model model){
        Place place = placeWrapper.getPlace(placeID);
        Host host = hostWrapper.getHost(place.getPlaceOwner());
        model.addAttribute("place", place);
        model.addAttribute("host", host);
        Restaurant restaurant = restaurantWrapper.getRestaurant(placeID);
        Hotel hotel = hotelWrapper.getHotel(placeID);
        Hostel hostel = hostelWrapper.getHostel(placeID);
        if(restaurant != null){
            model.addAttribute("type", "restaurant");
            restaurant.setPlace(place);
            model.addAttribute("restaurant", restaurant);
        }
        else if(hotel != null){
            model.addAttribute("type", "hotel");
            hotel.setPlace(place);
            model.addAttribute("hotel", hotel);
        }
        else if(hostel != null){
            model.addAttribute("type", "hostel");
            hostel.setPlace(place);
            model.addAttribute("hostel", hostel);
        }
        return "place";
    }

    @GetMapping("/viewPlaceTourist/{placeID}")
    public String viewPlaceTourist(@PathVariable("placeID") int placeID, Model model){
        Place place = placeWrapper.getPlace(placeID);
        Host host = hostWrapper.getHost(place.getPlaceOwner());
        model.addAttribute("place", place);
        model.addAttribute("host", host);
        Restaurant restaurant = restaurantWrapper.getRestaurant(placeID);
        Hotel hotel = hotelWrapper.getHotel(placeID);
        Hostel hostel = hostelWrapper.getHostel(placeID);
        if(restaurant != null){
            model.addAttribute("type", "restaurant");
            restaurant.setPlace(place);
            model.addAttribute("restaurant", restaurant);
        }
        else if(hotel != null){
            model.addAttribute("type", "hotel");
            hotel.setPlace(place);
            model.addAttribute("hotel", hotel);
        }
        else if(hostel != null){
            model.addAttribute("type", "hostel");
            hostel.setPlace(place);
            model.addAttribute("hostel", hostel);
        }
        return "place2";
    }

}
