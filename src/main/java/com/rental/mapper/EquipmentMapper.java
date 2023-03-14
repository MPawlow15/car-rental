package com.rental.mapper;


import com.rental.domain.Equipment;
import com.rental.domain.dto.EquipmentDto;
import org.springframework.stereotype.Service;

@Service
public class EquipmentMapper {

    public Equipment mapToEquipment(final EquipmentDto equipmentDto) {
        return new Equipment(
                equipmentDto.getName(),
                equipmentDto.getPrice()
        );
    }

    public EquipmentDto mapToEquipmentDto(final Equipment equipment) {
        return new EquipmentDto(
                equipment.getId(),
                equipment.getName(),
                equipment.getPrice()
        );
    }
}