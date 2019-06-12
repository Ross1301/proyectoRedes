/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

public interface Comunicacion
{
    public void enviado(String respuesta);
    public void recibido(String respuesta);
    public void error(String respuesta);
}

