/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.ArrayList;

/**
 *
 * @author Ruben
 */
public class DatosRouter 
{
    private int nombre;
    private ArrayList<Integer> vecinos;
    private ArrayList<String> puertos; //192.168.0.x/30
    //Arreglos gemelos para saber cual es el vecino y a que puerto esta conectado
    private ArrayList<nodo> tabla;

    public DatosRouter(int n) 
    {
        nombre = n;
        this.puertos = new ArrayList<>();
        this.vecinos = new ArrayList<>();
        this.tabla = new ArrayList<>();
    }
    
    public int getNombre()
    {
        return nombre;
    }
    
    public void setVecino(int v, String p)
    {
        vecinos.add(v);
        puertos.add(p);
    }
    
    
    public ArrayList<Integer> getVecinos()
    {
        return vecinos;
    }
    
    public void setNodo(String d, String m, String p, int s, int a)
    {
        tabla.add(new nodo(d,m,p,s,a));
    }
    
    public String getDireccion (int i) 
    {  
        return this.tabla.get(i).direccion;
    }
    
    public String getMascara (int i) 
    {
        return this.tabla.get(i).mascara;
    }   
    
    public String getPuerto (int i) 
    {
        return this.tabla.get(i).puerto;
    }  
    
    public void setPuerto (int i, String s) 
    {
        nodo temp = tabla.get(i);
        temp.puerto = s;
        tabla.set(i, temp);
    }
    
    public int getSaltos (int i) 
    {
        return this.tabla.get(i).saltos;
    }  
    
    public void setSalto (int i, int s) 
    {
        nodo temp = tabla.get(i);
        temp.saltos = s;
        tabla.set(i, temp);
    }
    
    public int getAprendido (int i) 
    {
        return this.tabla.get(i).aprendido;
    }  
    
    public void setAprendido (int i, int s)
    {
        nodo temp = tabla.get(i);
        temp.aprendido = s;
        tabla.set(i, temp);
    }
    
    public String getPuertoVecino (int i)
    {
        return this.puertos.get(i);
    }
    
    public String getTabla()
    {
        String respuesta = "";
        
        for (int i = 0; i < tabla.size(); i++) 
        {
            nodo temp = tabla.get(i);
            respuesta += temp.direccion + "\t" + 
                    temp.mascara + "\t" + temp.puerto + "\t" +
                    temp.saltos + "\n";
                  
        }
        
        return respuesta;
    }
    
    public int getTablaNodoSize() {
       return tabla.size();
    }
    
    //Segun se necesite van a ocupar gets y sets
    public class nodo
    {
        public String direccion;
        public String mascara;
        public String puerto;
        public int saltos;
        public int aprendido; //Este indica por quien se aprendio una ruta
        
        public nodo(String d, String m, String p, int s, int a)
        {
            direccion = d;
            mascara = m;
            puerto = p;
            saltos = s;
            aprendido = a;
        }
    }
}
