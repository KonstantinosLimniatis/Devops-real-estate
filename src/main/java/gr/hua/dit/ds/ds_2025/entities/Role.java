package gr.hua.dit.ds.ds_2025.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Το id του ρόλου που είναι πρωτεύον κλειδί και έχει μοναδική τιμή

    @Column(length = 20)
    private String name; // Τό όνομα του ρόλους που μπορεί να έχει τιμή, η οποία θα είναι το μέγιστο 20 γράμματα

    public Role() { // Άδειoς constructor
    }

    public Role(String name) { // Constructor της κλάσης
        this.name = name;
    }

    public Integer getId() { // Getter για το id
        return id;
    }

    public void setId(Integer id) { // Setter για το id
        this.id = id;
    }

    public String getName() { // Getter για το όνομα
        return name;
    }

    public void setName(String name) { // Setter για το όνομα
        this.name = name;
    }

    @Override
    public String toString() { // Μία toString για να επιστρέφει σε συμβολοσειρά το όνομα του ρόλου
        return name;
    }
}