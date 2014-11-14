package grupo9.externos;

import grupo9.conjuntosPareto.ConjuntoPareto;
import grupo9.Problema.Problema;
import grupo9.Problema.QAP;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 *
 * @author choffis
 */
public class ManejadorArchivo {

    public static final int QAP_PROBLEM = 1;
    public static final int INSTANCIA_UNO = 1;
    public static final int INSTANCIA_DOS = 2;
    private FileInputStream fis;
    private BufferedReader buffer;
    private PrintWriter pw;
    private Properties props;
    private String path;
    private FileWriter fw;

    public ManejadorArchivo() throws IOException {
        props = new Properties();

        path = System.getProperty("user.dir");
        props.load(new FileInputStream(path + "/props/bundle.properties"));
    }

      
    public void cargarDatosdeInstancias(Double[][] localidades, Double[][] objUno,
            Double[][] objDos, QAP problem, int instancia) {
        int size;
        String linea = "";

        try {
            if (instancia == INSTANCIA_UNO) {
                fis = new FileInputStream(path + props.getProperty
                        ("QAP_IN_FILE_INSTANCIA_UNO"));
            } else if (instancia == INSTANCIA_DOS) {
                fis = new FileInputStream(path + props.getProperty
                        ("QAP_IN_FILE_INSTANCIA_DOS"));
            }

            buffer = new BufferedReader(new InputStreamReader(fis));

            linea = buffer.readLine();
            size = Integer.parseInt(linea);
            problem.setSize(size);

            objUno = new Double[size][size];
            cargarValores(objUno, size);
            problem.setMatrizObjetivoUno(objUno);

            buffer.readLine();
            objDos = new Double[size][size];
            cargarValores(objDos, size);
            problem.setMatrizObjetivoDos(objDos);

            buffer.readLine(); 
            localidades = new Double[size][size];
            cargarValores(localidades, size);
            problem.setMatrizLocalidades(localidades);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarValores(Double[][] matrix, int size) throws
            IOException, NullPointerException {
        String linea = "";
        StringTokenizer tok;
        int i, j;
        Double valor;
        i = 0;
        do {
            linea = buffer.readLine();
            tok = new StringTokenizer(linea, " ");
            j = 0;
            while (j < size) {
                valor = Double.parseDouble(tok.nextToken());
                matrix[i][j] = valor;
                j++;
            }
            i++;
        } while (i < size);
    }
    

  
    public void enviarSalida(ConjuntoPareto paretoSet, Problema problem) {
        try {
             if (problem.getInstancia() == INSTANCIA_UNO) {
                    fw = new FileWriter(path + props.getProperty
                            ("QAP_OUTPUT_FILE_UNO") + problem.getCorrida(), true);
                    pw = new PrintWriter(fw);
             } else if (problem.getInstancia() == INSTANCIA_DOS) {
                    fw = new FileWriter(path + props.getProperty
                            ("QAP_OUTPUT_FILE_DOS") + problem.getCorrida(), true);
                    pw = new PrintWriter(fw);
             }
            
            for (int i = 0; i < paretoSet.getCantSoluciones(); i++) {
                    pw.println(problem.funObjUno(paretoSet.getIndividuo(i)).toString() + " "
                            + problem.funObjDos(paretoSet.getIndividuo(i)).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
    }
}
