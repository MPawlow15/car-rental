package com.rental.controller;

import com.rental.domain.Hire;
import com.rental.domain.dto.HireDto;
import com.rental.exception.HireNotFoundException;
import com.rental.mapper.HireMapper;
import com.rental.service.HireDbService;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HireControllerTest {

    @InjectMocks
    private HireController hireController;

    @Mock
    private HireDbService hireDbService;

    @Mock
    private HireMapper hireMapper;

    private Hire hire;
    private HireDto hireDto;

    @BeforeEach
    void setUp() {
        hire = new Hire(new Date(2023,2,2), new Date(2023,6,2), BigDecimal.valueOf(1000));
        hireDto = new HireDto(1L, new Date(2023,2,2), new Date(2023,6,2), BigDecimal.valueOf(1000));
    }

    @Test
    void shouldGetAllHires() {
        // given
        Hire hire2 = new Hire(new Date(2023,3,2), new Date(2023,7,2), BigDecimal.valueOf(1500));
        HireDto hireDto2 = new HireDto(2L, new Date(2023,3,2), new Date(2023,7,2), BigDecimal.valueOf(1500));
        when(hireDbService.getAllHires()).thenReturn(Arrays.asList(hire, hire2));
        when(hireMapper.mapToHireDto(hire)).thenReturn(hireDto);
        when(hireMapper.mapToHireDto(hire2)).thenReturn(hireDto2);

        // when
        ResponseEntity<List<HireDto>> response = hireController.getAllHires();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(hireDto, response.getBody().get(0));
        assertEquals(hireDto2, response.getBody().get(1));
    }

    @Test
    void shouldGetHire() throws HireNotFoundException {
        // given
        when(hireDbService.getHireById(1L)).thenReturn(hire);
        when(hireMapper.mapToHireDto(hire)).thenReturn(hireDto);

        // when
        ResponseEntity<HireDto> response = hireController.getHire(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hireDto, response.getBody());
    }

    @Test
    void shouldCreateHire() {
        // given
        when(hireMapper.mapToHire(hireDto)).thenReturn(hire);
        when(hireDbService.addHire(hire)).thenReturn(hire);
        when(hireMapper.mapToHireDto(hire)).thenReturn(hireDto);

        // when
        ResponseEntity<HireDto> response = hireController.createHire(hireDto);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(hireDto, response.getBody());
    }

    @Test
    void shouldUpdateHire() throws HireNotFoundException {
        // given
        when(hireMapper.mapToHire(hireDto)).thenReturn(hire);
        when(hireDbService.addHire(hire)).thenReturn(hire);
        when(hireMapper.mapToHireDto(hire)).thenReturn(hireDto);

        // when
        ResponseEntity<HireDto> response = hireController.updateHire(1L, hireDto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hireDto, response.getBody());
    }

    @Test
    void shouldDeleteHire() {
        // given
        when(hireMapper.mapToHire(hireDto)).thenReturn(hire);
        when(hireDbService.addHire(hire)).thenReturn(hire);
        when(hireMapper.mapToHireDto(hire)).thenReturn(hireDto);

        // when
        hireController.createHire(hireDto);
        ResponseEntity<HireDto> response = hireController.deleteHire(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(hireDbService, times(1)).deleteHire(1L);
    }
}