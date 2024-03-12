package juegocartas;

import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN_SUPERIOR = 10;
    private int MARGEN_IZQUIERDA = 10;
    private int DISTANCIA = 50; //espaciado entre cartas

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Carta[] cartasOrden = new Carta[TOTAL_CARTAS]; //arreglo copia donde se reordenan las cartas 
    private Random r = new Random();
    
    //contador de escaleras [Figura][Cantidad]
    private int[][] contEscalera = new int[Pinta.values().length][Grupo.values().length];
    private int crtActual , crtSiguiente;

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();

        int x = MARGEN_IZQUIERDA + (TOTAL_CARTAS*DISTANCIA);
        for (Carta carta : cartas) {
            x -= DISTANCIA;
            carta.mostrar(pnl, x, MARGEN_SUPERIOR);
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron Figuras.";
        //verificar que haya cartas
        if (cartas[0] != null) {         

            //ordenar el arreglo copia
            int aux;
            cartasOrden = cartas;
            for(int j=0;j<cartasOrden.length-1;j++){
                for(int i=0 ; i<cartasOrden.length-1;i++){   
                    if(cartasOrden[i].indice > cartasOrden[i+1].indice){
                        aux = cartasOrden[i+1].indice;
                        cartasOrden[i+1].indice = cartasOrden[i].indice;
                        cartasOrden[i].indice = aux;
                    }
                }
            }
            
            int contadorEsc = 0;
            int puntos = 0;
            boolean escal = false;
            
            System.out.println("________________________________________");
            System.out.println("Cartas en Orden:");
            
            for (int i = 0 ; i < cartasOrden.length -1 ; i++) {
                
                crtActual = cartasOrden[i].indice;
                crtSiguiente = cartasOrden[i+1].indice;
                
                System.out.println("Crt "+ (i+1) +": "+  crtActual +" <"+NombreCarta.values()[((crtActual-1) % 13)] +" DE "+ Pinta.values()[(crtActual-1)/13] +">" );
                //validar que sean consecutivas y que tengan la misma pinta
                if ((crtSiguiente - crtActual == 1) && (Pinta.values()[(crtSiguiente-1)/13] == Pinta.values()[(crtActual-1)/13])){
                    contadorEsc ++;
                    System.out.println("+");                   
                }
                else{
                    if(contadorEsc>0){
                        escal = true;
                        cuentaFiguras(i,contadorEsc);
                    }else{
                        puntos += calculoPuntos(((crtActual-1) % 13) + 1);//((cartasOrden[i].indice-1) % 13) + 1;
                        System.out.println("  Suma " + calculoPuntos(((crtActual-1) % 13) + 1) + " pnt = "+puntos);
                    }
                    contadorEsc = 0;
                }
                if(i == 8){
                    i++;
                    System.out.println("Crt "+ (i+1) +": "+  crtActual +" <"+NombreCarta.values()[calculoPuntos((crtActual-1) % 13)] +"  "+ Pinta.values()[(crtActual-1)/13] +">" );
                    if(contadorEsc > 0){
                        escal = true;
                        cuentaFiguras(i,contadorEsc);
                    }else{
                        puntos += calculoPuntos(((crtActual-1) % 13) + 1);
                        System.out.println("  Suma " + calculoPuntos(((crtActual-1) % 13) + 1)+ " pnt = " + puntos);
                    } 
                }
            }
            
            if (escal){
                mensaje = "Figuras:\n";
                for (int i = 0; i < Pinta.values().length; i++) {
                    for(int j = 0; j <Grupo.values().length;j++){
                        if(contEscalera[i][j]>0){
                            System.out.println("Figura["+ i +"]["+j+"]: "+ contEscalera[i][j]+ " "+Grupo.values()[j+1]+" de "+ Pinta.values()[i]);
                            mensaje += contEscalera[i][j] +" "+ Grupo.values()[j+1] + " de " + Pinta.values()[i] + "\n";
                            contEscalera[i][j]=0;
                        }
                    }
                }
            }
            System.out.println("\nTotal Puntos: " + puntos);
            mensaje += "\nTotal Puntos: "+ puntos;
        } else {
            mensaje = "No se han repartido cartas";
        }
        return mensaje;
    }
    
    //Calcula el puntaje de una carta
    private int calculoPuntos(int indiceCarta){
        int pnts;
        
        if(indiceCarta == 1 || indiceCarta > 10){
            pnts = 10;
        }else{
            pnts = indiceCarta;
        }
        return pnts; 
    }
    
    private void cuentaFiguras(int i,int cont){
        if(crtActual <= 13){
            contEscalera[0][cont]++; //TREBOL
        }else if (cartasOrden[i-1].indice>13 && crtActual<=26){
            contEscalera[1][cont]++; //PICA
        }else if (cartasOrden[i-1].indice>26 && crtActual<=39){
            contEscalera[2][cont]++; //CORAZON
        }else if (cartasOrden[i-1].indice>39 && crtActual<=52){
            contEscalera[3][cont]++; //DIAMANTE
        }
    }
}
