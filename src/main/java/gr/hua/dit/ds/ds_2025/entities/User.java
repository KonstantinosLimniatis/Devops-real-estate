package gr.hua.dit.ds.ds_2025.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"), // Το username θα έχει μοναδική τιμή
                @UniqueConstraint(columnNames = "email") // Το email θα έχει μοναδική τιμή
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Το id του χρήστη που είναι πρωτεύον κλειδί και έχει μοναδική τιμή

    @NotBlank
    @Size(max = 20)
    private String username; // Το username του χρήστη που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι το μέγιστο 20 γράμματα

    @NotBlank
    @Size(max = 50)
    @Email
    private String email; // Το email του χρήστη που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι το μέγιστο 50 γράμματα

    @NotBlank
    @Size(max = 120)
    private String password; // Το password του χρήστη που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι το μέγιστο 120 γράμματα

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(); // Μία σχέση πολλά προς πολλά αφού ένας χρήστης μπορεί να έχει πολλούς ρόλους

    @OneToMany(mappedBy = "user", cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<RealEstate> realEstates = new ArrayList<>(); // Μία σχέση ένα προς πολλά αφόυ ένας χρήστης(ιδιοκτήτης) μπορεί να έχει πολλά ακίνητα

    public List<RealEstate> getRealEstates() { // Getter για τα ακίνητα
        return realEstates;
    }

    public void setRealEstates(List<RealEstate> realEstates) { // Setter για τα ακίνητα
        this.realEstates = realEstates;
    }

    public User() { // Άδειoς constructor για τη δημιουργία ενός χρήστη
    }

    public User(String username, String email, String password) { // Constructor της κλάσης
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Integer getId() { // Getter για το id
        return id;
    }

    public void setId(Integer id) { // Setter για το id
        this.id = id;
    }

    public String getUsername() { // Getter για το username
        return username;
    }

    public void setUsername(String username) { // Setter για το username
        this.username = username;
    }

    public String getEmail() { // Getter για το email
        return email;
    }

    public void setEmail(String email) { // Setter για το email
        this.email = email;
    }

    public String getPassword() {// Getter για το password
        return password;
    }

    public void setPassword(String password) { // Setter για το password
        this.password = password;
    }

    public Set<Role> getRoles() { // Getter για τους ρόλους
        return roles;
    }

    public void setRoles(Set<Role> roles) { // Setter για τους ρόλους
        this.roles = roles;
    }

    @Override
    public String toString() { // Μία toString για να επιστρέφει σε συμβολοσειρά το username του χρήστη
        return username;
    }
}