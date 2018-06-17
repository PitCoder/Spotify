package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Atributo implements Serializable{
    private double entropia;
    private double ganancia;
    private double frecuencia;
    private String nombre;
    private HashMap<String, Valor> valores;
    
    public Atributo(String nombre){
        this.nombre = nombre;
        this.valores = new HashMap<>();
        this.entropia = 0;
        this.ganancia = 0;
    }
    
    public Atributo(String nombre, HashMap<String, Valor> valores){
        this.nombre = nombre;
        this.valores = valores;
        this.entropia = 0;
        this.ganancia = 0;
    }
    
    public Valor obtenerValor(String valor){
        return this.valores.get(valor);
    }
    
    public HashMap<String, Valor> obtenerValores(){
        return this.valores;
    }
    
    public String obtenerNombre(){
        return this.nombre;
    }
    
    public void calcularEntropia(String valor1, String valor2){
       Iterator iterador  = this.valores.keySet().iterator();
       Valor valor;
       int totalValor;
       double entropiaValor;
       this.entropia = 0;
       this.frecuencia = 0;
       
       while(iterador.hasNext()){
           valor = this.valores.get((String)iterador.next());
           System.out.println("Nombre valor: " + valor.obtenerNombre());
           entropiaValor = valor.obtenerEntropia(valor1, valor2);
           System.out.println("Entropia valor: " + entropiaValor);
           totalValor = valor.obtenerFrecuenciaTotal();
           this.frecuencia += totalValor;
           this.entropia += totalValor*entropiaValor;
           System.out.println("");
       }
       this.entropia /= this.frecuencia;
        System.out.println("Entropia Atributo: " + this.entropia);
        System.out.println("Frecuencia Atributo: " + this.frecuencia);
    }
    
    public double obtenerEntropia(){
        return this.entropia;
    }
    
    public void calcularGanancia(double informacion){
        this.ganancia = 0;
        this.ganancia = informacion - this.entropia;
        if(ganancia < 0)
        	this.ganancia = 0;
        System.out.println("Ganancia calculada: " + this.ganancia);
    }
    
    public double obtenerGanancia(){
        System.out.println("Ganancia obtenida: " + this.ganancia);
        return this.ganancia;
    }
    
    public void resetGanancia(){
        this.ganancia = 0;
    }
    
    public void resetEntropia(){
        this.entropia = 0;
    }
    
    public void reseteaFrecuencias(){
        for(String nomvalor: this.valores.keySet()){
            Valor valor = this.valores.get(nomvalor);
            valor.reseteaFrecuencias();
        }
    }
}
