package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Clase implements Serializable{
    private HashMap<String, Integer> valores;
    private String nombre;
    private Clase clase;
    
    public Clase(String nombre){
        this.nombre = nombre;
        this.valores = new HashMap<>();
    }
    
    public Clase(String nombre, HashMap<String, Integer> valores){
        this.nombre = nombre;
        this.valores = valores;
    }
    
    public Integer agregaValor(String valor){
        return valores.putIfAbsent(valor, 0);
    }
    
    public Integer eliminaValor(String valor){
        return valores.remove(valor);
    }
    
    public void incrementaFrecuencia(String valor){
        if(valores.containsKey(valor)){
            int frecuencia = valores.get(valor);
            valores.replace(valor, frecuencia, frecuencia + 1);
        }
    }
    
    public void reseteaFrecuencias(){
        for (String valor : valores.keySet()) {
            int frecuencia = valores.get(valor);
            valores.replace(valor, frecuencia, 0);
        }
    }
    
    public Integer obtenerFrecuencia(String valor){
        return this.valores.get(valor);
    }
    
    public Integer obtenerTotal(){
        Iterator iterador = valores.keySet().iterator();
        int total = 0;
        
        while(iterador.hasNext())
            total += valores.get((String)iterador.next());
      
        return total;
    }
    
    public Set<String> obtenerValores(){
        return valores.keySet();
    }
    
    public HashMap<String, Integer> obtenerTablaValores(){
        return this.valores;
    }
            
     public String obtenerNombre(){
         return this.nombre;
    }
     
    public String obtenerDominante(){
        String dominante = "";
        int max = 0;
        Iterator iterador = valores.keySet().iterator();
        
        while(iterador.hasNext()){
            String valor = (String)iterador.next(); 
            if(max <= valores.get(valor)){
                max = valores.get(valor);
                dominante = valor;
            }
        }
        return dominante;
    }
}
