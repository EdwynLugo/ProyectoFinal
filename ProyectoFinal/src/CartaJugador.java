import java.util.ArrayList;
import java.util.List;

//Clase hija encargada de generar números para la carta y manejarla
public class CartaJugador extends GeneradorDeNumeros {
    private List<List<String>> carta;
    private boolean[][] posiciones;

    //Constructor
    public CartaJugador() {
        carta = generarCartaJugador();
        posiciones = new boolean[5][5];  //Matriz que almacena el patrón de la carta
    }

    //Genera la carta de bingo añadiendo la estrella al centro de la carta
    private List<List<String>> generarCartaJugador() {
        List<List<String>> cartaGenerada = new ArrayList<>();

        //Genera los números con respecto a los rangos por letra
        cartaGenerada.add(convertirNumerosAGenerados(generarNumeros(5, 1, 15, true)));  //Columna B
        cartaGenerada.add(convertirNumerosAGenerados(generarNumeros(5, 16, 30, true))); //Columna I

        List<String> numerosN = new ArrayList<>();
        List<Integer> numerosColumnaN = generarNumeros(4, 31, 45, true); //Columna N
        for (Integer num : numerosColumnaN) {
            numerosN.add(num.toString());
        }
        numerosN.add(2, "estrella");
        cartaGenerada.add(numerosN);

        cartaGenerada.add(convertirNumerosAGenerados(generarNumeros(5, 46, 60, true))); //Columna G
        cartaGenerada.add(convertirNumerosAGenerados(generarNumeros(5, 61, 75, true))); //Columna O

        return cartaGenerada;
    }

    //Convierte la lista de int a una de string
    private List<String> convertirNumerosAGenerados(List<Integer> numerosGenerados) {
        List<String> numerosComoStrings = new ArrayList<>();
        for (Integer numero : numerosGenerados) {
            numerosComoStrings.add(numero.toString());
        }
        return numerosComoStrings;
    }

    //Indica la posición de los números en la carta actualizando la matriz de posiciones
    public void determinarPosicion(String valor) {
        for (int columna = 0; columna < carta.size(); columna++) {
            List<String> numerosColumna = carta.get(columna);
            for (int fila = 0; fila < numerosColumna.size(); fila++) {
                if (numerosColumna.get(fila).equals(valor)) {
                    posiciones[fila][columna] = true;
                    return;
                }
            }
        }
    }

    public List<List<String>> getCarta() {
        return carta;
    }

    //Vuelve a generar una nueva carta
    public void generarNuevaCarta() {
        carta = generarCartaJugador();
        posiciones = new boolean[5][5];
    }

    public boolean[][] getPosiciones() {
        return posiciones;
    }
}