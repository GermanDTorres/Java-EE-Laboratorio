package moduloComercio.dominio;

import java.util.ArrayList;
import java.util.List;

public class Comercio {

    private String rut;
    private String nombre;
    private String passwordHash; // solo almacenamos el hash
    private List<POS> posList;

    public Comercio(String rut, String nombre, String passwordPlano) {
        this.rut = rut;
        this.nombre = nombre;
        this.posList = new ArrayList<>();
        setPasswordPlano(passwordPlano);
    }

    // Getters y setters básicos
    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Solo obtener el hash (no existe getter para password plano)
    public String getPasswordHash() {
        return passwordHash;
    }

    // Setear password en texto plano -> guarda el hash internamente
    public void setPasswordPlano(String passwordPlano) {
        if (passwordPlano == null || passwordPlano.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede ser vacía");
        }
        this.passwordHash = moduloComercio.util.HashUtil.sha256(passwordPlano);
    }

    // Cambiar contraseña recibe el hash ya calculado (ej: cuando lo quiere cambiar el servicio)
    public void cambiarContrasena(String nuevoHash) {
        if (nuevoHash == null || nuevoHash.isBlank()) {
            throw new IllegalArgumentException("El hash de la contraseña no puede ser vacío");
        }
        this.passwordHash = nuevoHash;
    }

    // Métodos para POS
    public void agregarPOS(POS pos) {
        posList.add(pos);
    }

    public void cambiarEstadoPOS(int posId, EstadoPOS nuevoEstado) {
        for (POS pos : posList) {
            if (pos.getId() == posId) {
                pos.setEstado(nuevoEstado);
                break;
            }
        }
    }

    public List<POS> getPosList() {
        return posList;
    }
}
