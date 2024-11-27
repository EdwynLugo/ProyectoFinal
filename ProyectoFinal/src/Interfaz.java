import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Clase encargada de manejar la interfaz de usuario del programa
public class Interfaz extends JFrame {
    private String DireccionElementos = "Imagenes/"; //Atributo encargado de indicar la dirección de las imágenes
    private ArrayList<ElementosInterfaz> elementos; //ArrayList encargado de contener los elementos en la interfaz
    private Bingo bingo;
    private CartaJugador cartaJugador;
    private Tabla tabla;
    private Historial historial;
    private Tombola tombola;
    private String texto = " "; //Inicializa el mensaje
    private boolean botonGenerarActivo = true; //Booleano para controlar la generación de nuevas cartas

    //Constructor
    public Interfaz(Bingo bingo, Tabla tabla, CartaJugador cartaJugador, Historial historial) {
        setTitle("Proyecto Final - Bingo"); //Nombre de la ventana
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        elementos = new ArrayList<>();
        this.bingo = bingo;
        this.cartaJugador = cartaJugador;
        this.tabla = tabla;
        tombola = new Tombola(this);

        if (historial == null) {
            this.historial = new Historial();
        } else {
            this.historial = historial;
        }

        //Fondo de la interfaz
        String direccionFondoTexto = DireccionElementos + "/UI.png";
        elementos.add(new ElementosInterfaz(direccionFondoTexto, 0, 0, 1366, 768, "Fondo"));

        generartabla();
        generarBotones();
        funcionalidadInterfaz();
    }

    //Método encargado de dibujar los elementos de la tabla de bingo
    public void generartabla(){
        List<List<Integer>> numerosTabla = tabla.generarNumerosPorLetra(15, 1, 15, false);
        for (int letraIdx = 0; letraIdx < 5; letraIdx++) {
            for (int columna = 0; columna < 15; columna++) {
                int posicionX = 312 + (columna * 53);
                int posicionY = 27 + (letraIdx * 52);
                int numeroPNG = numerosTabla.get(letraIdx).get(columna);

                //Se asigna el nombre del número a generar conforme se recorre la lista
                String rutaImagen = DireccionElementos + "/" + numeroPNG + ".png";
                elementos.add(new ElementosInterfaz(rutaImagen, posicionX, posicionY, 50, 50, String.valueOf(numeroPNG)));
            }
        }
    }

    //Declara los elementos para dibujar los botones
    public void generarBotones(){
        String iniciar = DireccionElementos + "/Iniciar.png";
        elementos.add(new ElementosInterfaz(iniciar, 833, 610, 145, 44, "Iniciar"));

        String generar = DireccionElementos + "/Generar.png";
        elementos.add(new ElementosInterfaz(generar, 833, 657, 145, 72, "Generar"));
    }

    //Se encarga de asignar la carta a la interfaz
    public void generarCarta() {
        List<List<String>> numerosCarta = cartaJugador.getCarta(); //Obtiene la carta y la almacena como una lista de números

        //Asigna la carta al jugador recorriendo sus elementos
        for (int columna = 0; columna < 5; columna++) {
            for (int fila = 0; fila < 5; fila++) {
                int posicionX = 550 + (columna * 54);
                int posicionY = 462 + (fila * 52);
                String valor = numerosCarta.get(columna).get(fila);

                String rutaImagen = DireccionElementos + "/" + valor + ".png";
                elementos.add(new ElementosInterfaz(rutaImagen, posicionX, posicionY, 50, 50, valor));
            }
        }
    }

