package com.rental.mapper;

import com.rental.domain.Equipment;
import com.rental.domain.dto.EquipmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class EquipmentMapperTest {

    @Test
    public void testMapToEquipment() {
        //given
        EquipmentMapper equipmentMapper = new EquipmentMapper();
        EquipmentDto equipmentDto = new EquipmentDto(1L, "test equipment", BigDecimal.valueOf(99.99));

        //when
        Equipment equipment = equipmentMapper.mapToEquipment(equipmentDto);

        //then
        Assertions.assertEquals(equipment.getName(), equipmentDto.getName());
        Assertions.assertEquals(equipment.getPrice(), equipmentDto.getPrice());
    }

    @Test
    public void testMapToEquipmentDto() {
        //given
        EquipmentMapper equipmentMapper = new EquipmentMapper();
        Equipment equipment = new Equipment(1L, "test equipment", BigDecimal.valueOf(99.99), null);

        //when
        EquipmentDto equipmentDto = equipmentMapper.mapToEquipmentDto(equipment);

        //then
        Assertions.assertEquals(equipmentDto.getName(), equipment.getName());
        Assertions.assertEquals(equipmentDto.getPrice(), equipment.getPrice());
    }
}