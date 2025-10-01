package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Tren {
    private final String numeroSerie;
    private final String modelo;
    private final LocalDate fechaIngresoTaller;

    public Tren(String numeroSerie, String modelo, LocalDate fechaIngresoTaller) {
        this.numeroSerie = Objects.requireNonNull(numeroSerie, "numeroSerie no puede ser null").trim();
        this.modelo = Objects.requireNonNull(modelo, "modelo no puede ser null").trim();
        this.fechaIngresoTaller = Objects.requireNonNull(fechaIngresoTaller, "fechaIngresoTaller no puede ser null");
        if (this.numeroSerie.isEmpty()) {
            throw new IllegalArgumentException("El número de serie no puede estar vacío");
        }
        if (this.modelo.isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío");
        }
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getModelo() {
        return modelo;
    }

    public LocalDate getFechaIngresoTaller() {
        return fechaIngresoTaller;
    }

    
    @Override
    public String toString() {
        return "Tren{" +
                "numeroSerie='" + numeroSerie + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fechaIngresoTaller=" + fechaIngresoTaller +
                '}';
    }
}


