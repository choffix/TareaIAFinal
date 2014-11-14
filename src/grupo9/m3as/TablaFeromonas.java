package grupo9.m3as;

/**
 * 
 * @author choffis
 * 
 */
public class TablaFeromonas {

    private int tamano;
    private double[][] tabla;

    public TablaFeromonas(int t, double taoInicial) {
        tamano = t;
        tabla = new double[t][t];
        inicializarTabla(taoInicial);
    }

    private void inicializarTabla(double tao) {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                tabla[i][j] = tao;
            }
        }
    }

    public double getValor(int i, int j) {
        return tabla[i][j];
    }

    public void setValor(int i, int j, double valor) {
        tabla[i][j] = valor;
    }

    public void imprimirTabla() {
        for (int i = 0; i < tamano; i++) {
            System.out.println("\n");
            for (int j = 0; j < tamano; j++) {
                System.out.println(" " + tabla[i][j]);
            }
        }
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public double[][] getTabla() {
        return tabla;
    }

    public void setTabla(double[][] tabla) {
        this.tabla = tabla;
    }

    public void reiniciarTabla() {
        inicializarTabla(-1.0);
    }

    public void actualizar(int estOrigen, int estDestino, double tau) {
        tabla[estOrigen][estDestino] = tau;
    }
}
