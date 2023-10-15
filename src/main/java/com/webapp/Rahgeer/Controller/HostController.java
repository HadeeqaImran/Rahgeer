package com.webapp.Rahgeer.Controller;

import com.webapp.Rahgeer.DBWrapper.*;
import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;
import com.webapp.Rahgeer.OTPScript.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HostController {
    DBConnection db = new DBConnection();
    HostWrapper hostWrapper = new HostWrapper(db);
    UserWrapper userWrapper = new UserWrapper(db);
    MailSender mailSender = new MailSender();
    PlaceWrapper placeWrapper = new PlaceWrapper(db);
    TouristWrapper touristWrapper = new TouristWrapper(db);
    MessageWrapper messageWrapper = new MessageWrapper(db);

    @PostMapping("/addHost")
    public String addHost(@RequestParam String username, @RequestParam String name, @RequestParam String password, @RequestParam String email, @RequestParam String cnic, Model model){
        if (hostWrapper.hostAgainstUsername(username)){
            model.addAttribute("msg", "Username already exists");
            return "signupHost";
        }
        else if (hostWrapper.hostAgainstEmail(email)){
            model.addAttribute("msg", "Email already exists");
            return "signupHost";
        }
        else if (hostWrapper.hostAgainstCNIC(cnic)){
            model.addAttribute("msg", "CNIC already exists");
            return "signupHost";
        }
        else{
            userWrapper.addUser(username, name, password, email);
            hostWrapper.addHost(username, cnic, (float) 0);
            model.addAttribute("msg", "Host added successfully");
            return "login";
        }
    }

    @GetMapping("/hostProfile/{username}")
    public String hostProfile(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        List<Place> places = placeWrapper.getPlacesOfOwner(username);
        Place[] placesArray = new Place[places.size()];
        placesArray = places.toArray(placesArray);
        model.addAttribute("places", placesArray);
        return "profile";
    }

    @GetMapping("/goToHome/{username}")
    public String goToHome(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        List<Place> places = placeWrapper.getPlacesOfOwner(username);
        Place[] placesArray = new Place[places.size()];
        placesArray = places.toArray(placesArray);
        model.addAttribute("places", placesArray);
        return "homeHost";
    }

    @GetMapping("/managePlace/{username}")
    public String managePlace(Model model, @PathVariable("username") String username){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "managePlaces";
    }

    @GetMapping("/updateHostPass/{email}")
    public String updateHostPass(@PathVariable("email") String email, Model model){
        User user = userWrapper.getUserByEmail(email);
        Integer otp = userWrapper.generateOTP();
        mailSender.sendMail(email, "One Time Password", "Your OTP is " + otp);
        model.addAttribute("email", email);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("msg", "OTP sent to your email");
        model.addAttribute("otpcode", otp);
        return "changePassword";
    }

    @GetMapping("/updateHostProfile/{username}")
    public String updateHostProfile(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "updateProfile";
    }

    @PostMapping("/updateHostProfile/{email}")
    public String updateHostProfile(@PathVariable("email") String email, @RequestParam String name, Model model){
        Host host = hostWrapper.getHostByEmail(email);
        hostWrapper.updateName(host.getUsername(), name);
        model.addAttribute("host", host);
        List<Request> requests = touristWrapper.getAllAvailableRequests();
        Request[] requestsArray = new Request[requests.size()];
        requestsArray = requests.toArray(requestsArray);
        System.out.println("Got " + requestsArray.length + " requests");
        model.addAttribute("requests", requestsArray);
        return "redirect:/goToHome/" + host.getUsername();
    }

    @GetMapping("/getRequests/{username}")
    public String getRequests(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        List<Request> requests = touristWrapper.getAllAvailableRequests();
        Request[] requestsArray = new Request[requests.size()];
        requestsArray = requests.toArray(requestsArray);
        System.out.println("Got " + requestsArray.length + " requests");
        model.addAttribute("requests", requestsArray);
        return "hostRequests";
    }

    @GetMapping("/acceptRequest/{createdBy}/{createdOn}/{username}")
    public String acceptRequest(@PathVariable("createdBy") String createdBy, @PathVariable("createdOn") String createdOn, @PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        touristWrapper.acceptRequest(createdBy, createdOn, username, dtf.format(now).toString());
        List<Request> requests = touristWrapper.getAllAvailableRequests();
        Request[] requestsArray = new Request[requests.size()];
        requestsArray = requests.toArray(requestsArray);
        System.out.println("Got " + requestsArray.length + " requests");
        model.addAttribute("requests", requestsArray);
        return "redirect:/getRequests/" + username;
    }

    @GetMapping("/hostMessengerButton/{username}")
    public String hostMessengerButton(@PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        List<Tourist> chats = messageWrapper.getChatUsersTourist(username);
        if (chats.size() == 0){
            return "nomessagehost";
        }
        Tourist[] chatsArray = new Tourist[chats.size()];
        chatsArray = chats.toArray(chatsArray);
        model.addAttribute("chats", chatsArray);
        List<Message> messages = messageWrapper.getAllMessages(username, chatsArray[0].getUsername());
        Message[] messagesArray = new Message[messages.size()];
        messagesArray = messages.toArray(messagesArray);
        model.addAttribute("messages", messagesArray);
        Tourist tourist = touristWrapper.getTourist(chatsArray[0].getUsername());
        model.addAttribute("tourist", tourist);
        return "hostMessanger";
    }

    @GetMapping("/chatWithTourist/{createdBy}/{username}")
    public String chatWithTourist(@PathVariable("createdBy") String createdBy, @PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        Tourist tourist = touristWrapper.getTourist(createdBy);
        model.addAttribute("tourist", tourist);
        model.addAttribute("createdBy", createdBy);
        List<Message> messages = messageWrapper.getAllMessages(createdBy, username);
        Message[] messagesArray = new Message[messages.size()];
        messagesArray = messages.toArray(messagesArray);
        model.addAttribute("messages", messagesArray);
        return "hostmessage";
    }

    @GetMapping("/chatWithTourist2/{passport}/{username}")
    public String chatWithTourist2(@PathVariable("passport") String passport, @PathVariable("username") String username, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        Tourist tourist = touristWrapper.getTouristByPassport(passport);
        model.addAttribute("tourist", tourist);
        model.addAttribute("createdBy", tourist.getUsername());
        List<Message> messages = messageWrapper.getAllMessages(tourist.getUsername(), username);
        Message[] messagesArray = new Message[messages.size()];
        messagesArray = messages.toArray(messagesArray);
        model.addAttribute("messages", messagesArray);
        return "hostmessage";
    }

    @PostMapping("/sendMessagetoTourist/{createdBy}/{username}")
    public String sendMessagetoTourist(@PathVariable("createdBy") String createdBy, @PathVariable("username") String username, @RequestParam String message, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        Tourist tourist = touristWrapper.getTourist(createdBy);
        model.addAttribute("tourist", tourist);
        model.addAttribute("createdBy", createdBy);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String temp = messageWrapper.checkForOffensiveWords(message);
        if (temp.equals(message)) {
            messageWrapper.addMessage(username, createdBy, message, dtf.format(now).toString());
            return "redirect:/chatWithTourist/" + createdBy + "/" + username;
        }
        else {
            messageWrapper.addMessage(username, createdBy, temp, dtf.format(now).toString());
            userWrapper.blockUser(username);
            return "redirect:/login";
        }
    }

    @PostMapping("/sendMessagetoTourist2/{createdBy}/{username}")
    public String sendMessagetoTourist2(@PathVariable("createdBy") String createdBy, @PathVariable("username") String username, @RequestParam String message, Model model){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        Tourist tourist = touristWrapper.getTourist(createdBy);
        model.addAttribute("tourist", tourist);
        model.addAttribute("createdBy", createdBy);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String temp = messageWrapper.checkForOffensiveWords(message);
        if (temp.equals(message)) {
            messageWrapper.addMessage(username, createdBy, message, dtf.format(now).toString());
            return "redirect:/chatWithTourist/" + createdBy + "/" + username;
        }
        else {
            messageWrapper.addMessage(username, createdBy, temp, dtf.format(now).toString());
            userWrapper.blockUser(username);
            return "redirect:/login";
        }
    }
    //Added
    @GetMapping("/hostPublicProfile/{username}")
    public String hostPublicProfile(Model model, @PathVariable("username") String username){
        Host host = hostWrapper.getHost(username);
        model.addAttribute("host", host);
        return "hostPublicProfile";
    }

}
