
package juegofelix;

import hilos.MoverPersonaje;
import javax.swing.JOptionPane;
import vista.Tablero;

/*@Héctor Daza
  @Andrés Iriarte
*/
public class JuegoFelix {

    private String multijugador;
    private String dificultad;
    private int dificultadS;
    private boolean multijugadorS;
    private String nombreJugador;
    private MoverPersonaje felix;
    private MoverPersonaje sonic;
    private MoverPersonaje m3;
    private MoverPersonaje m4;

    public JuegoFelix() {
        multijugador = "";
        dificultad = "";
        dificultadS = 10;
        multijugadorS = false;
        nombreJugador="";
        menu();
    }

    private void menu() {
        multijugador = JOptionPane.showInputDialog("Bienvenido a Felix vs Sonic\n"
                + "Quieres jugar con Felix o con Sonic?\n1-Felix\n2- Sonic");
        dificultad = JOptionPane.showInputDialog("Que nivel de dificultad quieres Manejar?\n"
                + "1- Dificil\n2\n3\n4\n5\n6\n7\n8\n9\n10- Facil");
        switch (dificultad) {
            case "1":
                dificultadS = 1;
                break;
            case "2":
                dificultadS = 2;
                break;
            case "3":
                dificultadS = 3;
                break;
            case "4":
                dificultadS = 4;
                break;
            case "5":
                dificultadS = 5;
                break;
            case "6":
                dificultadS = 6;
                break;
            case "7":
                dificultadS = 7;
                break;
            case "8":
                dificultadS = 8;
                break;
            case "9":
                dificultadS = 9;
                break;
            case "10":
                dificultadS = 10;
                break;
            default:
                break;
        }

        if (multijugador.equalsIgnoreCase("Felix") || multijugador.equals("1")) {
            multijugadorS = true;
           nombreJugador = JOptionPane.showInputDialog("Felix se mueve con las flechas del teclado\nFlecha Atras Saltar"
                    + "\nFlecha arriba moverse hacia arriba\nFlecha abajo moverse hacia abajo\nFlecha adelante caminar de frente\nIngresa tu nombre de Jugador, y preparate para empezar!!!");
        } else {
           nombreJugador = JOptionPane.showInputDialog("Sonic se mueve con las teclas numericas del teclado\n4 Saltar"
                    + "\n8 moverse hacia arriba\n2 moverse hacia abajo\n6 caminar de frente\nIngresa tu nombre de Jugador, y preparate para empezar!!!");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoFelix juego = new JuegoFelix();
        juego.nuevoJuego();
    }

    public void nuevoJuego() {
        Tablero tablero = new Tablero(multijugadorS);
        tablero.setVisible(true);
        if (multijugadorS) {
            felix = new MoverPersonaje(tablero.getPanelControl().getFelix(), tablero, dificultadS, multijugadorS,nombreJugador);
            tablero.getPanelControl().getSonic().setIndiceImagenActual(tablero.getPanelControl().getSonic().getCantidadDeMovimientos() - 1);
            m3 = new MoverPersonaje(tablero.getPanelControl().getVegeta(), tablero, dificultadS, multijugadorS,nombreJugador);
            m4 = new MoverPersonaje(tablero.getPanelControl().getBomba(), tablero, dificultadS, multijugadorS,nombreJugador);
            felix.start();
            m3.start();
            m4.start();
        } else {
            sonic = new MoverPersonaje(tablero.getPanelControl().getSonic(), tablero, dificultadS, multijugadorS,nombreJugador);
            tablero.getPanelControl().getFelix().setIndiceImagenActual(tablero.getPanelControl().getFelix().getCantidadDeMovimientos() - 1);
            m3 = new MoverPersonaje(tablero.getPanelControl().getVegeta(), tablero, dificultadS, multijugadorS,nombreJugador);
            m4 = new MoverPersonaje(tablero.getPanelControl().getBomba(), tablero, dificultadS, multijugadorS,nombreJugador);
            sonic.start();
            m3.start();
            m4.start();
        }
    }
}
