package gr.hua.dit.ds.ds_2025.repositories;

import gr.hua.dit.ds.ds_2025.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // Ένα repository που κάνει extend το JpaRepository που έχει έτοιμες μεθόδους

    Optional<User> findByUsername(String username); // Μέθοδος που αναζητά έναν χρήστη με βάση το username

    Optional<User> findByEmail(String email); // Μέθοδος που αναζητά έναν χρήστη με βάση το email
}