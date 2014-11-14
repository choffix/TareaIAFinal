package grupo9.conjuntosPareto;

import grupo9.Problema.Hormiga;
import grupo9.Problema.Problema;


/**
 *
 * @author choffis
 */
public class ConjuntoPareto {

    private int cantSoluciones;
    private int size; 
    private Hormiga[] conjunto;

    public ConjuntoPareto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Hormiga getIndividuo(int i) {
        return conjunto[i];
    }

    public int getCantSoluciones() {
        return cantSoluciones;
    }

    public void setCantSoluciones(int cantSoluciones) {
        this.cantSoluciones = cantSoluciones;
    }

    public Hormiga[] getConjunto() {
        return conjunto;
    }

    public void setConjunto(Hormiga[] conjunto) {
        this.conjunto = conjunto;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ConjuntoPareto(int numSoluciones) {
        cantSoluciones = 0;
        size = numSoluciones;
        conjunto = new Hormiga[numSoluciones];
    }

    public int agregarNoDominado(Hormiga individuo, Problema prob) {
        double solFuncion1 = prob.funObjUno(individuo);
        double solFuncion2 = prob.funObjDos(individuo);

        double solauxfuncion1, solauxfuncion2;


        for (int i = 0; i < cantSoluciones; i++) {
            solauxfuncion1 = prob.funObjUno(conjunto[i]);
            solauxfuncion2 = prob.funObjDos(conjunto[i]);
            if (solauxfuncion1 <= solFuncion1 && solauxfuncion2 <= solFuncion2) {
                return 0;
            }
        }

        if (cantSoluciones == size) {
            Hormiga[] listaAux = conjunto;
            size = size * 2;
            conjunto = new Hormiga[size];
            for (int i = 0; i < cantSoluciones; i++) {
                conjunto[i] = listaAux[i];
            }
        }
        if (conjunto[cantSoluciones] == null) {
            conjunto[cantSoluciones] = new Hormiga(individuo.getLongitud());
        }

        conjunto[cantSoluciones].clone(individuo);
        cantSoluciones++;
        return 1;
    }

    public void eliminarDominados(Hormiga individuo, Problema prob) {
        double solfuncion1 = prob.funObjUno(individuo); 
        double solfuncion2 = prob.funObjDos(individuo); 
        double solauxfuncion1, solauxfuncion2; 

        for (int i = 0; i < cantSoluciones; i++) {
            solauxfuncion1 = prob.funObjUno(conjunto[i]);
            solauxfuncion2 = prob.funObjDos(conjunto[i]);

            if ((solauxfuncion1 > solfuncion1 && solauxfuncion2 >= solfuncion2) || (solauxfuncion1 >= solfuncion1 && solauxfuncion2 > solfuncion2)) {
                conjunto[i] = null;
                conjunto[i] = conjunto[cantSoluciones - 1];
                conjunto[cantSoluciones - 1] = null; 
                cantSoluciones--;
                i--;
            }
        }
    }
}
