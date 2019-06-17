/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author Ruben
 */
public class Enviar 
{
    public void envMensaje(String men, int port)
    {
        String host = "127.0.0.1";
        try (Socket socket = new Socket(host, port))
        {
            //System.out.println("Envia Men " + men + " Puerto " + port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println(men);
            
        }
        catch (IOException e)
        {}
    }
}
