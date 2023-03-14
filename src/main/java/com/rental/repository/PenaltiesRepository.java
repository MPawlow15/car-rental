package com.rental.repository;

import com.rental.domain.Penalties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltiesRepository extends JpaRepository<Penalties, Long> {
}
