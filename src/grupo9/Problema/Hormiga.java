package grupo9.Problema;

/**
 *
 * @author choffis
 */
public class Hormiga {
    private int []cromosoma;
    private int longitud;
    private double fitness;

    public Hormiga(int longitud){
        this.longitud = longitud;
        cromosoma = new int[longitud];
        for (int i = 0; i < longitud; i++) {
            cromosoma[i] = -1;
        }
    }

    public int[] getCromosoma() {
        return cromosoma;
    }

    public void setCromosoma(int[] cromosoma) {
        this.cromosoma = cromosoma;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    

    public void clone(Hormiga h){
        for (int i = 0; i < h.getLongitud(); i++) {
            cromosoma[i] = h.get(i);
        }
    }

    public int get(int pos){
        return cromosoma[pos];
    }

    public void resetear() {
        for (int i = 0; i < longitud; i++) {
            cromosoma[i] = -1;
        }
    }

    public void set(int pos, int valor) {
        cromosoma[pos] = valor;
    }
}
