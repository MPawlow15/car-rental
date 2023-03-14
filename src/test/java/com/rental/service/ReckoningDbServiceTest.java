package com.rental.service;

import com.rental.domain.Hire;
import com.rental.domain.Reckoning;
import com.rental.exception.HireNotFoundException;
import com.rental.exception.ReckoningNotFoundException;
import com.rental.repository.ReckoningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReckoningDbServiceTest {

    @Mock
    private ReckoningRepository reckoningRepository;

    @Mock
    private HireDbService hireDbService;

    @InjectMocks
    private ReckoningDbService reckoningDbService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReckonings() {
        // given
        Reckoning reckoning1 = new Reckoning(new BigDecimal("100"), new BigDecimal("200"),
                new BigDecimal("250"), new BigDecimal("550"));
        Reckoning reckoning2 = new Reckoning(new BigDecimal("150"), new BigDecimal("200"),
                new BigDecimal("250"), new BigDecimal("600"));
        List<Reckoning> expectedReckonings = Arrays.asList(reckoning1, reckoning2);
        when(reckoningRepository.findAll()).thenReturn(expectedReckonings);

        // when
        List<Reckoning> actualReckonings = reckoningDbService.getAllReckonings();

        // then
        assertEquals(expectedReckonings, actualReckonings);
    }

    @Test
    void shouldReturnReckoningById() throws ReckoningNotFoundException {
        // given
        Reckoning expectedReckoning = new Reckoning(new BigDecimal("100"), new BigDecimal("200"),
                new BigDecimal("250"), new BigDecimal("550"));
        when(reckoningRepository.findById(anyLong())).thenReturn(Optional.of(expectedReckoning));

        // when
        Reckoning actualReckoning = reckoningDbService.getReckoning(1L);

        // then
        assertEquals(expectedReckoning, actualReckoning);
    }

    @Test
    void shouldThrowReckoningNotFoundExceptionWhenReckoningNotFound() {
        // given
        when(reckoningRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when + then
        assertThrows(ReckoningNotFoundException.class, () -> reckoningDbService.getReckoning(1L));
    }

    @Test
    void shouldUpdateReckoning() throws ReckoningNotFoundException {
        // given
        Reckoning existingReckoning = new Reckoning(new BigDecimal("100"), new BigDecimal("200"),
                new BigDecimal("250"), new BigDecimal("550"));
        when(reckoningRepository.findById(anyLong())).thenReturn(Optional.of(existingReckoning));

        Reckoning updatedReckoning = new Reckoning(new BigDecimal("150"), new BigDecimal("200"),
                new BigDecimal("250"), new BigDecimal("600"));
        when(reckoningRepository.save(any(Reckoning.class))).thenReturn(updatedReckoning);

        // when
        Reckoning actualReckoning = reckoningDbService.updateReckoning(1L, updatedReckoning);

        // then
        assertEquals(updatedReckoning, actualReckoning);
        verify(reckoningRepository, times(1)).save(any(Reckoning.class));
    }

}