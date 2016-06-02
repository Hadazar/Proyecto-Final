package hilos;

import dto.Personaje;
import java.util.Random;
import javax.swing.JOptionPane;
import juegofelix.JuegoFelix;
import juegofelix.PuntajeRegistro;
import vista.Tablero;

/**
 @Héctor Daza
 @Andrés Iriarte
 *
 */
public class MoverPersonaje extends Thread {

    private Personaje personaje;
    private Tablero tablero;
    private Boolean start;
    private Random aleatoreo;
    private int dificultad;
    private boolean multijugador;
    private Boolean mensaje;
    private JuegoFelix juego;
    private PuntajeRegistro puntajeRegistro;
    private String nombreJugador;

    public MoverPersonaje(Personaje personaje, Tablero tablero, int dificultad, boolean multijugador, String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.mensaje = false;
        this.personaje = personaje;
        this.tablero = tablero;
        this.start = true;
        this.aleatoreo = new Random();
        this.dificultad = dificultad;
        this.multijugador = multijugador;
    }

    @Override
    public void run() {
        while (start) {
            enviarDatosServidor("no");
            verificarJuego();
            control();
            actualizarPuntaje();

            if ((verificarJuego() || validarVictoria()) && (!tablero.getPanelControl().getFelix().isActivo() && !tablero.getPanelControl().getSonic().isActivo())) {
                break;
            }
        }
        //System.out.println(start + "-" + mensaje + "-" + personaje.getNombrePersonaje());
        if (!start && mensaje) {
            try {
                if (personaje.getNombrePersonaje().equalsIgnoreCase("Felix") && multijugador && !tablero.getPanelControl().getFelix().isActivo()) {
                    vegetaVictorioso();
                    acabarJuegoIniciar("Perdiste tu puntaje fue " + tablero.getPanelControl().getFelix().getPuntaje());
                } else if (personaje.getNombrePersonaje().equalsIgnoreCase("Sonic") && !tablero.getPanelControl().getSonic().isActivo()) {
                    vegetaVictorioso();
                    acabarJuegoIniciar("Perdiste tu puntaje fue " + tablero.getPanelControl().getSonic().getPuntaje());
                }
            } catch (Throwable ex) {
                System.err.println("Error al terminar los hilos, destruyalos de manera manual " + ex.getMessage());
            }
        }
    }

    public void enviarDatosServidor(String reinicio) {
        if (multijugador) {
            tablero.enviarDatos("felix" + "-"
                    + tablero.getPanelControl().getFelix().getPosicionX() + "-" + tablero.getPanelControl().getFelix().getPosicionY() + "-" + tablero.getPanelControl().getFelix().getIndiceImagenActual()
                    + "-" + tablero.getPanelControl().getBomba().getPosicionX() + "-" + tablero.getPanelControl().getBomba().getPosicionY() + "-" + tablero.getPanelControl().getBomba().getIndiceImagenActual()
                    + "-" + tablero.getPanelControl().getVegeta().getPosicionX() + "-" + tablero.getPanelControl().getVegeta().getPosicionY() + "-" + tablero.getPanelControl().getVegeta().getIndiceImagenActual()
                    + "-" + tablero.getPanelControl().getFelix().getPuntaje() + "-" + reinicio);
        } else {
            tablero.enviarDatos("sonic" + "-"
                    + tablero.getPanelControl().getSonic().getPosicionX() + "-" + tablero.getPanelControl().getSonic().getPosicionY() + "-" + tablero.getPanelControl().getSonic().getIndiceImagenActual()
                    + "-" + tablero.getPanelControl().getBomba().getPosicionX() + "-" + tablero.getPanelControl().getBomba().getPosicionY() + "-" + tablero.getPanelControl().getBomba().getIndiceImagenActual()
                    + "-" + tablero.getPanelControl().getVegeta().getPosicionX() + "-" + tablero.getPanelControl().getVegeta().getPosicionY() + "-" + tablero.getPanelControl().getVegeta().getIndiceImagenActual()
                    + "-" + tablero.getPanelControl().getSonic().getPuntaje() + "-" + reinicio);
        }
    }

