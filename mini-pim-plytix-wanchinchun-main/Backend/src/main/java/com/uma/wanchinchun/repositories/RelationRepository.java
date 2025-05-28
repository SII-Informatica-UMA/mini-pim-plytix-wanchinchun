package com.uma.wanchinchun.repositories;

import com.uma.wanchinchun.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


public interface RelationRepository extends JpaRepository<Relationship, Long> {
    Optional<Relationship> findById(Long relacionId);
}
