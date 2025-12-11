package gr.hua.dit.ds.ds_2025.controllers;

import gr.hua.dit.ds.ds_2025.entities.RealEstate;
import gr.hua.dit.ds.ds_2025.services.RealEstateService;
import gr.hua.dit.ds.ds_2025.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static gr.hua.dit.ds.ds_2025.entities.Rented.*;

@Controller
@RequestMapping("realestate") // Το αρχικό URL για όλες τις παρακάτω μεθόδους
public class RealEstateController {

    private UserService userService; // Ένα αντικείμενο τύπου UserService

    private RealEstateService realEstateService; // Ένα αντικείμενο τύπου RealEstateService

    public RealEstateController(UserService userService, RealEstateService realEstateService) { // Constructor της κλάσης
        this.userService = userService;
        this.realEstateService = realEstateService;
    }

    @RequestMapping()
    public String showRealEstates(Model model) { // Μέθοδος για την εμφάνιση όλων των ακινήτων
        model.addAttribute("realestates", realEstateService.getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα στο model
        return "realestate/realestates"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/{id}")
    public String showRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την εμφάνιση ενός ακινήτου
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση του id που παίρνουμε από το path
        model.addAttribute("realestate", realestate); // Προσθέτουμε το ακίνητο στο model
        return "realestate/tenancy"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/new")
    public String addRealEstate(Model model) { // Μέθοδος για την προσθήκη ενός ακινήτου
        RealEstate realestate = new RealEstate(); // Δημιουργούμε ένα νέο αδειανό ακίνητο
        model.addAttribute("realestate", realestate); // Προσθέτουμε το ακίνητο στο model
        return "realestate/realestate"; // Επιστρέφουμε το template με τη φόρμα προσθήκης
    }

    @PostMapping("/new")
    public String saveRealEstate(@Valid @ModelAttribute("realestate") RealEstate realestate, BindingResult theBindingResult, Model model) { // Μέθοδος για την αποθήκευση του νέου ακινήτου
        if (theBindingResult.hasErrors()) { // Αν υπάρχει κάποιο error
            System.out.println("error"); // Μήνυμα λάθους στην κονσόλα
            return "realestate/realestate"; // Επιστρέφουμε ξανά το template με τη φόρμα προσθήκης
        } else { // Αν δεν υπάρχει κάποιο error
            realestate.setUser(userService.getUser(userService.getCurrentUserId())); // Ορισμός του χρήστη που είναι συνδεδεμένος εκείνη τη στιγμή(ο χρήστης που εισήγαγε το ακίνητο) ως ιδιοκτήτη του ακινήτου
            realEstateService.saveRealEstate(realestate); // Αποθήκευση του ακινήτου
            Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
            model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
            return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για τη διαγραφή ενός ακινήτου
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realEstateService.deleteRealEstate(realestate); // Διαγράφουμε το ακίνητο
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/rent/{id}")
    public String rentRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την ενοικίαση ενός ακινήτου
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realestate.setRented(In_Progress); // Θέτουμε την τιμή In_Progress στη μεταβλητή rented
        model.addAttribute("realestates", realEstateService.getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα στο model
        return "realestate/realestates"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/rentout/{id}")
    public String rentoutRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την ξενοικίαση ενός ακινήτου
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realestate.setRented(No); // Θέτουμε την τιμή No στη μεταβλητή rented
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/edit/{id}")
    public String editRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την επεξεργασία ενός ακινήτου
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        model.addAttribute("realestate", realestate); // Προσθέτουμε το ακίνητο στο model
        return "realestate/edit"; // Επιστρέφουμε το κατάλληλο template με τη φόρμα επεξεργασίας
    }

    @PostMapping("/edit/{id}")
    public String updateRealEstate(@Valid @ModelAttribute("realestate") RealEstate realestate, BindingResult result, Model model) { // Μέθοδος για την ενημέρωση ενός ακινήτου
        if (result.hasErrors()) { // Αν υπάρχει κάποιο error
            return "realestate/edit"; // Επιστρέφουμε ξανά το template με τη φόρμα επεξεργασίας
        }
        realestate.setStatus(false); // Θέτουμε την τιμή false στη μεταβλητή status για να πρέπει να το εγκρίνει ο διαχειριστής
        realestate.setUser(userService.getUser(userService.getCurrentUserId())); // Ορισμός του χρήστη που είναι συνδεδεμένος εκείνη τη στιγμή(ο χρήστης που εισήγαγε το ακίνητο) ως ιδιοκτήτη του ακινήτου
        realEstateService.updateRealEstate(realestate); // Ενημέρωση του ακινήτου
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/shownotapproved")
    public String showNotApprovedRealEstates(Model model) { // Μέθοδος για την εμφάνιση των νέων ακινήτων στον διαχειριστή για να τα εγκρίνει ή να τα απορρίψει
        model.addAttribute("realestates", realEstateService.getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα στο model
        return "realestate/notapproved"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/approve/{id}")
    public String approveRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την έγκριση ενός ακινήτου από τον διαχειριστή
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realestate.setStatus(true); // Θέτουμε την τιμή true στη μεταβλητή status για να φαίνεται ότι το έχει εγκρίνει ο διαχειριστής
        model.addAttribute("realestates", realEstateService.getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα στο model
        return "realestate/realestates"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/decline/{id}")
    public String declineRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την απόρριψη ενός ακινήτου από τον διαχειριστή
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realEstateService.deleteRealEstate(realestate); // Διαγράφουμε το ακίνητο
        model.addAttribute("realestates", realEstateService.getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα στο model
        return "realestate/realestates"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/shownotrented")
    public String showNotRentedRealEstates(Model model) { // Μέθοδος για την εμφάνιση των ακινήτων, στα οποία έχει γίνει αίτηση ενοικίασης
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/notrented"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/accept/rent/{id}")
    public String acceptRentRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την αποδοχή μίας αίτησης ενοικίασης από τον ιδιοκτήτη
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realestate.setRented(Yes); // Θέτουμε την τιμή Yes στη μεταβλητή rented
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/decline/rent/{id}")
    public String declineRentRealEstate(@PathVariable Integer id, Model model) { // Μέθοδος για την απόρριψη μίας αίτησης ενοικίασης από τον ιδιοκτήτη
        RealEstate realestate = realEstateService.getRealEstate(id); // Παίρνουμε το ακίνητο με βάση το id που παίρνουμε από το path
        realestate.setRented(No); // Θέτουμε την τιμή No στη μεταβλητή rented
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/owner")
    public String showOwnerRealEstates(Model model) { // Μέθοδος για την εμφάνιση των ακινήτων ενός ιδιοκτήτη
        Integer user_id = userService.getCurrentUserId(); // Παίρνουμε το id του συνδεδεμένου χρήστη
        model.addAttribute("realestates",userService.getUser(user_id).getRealEstates()); // Προσθέτουμε τη λίστα με τα ακίνητα αυτού του χρήστη στο model
        return "realestate/owner"; // Επιστρέφουμε το κατάλληλο template
    }
}