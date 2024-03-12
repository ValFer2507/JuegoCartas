package juegocartas;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carta {
    public int indice;
    //método constructor
    public Carta(Random r) {
        //generar el indice de la carta de manera aleatoria
        indice = r.nextInt(52) + 1;
    }
    public void mostrar(JPanel pnl, int x, int y) {
        //obtener el nombre del archivo de la carta
        String nombreArchivo = "/imagenes/CARTA" + String.valueOf(indice) + ".jpg";
        //cargar la imagen
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreArchivo));
        //Instanciar el objeto JLABEL que mostrará la carta
        JLabel lbl = new JLabel(imagen);
        //definir las coordenadas y dimension de la imagen
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        //mostrar la carta en el objeto PANEL de despliegue
        pnl.add(lbl);
    }
    public Pinta ObtenerPinta() {
        int pinta = (indice-1) / 13;
        /*if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }*/
        return Pinta.values()[pinta];
    }

    public NombreCarta ObtenerNombre() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13;
        }
        return NombreCarta.values()[residuo - 1];
    }
    
    public int ObtenerValor(){
        return 0;
    }

}
