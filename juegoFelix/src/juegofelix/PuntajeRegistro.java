
package juegofelix;

import java.io.*;
import java.util.Date;

/*@Héctor Daza
  @Andrés Iriarte
*/


public class PuntajeRegistro {

    private File archivo = null;
    private FileReader fr = null;
    private BufferedReader br = null;
    private FileWriter fichero = null;
    private PrintWriter pw = null;

    public void escrituraArchivo(String pAImprimir) {

        try {
            fichero = new FileWriter("C:\\FelixVsSonic/puntaje.txt", true);
            pw = new PrintWriter(fichero);
            pw.println(pAImprimir + "  - " + new Date());
        } catch (Exception e) {
            System.err.println("Error al escribir puntaje " + e.getMessage());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
