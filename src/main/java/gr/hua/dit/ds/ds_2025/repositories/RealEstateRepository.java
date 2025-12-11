package gr.hua.dit.ds.ds_2025.repositories;

import gr.hua.dit.ds.ds_2025.entities.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Integer> { // Ένα repository που κάνει extend το JpaRepository που έχει έτοιμες μεθόδους
}