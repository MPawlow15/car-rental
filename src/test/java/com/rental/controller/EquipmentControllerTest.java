package com.rental.controller;

import com.rental.domain.Equipment;
import com.rental.domain.dto.EquipmentDto;
import com.rental.exception.EquipmentNotFoundException;
import com.rental.mapper.EquipmentMapper;
import com.rental.service.EquipmentDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentControllerTest {

    @InjectMocks
    private EquipmentController equipmentController;

    @Mock
    private EquipmentDbService equipmentDbService;

    @Mock
    private EquipmentMapper equipmentMapper;

    Equipment equipment;
    EquipmentDto equipmentDto;

    @BeforeEach
    public void setUp() {
        equipment = new Equipment("Leather seats", BigDecimal.valueOf(500));
        equipmentDto = new EquipmentDto(1L, "Leather seats", BigDecimal.valueOf(500));
    }

    @Test
    void shouldGetAllEquipments() {
        // given
        Equipment equipment2 = new Equipment("GPS", BigDecimal.valueOf(100));
        EquipmentDto equipmentDto2 = new EquipmentDto(2L, "GPS", BigDecimal.valueOf(100));
        when(equipmentDbService.getAllEquipment()).thenReturn(Arrays.asList(equipment, equipment2));
        when(equipmentMapper.mapToEquipmentDto(equipment)).thenReturn(equipmentDto);
        when(equipmentMapper.mapToEquipmentDto(equipment2)).thenReturn(equipmentDto2);

        // when
        ResponseEntity<List<EquipmentDto>> response = equipmentController.getAllEquipments();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(equipmentDto, response.getBody().get(0));
        assertEquals(equipmentDto2, response.getBody().get(1));
    }

    @Test
    void shouldGetEquipment() throws EquipmentNotFoundException {
        // given
        when(equipmentDbService.getEquipment(1L)).thenReturn(equipment);
        when(equipmentMapper.mapToEquipmentDto(equipment)).thenReturn(equipmentDto);

        // when
        ResponseEntity<EquipmentDto> response = equipmentController.getEquipment(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(equipmentDto, response.getBody());
    }

    @Test
    void shouldCreateEquipment() {
        // given
        when(equipmentMapper.mapToEquipment(equipmentDto)).thenReturn(equipment);
        when(equipmentDbService.saveEquipment(equipment)).thenReturn(equipment);
        when(equipmentMapper.mapToEquipmentDto(equipment)).thenReturn(equipmentDto);

        // when
        ResponseEntity<EquipmentDto> response = equipmentController.createEquipment(equipmentDto);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(equipmentDto, response.getBody());
    }

    @Test
    void shouldDeleteEquipment() throws EquipmentNotFoundException {
        // given
        when(equipmentMapper.mapToEquipment(equipmentDto)).thenReturn(equipment);
        when(equipmentDbService.saveEquipment(equipment)).thenReturn(equipment);
        when(equipmentMapper.mapToEquipmentDto(equipment)).thenReturn(equipmentDto);

        // when
        equipmentController.createEquipment(equipmentDto);
        ResponseEntity<EquipmentDto> response = equipmentController.deleteEquipment(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(equipmentDbService, times(1)).deleteEquipment(1L);
    }

    @Test
    void shouldUpdateEquipment() throws EquipmentNotFoundException {
        // given
        when(equipmentMapper.mapToEquipment(equipmentDto)).thenReturn(equipment);
        when(equipmentDbService.saveEquipment(equipment)).thenReturn(equipment);
        when(equipmentMapper.mapToEquipmentDto(equipment)).thenReturn(equipmentDto);

        // when
        equipmentController.createEquipment(equipmentDto);
        ResponseEntity<EquipmentDto> response = equipmentController.updateEquipment(1L, equipmentDto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(equipmentDto, response.getBody());
    }
}