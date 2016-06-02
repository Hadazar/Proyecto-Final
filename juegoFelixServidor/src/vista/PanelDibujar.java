
package vista;

import dto.Personaje;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*@Héctor Daza
  @Andrés Iriarte
*/

public class PanelDibujar extends JPanel {

    private Graphics2D g2d;
    private BufferedImage imagen_en_memoria;
    private BufferedImage[] imagenes;
    private BufferedImage[] imagenFondo;
    private URL url;
    private Personaje felix;
    private Personaje sonic;
    private Personaje vegeta;
    private Personaje bomba;

    public PanelDibujar() {
        imagenFondo = new BufferedImage[1];
        cargarFondo();
        String nombrePersonaje = "felix";
        int cantidadImagenes = 5;
        imagenes = new BufferedImage[cantidadImagenes];
        BufferedImage[] grupoDeImagenes = cargarImagenes(nombrePersonaje, cantidadImagenes);
        felix = new Personaje(nombrePersonaje, 0, 370, grupoDeImagenes, cantidadImagenes);
        nombrePersonaje = "sonic";
        cantidadImagenes = 6;
        imagenes = new BufferedImage[cantidadImagenes];
        grupoDeImagenes = cargarImagenes(nombrePersonaje, cantidadImagenes);
        sonic = new Personaje(nombrePersonaje, 0, 310, grupoDeImagenes, cantidadImagenes);
        nombrePersonaje = "vegeta";
        cantidadImagenes = 7;
        imagenes = new BufferedImage[cantidadImagenes];
        grupoDeImagenes = cargarImagenes(nombrePersonaje, cantidadImagenes);
        vegeta = new Personaje(nombrePersonaje, 450, 350, grupoDeImagenes, cantidadImagenes);
        nombrePersonaje = "bomba";
        cantidadImagenes = 2;
        imagenes = new BufferedImage[cantidadImagenes];
        grupoDeImagenes = cargarImagenes(nombrePersonaje, cantidadImagenes);
        bomba = new Personaje(nombrePersonaje, 405, 375, grupoDeImagenes, cantidadImagenes);
    }

    private void cargarFondo() {
        try {
            url = new URL(getClass().getResource("/image/tablero.png").toString());
            imagenFondo[0] = ImageIO.read(url);
        } catch (IOException ex) {
            System.err.println("Error no cargo correctamente el fondo: " + ex.getMessage());
        }
    }

    private BufferedImage[] cargarImagenes(String nombrePersonaje, int cantidadImagenes) {
        try {
            for (int i = 0; i < cantidadImagenes; i++) {
                url = new URL(getClass().getResource("/image/" + nombrePersonaje + "S" + i + ".gif").toString());
                imagenes[i] = ImageIO.read(url);
            }

        } catch (IOException ex) {
            System.err.println("Error al cargar las imagenes: " + ex.getMessage());
        }
        return imagenes;
    }

    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(imagenFondo[0], 0, 0, null);
        g2.drawImage(vegeta.getImagenActual(), vegeta.getPosicionX(), vegeta.getPosicionY(), null);
        g2.drawImage(bomba.getImagenActual(), bomba.getPosicionX(), bomba.getPosicionY(), null);
        g2.drawImage(felix.getImagenActual(), felix.getPosicionX(), felix.getPosicionY(), null);
        g2.drawImage(sonic.getImagenActual(), sonic.getPosicionX(), sonic.getPosicionY(), null);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        imagen_en_memoria = new BufferedImage(this.getWidth(), this.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        g2d = imagen_en_memoria.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        g2d.fill(new Rectangle2D.Double(0, 0, this.getWidth(),
                this.getHeight()));
        dibujar(g2d);
        g2.drawImage(imagen_en_memoria, 0, 0, this);
    }

    /*
     * Getter´s y Setter´s
     */
    public Personaje getFelix() {
        return felix;
    }

    public void setFelix(Personaje felix) {
        this.felix = felix;
    }

    public Personaje getSonic() {
        return sonic;
    }

    public void setSonic(Personaje sonic) {
        this.sonic = sonic;
    }

    public Personaje getVegeta() {
        return vegeta;
    }

    public void setVegeta(Personaje vegeta) {
        this.vegeta = vegeta;
    }

    public Personaje getBomba() {
        return bomba;
    }

    public void setBomba(Personaje bomba) {
        this.bomba = bomba;
    }
}
