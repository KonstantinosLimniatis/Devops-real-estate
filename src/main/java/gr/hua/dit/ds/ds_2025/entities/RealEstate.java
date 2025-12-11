package gr.hua.dit.ds.ds_2025.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import static gr.hua.dit.ds.ds_2025.entities.Rented.No;

@Entity
public class RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer id; // Το id του ακινήτου που είναι πρωτεύον κλειδί και έχει μοναδική τιμή

    @NotEmpty(message = "City is required")
    @Size(min = 3, max = 50)
    @Column
    String city; // Η πόλη του ακινήτου που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι το ελάχιστο 3 γράμματα και το μέγιστο 50 γράμματα

    @NotEmpty(message = "Address is required")
    @Size(min = 3, max = 50)
    @Column
    String address; // Η διεύθυνση του ακινήτου που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι το ελάχιστο 3 γράμματα και το μέγιστο 50 γράμματα

    @Column
    String category; // Ο τύπος του ακινήτου

    @Column
    String description; // Η περιγραφή του ακινήτου

    @NotNull(message = "Size is required")
    @Positive(message = "Size must be a positive number")
    @Column
    Integer size; // Το μέγεθος του ακινήτου που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι μεγαλύτερη του μηδενός(0)

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    @Column
    Integer price; // Η τιμή του ακινήτου που πρέπει να έχει οπωσδήποτε τιμή, η οποία θα είναι μεγαλύτερη του μηδενός(0)

    @Enumerated
    Rented rented=No; // Μεταβλητή για την ενοικίαση ενός ακινήτου που αρχικοποιείται με No αφού όταν προστίθεται ένα ακίνητο δεν είναι ενοικιασμένο

    @Column
    Boolean status=false; // Μεταβλητή για την έγκριση ή απόρριψη ενός ακινήτου από τον διαχειριστή που αρχικοποιείται με false για να πρέπει να το εγκρίνει πρώτα ο διαχειριστής για να προστεθεί στην πλατφόρμα

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user; // Μία σχέση πολλά προς ένα ανάμεσα στα ακίνητα και τον χρήστη, αφού ο χρήστης(ιδιοκτήτης) μπορεί να έχει πολλά ακίνητα

    public User getUser() { // Getter για τον χρήστη
        return user;
    }

    public void setUser(User user) { // Setter για τον χρήστη
        this.user = user;
    }

    public RealEstate(String city, String address, String category, String description, Integer size, Integer price) { // Constructor της κλάσης
        this.city = city;
        this.address = address;
        this.category = category;
        this.description = description;
        this.size = size;
        this.price = price;
    }

    public RealEstate() { // Άδειoς constructor για τη δημιουργία ενός ακινήτου
    }

    public Integer getId() { // Getter για το id
        return id;
    }

    public void setId(Integer id) { // Setter για το id
        this.id = id;
    }

    public String getCity() { // Getter για την πόλη
        return city;
    }

    public void setCity(String city) { // Setter για την πόλη
        this.city = city;
    }

    public String getAddress() { // Getter για τη διεύθυνση
        return address;
    }

    public void setAddress(String address) { // Setter για τη διεύθυνση
        this.address = address;
    }

    public String getCategory() { // Getter για τον τύπο
        return category;
    }

    public void setCategory(String category) { // Setter για τον τύπο
        this.category = category;
    }

    public String getDescription() { // Getter για την περιγραφή
        return description;
    }

    public void setDescription(String description) { // Setter για την περιγραφή
        this.description = description;
    }

    public Integer getSize() { // Getter για το μέγεθος
        return size;
    }

    public void setSize(Integer size) { // Setter για το μέγεθος
        this.size = size;
    }

    public Integer getPrice() { // Getter για την τιμή
        return price;
    }

    public void setPrice(Integer price) { // Setter για την τιμή
        this.price = price;
    }

    public Rented getRented() { // Getter για τη μεταβλητή της ενοικίασηςακινήτου
        return rented;
    }

    public void setRented(Rented rented) { // Setter για τη μεταβλητή της ενοικίασης ακινήτου
        this.rented = rented;
    }

    public Boolean getStatus() { // Getter για τη μεταβλητή της έγκρισης ή απόρριψης ακινήτου
        return status;
    }

    public void setStatus(Boolean status) {// Setter για τη μεταβλητή της έγκρισης ή απόρριψης ακινήτου
        this.status = status;
    }

    @Override
    public String toString() { // Μία toString για να επιστρέφει σε συμβολοσειρά τα στοιχεία του ακινήτου
        return "RealEstate{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", rented=" + rented +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}