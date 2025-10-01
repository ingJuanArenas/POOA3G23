package patrones;

import excepciones.ValidacionException;
import modelo.*;
import modelo.enums.EstadoOrden;
import modelo.enums.TipoMantenimiento;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GestorTaller {
    private static GestorTaller instancia;

    private final Map<String, Tren> trenes = new HashMap<>(); // la llave es el numero de serio y la fecha
    private final List<Tecnico> tecnicos = new ArrayList<>();
    private final List<OrdenMantenimiento> ordenes = new ArrayList<>();
    private final OrdenFactory ordenFactory = new OrdenFactory();

    private GestorTaller() { }

    public static  GestorTaller getInstancia() {
        if (instancia == null) {
            instancia = new GestorTaller();
        }
        return instancia;
    }

    public Tren registrarTren(String numeroSerie, String modelo) {
        if (numeroSerie == null || numeroSerie.trim().isEmpty()) {
            throw new ValidacionException("El número de serie es obligatorio");
        }
        String claveHoy = numeroSerie.trim().toLowerCase() + "|" + LocalDate.now();
        if (trenes.containsKey(claveHoy)) {
            throw new ValidacionException("El tren ya fue registrado hoy");
        }
        Tren tren = new Tren(numeroSerie.trim(), modelo, LocalDate.now());
        trenes.put(claveHoy, tren);
        return tren;
    }

    public void registrarTecnico(Tecnico tecnico) {
        if (tecnico != null) {
            tecnicos.add(tecnico);
        }
    }

    public OrdenMantenimiento crearOrden(TipoMantenimiento tipo, Tren tren) {
        OrdenMantenimiento orden = ordenFactory.crear(tipo, tren);
        ordenes.add(orden);
        return orden;
    }

    public void asignarTecnico(OrdenMantenimiento orden, Tecnico tecnico) {
        if (orden.getEspecialidadRequerida() != null &&
                tecnico.getEspecialidad() != orden.getEspecialidadRequerida()) {
            throw new ValidacionException("El técnico no tiene la especialidad requerida");
        }
        orden.agregarTecnico(tecnico);
    }

    public List<OrdenMantenimiento> filtrarPorEstado(EstadoOrden estado) {
        return ordenes.stream()
                .filter(o -> o.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public List<Tren> trenesMantenimientoUrgente() {
        return ordenes.stream()
                .filter(o -> o.getEstado() == EstadoOrden.EN_PROCESO || o.getTipo() == TipoMantenimiento.CORRECTIVO)
                .map(OrdenMantenimiento::getTren)
                .distinct()
                .collect(Collectors.toList());
    }

    public double promedioOrdenesPorTecnico() {
        Map<Tecnico, Long> conteo = ordenes.stream()
                .flatMap(o -> o.getTecnicos().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        if (conteo.isEmpty()) return 0.0;
        double total = conteo.values().stream().mapToLong(Long::longValue).sum();
        return total / conteo.size();
    }

    public List<OrdenMantenimiento> getOrdenes() {
        return Collections.unmodifiableList(ordenes);
    }

    public List<Tecnico> getTecnicos() {
        return Collections.unmodifiableList(tecnicos);
    }

    public Collection<Tren> getTrenesRegistradosHoy() {
        return Collections.unmodifiableCollection(trenes.values());
    }
}


