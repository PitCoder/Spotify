package utils;

import java.io.Serializable;

public class Valor implements Serializable{
    private Clase clase;
    private String valor;
    private double entropia;

    public Valor(Clase clase, String valor) {
        this.clase = clase;
        this.valor = valor;
        this.entropia = 0;
    }
    
    public String obtenerNombre(){
        return this.valor;
    }
    
    public Clase obtenerClase(){
        return this.clase;
    }
    
    public void incrementaFrecuencia(String valor){
        this.clase.incrementaFrecuencia(valor);
    }
    
    public void reseteaFrecuencias(){
        this.clase.reseteaFrecuencias();
    }
    
    public int obtenerFrecuencia(String valor){
        return this.clase.obtenerFrecuencia(valor);
    }
    
    public int obtenerFrecuenciaTotal(){
        return this.clase.obtenerTotal();
    }
    
    public double obtenerEntropia(String valor1, String valor2){        
        int p = obtenerFrecuencia(valor1);
        int n = obtenerFrecuencia(valor2);
        System.out.println("Frecuencia de p: " + p);
        System.out.println("Frecuencia de n: " + n);
        this.entropia = calcularEntropia((double) p,(double) n);
        return this.entropia;
    }
    
    public double consultarEntropia(){
        return this.entropia;
    }
    
    private double calcularEntropia(double p, double n){
        double valueLogp = (p/(p+n)); 
        double valueLogn = (n/(p+n));
        System.out.println("Valor de logp: " + valueLogp);
        System.out.println("Valor de logn: " + valueLogn);
        if(valueLogp == 0 || valueLogn == 0)
            return 0;
        else
        return -valueLogp * (Math.log(valueLogp)/Math.log(2)) -valueLogn * (Math.log(valueLogn)/Math.log(2));
    }
}
