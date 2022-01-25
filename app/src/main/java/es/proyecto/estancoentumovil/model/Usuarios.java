package es.proyecto.estancoentumovil.model;

import java.util.HashMap;

public class Usuarios {
    private String uid;
    private String nombre;
    private String email;
    private String password;
    private HashMap<String, Boolean> carrito;

    public Usuarios(){  }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Boolean> getCarrito() {
        return carrito;
    }

    public void setCarrito(HashMap<String, Boolean> carrito) {
        this.carrito = carrito;
    }

    /*public Usuarios(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.carrito= new HashMap<>();
    }*/
}