    //Establece los elementos a mostrar en el historial de la interfaz
    public void generarHistorial(Historial historial) {
        //Elimina los elementos anteriores del historial y evita acumulaciones
        elementos.removeIf(e ->
                e.getPosicionX() >= 483 && e.getPosicionX() <= 883 &&
                        e.getPosicionY() >= 305 && e.getPosicionY() <= 400
        );

        HashMap<Integer, String[]> historialMap = historial.getHistorial(); //Obtiene el historial
        if (historialMap != null && !historialMap.isEmpty()) { //Verifica que el HashMap no esté vacío
            String[] primerElemento = historialMap.get(0); //Toma el primer elemento del HashMap

            //Ciclo para dar valor a las representaciones del historial
            for (int i = 0; i < 6; i++) {
                String[] elementoHistorial = historialMap.get(i);
                if (elementoHistorial != null && elementoHistorial.length > 1 && !elementoHistorial[0].isEmpty()) {
                    String letra = elementoHistorial[0];
                    String numero = elementoHistorial[1];

                    String colorHistorial = DireccionElementos + "/" + letra + ".png";
                    int posicionX = 591 + (i * 59);
                    int posicionY = 318;

                    if (i==0){
                        //Elemento más reciente
                        elementos.add(new ElementosInterfaz(colorHistorial, 483, 305, 81, 81, "Fondo"));
                    } else {
                        //Cola del historial
                        elementos.add(new ElementosInterfaz(colorHistorial, posicionX-59, posicionY, 56, 56, "Fondo"));

                    }

                    String numeroHistorial = DireccionElementos + "/" + numero + ".png";
                    posicionX = 606  + (i * 59);
                    posicionY = 344;

                    if (i==0){
                        //Elemento más reciente
                        elementos.add(new ElementosInterfaz(numeroHistorial, 506, 342, 35, 35, "Fondo"));
                    } else {
                        //Cola del historial
                        elementos.add(new ElementosInterfaz(numeroHistorial, posicionX-59, posicionY, 25, 25, "Fondo"));

                    }
                }
            }
        }
    }

    public void actualizarInterfaz() {
        elementos.removeIf(elemento -> !elemento.getValor().equals("Fondo"));
        //Mientras no inicie el juego, los botones siguen disponibles
        if (botonGenerarActivo) {
            generarBotones();
        }
        generartabla();
        generarCarta();
        repaint();
    }

    //Método encargado de resaltar elementos pasados o seleccionados
    public void marcarCarta(List<List<String>> cartaJugador, int numero) {
        //Recorre las posiciones de la carta
        for (int fila = 0; fila < cartaJugador.size(); fila++) {
            for (int columna = 0; columna < cartaJugador.get(fila).size(); columna++) {
                String valorCarta = cartaJugador.get(fila).get(columna);

                //Comprobación de coincidencias
                if (valorCarta.equals(String.valueOf(numero)) || "estrella".equals(valorCarta)) {
                    for (ElementosInterfaz elemento : getElementos()) {
                        if (elemento.getValor().equals(valorCarta)) {
                            //Rangos de la carta
                            int minX = 550;
                            int maxX = 770;
                            int minY = 462;
                            int maxY = 712;

                            //Verifica que los elementos estén en el rango de la carta
                            if (elemento.getPosicionX() >= minX && elemento.getPosicionX() <= maxX
                                    && elemento.getPosicionY() >= minY && elemento.getPosicionY() <= maxY) {
                                elemento.setSeleccionado(true); //Marca el elemento como seleccionado
                                elemento.setSeleccionadoCarta("SeleccionadoAzul.png");
                                break;  //Sale del bucle
                            }
                        }
                    }
                }
            }
        }
        repaint();
    }

