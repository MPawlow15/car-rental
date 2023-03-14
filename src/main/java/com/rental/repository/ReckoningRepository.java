package com.rental.repository;

import com.rental.domain.Reckoning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReckoningRepository extends JpaRepository<Reckoning, Long> {
}
