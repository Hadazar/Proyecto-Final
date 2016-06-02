
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*@Héctor Daza
  @Andrés Iriarte
*/
public class Tablero extends JFrame {

    private PanelDibujar panelControl;
    private JLabel puntaje;
    private Socket cliente;
    private boolean multijugador;
    private PrintStream salida;

    public Tablero(boolean multijugador) {
        this.setTitle(("El juego - Felix El Gato Vs Sonic - Cliente"));
        this.setBackground(Color.LIGHT_GRAY);
        setLocationRelativeTo(null);
        this.multijugador = multijugador;
        iniciarComponentes();
        puntaje = new JLabel("");
        panelControl.setLayout(new BorderLayout());
        panelControl.add(puntaje, BorderLayout.SOUTH);
        this.add(panelControl);
        this.setSize(520, 490);
        eventoPanel();
        socketEjecucion();
    }

    private void socketEjecucion() {
        try {
            cliente = new Socket("localhost", 8000);
            System.out.println("Me conecte a un servidor");
            salida = new PrintStream(cliente.getOutputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (multijugador) {
            enviarDatos("felix" + "-"
                    + panelControl.getFelix().getPosicionX() + "-" + panelControl.getFelix().getPosicionY() + "-" + panelControl.getFelix().getIndiceImagenActual()
                    + "-" + panelControl.getBomba().getPosicionX() + "-" + panelControl.getBomba().getPosicionY() + "-" + panelControl.getBomba().getIndiceImagenActual()
                    + "-" + panelControl.getVegeta().getPosicionX() + "-" + panelControl.getVegeta().getPosicionY() + "-" + panelControl.getVegeta().getIndiceImagenActual()
                    + "-" + panelControl.getFelix().getPuntaje() + "-no");
        } else {
            enviarDatos("sonic" + "-"
                    + panelControl.getSonic().getPosicionX() + "-" + panelControl.getSonic().getPosicionY() + "-" + panelControl.getSonic().getIndiceImagenActual()
                    + "-" + panelControl.getBomba().getPosicionX() + "-" + panelControl.getBomba().getPosicionY() + "-" + panelControl.getBomba().getIndiceImagenActual()
                    + "-" + panelControl.getVegeta().getPosicionX() + "-" + panelControl.getVegeta().getPosicionY() + "-" + panelControl.getVegeta().getIndiceImagenActual()
                    + "-" + panelControl.getSonic().getPuntaje() + "-no");
        }
    }

    private void iniciarComponentes() {
        panelControl = new PanelDibujar();
    }

    private void eventoPanel() {
        setContentPane(panelControl);

        panelControl.setFocusable(true);
        this.panelControl.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //System.err.println("keyType Personaje ");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.err.println("keyReleased Personaje ");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 37:
                        panelControl.getFelix().setControlTecla(37);
                        break;
                    case 38:
                        panelControl.getFelix().setControlTecla(38);
                        break;
                    case 39:
                        panelControl.getFelix().setControlTecla(39);
                        break;
                    case 40:
                        panelControl.getFelix().setControlTecla(40);
                        break;
                    case 98:
                        panelControl.getSonic().setControlTecla(98);
                        break;
                    case 100:
                        panelControl.getSonic().setControlTecla(100);
                        break;
                    case 102:
                        panelControl.getSonic().setControlTecla(102);
                        break;
                    case 104:
                        panelControl.getSonic().setControlTecla(104);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void enviarDatos(String posiciones) {
        salida.println(posiciones);
        salida.flush();
    }

    public void cerrarConexion() {
        try {
            salida.close();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PanelDibujar getPanelControl() {
        return panelControl;
    }

    public JLabel getPuntaje() {
        return puntaje;
    }
    
    
}
