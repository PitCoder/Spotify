package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Dato implements Serializable{
    HashMap<String, String> columnas;

    public Dato() {
        this.columnas = new HashMap<>();
    }
    
    public void agregarColumna(String atributo, String valor){
        this.columnas.putIfAbsent(atributo, valor);
    }
    
    public void quitarColumna(String atributo){
        this.columnas.remove(atributo);
    }
    
    public String obtenerValor(String atributo){
        return this.columnas.get(atributo);
    }
    
    public Set<String> obtenerColumnas(){
        return this.columnas.keySet();
    }
    
    public boolean verificarColumna(String atributo, String valor){
        return this.columnas.containsKey(valor);
    }
}
