package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> 
{
    
}
