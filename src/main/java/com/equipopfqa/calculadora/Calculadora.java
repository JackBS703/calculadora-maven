package com.equipopfqa.calculadora;

public class Calculadora {

    public double sumar(double primerNumero, double segundoNumero) {
        return primerNumero + segundoNumero;
    }

    public double metodoLargo1(double a, double b) {
        double temp = a + b;
        temp = temp * 2;
        temp = temp - 1;
        temp = temp / 2;
        temp = temp + 5;
        temp = temp * 3;
        temp = temp - 4;
        temp = temp / 3;
        temp = temp + 10;
        temp = temp * 4;
        temp = temp - 8;
        return temp;
    }

        public double metodoLargo2(double a, double b) {
        double temp = a + b;
        temp = temp * 2;
        temp = temp - 1;
        temp = temp / 2;
        temp = temp + 5;
        temp = temp * 3;
        temp = temp - 4;
        temp = temp / 3;
        temp = temp + 10;
        temp = temp * 4;
        temp = temp - 8;
        return temp;
    }

        public double metodoLargo3(double a, double b) {
        double temp = a + b;
        temp = temp * 2;
        temp = temp - 1;
        temp = temp / 2;
        temp = temp + 5;
        temp = temp * 3;
        temp = temp - 4;
        temp = temp / 3;
        temp = temp + 10;
        temp = temp * 4;
        temp = temp - 8;
        return temp;
    }

        public double metodoLargo4(double a, double b) {
        double temp = a + b;
        temp = temp * 2;
        temp = temp - 1;
        temp = temp / 2;
        temp = temp + 5;
        temp = temp * 3;
        temp = temp - 4;
        temp = temp / 3;
        temp = temp + 10;
        temp = temp * 4;
        temp = temp - 8;
        return temp;
    }

        public double metodoLargo5(double a, double b) {
        double temp = a + b;
        temp = temp * 2;
        temp = temp - 1;
        temp = temp / 2;
        temp = temp + 5;
        temp = temp * 3;
        temp = temp - 4;
        temp = temp / 3;
        temp = temp + 10;
        temp = temp * 4;
        temp = temp - 8;
        return temp;
    }

    public double restar(double primerNumero, double segundoNumero) {
        return primerNumero - segundoNumero;
    }

    public double multiplicar(double primerNumero, double segundoNumero) {
        return primerNumero * segundoNumero;
    }

    public double dividir(double primerNumero, double segundoNumero) {
        if (segundoNumero == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero.");
        }
        return primerNumero / segundoNumero;
    }
}