/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class LogicaJuego {
    private int[][] matrizCodigo; // Matriz para almacenar los intentos del jugador y el código secreto
    private int[][] matrizResultado; // Matriz para almacenar los resultados de los intentos
    private String[][] matrizEstadisticas; // Matriz para almacenar las estadísticas de los jugadores
    private int codigoSecreto; // Código secreto que el jugador debe adivinar
    private int contadorEstadisticas; // Contador para la matriz de estadísticas

    public LogicaJuego() {
        matrizCodigo = new int[13][4]; // Inicializamos la matriz de códigos
        matrizResultado = new int[12][2]; // Inicializamos la matriz de resultados
        matrizEstadisticas = new String[100][4]; // Inicializamos la matriz de estadísticas (asumiendo un máximo de 100 juegos)
        contadorEstadisticas = 0; // Inicializamos el contador de estadísticas
        generarCodigoSecreto(); // Generamos el código secreto al inicio del juego
    }

    // Método para reiniciar el estado del juego
    public void reiniciarJuego() {
        matrizCodigo = new int[13][4];
        matrizResultado = new int[12][2];
        generarCodigoSecreto();
    }

    // Método para generar un código secreto de cuatro cifras distintas
    private void generarCodigoSecreto() {
        Random aleatorio = new Random();
        int[] digitos = new int[4];
        boolean[] usado = new boolean[10]; // Para asegurar que los dígitos sean distintos

        for (int i = 0; i < 4; i++) {
            int nuevoDigito;
            do {
                nuevoDigito = aleatorio.nextInt(10); // Genera un dígito entre 0 y 9
            } while (usado[nuevoDigito]);
            usado[nuevoDigito] = true;
            digitos[i] = nuevoDigito;
        }

        codigoSecreto = digitos[0] * 1000 + digitos[1] * 100 + digitos[2] * 10 + digitos[3];
        matrizCodigo[0] = digitos; // Almacena el código secreto en la primera fila de la matriz de códigos
    }

    // Método para evaluar el intento del usuario y devolver el número de aciertos y aproximaciones
    public int[] evaluarIntento(String entradaUsuario) {
        int[] intento = Arrays.stream(entradaUsuario.split("")).mapToInt(Integer::parseInt).toArray();
        int aciertos = 0, aproximaciones = 0;

        // Evaluamos el intento del jugador comparándolo con el código secreto
        for (int i = 0; i < 4; i++) {
            if (intento[i] == matrizCodigo[0][i]) {
                aciertos++; // Contamos los aciertos si el número coincide en la misma posición
            } else if (contiene(matrizCodigo[0], intento[i])) {
                aproximaciones++; // Contamos las aproximaciones si el número está presente en el código secreto pero en una posición diferente
            }
        }

        return new int[]{aciertos, aproximaciones}; // Devolvemos el número de aciertos y aproximaciones en un array
    }

    // Método auxiliar para verificar si un valor está presente en un array
    private boolean contiene(int[] array, int valor) {
        for (int i : array) {
            if (i == valor) {
                return true;
            }
        }
        return false;
    }

    // Método para actualizar las matrices con el intento del jugador y el resultado obtenido
    public void actualizarMatrices(String entradaUsuario, int[] resultado) {
        int indiceIntento = obtenerCantidadIntentos();
        for (int i = 0; i < 4; i++) {
            matrizCodigo[indiceIntento][i] = Integer.parseInt(String.valueOf(entradaUsuario.charAt(i)));
        }
        matrizResultado[indiceIntento - 1][0] = resultado[0]; // Actualiza la matriz de resultados con aciertos
        matrizResultado[indiceIntento - 1][1] = resultado[1]; // Actualiza la matriz de resultados con aproximaciones
    }

    // Método para registrar el resultado del juego
    public void registrarResultadoJuego(String nombreJugador, boolean juegoGanado, int intentos) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha
        String fecha = formatoFecha.format(new Date()); // Obtenemos la fecha actual
        String resultado = juegoGanado ? String.valueOf(intentos) : "No adivinó"; // Determinamos el resultado del juego

        // Registramos el nombre del jugador, la fecha, el resultado y el código secreto en la matriz de estadísticas
        matrizEstadisticas[contadorEstadisticas][0] = nombreJugador;
        matrizEstadisticas[contadorEstadisticas][1] = fecha;
        matrizEstadisticas[contadorEstadisticas][2] = resultado;
        matrizEstadisticas[contadorEstadisticas][3] = juegoGanado ? "" : String.valueOf(codigoSecreto); // No incluir el código secreto si el jugador no adivinó
        contadorEstadisticas++;
    }

    // Método para obtener las estadísticas del juego
    public String obtenerEstadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("+------------+------------+------------+---------------+\n");
        sb.append("|   Nombre   |   Fecha    |   Intento  | Código secreto|\n");
        sb.append("+------------+------------+------------+---------------+\n");
        for (int i = 0; i < contadorEstadisticas; i++) {
            sb.append(String.format("| %-10s | %-10s | %-10s | %-13s|\n",
                    matrizEstadisticas[i][0], matrizEstadisticas[i][1], matrizEstadisticas[i][2], matrizEstadisticas[i][3]));
        }
        return sb.toString();
    }

    // Método para obtener la cantidad de intentos realizados por el jugador
    public int obtenerCantidadIntentos() {
        int contador = 0;
        for (int i = 1; i < 13; i++) {
            if (matrizCodigo[i][0] != 0) {
                contador++;
            }
        }
        return contador + 1;
    }

    // Método para obtener el código secreto
    public int obtenerCodigoSecreto() {
        return codigoSecreto;
    }

    // Método para obtener la matriz de códigos
    public int[][] obtenerMatrizCodigo() {
        return matrizCodigo;
    }

    // Método para obtener la matriz de resultados
    public int[][] obtenerMatrizResultado() {
        return matrizResultado;
    }
}