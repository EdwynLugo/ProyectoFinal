import javax.swing.*;
import java.awt.*;

//Clase encargada de manejar los elementos individuales utilizados en la interfaz
public class ElementosInterfaz extends JPanel {
    private String direccionImagen;
    private int posicionX;
    private int posicionY;
    private int ancho;
    private int alto;
    private String valor;
    private boolean seleccionado = false; //Booleano que indica si un elemento ya fue marcado
    private String seleccionadoString = ""; //String que almacena el elemento

    //Constructor
    public ElementosInterfaz(String direccionImagen, int posicionX, int posicionY, int ancho, int alto, String valor) {
        this.direccionImagen = direccionImagen;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.ancho = ancho;
        this.alto = alto;
        this.valor = valor;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public String getValor() {
        return valor;
    }

    //Actualiza el indicador para saber si un numero ya fue marcado
    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public void setSeleccionadoCarta(String seleccionadoString) {
        this.seleccionadoString = seleccionadoString;
    }

    //Método heredado encargado de dibujar los elementos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = new ImageIcon(direccionImagen).getImage();
        g.drawImage(img, posicionX, posicionY, ancho, alto, this);

        if (!seleccionadoString.isEmpty()) {
            Image seleccionadoImg = new ImageIcon(direccionImagen + "/" + seleccionadoString).getImage();
            g.drawImage(seleccionadoImg, posicionX, posicionY, ancho, alto, this);
        }
    }
}