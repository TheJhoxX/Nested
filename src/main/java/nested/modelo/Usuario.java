/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nested.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import javax.servlet.http.*;

/**
 *
 * @author Jacobo M
 */
public class Usuario implements Serializable {

    private int idU;
    private String nombre;
    private String apellidos;
    private String correo;
    private LocalDate fechaNacimiento;
    private String universidad;
    private String grado;
    private String instagram;
    private String arroba;
    private String contrasena;
    private String sexo;
    private Part pfp;
    private int reputacion;

    public Usuario() {
        this.nombre = "";
        this.apellidos = "";
        this.correo = "";
        this.fechaNacimiento = null;
        this.universidad = "";
        this.grado = "";
        this.instagram = "";
        this.arroba = "";
        this.contrasena = "";
        this.sexo = "";
        this.pfp = null;
    }

    public Usuario(int idU, String nombre, String apellidos, String correo, 
            LocalDate fechaNacimiento, String universidad, String grado, 
            String instagram, String arroba, String contrasena, String sexo,
            Part imagenFondo, int reputacion) {
        this.idU = idU;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.universidad = universidad;
        this.grado = grado;
        this.instagram = instagram;
        this.arroba = arroba;
        this.contrasena = contrasena;
        this.sexo = sexo;
        this.reputacion = reputacion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getId() {
        return idU;
    }

    public void setId(int id) {
        this.idU = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getArroba() {
        return arroba;
    }

    public void setArroba(String arroba) {
        this.arroba = arroba;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public void setPfp(Part pfp){
        this.pfp = pfp;
    }
    
    public Part getPfp() {
        return pfp;
    }
    
    public void setReputacion(int reputacion) {
        this.reputacion = reputacion;
    }
    
    public int getReputacion() {
        return this.reputacion;
    }
}
