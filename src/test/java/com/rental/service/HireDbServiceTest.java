package com.rental.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rental.exception.HireNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rental.domain.Hire;
import com.rental.repository.HireRepository;

@ExtendWith(MockitoExtension.class)
public class HireDbServiceTest {

    @Mock
    private HireRepository hireRepository;

    @InjectMocks
    private HireDbService hireDbService;

    private Hire hire;

    @BeforeEach
    public void setUp() {
        hire = new Hire();
        hire.setId(1L);
        hire.setDateOfRental(Date.valueOf(LocalDate.of(2021, 1, 3)));
        hire.setDateOfReturn(Date.valueOf(LocalDate.of(2022, 1, 3)));
    }

    @Test
    public void testFindAllHires() {
        List<Hire> hireList = new ArrayList<>();
        hireList.add(hire);
        when(hireRepository.findAll()).thenReturn(hireList);

        List<Hire> result = hireDbService.getAllHires();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(hire, result.get(0));
    }

    @Test
    public void testFindHireById() throws HireNotFoundException {
        when(hireRepository.findById(hire.getId())).thenReturn(Optional.of(hire));

        Optional<Hire> result = Optional.ofNullable(hireDbService.getHireById(hire.getId()));

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(hire, result.get());
    }

    @Test
    public void testAddNewHire() {
        when(hireRepository.save(any(Hire.class))).thenReturn(hire);

        Hire result = hireDbService.addHire(hire);

        Assertions.assertEquals(hire, result);
        verify(hireRepository).save(eq(hire));
    }

}