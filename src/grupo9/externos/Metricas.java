/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package grupo9.externos;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author choffis
 */
public class Metricas {
    private static final double SIGMA = 100000.0;
    public List<Double> Ytrue1;
    public List<Double> Ytrue2;
    public List<Double> funObj1;
    public List<Double> funObj2;
    private Properties props;
    private String path;
    int problema;
    int corrida;
    int instancia;
  
    public Metricas() throws IOException {
        props = new Properties();
        path = System.getProperty("user.dir");
        props.load(new FileInputStream(path + "/props/bundle.properties"));
    }
    
    public Metricas(int problema, int corrida, int instancia) throws IOException {
        props = new Properties();
        path = System.getProperty("user.dir");
        props.load(new FileInputStream(path + "/props/bundle.properties"));
        this.problema = problema;
        this.corrida = corrida;
        this.instancia = instancia;
    }

    public Double metricaM1() {
        Double[] YtrueX = this.Ytrue1.toArray(new Double[0]);
        Double[] YtrueY = this.Ytrue2.toArray(new Double[0]);
        Double[] funObjX = this.funObj1.toArray(new Double[0]);
        Double[] funObjY = this.funObj2.toArray(new Double[0]);

        Double sumaDistanciasMin = 0.0; //almacena la sumatoria de distancias
        Double distMinFinal = Double.MAX_VALUE; //distanciaEntreDosPutnos(funObjX[1] / YtrueXMax, YtrueX[1] / YtrueXMax, funObjY[1] / YtrueYMax, YtrueY[1] / YtrueYMax);
        Double distMin;

        for (int i = 0; i < funObjX.length; i++) {
            distMinFinal = Double.MAX_VALUE;
            for (int j = 0; j < YtrueX.length; j++) {
                distMin = distanciaEntreDosPuntos(funObjX[i], YtrueX[j], funObjY[i], YtrueY[j]);
                if (distMinFinal > distMin) {
                    distMinFinal = distMin;
                }
            }
            sumaDistanciasMin += distMinFinal;

        }

        return sumaDistanciasMin / funObjX.length;

    }
        

   

    public Double metricaM2() {

        Double[] funObjX = this.funObj1.toArray(new Double[0]);
        Double[] funObjY = this.funObj2.toArray(new Double[0]);

        double total = 0.0;
        for (int i = 0; i < funObjX.length; i++) {
            double cont = 0.0;
            for (int j = 0; j < funObjX.length; j++) {
                if(distanciaEntreDosPuntos(funObjX[i], funObjX[j], funObjY[i], funObjY[j]) < SIGMA){
                    cont = cont + 1;
                }
            }
            total += cont;
        }

        return total / (funObjX.length - 1);
    }

    public Double metricaM3() {

        Double[] funObjX = this.funObj1.toArray(new Double[0]);
        Double[] funObjY = this.funObj2.toArray(new Double[0]);
        Double distMinFinal = Double.MIN_VALUE;
        Double distMin = 0.0;


        for (int i = 0; i < funObjX.length; i++) {
            for (int j = 0; j < funObjX.length; j++) {
                distMin = distanciaEntreDosPuntos(funObjX[i], funObjX[j], funObjY[i], funObjY[j]);
                if (distMinFinal < distMin) {
                    distMinFinal = distMin;
                }
            }
        }

        return distMinFinal;
    }

   public Double metricaM4() throws IOException {
        Double[] YtrueX = this.Ytrue1.toArray(new Double[0]);
        Double[] funObjX = this.funObj1.toArray(new Double[0]);

        if (instancia == ManejadorArchivo.INSTANCIA_DOS) {
                YtrueX = this.Ytrue2.toArray(new Double[0]);
                funObjX = this.funObj2.toArray(new Double[0]);
        } 
        int FinterseccionF;
        FinterseccionF=interseccion(YtrueX,funObjX);
        double div = 0.0;
        if(FinterseccionF != 0){
            div = FinterseccionF/funObjX.length;
        }
        
        return 1- div;
        

    }

    public int interseccion(Double [] Y, Double[] F) throws IOException {
        int pos=0;
        for (Double Y1 : Y) {
            for (Double F1 : F) {
                if (Y1 == F1) {
                    pos++;
                }
            }
        }
     return pos;
   
    }
    
    public void cargarYtrue(String frenteParetoOptimo) throws IOException {
        try {
            FileInputStream archivo = new FileInputStream(path + "/fronts/" + frenteParetoOptimo);
            DataInputStream entrada = new DataInputStream(archivo);
            StringTokenizer st;
            String f1;
            String f2;
            String linea;
            Ytrue1 = new ArrayList<Double>();
            Ytrue2 = new ArrayList<Double>();
            while (true) {
                linea = entrada.readLine();
                if (linea == null) {
                    break;
                }
                st = new StringTokenizer(linea);

                f1 = st.nextToken();
                f2 = st.nextToken();

                Ytrue1.add(Double.parseDouble(f1));
                Ytrue2.add(Double.parseDouble(f2));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Metricas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarFrente() throws IOException {
        try {
            FileInputStream archivo = null;
            if (instancia == ManejadorArchivo.INSTANCIA_UNO) {
                archivo = new FileInputStream(path + props.getProperty
                ("QAP_OUTPUT_FILE_UNO") + corrida);
            } else if (instancia == ManejadorArchivo.INSTANCIA_DOS) {
                archivo = new FileInputStream(path + props.getProperty
                ("QAP_OUTPUT_FILE_DOS") + corrida);
            }
            
            DataInputStream entrada = new DataInputStream(archivo);
            StringTokenizer st;
            String f1;
            String f2;
            String linea;
            funObj1 = new ArrayList<Double>();
            funObj2 = new ArrayList<Double>();
            while (true) {
                linea = entrada.readLine();
                if (linea == null) {
                    break;
                }
                st = new StringTokenizer(linea);

                f1 = st.nextToken(); 
                f2 = st.nextToken(); 

                this.funObj1.add(Double.parseDouble(f1));
                this.funObj2.add(Double.parseDouble(f2));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Metricas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double distanciaEntreDosPuntos(double x, double x1, double y, double y1) {
        double distancia = 0;
        double aux = Math.pow(x - x1, 2);
        aux += Math.pow(y - y1, 2);
        distancia = Math.sqrt(aux);
        return distancia;
    }
}
   

