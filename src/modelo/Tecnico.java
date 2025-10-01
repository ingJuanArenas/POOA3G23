package modelo;

import java.util.Objects;

import modelo.enums.Especialidad;

public abstract class Tecnico {
    private final String nombreCompleto;
    private final Especialidad especialidad;
    private final String turno; 

    protected Tecnico(String nombreCompleto, Especialidad especialidad, String turno) {
        this.nombreCompleto = Objects.requireNonNull(nombreCompleto, "nombreCompleto no puede ser null").trim();
        this.especialidad = Objects.requireNonNull(especialidad, "especialidad no puede ser null");
        this.turno = Objects.requireNonNull(turno, "turno no puede ser null").trim();
        if (this.nombreCompleto.isEmpty()) {
            throw new IllegalArgumentException("El nombre del técnico no puede estar vacío");
        }
        if (this.turno.isEmpty()) {
            throw new IllegalArgumentException("El turno no puede estar vacío");
        }
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getTurno() {
        return turno;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "nombre='" + nombreCompleto + '\'' +
                ", especialidad=" + especialidad +
                ", turno='" + turno + '\'' +
                '}';
    }
}


