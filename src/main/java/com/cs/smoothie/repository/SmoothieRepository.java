package com.cs.smoothie.repository;

import com.cs.smoothie.model.Smoothie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmoothieRepository extends JpaRepository<Smoothie, Long> {
    List<Smoothie> findByEnabled(boolean enabled);
    List<Smoothie> findByName(String name);
    List<Smoothie> findByNameContaining(String name);

    List<Smoothie> findByNameContainingIgnoreCase(String name);
    @Query("UPDATE Smoothie s SET s.enabled = :enabled WHERE s.id = :id")
    @Modifying
    public void updateEnabled(Long id, boolean enabled);}
