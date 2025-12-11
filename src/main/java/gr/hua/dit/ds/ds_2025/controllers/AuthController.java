package gr.hua.dit.ds.ds_2025.controllers;

import gr.hua.dit.ds.ds_2025.entities.Role;
import gr.hua.dit.ds.ds_2025.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    RoleRepository roleRepository; // Ένα αντικείμενο τύπου RoleRepository

    public AuthController(RoleRepository roleRepository) { // Constructor της κλάσης
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setup() { // Μέθοδος που δημιουργεί όλους τους ρόλους
        Role role_user = new Role("ROLE_USER"); // Δημιουργία του ρόλου χρήστη(ενοικιαστή)
        roleRepository.updateOrInsert(role_user); // Αποθήκευση του νέου ρόλου

        Role role_admin = new Role("ROLE_ADMIN"); // Δημιουργία του ρόλου διαχειριστή
        roleRepository.updateOrInsert(role_admin); // Αποθήκευση του νέου ρόλου

        Role role_owner = new Role("ROLE_OWNER"); // Δημιουργία του ρόλου ιδιοκτήτη
        roleRepository.updateOrInsert(role_owner); // Αποθήκευση του νέου ρόλου
    }

    @GetMapping("/login")
    public String login() { // Μέθοδος που επιστρέφει το template για το login όταν το επιλέξει ο χρήστης
        return "auth/login";
    }
}