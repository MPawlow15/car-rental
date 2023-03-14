package com.rental.mapper;

import com.rental.domain.Hire;
import com.rental.domain.dto.HireDto;
import org.springframework.stereotype.Service;

@Service
public class HireMapper {

    public Hire mapToHire(final HireDto hireDto) {
        return new Hire(
                hireDto.getDateOfRental(),
                hireDto.getDateOfReturn(),
                hireDto.getPenalty()
        );
    }

    public HireDto mapToHireDto(final Hire hire) {
        return new HireDto(
                hire.getId(),
                hire.getDateOfRental(),
                hire.getDateOfReturn(),
                hire.getPenalty()
        );
    }
}
