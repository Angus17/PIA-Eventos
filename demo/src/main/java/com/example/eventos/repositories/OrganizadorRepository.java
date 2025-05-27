package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.eventos.models.Organizador;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Integer> 
{
    Optional<Organizador> findByEmpresa(String empresa);
}
