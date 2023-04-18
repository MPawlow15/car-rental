package com.rental.service;

import com.rental.domain.Equipment;
import com.rental.exception.EquipmentNotFoundException;
import com.rental.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentDbService {

    private final EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Equipment getEquipment(Long id) throws EquipmentNotFoundException {
        return equipmentRepository.findById(id).orElseThrow(EquipmentNotFoundException::new);
    }

    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }

    public Equipment updateEquipment(Long id, Equipment equipment) throws EquipmentNotFoundException {
        Equipment equipmentToUpdate = getEquipment(id);
        equipmentToUpdate.setName(equipment.getName());
        equipmentToUpdate.setPrice(equipment.getPrice());
        return equipmentRepository.save(equipmentToUpdate);
    }
}