
package vista;

import hilos.Hilo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*@Héctor Daza
  @Andrés Iriarte
*/

public class Tablero extends JFrame {

    public PanelDibujar panelControl;
    private JLabel puntaje;
    private ServerSocket serverSocket;
    private Socket cliente;
    private Thread proceso;

    public Tablero() {
        this.setTitle(("El juego - Felix El Gato Vs Sonic - Servidor"));
        this.setBackground(Color.LIGHT_GRAY);
        iniciarComponentes();
        puntaje = new JLabel("");
        panelControl.setLayout(new BorderLayout());
        panelControl.add(puntaje, BorderLayout.SOUTH);
        this.add(panelControl);
        this.setSize(520, 490);
        eventoPanel();
    }

    public void socketEjecucion() {
        try {
           // serverSocket = new ServerSocket(8000);
            cliente = serverSocket.accept();
            System.out.println("Acepte un cliente");
            proceso = new Thread(new Hilo(cliente, this));
            proceso.start();
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarComponentes() {
        panelControl = new PanelDibujar();
    }

    private void eventoPanel() {
        try {
            setContentPane(panelControl);
            panelControl.setFocusable(true);
            serverSocket = new ServerSocket(8000);
            socketEjecucion();
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
