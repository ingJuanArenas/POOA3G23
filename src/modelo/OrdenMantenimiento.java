package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.enums.Especialidad;
import modelo.enums.EstadoOrden;
import modelo.enums.TipoMantenimiento;

public class OrdenMantenimiento {
    private static long secuenciaId = 1;

    private final long id;
    private final LocalDate fecha;
    private final TipoMantenimiento tipo;
    private final Tren tren;
    private final List<Tecnico> tecnicos = new ArrayList<>();
    private EstadoOrden estado;
    private final Especialidad especialidadRequerida; // Puede ser null si aplica cualquiera

    public OrdenMantenimiento(LocalDate fecha, TipoMantenimiento tipo, Tren tren, Especialidad especialidadRequerida) {
        this.id = secuenciaId++;
        this.fecha = fecha;
        this.tipo = tipo;
        this.tren = tren;
        this.estado = EstadoOrden.PENDIENTE;
        this.especialidadRequerida = especialidadRequerida;
    }

    public long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TipoMantenimiento getTipo() {
        return tipo;
    }

    public Tren getTren() {
        return tren;
    }

    public List<Tecnico> getTecnicos() {
        return Collections.unmodifiableList(tecnicos);
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public Especialidad getEspecialidadRequerida() {
        return especialidadRequerida;
    }

    public void agregarTecnico(Tecnico tecnico) {
        if (tecnico != null && !tecnicos.contains(tecnico)) {
            tecnicos.add(tecnico);
        }
    }

    public void iniciar() {
        if (estado == EstadoOrden.PENDIENTE) {
            estado = EstadoOrden.EN_PROCESO;
        }
    }

    public void finalizar() {
        if (estado == EstadoOrden.EN_PROCESO) {
            estado = EstadoOrden.FINALIZADA;
        }
    }

    @Override
    public String toString() {
        return "Orden{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipo=" + tipo +
                ", tren=" + tren.getNumeroSerie() +
                ", estado=" + estado +
                ", especialidadRequerida=" + especialidadRequerida +
                '}';
    }
}


