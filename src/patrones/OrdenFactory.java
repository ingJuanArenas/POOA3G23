package patrones;

import modelo.OrdenMantenimiento;
import modelo.Tren;
import modelo.enums.Especialidad;
import modelo.enums.TipoMantenimiento;

import java.time.LocalDate;

public class OrdenFactory {
    public OrdenMantenimiento crear(TipoMantenimiento tipo, Tren tren) {
        if (tipo == null || tren == null) {
            throw new IllegalArgumentException("tipo y tren son obligatorios");
        }
        Especialidad requerida;
        switch (tipo) {
            case PREVENTIVO:
                requerida = null; // cualquiera
                break;
            case CORRECTIVO:
                requerida = Especialidad.MECANICO;
                break;
            case RUTINARIO:
                requerida = Especialidad.ELECTRICO;
                break;
            default:
                requerida = null;
        }
        return new OrdenMantenimiento(LocalDate.now(), tipo, tren, requerida);
    }
}


