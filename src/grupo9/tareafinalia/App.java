package grupo9.tareafinalia;

import grupo9.Problema.QAP;
import grupo9.externos.ManejadorArchivo;
import grupo9.externos.Metricas;
import grupo9.m3as.M3AS_QAP;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author choffis
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, Exception
    {
        Metricas zitzler;
        M3AS_QAP m3asQap;
        String Ytrue="YTRUE-qapUni.75.0.1.qap.txt";
        String instancia="qapUni.75.0.1.qap ";
        ArrayList<Integer> inst= new ArrayList<>();
        inst.add(ManejadorArchivo.INSTANCIA_UNO);
        inst.add(ManejadorArchivo.INSTANCIA_DOS);
        int j=0;
        while(j<2){
            if(j==1){
                Ytrue="YTRUE-qapUni.75.p75.1.qap.txt";
                instancia="qapUni.75.p75.1.qap ";
            }
            for (int i = 0; i < 5; i++) {
                m3asQap = new M3AS_QAP(new QAP(inst.get(j), i));
                m3asQap.hacerQAPM3AS();
                m3asQap.enviarSalida();
                zitzler = new Metricas(m3asQap.getProblema().getProblem()
                        ,m3asQap.getProblema().getCorrida()
                        ,m3asQap.getProblema().getInstancia());
                zitzler.cargarFrente();
                zitzler.cargarYtrue(Ytrue);
                System.out.println( instancia+ i);
                System.out.println("M1: " + zitzler.metricaM1());
                System.out.println("M2: " + zitzler.metricaM2());
                System.out.println("M3: " + zitzler.metricaM3());
                System.out.println("M4: " + zitzler.metricaM4());
                System.out.println();
            }
            j++;
        }
}
}