    //Método encargado de dibujar y detectar clicks en la interfaz
    public void funcionalidadInterfaz() {
        JPanel panel = new JPanel() {
            @Override
            //Dibuja los elementos de la interfaz
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(220, 220, 220));
                g.fillRect(0, 0, getWidth(), getHeight());

                //Recorrido de los elementos de la interfaz
                for (ElementosInterfaz letra : elementos) {
                    if ("Fondo".equals(letra.getValor())) {
                        letra.paintComponent(g);
                    }
                }

                //Marca los elementos de la tabla
                for (ElementosInterfaz letra : elementos) {
                    if (!"Fondo".equals(letra.getValor())) {
                        if (letra.isSeleccionado()) {
                            Image seleccionado;
                            //Asigna una imagen u otra dependiendo de si es para la tabla o para la carta
                            if (letra.getPosicionX() >= 550 && letra.getPosicionX() <= 770 && letra.getPosicionY() >= 462 && letra.getPosicionY() <= 712) {
                                seleccionado = new ImageIcon(DireccionElementos + "/SeleccionadoAzul.png").getImage();
                            } else {
                                seleccionado = new ImageIcon(DireccionElementos + "/SeleccionadoGris.png").getImage();
                            }

                            g.drawImage(seleccionado, letra.getPosicionX(), letra.getPosicionY(), letra.getAncho(), letra.getAlto(), this);
                        }
                        letra.paintComponent(g);
                    }
                }

                //Declaración y asignación de parámetros para dibujar texto
                Font fontTexto = new Font("Arial", Font.BOLD, 25);
                int xTextos = 75;
                int yTextos = 522;
                int desplazamientoSombra = 1;
                g.setFont(fontTexto);
                g.setColor(Color.BLACK);
                g.drawString(texto, xTextos - desplazamientoSombra, yTextos - desplazamientoSombra);
                g.drawString(texto, xTextos + desplazamientoSombra, yTextos - desplazamientoSombra);
                g.drawString(texto, xTextos - desplazamientoSombra, yTextos + desplazamientoSombra);
                g.drawString(texto, xTextos + desplazamientoSombra, yTextos + desplazamientoSombra);
                g.setColor(Color.WHITE);
                g.drawString(texto, xTextos, yTextos);
            }
        };

        //Método encargado de detectar clicks
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                //Deteccion del boton iniciar
                if (x >= 833 && x <= 833 + 145 && y >= 610 && y <= 610 + 44) {
                    setTexto("Iniciando juego");
                    //Contiene un timer para despues borrar el texto
                    Timer timer = new Timer(1000, event -> {
                        setTexto(" ");
                        Main.setIniciar(true);
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return;
                }

                //Verificar click para generar carta
                if (botonGenerarActivo && x >= 833 && x <= 833 + 145 && y >= 657 && y <= 657 + 72) {
                    setTexto("Generando una nueva carta");
                    //Contiene un timer para despues borrar el texto
                    Timer timer = new Timer(1000, event -> {
                        setTexto(" ");
                        bingo.generarCarta();
                        actualizarInterfaz();
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return;
                }

                //Verificar clicks en la carta mediante rangos
                elementos.stream()
                        .filter(boton -> !"Fondo".equals(boton.getValor())) //Excluir fondo
                        .filter(boton -> {
                            //Rangos de la carta
                            int minXCarta = 550;
                            int maxXCarta = 770;
                            int minYCarta = 462;
                            int maxYCarta = 712;
                            return boton.getPosicionX() >= minXCarta && boton.getPosicionX() < maxXCarta &&
                                    boton.getPosicionY() >= minYCarta && boton.getPosicionY() < maxYCarta &&
                                    x >= boton.getPosicionX() && x <= boton.getPosicionX() + boton.getAncho() &&
                                    y >= boton.getPosicionY() && y <= boton.getPosicionY() + boton.getAlto();
                        })
                        .findFirst()
                        .ifPresent(boton -> {
                            String valorBoton = boton.getValor();

                            if ("estrella".equals(valorBoton)) {
                                if (!boton.isSeleccionado()) {
                                    cartaJugador.determinarPosicion(valorBoton);
                                    boton.setSeleccionado(true);
                                    repaint();
                                }
                            } else {
                                try {
                                    int valorInt = Integer.parseInt(valorBoton);
                                    bingo.validarJugada(valorInt);  //Pasa el valor como int para validación
                                } catch (NumberFormatException ex) {
                                }
                            }
                        });
            }
        });
        add(panel);
    }

    //Se encarga de resaltar los números ya seleccionados
    public void marcarNumero(int numero, Historial historial) {
        if (botonGenerarActivo) {
            setBotonGenerarActivo(false);
            actualizarInterfaz();
        }
        historial.agregarElemento(numero);

        //Recorre los elementos para buscar coincidencia
        for (ElementosInterfaz elemento : elementos) {
            try {
                if (!"Fondo".equals(elemento.getValor()) && Integer.parseInt(elemento.getValor()) == numero) {
                    elemento.setSeleccionado(true);
                    repaint();
                    break;
                }
            } catch (NumberFormatException e) {
            }
        }
        generarHistorial(historial);
        repaint();
    }

    public void setBotonGenerarActivo(boolean botonGenerarActivo) {
        this.botonGenerarActivo = botonGenerarActivo;
    }

    public ArrayList<ElementosInterfaz> getElementos() {
        return elementos;
    }

    //Método encargado de recibir y reasignar un String para el texto en la interfaz
    public void setTexto(String texto) {
        this.texto = texto;
        repaint();
    }
}