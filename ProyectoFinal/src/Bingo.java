import java.util.ArrayList;

public class Bingo {
    private Interfaz interfaz;
    private Tombola tombola;
    private Tabla tabla;
    private CartaJugador cartaJugador;
    private Historial historial;
    private boolean jugadaValida = false;

    //Constructor
    public Bingo() {
        tabla = new Tabla();
        cartaJugador = new CartaJugador();
        historial = new Historial();
        interfaz = new Interfaz(this, tabla, cartaJugador, historial);
        tombola = new Tombola(interfaz);
        interfaz.setVisible(true);
    }

    public void iniciarTombola(int delay) {
        tombola.generarNumeros(delay);
    }

    public void generarCarta() {
        cartaJugador.generarNuevaCarta();
    }

    public void validarJugada(int valorBoton) {
        ArrayList<ElementosInterfaz> elementos = interfaz.getElementos();
        boolean encontrado = false;
        boolean estrellaMarcada = false;

        //Verificar si la estrella está seleccionada
        for (ElementosInterfaz boton : elementos) {
            if ("estrella".equals(boton.getValor())) {
                if (boton.isSeleccionado()) {
                    estrellaMarcada = true;
                }
                break;
            }
        }

        //Verificar booleano de estrella
        if (!estrellaMarcada) {
            setJugadaValida(false);
            return;
        }

        //Buscar el número marcado entre los que han pasado
        for (ElementosInterfaz boton : elementos) {
            if (!"Fondo".equals(boton.getValor())) {
                try {
                    int numero = Integer.parseInt(boton.getValor());
                    if (numero == valorBoton) {
                        encontrado = true;
                        if (boton.isSeleccionado()) {
                            cartaJugador.determinarPosicion(String.valueOf(valorBoton));
                            interfaz.marcarCarta(cartaJugador.getCarta(), valorBoton);

                            //verifica el final del juego
                            if (verificarBingo()) {
                                //Se hace uso de timers para finalizar la aplicación
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    for (int i = 5; i > 0; i--) {
                                        //Actualizar el mensaje en la interfaz
                                        interfaz.setTexto("Finalizando juego en: " + i + " segundos");

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    System.exit(0);  //Finaliza la aplicación
                                }).start();
                            }

                            setJugadaValida(false); //No es válida si no ha pasado
                        } else {
                            setJugadaValida(true);  //Es válida si no ha pasado
                        }
                        break;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    public void setJugadaValida(boolean jugadaValida) {
        this.jugadaValida = jugadaValida;
    }

    //Método que busca los patrones para hacer bingo
    public boolean verificarBingo() {
        boolean[][] posiciones = cartaJugador.getPosiciones(); //Llama a la matriz de la carta para comprobar

        //Buscar líneas horizontales
        for (int i = 0; i < 5; i++) {
            boolean esLineaHorizontal = true;
            for (int j = 0; j < 5; j++) {
                if (!posiciones[i][j]) {
                    esLineaHorizontal = false;
                    break;
                }
            }
            if (esLineaHorizontal) {
                interfaz.setTexto("¡Bingo con 5 en línea!");
                return true;
            }
        }

        //Buscar líneas verticales
        for (int j = 0; j < 5; j++) {
            boolean esLineaVertical = true;
            for (int i = 0; i < 5; i++) {
                if (!posiciones[i][j]) {
                    esLineaVertical = false;
                    break;
                }
            }
            if (esLineaVertical) {
                interfaz.setTexto("¡Bingo con 5 en linea!");
                return true;
            }
        }

        //Verificar diagonal
        boolean esDiagonalPrincipal = true;
        for (int i = 0; i < 5; i++) {
            if (!posiciones[i][i]) {
                esDiagonalPrincipal = false;
                break;
            }
        }
        if (esDiagonalPrincipal) {
            interfaz.setTexto("¡Bingo con 5 en línea!");
            return true;
        }

        //Verificar diagonal
        boolean esDiagonalInversa = true;
        for (int i = 0; i < 5; i++) {
            if (!posiciones[i][4 - i]) {
                esDiagonalInversa = false;
                break;
            }
        }
        if (esDiagonalInversa) {
            interfaz.setTexto("¡Bingo con 5 en línea!");
            return true;
        }

        //Buscar patrón diamante
        boolean esDiamante = true;
        int[][] patronDiamante = {
                {0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {1, 0, 0, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0}
        };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (patronDiamante[i][j] == 1 && !posiciones[i][j]) {
                    esDiamante = false;
                    break;
                }
            }
            if (!esDiamante) break;
        }
        if (esDiamante) {
            interfaz.setTexto("¡Bingo con patrón diamante!");
            return true;
        }

        //Buscar patrón en el centro
        boolean esCentro = true;
        int[][] patronCentro = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (patronCentro[i][j] == 1 && !posiciones[i][j]) {
                    esCentro = false;
                    break;
                }
            }
            if (!esCentro) break;
        }
        if (esCentro) {
            interfaz.setTexto("¡Bingo con patrón en el centro!");
            return true;
        }

//Verificar patrón 6-pack vertical
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (j + 1 < 5 && i + 2 < 5) {  //Verificación para no salir de los límites
                    boolean esRectanguloVertical = true;
                    for (int x = 0; x < 3; x++) {
                        if (!posiciones[i + x][j] || !posiciones[i + x][j + 1]) {
                            esRectanguloVertical = false;
                            break;
                        }
                    }
                    if (esRectanguloVertical) {
                        interfaz.setTexto("¡Bingo con patrón 6-Pack!");
                        return true;
                    }
                }
            }
        }

        //Verificar patrón 6-pack horizontal
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (i + 1 < 5 && j + 2 < 5) {  //Verificación para no salir de los límites
                    boolean esRectanguloHorizontal = true;
                    for (int y = 0; y < 3; y++) {
                        if (!posiciones[i][j + y] || !posiciones[i + 1][j + y]) {
                            esRectanguloHorizontal = false;
                            break;
                        }
                    }
                    if (esRectanguloHorizontal) {
                        interfaz.setTexto("¡Bingo con patrón 6-Pack!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
