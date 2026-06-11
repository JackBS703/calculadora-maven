package com.equipopfqa.calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CalculadoraTest {

    private final Calculadora calculadora = new Calculadora();

    @Test
    void deberiaSumarDosNumerosCorrectamente() {
        double resultado = calculadora.sumar(10, 5);
        assertEquals(15, resultado);
    }

    @Test
    void deberiaRestarDosNumerosCorrectamente() {
        double resultado = calculadora.restar(8, 3);
        assertEquals(5, resultado);
    }

    @Test
    void deberiaMultiplicarDosNumerosCorrectamente() {
        double resultado = calculadora.multiplicar(4, 6);
        assertEquals(24, resultado);
    }

    @Test
    void deberiaDividirDosNumerosCorrectamente() {
        double resultado = calculadora.dividir(20, 4);
        assertEquals(5, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoSeDividePorCero() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.dividir(10, 0)
        );

        assertEquals("No se puede dividir por cero.", excepcion.getMessage());
    }

    @Test
    void deberiaRegistrarOperacionEnElHistorial() {
        Historial historial = new Historial();

        historial.registrarOperacion(10, "+", 5, 15);

        List<String> operaciones = historial.obtenerHistorial();

        assertFalse(operaciones.isEmpty());
        assertEquals(1, operaciones.size());
        assertTrue(operaciones.get(0).contains("+"));
        assertTrue(operaciones.get(0).contains("15"));
    }

    @Test
    void deberiaLimpiarElHistorial() {
        Historial historial = new Historial();

        historial.registrarOperacion(10, "+", 5, 15);
        historial.limpiarHistorial();

        assertTrue(historial.estaVacio());
    }
}