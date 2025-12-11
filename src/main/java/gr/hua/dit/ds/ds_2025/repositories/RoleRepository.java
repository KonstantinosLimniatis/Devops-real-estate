package gr.hua.dit.ds.ds_2025.repositories;

import gr.hua.dit.ds.ds_2025.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> { // Ένα repository που κάνει extend το JpaRepository που έχει έτοιμες μεθόδους

    Optional<Role> findByName(String roleName); // Μέθοδος που αναζητά έναν ρόλο με βάση το όνομα του ρόλου

    default Role updateOrInsert(Role role) { // Μέθοδος που ενημερώνει έναν ρόλο
        Role existing_role = findByName(role.getName()).orElse(null); // Αναζήτηση αν υπάρχει ήδη ο ρόλος με βάση το όνομα του
        if (existing_role != null) { // Αν υπάρχει τον επιστρέφουμε
            return existing_role;
        }
        else { // Αν δεν υπάρχει
            return save(role); // Αποθηκεύουμε τον νέο ρόλο
        }
    }
}