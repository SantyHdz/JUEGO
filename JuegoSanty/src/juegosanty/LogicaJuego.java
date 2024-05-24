/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosanty;

import java.util.Random;

/**
 *
 * @author santi
 */
public class LogicaJuego {
    private int[][] matrizCodigo;
    private int[][] matrizResultado;
    private String[][] matrizEstadisticas; 
    private int codigoSecreto;
    private int contadorEstadisticas;
    
    public LogicaJuego() {
        matrizCodigo = new int[13][4];
        matrizResultado = new int[12][2]; 
        matrizEstadisticas = new String[100][4]; 
        contadorEstadisticas = 0;
        generarCodigoSecreto();
    }
    
    public void reiniciarJuego() {
        matrizCodigo = new int[13][4];
        matrizResultado = new int[12][2];
        generarCodigoSecreto();
    }
    
    private void generarCodigoSecreto() {
        Random aleatorio = new Random();
        int[] digitos = new int[4];
        boolean[] usado = new boolean[10];

        for (int i = 0; i < 4; i++) {
            int nuevoDigito;
            do {
                nuevoDigito = aleatorio.nextInt(10);
            } while (usado[nuevoDigito]);
            usado[nuevoDigito] = true;
            digitos[i] = nuevoDigito;
        }

        codigoSecreto = digitos[0] * 1000 + digitos[1] * 100 + digitos[2] * 10 + digitos[3];
        matrizCodigo[0] = digitos;
    }
    
}
