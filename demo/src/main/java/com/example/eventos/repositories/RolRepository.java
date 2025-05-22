package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>
{
    
}
