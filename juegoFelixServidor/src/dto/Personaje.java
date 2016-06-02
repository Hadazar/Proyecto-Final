
package dto;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/*@Héctor Daza
  @Andrés Iriarte
*/

public class Personaje extends JPanel {

    private BufferedImage[] imagenes;
    private String nombrePersonaje;
    private int posicionX;
    private int posicionY;
    private int cantidadDeMovimientos;
    private BufferedImage imagenActual;
    private int indiceImagenActual;
    private int controlTecla;
    private Double puntaje;
    private boolean activo;

    public Personaje(String nombrePersonaje, int posicionX, int posicionY, BufferedImage[] imagenes, int cantidadDeMovimientos) {
        this.indiceImagenActual=0;
        this.nombrePersonaje = nombrePersonaje;
        this.posicionY = posicionY;
        this.posicionX = posicionX;
        this.imagenes = imagenes;
        this.cantidadDeMovimientos = cantidadDeMovimientos;
        this.imagenActual=imagenes[indiceImagenActual];
        this.controlTecla=0;
        this.puntaje=0.0;
        this.activo=true;
    }

    public BufferedImage[] getImagenes() {
        return imagenes;
    }

    public void setImagenes(BufferedImage[] imagenes) {
        this.imagenes = imagenes;
    }        

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }   
    
    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }

    public int getCantidadDeMovimientos() {
        return cantidadDeMovimientos;
    }

    public void setCantidadDeMovimientos(int cantidadDeMovimientos) {
        this.cantidadDeMovimientos = cantidadDeMovimientos;
    }

    public BufferedImage getImagenActual() {
        return imagenes[indiceImagenActual];
    }

    public void setImagenActual(BufferedImage imagenActual) {
        this.imagenActual = imagenActual;
    }

    public int getIndiceImagenActual() {
        return indiceImagenActual;
    }

    public void setIndiceImagenActual(int indiceImagenActual) {
        this.indiceImagenActual = indiceImagenActual;
    }

    public int getControlTecla() {
        return controlTecla;
    }

    public void setControlTecla(int controlTecla) {
        this.controlTecla = controlTecla;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }            
}
