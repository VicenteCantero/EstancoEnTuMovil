package es.proyecto.estancoentumovil.model;

import java.util.HashMap;

public class Pedido {
    private String uid;
    private String idUsuario;
    private String fecha;
    private String total;

    public Pedido() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return idUsuario+". -"+fecha+"- ("+total+")";
    }


}
