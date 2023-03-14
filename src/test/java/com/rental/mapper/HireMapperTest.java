package com.rental.mapper;

import com.rental.domain.Hire;
import com.rental.domain.dto.HireDto;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HireMapperTest {

    private HireMapper hireMapper = new HireMapper();

    @Test
    public void shouldMapToHire() {
        // given
        HireDto hireDto = new HireDto(1L, new Date(), new Date(), BigDecimal.valueOf(99.99));

        // when
        Hire hire = hireMapper.mapToHire(hireDto);

        // then
        assertEquals(hireDto.getDateOfRental(), hire.getDateOfRental());
        assertEquals(hireDto.getDateOfReturn(), hire.getDateOfReturn());
        assertEquals(hireDto.getPenalty(), hire.getPenalty());
    }

    @Test
    public void shouldMapToHireDto() {
        // given
        Hire hire = new Hire(new Date(), new Date(), BigDecimal.valueOf(99.99));

        // when
        HireDto hireDto = hireMapper.mapToHireDto(hire);

        // then
        assertEquals(hire.getId(), hireDto.getId());
        assertEquals(hire.getDateOfRental(), hireDto.getDateOfRental());
        assertEquals(hire.getDateOfReturn(), hireDto.getDateOfReturn());
        assertEquals(hire.getPenalty(), hireDto.getPenalty());
    }
}