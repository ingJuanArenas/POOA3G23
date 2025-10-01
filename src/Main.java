import excepciones.ValidacionException;
import modelo.*;
import modelo.enums.EstadoOrden;
import modelo.enums.TipoMantenimiento;
import patrones.GestorTaller;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorTaller gestor = GestorTaller.getInstancia();
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;
        while (!salir) {
            imprimirMenu();
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            try {
                switch (opcion) {
                    case "1":
                        registrarTecnico(scanner, gestor);
                        break;
                    case "2":
                        registrarTren(scanner, gestor);
                        break;
                    case "3":
                        crearOrden(scanner, gestor);
                        break;
                    case "4":
                        asignarTecnico(scanner, gestor);
                        break;
                    case "5":
                        cambiarEstado(scanner, gestor);
                        break;
                    case "6":
                        listarOrdenesPorEstado(scanner, gestor);
                        break;
                    case "7":
                        System.out.println("Trenes urgentes: " + gestor.trenesMantenimientoUrgente());
                        break;
                    case "8":
                        System.out.println("Promedio órdenes por técnico: " + gestor.promedioOrdenesPorTecnico());
                        break;
                    case "9":
                        listarDatos(gestor);
                        break;
                    case "0":
                        salir = true;
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (ValidacionException ve) {
                System.out.println("Error de validación: " + ve.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void imprimirMenu() {
        System.out.println("==== Sistema de Gestión de Talleres (Metro de Medellín) ====");
        System.out.println("1. Registrar técnico");
        System.out.println("2. Registrar tren (hoy)");
        System.out.println("3. Crear orden de mantenimiento");
        System.out.println("4. Asignar técnico a orden");
        System.out.println("5. Cambiar estado de orden (iniciar/finalizar)");
        System.out.println("6. Listar órdenes por estado");
        System.out.println("7. Listar trenes con mantenimiento urgente");
        System.out.println("8. Promedio de órdenes por técnico");
        System.out.println("9. Listar técnicos, trenes y órdenes");
        System.out.println("0. Salir");
    }

    private static void registrarTecnico(Scanner scanner, GestorTaller gestor) {
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Turno (Mañana/Tarde/Noche): ");
        String turno = scanner.nextLine();
        System.out.print("Especialidad (1=Eléctrico, 2=Mecánico): ");
        String esp = scanner.nextLine();
        Tecnico tecnico;
        if ("1".equals(esp)) {
            tecnico = new TecnicoElectrico(nombre, turno);
        } else if ("2".equals(esp)) {
            tecnico = new TecnicoMecanico(nombre, turno);
        } else {
            System.out.println("Opción de especialidad inválida");
            return;
        }
        gestor.registrarTecnico(tecnico);
        System.out.println("Técnico registrado: " + tecnico);
    }

    private static void registrarTren(Scanner scanner, GestorTaller gestor) {
        System.out.print("Número de serie: ");
        String serie = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        Tren tren = gestor.registrarTren(serie, modelo);
        System.out.println("Tren registrado hoy: " + tren);
    }

    private static void crearOrden(Scanner scanner, GestorTaller gestor) {
        if (gestor.getTrenesRegistradosHoy().isEmpty()) {
            System.out.println("No hay trenes registrados hoy. Registre uno primero.");
            return;
        }
        System.out.print("Tipo (1=Preventivo, 2=Correctivo, 3=Rutinario): ");
        String tipoStr = scanner.nextLine();
        TipoMantenimiento tipo;
        switch (tipoStr) {
            case "1": tipo = TipoMantenimiento.PREVENTIVO; break;
            case "2": tipo = TipoMantenimiento.CORRECTIVO; break;
            case "3": tipo = TipoMantenimiento.RUTINARIO; break;
            default:
                System.out.println("Tipo inválido");
                return;
        }
        System.out.println("Trenes disponibles hoy:");
        gestor.getTrenesRegistradosHoy().forEach(t -> System.out.println("- " + t.getNumeroSerie() + " (" + t.getModelo() + ")"));
        System.out.print("Ingrese número de serie del tren: ");
        String serie = scanner.nextLine();
        Optional<Tren> trenOpt = gestor.getTrenesRegistradosHoy().stream()
                .filter(t -> t.getNumeroSerie().equalsIgnoreCase(serie))
                .findFirst();
        if (!trenOpt.isPresent()) {
            System.out.println("Tren no encontrado");
            return;
        }
        OrdenMantenimiento orden = gestor.crearOrden(tipo, trenOpt.get());
        System.out.println("Orden creada: " + orden);
    }

    private static void asignarTecnico(Scanner scanner, GestorTaller gestor) {
        if (gestor.getOrdenes().isEmpty()) {
            System.out.println("No hay órdenes. Cree una primero.");
            return;
        }
        if (gestor.getTecnicos().isEmpty()) {
            System.out.println("No hay técnicos registrados.");
            return;
        }
        System.out.println("Órdenes:");
        gestor.getOrdenes().forEach(o -> System.out.println("- ID=" + o.getId() + " | " + o));
        System.out.print("ID de la orden: ");
        String idStr = scanner.nextLine();
        long id;
        try { id = Long.parseLong(idStr); } catch (NumberFormatException ex) { System.out.println("ID inválido"); return; }
        Optional<OrdenMantenimiento> ordenOpt = gestor.getOrdenes().stream().filter(o -> o.getId() == id).findFirst();
        if (!ordenOpt.isPresent()) { System.out.println("Orden no encontrada"); return; }

        System.out.println("Técnicos:");
        for (int i = 0; i < gestor.getTecnicos().size(); i++) {
            System.out.println((i+1) + ") " + gestor.getTecnicos().get(i));
        }
        System.out.print("Seleccione técnico por número de lista: ");
        String idxStr = scanner.nextLine();
        int idx;
        try { idx = Integer.parseInt(idxStr) - 1; } catch (NumberFormatException ex) { System.out.println("Número inválido"); return; }
        if (idx < 0 || idx >= gestor.getTecnicos().size()) { System.out.println("Índice fuera de rango"); return; }
        Tecnico tecnico = gestor.getTecnicos().get(idx);
        gestor.asignarTecnico(ordenOpt.get(), tecnico);
        System.out.println("Asignado: " + tecnico + " -> Orden " + id);
    }

    private static void cambiarEstado(Scanner scanner, GestorTaller gestor) {
        if (gestor.getOrdenes().isEmpty()) { System.out.println("No hay órdenes."); return; }
        gestor.getOrdenes().forEach(o -> System.out.println("- ID=" + o.getId() + " | Estado=" + o.getEstado()));
        System.out.print("ID de la orden: ");
        String idStr = scanner.nextLine();
        long id;
        try { id = Long.parseLong(idStr); } catch (NumberFormatException ex) { System.out.println("ID inválido"); return; }
        Optional<OrdenMantenimiento> ordenOpt = gestor.getOrdenes().stream().filter(o -> o.getId() == id).findFirst();
        if (!ordenOpt.isPresent()) { System.out.println("Orden no encontrada"); return; }
        OrdenMantenimiento orden = ordenOpt.get();
        System.out.print("Acción (1=Iniciar, 2=Finalizar): ");
        String acc = scanner.nextLine();
        if ("1".equals(acc)) {
            orden.iniciar();
            System.out.println("Orden iniciada.");
        } else if ("2".equals(acc)) {
            orden.finalizar();
            System.out.println("Orden finalizada.");
        } else {
            System.out.println("Acción inválida");
        }
    }

    private static void listarOrdenesPorEstado(Scanner scanner, GestorTaller gestor) {
        System.out.print("Estado (1=Pendiente, 2=En proceso, 3=Finalizada): ");
        String est = scanner.nextLine();
        EstadoOrden estado;
        switch (est) {
            case "1": estado = EstadoOrden.PENDIENTE; break;
            case "2": estado = EstadoOrden.EN_PROCESO; break;
            case "3": estado = EstadoOrden.FINALIZADA; break;
            default:
                System.out.println("Estado inválido");
                return;
        }
        System.out.println("Órdenes: " + gestor.filtrarPorEstado(estado));
    }

    private static void listarDatos(GestorTaller gestor) {
        System.out.println("Técnicos:");
        gestor.getTecnicos().forEach(System.out::println);
        System.out.println("Trenes (registrados hoy):");
        gestor.getTrenesRegistradosHoy().forEach(System.out::println);
        System.out.println("Órdenes:");
        gestor.getOrdenes().forEach(System.out::println);
    }
}


