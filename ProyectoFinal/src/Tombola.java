import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

//Clase hija encargada de generar los 75 números del bingo
public class Tombola extends GeneradorDeNumeros {
    private Interfaz interfaz;
    private Historial historial;

    //Constructor
    public Tombola(Interfaz interfaz) {
        this.interfaz = interfaz;
        this.historial = new Historial();
    }

    //Método que genera los 75 números de bingo en un orden aleatorio
    public void generarNumeros(int delay) {
        HashSet<Integer> numerosGenerados = generarNumeros(75, 1, 75);
        ArrayList<Integer> listaNumeros = new ArrayList<>(numerosGenerados);
        Collections.shuffle(listaNumeros); //Revuelve el ArrayList

        for (Integer numero : listaNumeros) {
            interfaz.marcarNumero(numero, historial);
            delay(delay);
        }
    }
}
