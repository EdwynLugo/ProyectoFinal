public class Main {
    private static boolean iniciar = false;

    public static void main(String[] args) {
        IniciarBingo();
    }

    //Metodo encargado del orden de la aplicacion para su funcionamiento
    public static void IniciarBingo() {
        Bingo bingo = new Bingo();
        Tabla tabla = new Tabla();
        CartaJugador cartaJugador = new CartaJugador();
        Historial historial = new Historial();
        Interfaz interfaz = new Interfaz(bingo, tabla, cartaJugador, historial);

        //Ciclo que espera señal para inicial juego
        while (!iniciar) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        interfaz.setBotonGenerarActivo(false);
        interfaz.repaint();
        bingo.iniciarTombola(5000);
    }

    public static void setIniciar(boolean iniciar) {
        Main.iniciar = iniciar;
    }
}