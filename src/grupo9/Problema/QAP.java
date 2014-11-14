package grupo9.Problema;

import grupo9.externos.ManejadorArchivo;

/**
 * 
 * @author choffis
 * 
 */
public class QAP extends Problema {

    private Double[][] matrizLocalidades;
    

    public QAP(int instancia, int corrida) throws Exception {
        super(ManejadorArchivo.QAP_PROBLEM, instancia, corrida);
    }

    @Override
    public Double funObjUno(Hormiga individuo) {
        int i, j;
        double suma = 0;
        for (i = 0; i < individuo.getLongitud(); i++) {
            for (j = 0; j < individuo.getLongitud(); j++) {
                suma += matrizLocalidades[i][j] * getMatrizObjetivoUno()[individuo.get(i)][individuo.get(j)];
            }
        }
        return suma;
    }

    @Override
    public Double funObjDos(Hormiga individuo) {
        int i, j;
        double suma = 0;
        for (i = 0; i < individuo.getLongitud(); i++) {
            for (j = 0; j < individuo.getLongitud(); j++) {
                suma += matrizLocalidades[i][j] * getMatrizObjetivoDos()[individuo.get(i)][individuo.get(j)];
            }
        }

        return suma;
    }

    // no son implementadas funciones heuristicas
    @Override
    public double heuriUno(int estOrigen, int estDest) {
        return 1.0;
    }

    @Override
    public double heuriDos(int estOrigen, int estDest) {
        return 1.0;
    }

    public Double[][] getMatrizLocalidades() {
        return matrizLocalidades;
    }

    public void setMatrizLocalidades(Double[][] matrizLocalidades) {
        this.matrizLocalidades = matrizLocalidades;
    }

    @Override
    public void initProblem(int instancia) {
        try {
            ManejadorArchivo fh = new ManejadorArchivo();
            fh.cargarDatosdeInstancias(matrizLocalidades, getMatrizObjetivoUno(), getMatrizObjetivoDos(),
                    this, instancia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
