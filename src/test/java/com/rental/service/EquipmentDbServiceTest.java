package com.rental.service;

import com.rental.domain.Equipment;
import com.rental.exception.EquipmentNotFoundException;
import com.rental.repository.EquipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentDbServiceTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentDbService equipmentDbService;

    @Captor
    private ArgumentCaptor<Equipment> equipmentCaptor;

    private final Long equipmentId = 1L;
    private Equipment equipment;

    @BeforeEach
    void setUp() {
        equipment = new Equipment("Test", BigDecimal.valueOf(100));
    }

    @Test
    void testAddEquipment() {
        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        Equipment result = equipmentDbService.saveEquipment(equipment);

        verify(equipmentRepository, times(1)).save(equipmentCaptor.capture());
        Assertions.assertEquals(equipment, equipmentCaptor.getValue());
        Assertions.assertEquals(equipment, result);
    }

    @Test
    void testGetAllEquipment() {
        when(equipmentRepository.findAll()).thenReturn(Arrays.asList(equipment));

        List<Equipment> result = equipmentDbService.getAllEquipment();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(equipment, result.get(0));
    }

    @Test
    void testGetExistingEquipment() throws EquipmentNotFoundException {
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(equipment));

        Equipment result = equipmentDbService.getEquipment(equipmentId);

        Assertions.assertEquals(equipment, result);
    }

    @Test
    void testGetNonExistingEquipment() {
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EquipmentNotFoundException.class, () -> equipmentDbService.getEquipment(equipmentId));
    }
}