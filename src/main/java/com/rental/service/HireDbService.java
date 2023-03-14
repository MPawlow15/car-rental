package com.rental.service;

import com.rental.domain.Hire;
import com.rental.exception.HireNotFoundException;
import com.rental.repository.HireRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HireDbService {

    private final HireRepository hireRepository;

    public HireDbService(HireRepository hireRepository) {
        this.hireRepository = hireRepository;
    }

    public List<Hire> getAllHires() {
        return hireRepository.findAll();
    }

    public Hire getHireById(Long id) throws HireNotFoundException {
        return hireRepository.findById(id).orElseThrow(HireNotFoundException::new);
    }

    public Hire addHire(Hire hire) {
        return hireRepository.save(hire);
    }

    public void deleteHire(Long id) {
        hireRepository.deleteById(id);
    }
}