package es.proyecto.estancoentumovil.model;

public class Clientes {
    private String uid;
    private String nombreEmpresa;
    private String persona;
    private String telefono;


    public Clientes() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombreEmpresa;
    }

    public void setNombre(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombreEmpresa+". -"+persona+"- ("+telefono+")";
    }
}


