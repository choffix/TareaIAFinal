package grupo9.Problema;


/**
 * 
 * @author choffis
 * 
 */
public abstract class Problema {

    private Double[][] matrizObjetivoUno;
    private Double[][] matrizObjetivoDos;
    private int size;
    private int problem;
    private int instancia;
    private int corrida;

    public int getCorrida() {
        return corrida;
    }

    public void setCorrida(int corrida) {
        this.corrida = corrida;
    }

    public int getInstancia() {
        return instancia;
    }

    public void setInstancia(int instancia) {
        this.instancia = instancia;
    }

    public int getProblem() {
        return problem;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Double[][] getMatrizObjetivoDos() {
        return matrizObjetivoDos;
    }

    public void setMatrizObjetivoDos(Double[][] matrizObjetivoDos) {
        this.matrizObjetivoDos = matrizObjetivoDos;
    }

    public Double[][] getMatrizObjetivoUno() {
        return matrizObjetivoUno;
    }

    public void setMatrizObjetivoUno(Double[][] matrizObjetivoUno) {
        this.matrizObjetivoUno = matrizObjetivoUno;
    }

    public Problema(int p, int instancia, int corrida) {
        problem = p;
        this.corrida = corrida;
        this.instancia = instancia;
        initProblem(instancia);
    }

    public int getSize() {
        return size;
    }

    public double getValueMatrizUno(int i, int j) {
        return matrizObjetivoUno[i][j];
    }

    public double getValueMatrizDos(int i, int j) {
        return matrizObjetivoDos[i][j];
    }

    public abstract void initProblem(int instancia);

    public abstract Double funObjUno(Hormiga individuo);

    public abstract Double funObjDos(Hormiga individuo);

    public abstract double heuriUno(int estOrigen, int estDest);

    public abstract double heuriDos(int estOrigen, int estDest);
}
