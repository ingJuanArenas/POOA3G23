package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Taller {
    private final String nombre;
    private final List<OrdenMantenimiento> ordenes = new ArrayList<>();

    public Taller(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarOrden(OrdenMantenimiento orden) {
        if (orden != null) {
            ordenes.add(orden);
        }
    }

    public List<OrdenMantenimiento> getOrdenes() {
        return Collections.unmodifiableList(ordenes);
    }
}


