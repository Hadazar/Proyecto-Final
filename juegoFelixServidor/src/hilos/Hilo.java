
package hilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.Tablero;

/*@Héctor Daza
  @Andrés Iriarte
*/

public class Hilo implements Runnable {

    private Tablero tablero;
    private int multijugador;
    private Socket socket;
    private BufferedReader entrada;
    private boolean bandera;
    private boolean banderaClose;

    public Hilo(Socket pSocket, Tablero pTablero) throws IOException {
        bandera = true;
        banderaClose = true;
        multijugador = 0;
        socket = pSocket;
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        tablero = pTablero;
    }

    @Override
    public void run() {
        while (bandera) {
            try {
                String recibi = entrada.readLine();
                //System.out.println("recibi" + recibi);
                String[] posiciones = recibi.split("-");
                if (posiciones[11].equalsIgnoreCase("si") && posiciones[11] != null) {
                    bandera = false;
                }
                if (posiciones[11].equalsIgnoreCase("siC") && posiciones[11] != null) {
                    bandera = false;
                    banderaClose=false;
                }
                if (posiciones[0].equalsIgnoreCase("felix") && posiciones[0] != null) {
                    tablero.getPanelControl().getSonic().setIndiceImagenActual(tablero.getPanelControl().getSonic().getCantidadDeMovimientos() - 1);
                    moverFelix(posiciones);
                } else if (posiciones[0].equalsIgnoreCase("sonic") && posiciones[0] != null) {
                    tablero.getPanelControl().getFelix().setIndiceImagenActual(tablero.getPanelControl().getFelix().getCantidadDeMovimientos() - 1);
                    moverSonic(posiciones);
                }
                moverBomba(posiciones);
                moverVegeta(posiciones);
                tablero.repaint();
            } catch (IOException ex) {
                System.err.println("Error enviando datos del socket " + ex.getMessage());
            }
        }

        if (!bandera && banderaClose) {
            tablero.socketEjecucion();
        }else{
            try {
                entrada.close();
                socket.close();
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void moverFelix(String[] posiciones) {
        try {
            if (posiciones[1] != null && posiciones[2] != null && posiciones[3] != null) {
                tablero.getPanelControl().getFelix().setPosicionX(Integer.parseInt(posiciones[1]));
                tablero.getPanelControl().getFelix().setPosicionY(Integer.parseInt(posiciones[2]));
                tablero.getPanelControl().getFelix().setIndiceImagenActual(Integer.parseInt(posiciones[3]));
                tablero.getPuntaje().setText("Felix:" + posiciones[10]);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Error formato numero " + nfe.getMessage() + "--" + nfe.getCause());
        } catch (ArrayIndexOutOfBoundsException aie) {
            System.err.println("Error array indice " + aie.getMessage() + "--" + aie.getCause());
        }
    }

    public void moverSonic(String[] posiciones) {
        try {
            if (posiciones[1] != null && posiciones[2] != null && posiciones[3] != null) {
                tablero.getPanelControl().getSonic().setPosicionX(Integer.parseInt(posiciones[1]));
                tablero.getPanelControl().getSonic().setPosicionY(Integer.parseInt(posiciones[2]));
                tablero.getPanelControl().getSonic().setIndiceImagenActual(Integer.parseInt(posiciones[3]));
                tablero.getPuntaje().setText("Sonic:" + posiciones[10]);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Error formato numero " + nfe.getMessage() + "--" + nfe.getCause());
        } catch (ArrayIndexOutOfBoundsException aie) {
            System.err.println("Error array indice " + aie.getMessage() + "--" + aie.getCause());
        }
    }

    public void moverBomba(String[] posiciones) {
        try {
            if (posiciones[4] != null && posiciones[5] != null && posiciones[6] != null) {
                tablero.getPanelControl().getBomba().setPosicionX(Integer.parseInt(posiciones[4]));
                tablero.getPanelControl().getBomba().setPosicionY(Integer.parseInt(posiciones[5]));
                tablero.getPanelControl().getBomba().setIndiceImagenActual(Integer.parseInt(posiciones[6]));
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Error formato numero " + nfe.getMessage() + "--" + nfe.getCause());
        } catch (ArrayIndexOutOfBoundsException aie) {
            System.err.println("Error array indice " + aie.getMessage() + "--" + aie.getCause());
        }
    }

    public void moverVegeta(String[] posiciones) {
        try {
            if (posiciones[7] != null && posiciones[8] != null && posiciones[9] != null) {
                tablero.getPanelControl().getVegeta().setPosicionX(Integer.parseInt(posiciones[7]));
                tablero.getPanelControl().getVegeta().setPosicionY(Integer.parseInt(posiciones[8]));
                tablero.getPanelControl().getVegeta().setIndiceImagenActual(Integer.parseInt(posiciones[9]));
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Error formato numero " + nfe.getMessage() + "--" + nfe.getCause());
        } catch (ArrayIndexOutOfBoundsException aie) {
            System.err.println("Error array indice " + aie.getMessage() + "--" + aie.getCause());
        }
    }
}
