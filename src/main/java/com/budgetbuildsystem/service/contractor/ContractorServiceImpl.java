package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.repository.IContractorRepository;
import com.budgetbuildsystem.repository.IRecommendationsRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class ContractorServiceImpl implements IContractorService {

    private final IContractorRepository repo;
    private final IRecommendationsRepo recommendationRepository;

    @Override
    public void saveContractor(Contractor contractor) {
        try {
            Optional<Contractor> checkContractor = repo.findContractorByEmailAndUsername(contractor.getEmail(), contractor.getUsername());
            if (checkContractor.isPresent()) {
                Contractor contractor1 = new Contractor();
                contractor1.setCompanyName(contractor.getCompanyName());
                contractor1.setLicenseNumber(contractor.getLicenseNumber());
                contractor1.setContactDetails(contractor.getContactDetails());
                repo.save(contractor1);
            } else {
                throw new RuntimeException("Entity EXISTS E");
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal error exception");
        }
    }

    @Override
    public Optional<Contractor> findByName(String contractorName) {
        return Optional.ofNullable(repo.findByCompanyName(contractorName).orElseThrow(() -> new EntityNotFoundException("Contractor not found")));
    }

    @Override
    public Optional<Contractor> findContractorByEmailAndUsername(String email, String username) {
        return Optional.ofNullable(repo.findContractorByEmailAndUsername(email, username).orElseThrow(EntityNotFoundException::new));
    }


    @Override
    public List<Contractor> findAllContractors() {
        return repo.findAll();
    }

    @Override
    public Optional<Contractor> getContractorById(UUID uuid) {
        return Optional.ofNullable(repo.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Contractor of ID : {}" + uuid + "NOT FOUND")));
    }

    @Override
    public double getAverageContractorRating() {
        return repo.calculateAverageRating();
    }

    @Override
    public long countContractors() {
        return repo.count();
    }

    @Override
    public List<Contractor> findTopContractor() {
        return null;
    }

    @Override
    public long getTotalContractors(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return repo.count();
        }
        return repo.countByReviewDateBetween(startDate, endDate);
    }

    @Override
    public double calculateAverageRating(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return repo.calculateAverageRating();
        }
        return repo.calculateAverageRatingBetweenDates(startDate, endDate);
    }

    @Override
    public List<Contractor> findTopContractors(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return repo.findTopContractors();
        }
        return repo.findTopContractorsBetweenDates(startDate, endDate);
    }

    @Override
    public List<Recommendation> findAllReviews() {
        return recommendationRepository.findAll();
    }
    @Override
    public long getTotalReviews(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return recommendationRepository.count();
        }
        return recommendationRepository.countByDateBetween(startDate, endDate);
    }
}
