/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package grupo9.spea;

import grupo9.Problema.Problema;
import grupo9.conjuntosPareto.ConjuntoPareto;

/**
 *
 * @author choffis
 */
public class SPEA {
      private Problema problema;
      private ConjuntoPareto paretoSet;
      private int fitness;
      public static final int MAX_GENERACIONES = 100;

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public Problema getProblema() {
        return problema;
    }

    public ConjuntoPareto getParetoSet() {
        return paretoSet;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public void setParetoSet(ConjuntoPareto paretoSet) {
        this.paretoSet = paretoSet;
    }

    public void seleccionarNoDominadas(){
        
    }  

    public void combinarParetoSet(){
        
    }
    
    public void calcularFuerza(){
        
    }
    
    public void reducirParetoSet(){
        
    }
    public boolean compareDominancia(ConjuntoPareto A, ConjuntoPareto B){
        return A.getSize()>B.getSize();
    }
    
    public void determinarFitness(){
        
    }
    public void seleccion(){
        
    }
    
    public void crossover(){
        
    }
    
    public void mutacion(){
        
    }

}
