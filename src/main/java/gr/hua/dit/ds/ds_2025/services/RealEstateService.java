package gr.hua.dit.ds.ds_2025.services;

import gr.hua.dit.ds.ds_2025.entities.RealEstate;
import gr.hua.dit.ds.ds_2025.repositories.RealEstateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealEstateService {

    private RealEstateRepository realEstateRepository; // Ένα αντικείμενο τύπου RealEstateRepository

    public RealEstateService(RealEstateRepository realEstateRepository) { // Constructor της κλάσης
        this.realEstateRepository = realEstateRepository;
    }

    @Transactional
    public List<RealEstate> getRealEstates() { // Μέθοδος που επιστρέφει όλα τα ακίνητα
        return realEstateRepository.findAll();
    }

    @Transactional
    public void saveRealEstate(RealEstate realEstate) { // Μέθοδος που αποθηκεύει ένα νέο ακίνητο
        realEstateRepository.save(realEstate);
    }

    @Transactional
    public RealEstate getRealEstate(Integer realEstateId) { // Μέθοδος που επιστρέφει ένα ακίνητο με βάση το id
        return realEstateRepository.findById(realEstateId).get();
    }

    @Transactional
    public void deleteRealEstate(RealEstate realEstate) { // Μέθοδος που διαγράφει ένα ακίνητο
        realEstateRepository.delete(realEstate);
    }

    @Transactional
    public void updateRealEstate(RealEstate realEstate) { // Μέθοδος που ενημερώνει ένα ακίνητο
        realEstateRepository.save(realEstate);
    }
}