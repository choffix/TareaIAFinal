package grupo9.m3as;

import grupo9.Problema.Problema;
import grupo9.conjuntosPareto.ConjuntoPareto;
import grupo9.Problema.Hormiga;
/**
 *
 * @author choffis
 */


public class M3AS {

    private Problema problema;
    private int cantidadHormigas;
    private ConjuntoPareto paretoSet;
    // Matriz de feromonas
    private TablaFeromonas tablaFeromonas; 
    // exponente para las feromonas
    private double alfa; 
    // exponente para la visibilidad
    private double beta;
    // coeficiente de evaporacion
    private double rho; 
    // valor inicial para las tablas de feromonas
    private double taoInicial; 
    protected final double NORMA_UNO = 100;
    protected final double NORMA_DOS = 100;
    protected final double MAX_FUN_1 = 100;
    protected final double MAX_FUN_2 = 100;
    private int hormigaObservada;
    public static final int MAX_GENERACIONES = 100;

    public M3AS(Problema prob) {
        inicializarParametros();

        problema = prob;
        paretoSet = new ConjuntoPareto(problema.getSize());
        tablaFeromonas = new TablaFeromonas(prob.getSize(), taoInicial);
    }
 
    private void inicializarParametros() {
        this.cantidadHormigas = 4;
        this.alfa = 1; // importancia relativa de las feromonas
        this.beta = 1; // importancia relativa de las feromonas
        this.rho = 0.1; // coeficiente de evaporacion
        this.taoInicial = 1; // valor inicial de la tabla de feromonas
    }

    public int seleccionarProbabilistico(int estOrigen, int[] visitados) {
        int sgteEstado = 0;
        double heuristica1, heuristica2;
        double[] productos = new double[problema.getSize()];
        double random;
        double suma = 0;
        double acum = 0;
        double lambda1, lambda2;
        int[] sinPorcion = new int[problema.getSize()];
        int cantSinPorcion = 0;

        lambda1 = hormigaObservada; 
        lambda2 = cantidadHormigas - hormigaObservada + 1; 

        random = (int) (Math.random() * 10) / 10;

        for (int i = 0; i < problema.getSize(); i++) {
            if (visitados[i] != 1) {
                heuristica1 = problema.heuriUno(estOrigen, i) * NORMA_UNO;
                heuristica2 = problema.heuriDos(estOrigen, i) * NORMA_DOS;
                productos[i] = Math.pow(tablaFeromonas.getValor(estOrigen, i), alfa) * Math.pow(heuristica1, lambda1 * beta) * Math.pow(heuristica2, lambda2 * beta);
                suma += productos[i];
                sinPorcion[cantSinPorcion++] = i;
            }
        }


        if (suma == 0) {
            
            random = Math.random() * 100 % cantSinPorcion;
            sgteEstado = sinPorcion[(int) random];
        } else {
            for (int i = 0; i < problema.getSize(); i++) {                
                if (visitados[i] != 1) {
                    acum += productos[i] / suma;
                    if (acum >= random) {
                        sgteEstado = i;
                        break;
                    }
                }
            }
        }

        return sgteEstado;
    }


    public void actualizarFeromonas(Hormiga individuo, int individuoSize,
            double deltaTau, double taumin, double taumax) {
        int j, k;
        double tjk;
        for (int i = 0; i < individuoSize - 1; i++) { // actualizar la tabla de
            // feromonas con el valor indicado por deltaTau
            j = individuo.get(i);
            k = individuo.get(i + 1);
            tjk = tablaFeromonas.getValor(j, k);
            if (tjk + deltaTau < taumin) {
                tablaFeromonas.actualizar(j, k, taumin);
            } else if (tjk + deltaTau > taumax) {
                tablaFeromonas.actualizar(j, k, taumax);
            } else {
                tablaFeromonas.actualizar(j, k, tjk + deltaTau);
            }
        }
    }

    public void evaporarFeromonas() {
        for (int i = 0; i < problema.getSize(); i++) {
            for (int j = 0; j < problema.getSize(); j++) {
                tablaFeromonas.actualizar(i, j, tablaFeromonas.getValor(i, j) * (1 - rho));
            }
        }
    }

    public double calcularDelta(Hormiga individuo) {
        double delta = 1.0 / (problema.funObjUno(individuo) / MAX_FUN_1 + problema.funObjDos(individuo) / MAX_FUN_2);
        return delta;
    }

    public void actualizarOrigenDestino(int origen, int destino) {
        double tau;
        tau = (1 - rho) * tablaFeromonas.getValor(origen, destino) + rho * taoInicial;
        tablaFeromonas.actualizar(origen, destino, tau);
    }

    public void actualizarPasoAPaso(int origen, int destino) {
        double tau;
        tau = (1 - rho) * tablaFeromonas.getValor(origen, destino) + rho * taoInicial;
        tablaFeromonas.actualizar(origen, destino, tau);
    }

    public int seleccionarSgteEstado(int estOrigen, Hormiga sol) {
        int sgteEstado;
        int[] visitados = new int[getProblema().getSize()];

        for (int i = 0; sol.get(i) != -1; i++) {
            visitados[sol.get(i)] = 1;
        }

        sgteEstado = seleccionarProbabilistico(estOrigen, visitados);

        return sgteEstado;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public int getHormigaActual() {
        return hormigaObservada;
    }

    public void setHormigaActual(int hormigaActual) {
        this.hormigaObservada = hormigaActual;
    }

    public int getHormigas() {
        return cantidadHormigas;
    }

    public void setHormigas(int hormigas) {
        this.cantidadHormigas = hormigas;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public double getRho() {
        return rho;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public TablaFeromonas getTablaFeromonas() {
        return tablaFeromonas;
    }

    public void setTablaFeromonas(TablaFeromonas tablaFeromonas) {
        this.tablaFeromonas = tablaFeromonas;
    }

    public double getTaoInicial() {
        return taoInicial;
    }

    public void setTaoInicial(double taoInicial) {
        this.taoInicial = taoInicial;
    }

    public int getHormigaDeTurno() {
        return hormigaObservada;
    }

    public void setHormigaDeTurno(int hormigaDeTurno) {
        this.hormigaObservada = hormigaDeTurno;
    }

    public ConjuntoPareto getParetoSet() {
        return paretoSet;
    }

    public void setParetoSet(ConjuntoPareto paretoSet) {
        this.paretoSet = paretoSet;
    }

    public int getCantidadHormigas() {
        return cantidadHormigas;
    }

    public void setCantidadHormigas(int cantidadHormigas) {
        this.cantidadHormigas = cantidadHormigas;
    }

    public int getHormigaObservada() {
        return hormigaObservada;
    }

    public void setHormigaObservada(int hormigaObservada) {
        this.hormigaObservada = hormigaObservada;
    }
}
