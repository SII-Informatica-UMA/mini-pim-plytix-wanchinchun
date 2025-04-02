package com.uma.wanchinchun.repositories;

import com.uma.wanchinchun.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relationship, Long> {
}
