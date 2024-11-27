import java.util.HashMap;

//Método encargado de llevar el control del historial para la interfaz
public class Historial {
    private HashMap<Integer, String[]> historial; //HashMap para almacenar el número y la letra a la que corresponde

    //Constructor
    public Historial() {
        historial = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            historial.put(i, new String[]{"", ""});
        }
    }

    //Agrega y recorre los elementos
    public void agregarElemento(int numero) {
        String letra = asignarLetra(numero);
        for (int i = 5; i > 0; i--) {
            historial.put(i, historial.get(i - 1));
        }
        historial.put(0, new String[]{letra, String.valueOf(numero)});
    }


    //Asigna una letra a cada número dependiendo de un rango
    private String asignarLetra(int numero) {
        if (numero >= 1 && numero <= 15) {
            return "B";
        } else if (numero >= 16 && numero <= 30) {
            return "I";
        } else if (numero >= 31 && numero <= 45) {
            return "N";
        } else if (numero >= 46 && numero <= 60) {
            return "G";
        } else if (numero >= 61 && numero <= 75) {
            return "O";
        }
        return "";
    }

    //Devuelve el HashMap con el historial
    public HashMap<Integer, String[]> getHistorial() {
        return historial;
    }
}
