package grupo9.m3as;

import grupo9.Problema.Hormiga;
import grupo9.Problema.Problema;
import grupo9.conjuntosPareto.ConjuntoPareto;
import grupo9.externos.ManejadorArchivo;
import java.io.IOException;

/**
 * 
 * @author choffis
 * 
 */
public class M3AS_QAP extends M3AS {

    public M3AS_QAP(Problema p) {
        super(p);
    }

    public ConjuntoPareto hacerQAPM3AS() {
        int estOrigen, generacion = 0;
        double deltaTao;
        double taumin, taumax;

        Hormiga hormiga = new Hormiga(getProblema().getSize());
        long tiempoInicial = System.currentTimeMillis();
        while (generacion < 200) {
            generacion++;
            for (int i = 0; i < getHormigas(); i++) {
                estOrigen = (int) ((Math.random() * 1000) % (getProblema().getSize()));
                setHormigaActual(i); 
                this.construirIndividuoQAP(estOrigen, this, 0, hormiga);
                if (getParetoSet().agregarNoDominado(hormiga, getProblema()) == 1) {
                    getParetoSet().eliminarDominados(hormiga, getProblema());
                }

                hormiga.resetear();
            }

            evaporarFeromonas();
            for (int i = 0; i < getParetoSet().getCantSoluciones(); i++) {
                deltaTao = calcularDelta(getParetoSet().getIndividuo(i));
                taumax = deltaTao / (1 - getRho());
                taumin = deltaTao / (2 * getHormigas() * (1 - getRho()));
                actualizarFeromonas(getParetoSet().getIndividuo(i),
                        getParetoSet().getIndividuo(
                        i).getLongitud(), deltaTao, taumin, taumax);
            }
        }
        return getParetoSet();
    }

    public void construirIndividuoQAP(int estOrigen, M3AS_QAP aco,
            int onlineUpdate, Hormiga sol) {
        int estVisitados = 0;
        int sgteEstado;
        int estActual = estOrigen;

        sol.set(estVisitados, estOrigen);
        estVisitados++;
        while (estVisitados < aco.getProblema().getSize()) {
            sgteEstado = aco.seleccionarSiguienteEstadoQAP(estActual, sol);
            if (onlineUpdate == 1) {
                aco.actualizarPasoAPaso(estActual, sgteEstado);
            }
            estActual = sgteEstado;
            sol.set(estVisitados, sgteEstado);
            estVisitados++;
        }
    }

    public int seleccionarSiguienteEstadoQAP(int estOrigen, Hormiga sol) {
        int sgte = seleccionarSgteEstado(estOrigen, sol);
        return sgte;
    }

    public void enviarSalida() throws IOException {
        ManejadorArchivo fh = new ManejadorArchivo();
        fh.enviarSalida(getParetoSet(), getProblema());
    }
}
