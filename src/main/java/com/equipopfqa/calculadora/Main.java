package com.equipopfqa.calculadora;

public class Main {

    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        Historial historial = new Historial();

        ejecutarOperacion("Suma", 10, "+", 5, calculadora.sumar(10, 5), historial);
        ejecutarOperacion("Resta", 8, "-", 3, calculadora.restar(8, 3), historial);
        ejecutarOperacion("Multiplicacion", 4, "*", 6, calculadora.multiplicar(4, 6), historial);
        ejecutarOperacion("Division", 20, "/", 4, calculadora.dividir(20, 4), historial);

        try {
            calculadora.dividir(15, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        System.out.println("\nHistorial de operaciones:");
        for (String operacion : historial.obtenerHistorial()) {
            System.out.println(operacion);
        }
    }

    private static void ejecutarOperacion(
            String nombreOperacion,
            double primerNumero,
            String operador,
            double segundoNumero,
            double resultado,
            Historial historial
    ) {
        System.out.println(nombreOperacion + ": " + resultado);
        historial.registrarOperacion(primerNumero, operador, segundoNumero, resultado);
    }
}
