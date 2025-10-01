package modelo;

import modelo.enums.Especialidad;

public class TecnicoElectrico extends Tecnico {
    public TecnicoElectrico(String nombreCompleto, String turno) {
        super(nombreCompleto, Especialidad.ELECTRICO, turno);
    }
}


