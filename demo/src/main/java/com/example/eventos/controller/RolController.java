package com.example.eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventos.models.Rol;
import com.example.eventos.repositories.RolRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/roles")
public class RolController 
{
    @Autowired
    private RolRepository rRoles;

    @GetMapping("/getrol")
    public List<Rol> getRol(@RequestParam String param) 
    {
        return (List<Rol>) rRoles.findAll();
    }

    @PostMapping("/postrol")
    public Rol postRol(@RequestBody @Valid Rol rol) 
    {
        return rRoles.save(rol);
    }
}
