
package juegofelixservidor;

import vista.Tablero;

/*@Héctor Daza
  @Andrés Iriarte
*/
public class JuegoFelixServidor {

    public JuegoFelixServidor() {
     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoFelixServidor juego = new JuegoFelixServidor();
        juego.nuevoJuego();
    }

    public void nuevoJuego() {
    Tablero tablero = new Tablero();
        tablero.setVisible(true);
    }
}
