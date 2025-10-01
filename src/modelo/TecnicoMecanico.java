package modelo;

import modelo.enums.Especialidad;

public class TecnicoMecanico extends Tecnico {
    public TecnicoMecanico(String nombreCompleto, String turno) {
        super(nombreCompleto, Especialidad.MECANICO, turno);
    }
}


