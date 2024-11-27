import java.util.*;

//Clase padre encargada de generar números aleatorios
public class GeneradorDeNumeros {
    protected HashSet<Integer> generarNumeros(int cantidad, int inicio, int fin) {
        Random random = new Random();
        HashSet<Integer> numeros = new HashSet<>(); //Utiliza HashSet para evitar elementos duplicados
        while (numeros.size() < cantidad) {
            int numeroAleatorio = random.nextInt(fin - inicio + 1) + inicio;
            numeros.add(numeroAleatorio);
        }
        return numeros;
    }

    protected void delay(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Genera una lista de números dentro de un rango y la entrega mezclada u ordenada
    protected List<Integer> generarNumeros(int cantidad, int inicio, int fin, boolean mezclar) {
        List<Integer> numeros = new ArrayList<>(generarNumeros(cantidad, inicio, fin));
        if (mezclar) {
            Collections.shuffle(numeros);
        } else {
            Collections.sort(numeros);
        }
        return numeros;
    }

    //Método para generar los números, recibe cantidad, rangos y si debe mezclar la lista
    protected List<List<Integer>> generarNumerosPorLetra(int cantidad, int inicio, int fin, boolean mezclar) {
        List<List<Integer>> numerosPorLetra = new ArrayList<>();
        numerosPorLetra.add(generarNumeros(cantidad, inicio, fin, mezclar));
        return numerosPorLetra;
    }
}
