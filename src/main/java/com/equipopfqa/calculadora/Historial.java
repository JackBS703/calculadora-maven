package com.equipopfqa.calculadora;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Historial {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<String> operaciones;

    public Historial() {
        this.operaciones = new ArrayList<>();
    }

    public void registrarOperacion(double primerNumero, String operador, double segundoNumero, double resultado) {
        String fechaHora = LocalDateTime.now().format(FORMATO_FECHA);
        String operacion = String.format(
                "[%s] %.2f %s %.2f = %.2f",
                fechaHora,
                primerNumero,
                operador,
                segundoNumero,
                resultado
        );
        operaciones.add(operacion);
    }

    public List<String> obtenerHistorial() {
        return Collections.unmodifiableList(operaciones);
    }

    public boolean estaVacio() {
        return operaciones.isEmpty();
    }

    public void limpiarHistorial() {
        operaciones.clear();
    }
}
