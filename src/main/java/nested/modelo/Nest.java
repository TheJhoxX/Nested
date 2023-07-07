package nested.modelo;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class Nest implements Serializable{
    private int idN;
    private int idOrganizador;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private Timestamp fechaRealizacion;
    private Timestamp fechaFinalizacion;
    private int limitePersonas;
    private Boolean publico;
    private Part imagenFondo;
    
    public Nest () {
        this.idOrganizador = 1;
        this.nombre = null;
        this.ubicacion = null;
        this.descripcion = null;
        this.fechaRealizacion = null;
        this.publico = true;
        this.imagenFondo = null;
    }
    
    public Nest (int idN, int idOrganizador, String nombre, String ubicacion,
        String descripcion, Timestamp fechaRealizacion, Timestamp FechaFinalizacion,
        int limitePersonas, Boolean publico, Part imagenFondo){
            setID(idN);
            setIdOrganizador(idOrganizador);
            setNombre(nombre);
            setUbicacion(ubicacion);
            setDescripcion(descripcion);
            setFechaRealizacion(fechaRealizacion);
            setFechaFinalizacion(fechaFinalizacion);
            setLimitePersonas(limitePersonas);
            setPublico(publico);
            setImagenFondo(imagenFondo);
            
    }
    
    public void setID(int id) {
        this.idN = id;
    }
    
    public void setIdOrganizador(int identificadorOrganizador){
        this.idOrganizador = identificadorOrganizador;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaRealizacion(Timestamp fecha) {
        this.fechaRealizacion = fecha;
    }
    
    public void setFechaFinalizacion(Timestamp fecha) {
        this.fechaFinalizacion = fecha;
    }
    
    public void setLimitePersonas(int limite) {
        this.limitePersonas = limite;
    }
    
    public void setPublico(Boolean privacidad) {
        this.publico = privacidad;
    }
    
    public void setImagenFondo(Part imagen) {
        this.imagenFondo = imagen;
    }
    
    public int getIdentificador(){
        return idN;
    }

    public int getIdOrganizador(){
        return idOrganizador;
    }

    public String getNombre(){
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Timestamp getFechaDeRealizacion() {
        return fechaRealizacion;
    }
    
    public Timestamp getFechaDeFinalizacion() {
        return fechaFinalizacion;
    }

    public int getLimitePersonas(){
        return limitePersonas;
    }

    public Boolean getPublico() {
        return publico;
    }

    public Part getImagenFondo() throws FileNotFoundException {
        return imagenFondo;
    }
    
    public  String getIconoCandado(){
        if (publico == true){
            return "lock_open";
        }
        else {
            return "lock";
        }
    }
    
      
}