    /**
     * Movimientos del personaje
     */
    public void control() {
        if (!personaje.getNombrePersonaje().equalsIgnoreCase("vegeta") && !personaje.getNombrePersonaje().equalsIgnoreCase("bomba")) {
            switch (personaje.getControlTecla()) {
                case 37:
                    saltar();
                    personaje.setControlTecla(0);
                    break;
                case 38:
                    hacerCaminarArribaPersonaje();
                    personaje.setControlTecla(0);
                    break;
                case 39:
                    caminar();
                    personaje.setControlTecla(0);
                    break;
                case 40:
                    hacerCaminarAbajoPersonaje();
                    personaje.setControlTecla(0);
                    break;
                case 98:
                    hacerCaminarAbajoPersonaje();
                    personaje.setControlTecla(0);
                    break;
                case 100:
                    saltar();
                    personaje.setControlTecla(0);
                    break;
                case 102:
                    caminar();
                    personaje.setControlTecla(0);
                    break;
                case 104:
                    hacerCaminarArribaPersonaje();
                    personaje.setControlTecla(0);
                    break;
                default:
                    break;
            }
        } else if (personaje.getNombrePersonaje().equalsIgnoreCase("vegeta")) {
            vegetaCargando();
        } else {
            bomba();
        }
    }

    public void saltar() {
        personaje.setIndiceImagenActual(personaje.getCantidadDeMovimientos() - 2);
        for (int i = 0; i < 15; i++) {
            esperarXSegundos(1);
            personaje.setPosicionY(personaje.getPosicionY() - i);
            tablero.repaint();
        }
        for (int i = 0; i < 15; i++) {
            esperarXSegundos(1);
            personaje.setPosicionY(personaje.getPosicionY() + i);
            tablero.repaint();
        }
        personaje.setIndiceImagenActual(0);
    }

    public void hacerCaminarArribaPersonaje() {
        for (int i = 1; i < personaje.getCantidadDeMovimientos() - 2; i++) {
            esperarXSegundos(1);
            personaje.setIndiceImagenActual(i);
            if (validarLimitesPersonajes()) {
                personaje.setPosicionY(personaje.getPosicionY() - 1);
            }
            tablero.repaint();
        }
    }

    public void hacerCaminarAbajoPersonaje() {
        for (int i = 1; i < personaje.getCantidadDeMovimientos() - 2; i++) {
            esperarXSegundos(1);
            personaje.setIndiceImagenActual(i);
            if (validarLimitesPersonajes()) {
                personaje.setPosicionY(personaje.getPosicionY() + 1);
            }
            tablero.repaint();
        }
    }

    public void caminar() {
        for (int i = 1; i < personaje.getCantidadDeMovimientos() - 2; i++) {
            esperarXSegundos(1);
            personaje.setIndiceImagenActual(i);
            personaje.setPosicionX(personaje.getPosicionX() + 2);
            tablero.repaint();
        }
    }

    public void vegetaCargando() {
        for (int i = 0; i < tablero.getPanelControl().getVegeta().getCantidadDeMovimientos() - 2; i++) {
            esperarXSegundos(2);
            if ((tablero.getPanelControl().getFelix().isActivo() || tablero.getPanelControl().getSonic().isActivo()) && tablero.getPanelControl().getVegeta().isActivo()) {
                tablero.getPanelControl().getVegeta().setIndiceImagenActual(i);
                tablero.repaint();
            }
        }
    }

