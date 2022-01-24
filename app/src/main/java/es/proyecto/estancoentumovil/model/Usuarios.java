package es.proyecto.estancoentumovil.model;

import java.util.HashMap;

public class Usuarios {
    public String uid;
    public String name;
    public String email;
    public String password;
    public HashMap<String, Boolean> carrito;

    public Usuarios(){  }

    public Usuarios(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.carrito= new HashMap<>();
    }
}

