import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Clase hija encargada de manejar la tabla del juego
public class Tabla extends GeneradorDeNumeros {
    @Override

    //Maneja la listas de números para la tabla
    protected List<Integer> generarNumeros(int cantidad, int inicio, int fin, boolean mezclar) {
        List<Integer> numeros = new ArrayList<>();
        for (int i = inicio; i <= fin; i++) {
            numeros.add(i);
        }
        return numeros.subList(0, cantidad);
    }

    //Genera 5 listas de números dentro de un rango y las devuelve
    public List<List<Integer>> generarNumerosPorLetra(int cantidadPorLetra, int inicio, int fin, boolean mezclar) {
        List<List<Integer>> numerosPorLetra = new ArrayList<>();
        String[] letras = {"B", "I", "N", "G", "O"};

        for (String letra : letras) {
            List<Integer> numeros = generarNumeros(cantidadPorLetra, inicio, fin, mezclar);
            numerosPorLetra.add(numeros);
            inicio += 15;
            fin += 15;
        }
        return numerosPorLetra;
    }
}