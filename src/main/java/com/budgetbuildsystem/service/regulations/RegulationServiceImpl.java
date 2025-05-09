package com.budgetbuildsystem.service.regulations;

import com.budgetbuildsystem.exception.BuildingRegulationNotFoundException;
import com.budgetbuildsystem.model.Regulations;
import com.budgetbuildsystem.repository.IRegulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class RegulationServiceImpl implements IRegulationsService {

    @Autowired
    private IRegulationRepository repository;

    @Override
    public List<Regulations> getAllRegulations() {
        return repository.findAll();
    }

    @Override
    public Optional<Regulations> getRegulationById(UUID id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new BuildingRegulationNotFoundException("Regulation with ID " + id + "")));
    }

    @Override
    public Regulations saveRegulation(Regulations regulation) {
        return repository.save(regulation);
    }

    @Override
    public void deleteRegulationById(UUID id) {
        Optional<Regulations> regulation = repository.findById(id);
        if (regulation.isEmpty()) {
            throw new BuildingRegulationNotFoundException("Regulation with ID " + id + " not found");
        }
        repository.deleteById(id);
    }



    @Override
    public long getTotalRegulations() {
        return repository.count();
    }
}
