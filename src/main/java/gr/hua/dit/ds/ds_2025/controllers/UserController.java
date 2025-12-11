package gr.hua.dit.ds.ds_2025.controllers;

import gr.hua.dit.ds.ds_2025.entities.Role;
import gr.hua.dit.ds.ds_2025.entities.User;
import gr.hua.dit.ds.ds_2025.repositories.RoleRepository;
import gr.hua.dit.ds.ds_2025.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserService userService; // Ένα αντικείμενο τύπου UserService

    private RoleRepository roleRepository; // Ένα αντικείμενο τύπου RoleRepository

    public UserController(UserService userService, RoleRepository roleRepository) { // Constructor της κλάσης
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String register(Model model) { // Μέθοδος για το register
        User user = new User(); // Δημιουργούμε νέο χρήστη
        model.addAttribute("user", user); // Προσθέτουμε τον χρήστη στο model
        return "auth/register"; // Επιστρέφουμε το κατάλληλο template με τη φόρμα για να κάνει ο χρήστης register
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model) { // Μέθοδος για την αποθήκευση ενός χρήστη
        System.out.println("Roles: "+user.getRoles()); // Εκτυπώνουμε τους ρόλους του χρήστη στην κονσόλα
        Integer id = userService.saveUser(user); // Αποθηκεύουμε τον χρήστη
        String message = "User '"+id+"' saved successfully !"; // Μήνυμα επιτυχίας
        model.addAttribute("msg", message); // Προσθέτουμε το μήνυμα επιτυχίας στο model
        return "index"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/users")
    public String showUsers(Model model) { // Μέθοδος για την εμφάνιση των χρηστών
        model.addAttribute("users", userService.getUsers()); // Προσθέτουμε τους χρήστες στο model
        model.addAttribute("roles", roleRepository.findAll()); // Προσθέτουμε τους ρόλους στο model
        return "auth/users"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Integer user_id, Model model) { // Μέθοδος για την επεξεργασία των στοιχείων ενός χρήστη
        model.addAttribute("user", userService.getUser(user_id)); // Προσθέτουμε τον χρήστη στο model
        return "auth/user"; // Επιστρέφουμε το κατάλληλο template με τη φόρμα για την επεξεργασία των στοιχείων του χρήστη
    }

    @PostMapping("/user/{user_id}")
    public String editUser(@PathVariable Integer user_id, @ModelAttribute("user") User user, Model model) { // Μέθοδος για την ενημέρωση των στοιχείων του χρήστη
        User the_user = (User) userService.getUser(user_id); // Παίρνουμε τον χρήστη με βάση το id του
        the_user.setEmail(user.getEmail()); // Θέτουμε το καινούριο email
        the_user.setUsername(user.getUsername()); // Θέτουμε το καινούριο username
        userService.updateUser(the_user); // Ενημερώνουμε τα στοιχεία του χρήστη
        model.addAttribute("users", userService.getUsers()); // Προσθέτουμε τους χρήστες στο model
        model.addAttribute("roles", roleRepository.findAll()); // Προσθέτουμε τους ρόλους στο model
        return "auth/users"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Integer user_id, @PathVariable Integer role_id, Model model) { // Μέθοδο για τη διαγραφή ενός ρόλου του χρήστη
        User user = (User) userService.getUser(user_id); // Παίρνουμε τον χρήστη με βάση το id του
        Role role = roleRepository.findById(role_id).get(); // Παίρνουμε τον ρόλο με βάση το id του
        user.getRoles().remove(role); // Διαγράφουμε τον ρόλο από τον χρήστη
        System.out.println("Roles: "+user.getRoles()); // Εκτυπώνουμε τους ρόλους του χρήστη στην κονσόλα
        userService.updateUser(user); // Ενημερώνουμε τον χρήστη
        model.addAttribute("users", userService.getUsers()); // Προσθέτουμε τους χρήστες στο model
        model.addAttribute("roles", roleRepository.findAll()); // Προσθέτουμε τους ρόλους στο model
        return "auth/users"; // Επιστρέφουμε το κατάλληλο template
    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Integer user_id, @PathVariable Integer role_id, Model model) { // Μέθοδο για την προσθήκη ενός ρόλου του χρήστη
        User user = (User) userService.getUser(user_id); // Παίρνουμε τον χρήστη με βάση το id του
        Role role = roleRepository.findById(role_id).get(); // Παίρνουμε τον ρόλο με βάση το id του
        user.getRoles().add(role); // Προσθέτουμε τον ρόλο στον χρήστη
        System.out.println("Roles: "+user.getRoles()); // Εκτυπώνουμε τους ρόλους του χρήστη στην κονσόλα
        userService.updateUser(user); // Ενημερώνουμε τον χρήστη
        model.addAttribute("users", userService.getUsers()); // Προσθέτουμε τους χρήστες στο model
        model.addAttribute("roles", roleRepository.findAll()); // Προσθέτουμε τους ρόλους στο model
        return "auth/users"; // Επιστρέφουμε το κατάλληλο template
    }
}