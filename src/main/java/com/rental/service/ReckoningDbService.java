package com.rental.service;


import com.rental.domain.Reckoning;
import com.rental.exception.ReckoningNotFoundException;
import com.rental.repository.ReckoningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReckoningDbService {

        private final ReckoningRepository reckoningRepository;

        public List<Reckoning> getAllReckonings() {
            return reckoningRepository.findAll();
        }

        public Reckoning getReckoning(final Long id) throws ReckoningNotFoundException {
            return reckoningRepository.findById(id).orElseThrow(ReckoningNotFoundException::new);
        }

        public Reckoning updateReckoning(final Long id, final Reckoning newReckoning) throws ReckoningNotFoundException {
            Reckoning reckoning = getReckoning(id);

            reckoning.setFuelUsed(newReckoning.getFuelUsed());
            reckoning.setEquipmentPrice(newReckoning.getEquipmentPrice());
            reckoning.setPenaltiesPrice(newReckoning.getPenaltiesPrice());
            reckoning.setTotalPrice(newReckoning.getTotalPrice());

            return reckoningRepository.save(reckoning);
        }


        public Reckoning saveReckoning(final Reckoning reckoning) {
            return reckoningRepository.save(reckoning);
        }

        public void deleteReckoning(final Long id) {
            reckoningRepository.deleteById(id);
        }
}