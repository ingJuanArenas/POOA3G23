package validaciones;

import excepciones.ValidacionException;

public final class Validador {
    private Validador() {}

    public static void textoObligatorio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo '" + nombreCampo + "' es obligatorio");
        }
    }
}