    public void vegetaDerrotado() {
        tablero.getPanelControl().getVegeta().setActivo(false);
        tablero.getPanelControl().getVegeta().setIndiceImagenActual(tablero.getPanelControl().getVegeta().getCantidadDeMovimientos() - 1);
        tablero.getPanelControl().getVegeta().setPosicionX(300);
        tablero.getPanelControl().getBomba().setIndiceImagenActual(tablero.getPanelControl().getBomba().getCantidadDeMovimientos() - 1);
        tablero.repaint();
    }

    public void vegetaVictorioso() {
        tablero.getPanelControl().getVegeta().setActivo(false);
        tablero.getPanelControl().getVegeta().setIndiceImagenActual(tablero.getPanelControl().getVegeta().getCantidadDeMovimientos() - 2);
        tablero.getPanelControl().getVegeta().setPosicionX(300);
        tablero.getPanelControl().getBomba().setIndiceImagenActual(tablero.getPanelControl().getBomba().getCantidadDeMovimientos() - 1);
        tablero.repaint();
    }

    public void esperarXSegundos(int segundos) {
        try {
            Thread.sleep(segundos * 30);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     *
     * @param segundos
     */
    public void esperarXsegundosBomba(int segundos) {
        try {
            Thread.sleep(segundos * dificultad);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    /*
     * Dificultad
     */

    public void bomba() {
        esperarXsegundosBomba(1);
        personaje.setActivo(validarLimitesBomba());

        if (personaje.isActivo()) {
            tablero.getPanelControl().getBomba().setPosicionX(personaje.getPosicionX() - 1);
        }
        tablero.repaint();
    }

    /**
     * Cotroles generales del juego
     */
    public void adicionarPuntaje() {
        if (multijugador && start) {
            if (tablero.getPanelControl().getFelix().isActivo()) {
                tablero.getPanelControl().getFelix().setPuntaje(tablero.getPanelControl().getFelix().getPuntaje() + tablero.getPanelControl().getFelix().getPosicionX());
            }
        } else if (tablero.getPanelControl().getSonic().isActivo() && start) {
            tablero.getPanelControl().getSonic().setPuntaje(tablero.getPanelControl().getSonic().getPuntaje() + tablero.getPanelControl().getSonic().getPosicionX());
        }
    }

    public void actualizarPuntaje() {
        if (tablero.getPanelControl().getFelix().isActivo() && multijugador) {
            tablero.getPuntaje().setText("FELIX: " + tablero.getPanelControl().getFelix().getPuntaje().intValue());
        } else if (tablero.getPanelControl().getSonic().isActivo() && !multijugador) {
            tablero.getPuntaje().setText(" SONIC: " + tablero.getPanelControl().getSonic().getPuntaje().intValue());
        }
    }

    public void acabarJuegoIniciar(String mensaje) {
        String aGuardar = "";
        String estado = "Gano";
        int eleccion = 0;
        if (mensaje.contains("Perdiste")) {
            estado = "Perdio";
        }
        if (!tablero.getPanelControl().getFelix().isActivo() && multijugador) {
            aGuardar = nombreJugador + " jugo con FELIX: " + tablero.getPanelControl().getFelix().getPuntaje().intValue();
            puntajeRegistro = new PuntajeRegistro();
            puntajeRegistro.escrituraArchivo(aGuardar + " - " + estado);
            eleccion = JOptionPane.showConfirmDialog(null, mensaje + ", quieres volver a intentarlo ?");
            decisionJuego(eleccion);
        } else if (!tablero.getPanelControl().getSonic().isActivo() && !multijugador) {
            aGuardar = nombreJugador + " jugo con SONIC: " + tablero.getPanelControl().getSonic().getPuntaje().intValue();
            puntajeRegistro = new PuntajeRegistro();
            puntajeRegistro.escrituraArchivo(aGuardar + " - " + estado);
            eleccion = JOptionPane.showConfirmDialog(null, mensaje + ", quieres volver a intentarlo ?");
            decisionJuego(eleccion);
        }


    }

    public void decisionJuego(int eleccion) {
        if (eleccion == JOptionPane.YES_OPTION) {
            enviarDatosServidor("si");
            tablero.getPanelControl().getFelix().setActivo(false);
            tablero.getPanelControl().getSonic().setActivo(false);
            tablero.getPanelControl().getBomba().setActivo(false);
            tablero.getPanelControl().getVegeta().setActivo(false);
            mensaje = false;
            tablero.setVisible(false);
            juego = new JuegoFelix();
            juego.nuevoJuego();
        } else if (eleccion == JOptionPane.NO_OPTION) {
            mensaje = false;
            tablero.setVisible(false);
            enviarDatosServidor("siC");
            tablero.cerrarConexion();
            System.exit(0);
        }
    }

    /**
     * Validaciones
     */
    public boolean validarLimitesPersonajes() {
        if (personaje.getPosicionY() >= 308 && personaje.getPosicionY() <= 370) {
            return true;
        } else {
            if (personaje.getPosicionY() < 308) {
                personaje.setPosicionY(308);
            } else {
                personaje.setPosicionY(370);
            }
            return false;
        }
    }

    public boolean validarLimitesBomba() {
        Double aleatoreoNumero;
        if (personaje.getPosicionX() >= 0) {
            return true;
        } else if (personaje.getPosicionX() < 0) {
            tablero.getPanelControl().getBomba().setPosicionX(405);
            aleatoreoNumero = aleatoreo.nextDouble() * 70;
            tablero.getPanelControl().getBomba().setPosicionY(375 - aleatoreoNumero.intValue());
            adicionarPuntaje();
            return false;
        } else {
            return false;
        }
    }

    public boolean verificarJuego() {
        if (validarRangoBombaY() && validarRangoBombaX()) {
            personaje.setActivo(false);
            if (personaje.getNombrePersonaje().equalsIgnoreCase("felix")) {
                tablero.getPanelControl().getFelix().setIndiceImagenActual(personaje.getCantidadDeMovimientos() - 1);
            } else if (personaje.getNombrePersonaje().equalsIgnoreCase("sonic")) {
                tablero.getPanelControl().getSonic().setIndiceImagenActual(personaje.getCantidadDeMovimientos() - 1);
            }
            return true;
        }

        if (multijugador) {
            if (!tablero.getPanelControl().getFelix().isActivo()) {
                start = false;
                mensaje = true;
                return true;
            }
        } else if (!tablero.getPanelControl().getSonic().isActivo()) {
            start = false;
            mensaje = true;
            return true;
        }
        return false;
    }

    public boolean validarRangoBombaY() {
        if (personaje.getPosicionY() >= tablero.getPanelControl().getBomba().getPosicionY() - 25 && personaje.getPosicionY() < tablero.getPanelControl().getBomba().getPosicionY() + 25) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarRangoBombaX() {
        if (personaje.getPosicionX() >= tablero.getPanelControl().getBomba().getPosicionX() && personaje.getPosicionX() < tablero.getPanelControl().getBomba().getPosicionX() + 20) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarVictoria() {
        if (multijugador) {
            if (tablero.getPanelControl().getFelix().getPosicionX() > 450 && personaje.getNombrePersonaje().equalsIgnoreCase("Felix")) {
                tablero.getPanelControl().getFelix().setActivo(false);
                vegetaDerrotado();
                acabarJuegoIniciar("Ganaste tu puntaje fue " + tablero.getPanelControl().getFelix().getPuntaje());
                return true;
            }
        } else {
            if (tablero.getPanelControl().getSonic().getPosicionX() > 450 && personaje.getNombrePersonaje().equalsIgnoreCase("Sonic")) {
                tablero.getPanelControl().getSonic().setActivo(false);
                vegetaDerrotado();
                acabarJuegoIniciar("Ganaste tu puntaje fue " + tablero.getPanelControl().getSonic().getPuntaje());
                return true;
            }
        }
        return false;
    }
}
