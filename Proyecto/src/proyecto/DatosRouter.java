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
    
    public String getTabla()
    {
        String respuesta = "";
        
        for (int i = 0; i < tabla.size(); i++) 
        {
            nodo temp = tabla.get(i);
            respuesta += temp.direccion + "\t" + 
                    temp.mascara + "\t" + temp.puerto + "\t" +
                    temp.saltos;
        }
        
        return respuesta;
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
