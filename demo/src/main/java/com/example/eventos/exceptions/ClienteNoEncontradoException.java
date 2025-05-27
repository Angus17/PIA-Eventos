package com.example.eventos.exceptions;

public class ClienteNoEncontradoException extends RuntimeException 
{
    public ClienteNoEncontradoException(String message) 
    {
        super(message);
    }
    
}
