package com.rental.service;

import com.rental.domain.Penalties;
import com.rental.exception.PenaltiesNotFoundException;
import com.rental.repository.PenaltiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltiesDbService {

    private final PenaltiesRepository penaltiesRepository;

    public List<Penalties> getAllPenalties() {
        return penaltiesRepository.findAll();
    }

    public Penalties getPenaltiesById(Long id) throws PenaltiesNotFoundException {
        return penaltiesRepository.findById(id).orElseThrow(PenaltiesNotFoundException::new);
    }

    public Penalties addPenalties(Penalties penalties) {
        return penaltiesRepository.save(penalties);
    }

    public Penalties updatePenalties(Long id, Penalties newPenalties) throws PenaltiesNotFoundException {
        Penalties penalties = getPenaltiesById(id);

        penalties.setPenaltyForDamage(newPenalties.getPenaltyForDamage());
        penalties.setPenaltyForUntimelyReturn(newPenalties.getPenaltyForUntimelyReturn());

        return penaltiesRepository.save(penalties);
    }

    public void deletePenalties(Long id) {
        penaltiesRepository.deleteById(id);
    }
}