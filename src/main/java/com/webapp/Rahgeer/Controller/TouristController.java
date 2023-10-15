package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.*;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TouristController {
    DBConnection db = new DBConnection();
    TouristWrapper touristWrapper = new TouristWrapper(db);
    UserWrapper userWrapper = new UserWrapper(db);
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    HostWrapper hostWrapper = new HostWrapper(db);
    MessageWrapper messageWrapper = new MessageWrapper(db);
    HotelWrapper hotelWrapper = new HotelWrapper(db);
    HostelWrapper hostelWrapper = new HostelWrapper(db);
    RestaurantWrapper restaurantWrapper = new RestaurantWrapper(db);
    private static Trip trip = new Trip();
    TripWrapper tripWrapper = new TripWrapper(db);

    @PostMapping("/addTourist")
    public String addTourist(@RequestParam String username, @RequestParam String name, @RequestParam String password, @RequestParam String email, @RequestParam String passport, Model model){
        if (touristWrapper.touristAgainstEmail(email)){
            model.addAttribute("msg", "Email already exists");
            return "signupTourist";
        }
        else if (touristWrapper.touristAgainstPassport(passport)){
            model.addAttribute("msg", "Passport already exists");
            return "signupTourist";
        }
        else{
            userWrapper.addUser(username, name, password, email);
            touristWrapper.addTourist(username, passport, (float) 0);
            model.addAttribute("msg", "Tourist added successfully");
            return "login";
        }
    }

    @GetMapping("/goToReqPage/{username}")
    public String goToReqPage(@PathVariable("username") String username, Model model){
        List<Request> requests = touristWrapper.getAcceptedRequest(username);
        Request[] requestsArray = new Request[requests.size()];
        requestsArray = requests.toArray(requestsArray);
        model.addAttribute("requests", requestsArray);
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        return "requestsTourists";
    }

    @GetMapping("/goTouristHome/{username}")
    public String goToHome(@PathVariable("username") String username, Model model){
        List<Place> places = placeWrapper.getAllPlaces();
        Place[] placesArray = new Place[places.size()];
        placesArray = places.toArray(placesArray);
        model.addAttribute("places", placesArray);
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        return "homeTourist";
    }

    @GetMapping("/postRequestButton/{username}")
    public String postRequestButton(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        return "postRequest";
    }

    @PostMapping("/addRequest/{username}")
    public String addRequest(@PathVariable("username") String username, @RequestParam String destination, @RequestParam String description, Model model){
        // format YYYY-MM-DD HH-MM-SS
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        touristWrapper.addRequest(username, destination, description, dtf.format(now));
        return "redirect:/goTouristHome/" + username;
    }

    @GetMapping("/rateRequest/{createdBy}/{createdOn}/{username}")
    public String rateRequest(@PathVariable("createdBy") String createdBy, @PathVariable("createdOn") String createdOn, @PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(createdBy);
        model.addAttribute("tourist", tourist);
        Request request = touristWrapper.getAcceptedRequest(createdBy, createdOn);
        model.addAttribute("request", request);
        return "rate";
    }

    @PostMapping("/rateHost/{username}/{createdBy}")
    public String rateHost(@PathVariable("username") String username, @RequestParam float rating, @PathVariable("createdBy") String createdBy, Model model){
        hostWrapper.addRating(username, rating);
        return "redirect:/goTouristHome/" + createdBy;
    }

    @GetMapping("/viewAttractions/{username}")
    public String viewAttractions(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        String location = "Fast%20NU%20Lahore";
        model.addAttribute("location", location);
        return "touristattractions";
    }

    @GetMapping("/touristProfile/{username}")
    public String touristProfile(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        return "profileTourist";
    }

    @GetMapping("/passLocation/{username}")
    public String passLocation(@PathVariable("username") String username, @RequestParam String location, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        model.addAttribute("location", location);
        return "touristattractions";
    }

    @GetMapping("/touristMessenger/{username}")
    public String touristMessenger(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        List<Host> chats = messageWrapper.getChatUsersHost(username);
        if (chats.size() == 0){
            return "nomessagetourist";
        }
        Host[] chatsArray = new Host[chats.size()];
        chatsArray = chats.toArray(chatsArray);
        model.addAttribute("chats", chatsArray);
        List<Message> messages = messageWrapper.getAllMessages(username, chatsArray[0].getUsername());
        Message[] messagesArray = new Message[messages.size()];
        messagesArray = messages.toArray(messagesArray);
        model.addAttribute("messages", messagesArray);
        Host host = hostWrapper.getHost(chatsArray[0].getUsername());
        model.addAttribute("host", host);
        return "messanger";
    }

    @GetMapping("/chatWithHost/{cnic}/{username}")
    public String chatWithHost(@PathVariable("cnic") String cnic, @PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        Host host = hostWrapper.getHostByCNIC(cnic);
        model.addAttribute("host", host);
        List<Message> messages = messageWrapper.getAllMessages(username, host.getUsername());
        Message[] messagesArray = new Message[messages.size()];
        messagesArray = messages.toArray(messagesArray);
        model.addAttribute("messages", messagesArray);
        return "touristmessage";
    }

    @PostMapping("/sendMessagetoHost/{createdBy}/{username}")
    public String sendMessagetoHost(@PathVariable("createdBy") String createdBy, @PathVariable("username") String username, @RequestParam String message, Model model){
        Host host = hostWrapper.getHost(username);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String temp = messageWrapper.checkForOffensiveWords(message);
        if (temp.equals(message)){
            messageWrapper.addMessage(createdBy, username, message, dtf.format(now));
            return "redirect:/chatWithHost/" + host.getCnic() + "/" + createdBy;
        }
        else{
            messageWrapper.addMessage(createdBy, username, temp, dtf.format(now));
            userWrapper.blockUser(createdBy);
            return "redirect:/login";
        }

    }

    @GetMapping("/planTripButton/{username}")
    public String planTripButton(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        model.addAttribute("msg", "");
        return "trip1";
    }

    @PostMapping("/proceedTripPlan/{username}")
    public String proceedTripPlan(@PathVariable("username") String username, @RequestParam String source, @RequestParam String dest, @RequestParam String start, @RequestParam String end, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        System.out.println("From: " + source + " To: " + dest + " Start: " + start + " End: " + end);
        // if start date is after end date
        if (start.compareTo(end) > 0){
            model.addAttribute("tourist", tourist);
            model.addAttribute("msg", "Start date cannot be after end date");
            return "trip1";
        }
        // if start date is before current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.time.LocalDate now = java.time.LocalDate.now();
        if (start.compareTo(dtf.format(now)) < 0){
            model.addAttribute("tourist", tourist);
            model.addAttribute("msg", "Start date cannot be before current date");
            return "trip1";
        }
        tripWrapper.addTrip(username, source, dest, start, end);
        trip = tripWrapper.getTrip(username, source, dest, start, end);
        model.addAttribute("tourist", tourist);
        return "searchPlaceforPlanTrip";
    }

    @PostMapping("/searchPlacesToAdd/{username}")
    public String searchPlacesToAdd(@PathVariable("username") String username, @RequestParam Optional<String>  hotel,  @RequestParam Optional<String> restaurant, @RequestParam Optional<String>  hostel, @RequestParam Optional<String>  all, @RequestParam float maxBudget, Model model, @RequestParam Optional<String> place){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        System.out.println("Hotel: " + hotel + " Restaurant: " + restaurant + " Hostel: " + hostel + " All: " + all + " Max Budget: " + maxBudget + " Place: " + place + " City: " + trip.getDestination());
        if (hotel.isPresent() || all.isPresent()){
            List<Hotel> hotels = hotelWrapper.getHotelsForBooking(trip.getDestination(),maxBudget, place);
            for (Hotel h : hotels){
                h.setPlace(placeWrapper.getPlace(h.getHotelID()));
            }
            System.out.println("Hotels: " + hotels.size());
            Hotel[] hotelsArray = new Hotel[hotels.size()];
            hotelsArray = hotels.toArray(hotelsArray);
            model.addAttribute("hotels", hotelsArray);
            model.addAttribute("hotelFlag", "true");
        }
        else {
            model.addAttribute("hotelFlag", "false");
        }
        if (restaurant.isPresent() || all.isPresent()){
            List<Restaurant> restaurants = restaurantWrapper.getAllRestaurantsByPlace(trip.getDestination(),place);
            System.out.println("Restaurants: " + restaurants.size());
            Restaurant[] restaurantsArray = new Restaurant[restaurants.size()];
            restaurantsArray = restaurants.toArray(restaurantsArray);
            model.addAttribute("restaurants", restaurantsArray);
            model.addAttribute("restaurantFlag", "true");
        }
        else {
            model.addAttribute("restaurantFlag", "false");
        }
        if (hostel.isPresent() || all.isPresent()){
            List<Hostel> hostels = hostelWrapper.getHostelForBooking(trip.getDestination(),maxBudget, place);
            for (Hostel hostel1 : hostels){
                hostel1.setPlace(placeWrapper.getPlace(hostel1.getHostelID()));
            }
            System.out.println("Hostels: " + hostels.size());
            Hostel[] hostelsArray = new Hostel[hostels.size()];
            hostelsArray = hostels.toArray(hostelsArray);
            model.addAttribute("hostels", hostelsArray);
            model.addAttribute("hostelFlag", "true");
        }
        else {
            model.addAttribute("hostelFlag", "false");
        }
        model.addAttribute("tripID", trip.getTripID());
        return "searchResponse";
    }

    @GetMapping("/bookPlaceForTrip/{tripID}/{placeID}/{username}")
    public String bookPlaceForTrip(@PathVariable("tripID") int tripID, @PathVariable("placeID") int placeID, @PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        trip = tripWrapper.getTrip(tripID);
        model.addAttribute("trip", trip);
        model.addAttribute("placeID", placeID);
        model.addAttribute("msg", "");
        return "timelineOfBooking";
    }

    @PostMapping("/getDateforBooking/{tripID}/{username}/{placeID}")
    public String getDateforBooking(@PathVariable("tripID") int tripID, @PathVariable("username") String username, @PathVariable("placeID") int placeID, @RequestParam String end, Model model, @RequestParam String start){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        trip = tripWrapper.getTrip(tripID);
        if (start.compareTo(trip.getStartDate()) < 0){
            model.addAttribute("trip", trip);
            model.addAttribute("placeID", placeID);
            model.addAttribute("msg", "Start date cannot be before trip start date");
            return "timelineOfBooking";
        }
        if (end.compareTo(trip.getEndDate()) > 0){
            model.addAttribute("trip", trip);
            model.addAttribute("placeID", placeID);
            model.addAttribute("msg", "Date cannot be after end date");
            return "timelineOfBooking";
        }
        tripWrapper.addPlaceToTrip(tripID, placeID);
        model.addAttribute("trip", trip);
        Hostel hostel = hostelWrapper.getHostel(placeID);
        Hotel hotel = hotelWrapper.getHotel(placeID);
        if (hostel != null){
            hostel.setPlace(placeWrapper.getPlace(placeID));
            MealPrice mealPrice = placeWrapper.getMealprice(placeID);
            if (mealPrice != null){
                // end - trip start date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startdate = LocalDate.parse(trip.getStartDate(), dtf);
                LocalDate end1 = LocalDate.parse(end, dtf);
                int days = (int) ChronoUnit.DAYS.between(startdate, end1);
                float mealCost = (mealPrice.getBreakfastPrice() + mealPrice.getLunchPrice() + mealPrice.getDinnerPrice()) * days;
                float totalCost = mealCost + hostel.getPricePerNight() * days;
                tripWrapper.addTripCost(tripID, totalCost, placeID, mealCost, hostel.getPricePerNight() * days);
                tripWrapper.addTripBooking(tripID, placeID, totalCost, start, end);
            }
        }
        else if (hotel != null){
            hotel.setPlace(placeWrapper.getPlace(placeID));
            MealPrice mealPrice = placeWrapper.getMealprice(placeID);
            if (mealPrice != null){
                // end - trip start date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startdate = LocalDate.parse(trip.getStartDate(), dtf);
                LocalDate end1 = LocalDate.parse(end, dtf);
                int days = (int) ChronoUnit.DAYS.between(startdate, end1);
                float mealCost = (mealPrice.getBreakfastPrice() + mealPrice.getLunchPrice() + mealPrice.getDinnerPrice()) * days;
                float totalCost = mealCost + hotel.getPricePerNight() * days;
                tripWrapper.addTripCost(tripID, totalCost, placeID, mealCost, hotel.getPricePerNight() * days);
                tripWrapper.addTripBooking(tripID, placeID, totalCost, start, end);
            }
        }
        return "redirect:/goTouristHome/" + username;
    }

    @GetMapping("/showTrips/{username}")
    public String showTrips(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        List<Trip> trips = tripWrapper.getTripsofTourist(username);
        Trip[] tripsArray = new Trip[trips.size()];
        tripsArray = trips.toArray(tripsArray);
        model.addAttribute("trips", tripsArray);
        return "upcomingTrips";
    }

    @GetMapping("/viewEachTrip/{tripID}/{username}")
    public String viewEachTrip(@PathVariable("tripID") int tripID, @PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        trip = tripWrapper.getTrip(tripID);
        model.addAttribute("trip", trip);
        List<Integer> placeIDs = tripWrapper.getPlaceID(tripID);
        List<TripCost> tripCosts = new ArrayList<>();
        for (int placeID : placeIDs){
            TripCost tripCost = tripWrapper.getTripCost(tripID, placeID);
            tripCost.setPlace(placeWrapper.getPlace(placeID));
            tripCost.setTripBooking(tripWrapper.getTripBooking(tripID, placeID));
            tripCosts.add(tripCost);
        }
        TripCost[] tripCostsArray = new TripCost[tripCosts.size()];
        tripCostsArray = tripCosts.toArray(tripCostsArray);
        model.addAttribute("tripCosts", tripCostsArray);
        return "viewTripDetails";
    }

    //Added
    @GetMapping("/touristPublicProfile/{username}")
    public String touristPublicProfile(Model model, @PathVariable("username") String username){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        return "touristPublicProfile";
    }

    @GetMapping("/compareTrips/{username}")
    public String compareTrips(@PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        List<String> sources = tripWrapper.getAllSourcesofTourist(username);
        List<String> destinations = tripWrapper.getAllDestinationsofTourist(username);
        model.addAttribute("sources", sources.toArray());
        model.addAttribute("destinations", destinations.toArray());
        return "compareTrips";
    }

    @PostMapping("/compareTripInput/{username}")
    public String compareTripInput(@PathVariable("username") String username, @RequestParam String source, @RequestParam String dest, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        List<Trip> trips = tripWrapper.getTripsOfTouristBySourceDestination(username, source, dest);
        List<Trip> trips1 = new ArrayList<>();
        for (Trip trip : trips){
            if (trip.getSource().equals(source) && trip.getDestination().equals(dest)){
                trips1.add(trip);
            }
        }
        Trip[] tripsArray = new Trip[trips1.size()];
        tripsArray = trips1.toArray(tripsArray);
        model.addAttribute("trips", tripsArray);
        System.out.println(source + " " + dest);
        return "compareTrips";
    }

    @GetMapping("/cancelTrip/{username}/{tripID}/{source}/{dest}")
    public String cancelTrip(@PathVariable("username") String username, @PathVariable("tripID") int tripID, @PathVariable("source") String source, @PathVariable("dest") String dest, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        if (tripWrapper.isFutureTrip(tripID)) {
            tripWrapper.deleteTripBooking(tripID);
            tripWrapper.deleteTripCost(tripID);
            tripWrapper.deletePlaceFromTrip(tripID);
            tripWrapper.deleteTrip(tripID);
        }
        return "redirect:/goTouristHome/" + username;
    }

    @GetMapping("/cancelTrip1/{username}/{tripID}")
    public String cancelTrip1(@PathVariable("username") String username, @PathVariable("tripID") int tripID, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        if (tripWrapper.isFutureTrip(tripID)) {
            tripWrapper.deleteTripBooking(tripID);
            tripWrapper.deleteTripCost(tripID);
            tripWrapper.deletePlaceFromTrip(tripID);
            tripWrapper.deleteTrip(tripID);
        }
        return "redirect:/goTouristHome/" + username;
    }

    @GetMapping("/saveTrip/{tripID}/{username}")
    public String saveTrip(@PathVariable("tripID") int tripID, @PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        return "redirect:/goTouristHome/" + username;
    }

    @GetMapping("/discardTrip/{tripID}/{username}")
    public String discardTrip(@PathVariable("tripID") int tripID, @PathVariable("username") String username, Model model){
        Tourist tourist = touristWrapper.getTourist(username);
        model.addAttribute("tourist", tourist);
        tripWrapper.deleteTripBooking(tripID);
        tripWrapper.deleteTripCost(tripID);
        tripWrapper.deletePlaceFromTrip(tripID);
        tripWrapper.deleteTrip(tripID);
        return "redirect:/goTouristHome/" + username;
    }

}
