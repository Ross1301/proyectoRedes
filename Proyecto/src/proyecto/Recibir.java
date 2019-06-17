/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ruben
 */
public class Recibir implements Runnable 
{
    private Comunicacion responder;
    private int puerto;
    
    public Recibir(Comunicacion r, int p)
    {
        responder = r;
        puerto = p;
    }
    
    @Override
    public void run() 
    {
        try 
        {
            //System.out.println("Escuchando a " + puerto);
            ServerSocket sk = new ServerSocket(puerto);
            while (true) 
            {
                String datos;
                try (Socket cliente = sk.accept()) 
                {
                    BufferedReader entrada = new BufferedReader
                        (new InputStreamReader(cliente.getInputStream()));
                    PrintWriter salida = new PrintWriter(new OutputStreamWriter
                        (cliente.getOutputStream()), true);
                    
                    datos = entrada.readLine();
                    
                    responder.recibido(datos);
                    
                    salida.println(datos);
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
   }
}
