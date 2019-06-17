/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruben
 */
public class Router extends javax.swing.JFrame implements Runnable, Comunicacion
{

    /**
     * Creates new form Router
     */
    DatosRouter datos;
    
    
    
    Enviar env;
    Recibir rec;
    
    boolean vivo;
    
    public Router(DatosRouter dr) 
    {
        datos = dr;
    }
    
    //Cuando crea un hilo es el metodo que llama
    @Override
    public void run() 
    {
        this.setVisible(true);
        initComponents();
        
        vivo = true;
        
        txtNumero.setText("" + datos.getNombre());
        env = new Enviar();
        
        //Hilo que escucha a sus vecinos
        rec = new Recibir(this, datos.getNombre());
        Thread hRec = new Thread(rec);
        hRec.start();
        
        taTabla.setText(datos.getTabla());
        
        pasoInfoVecinos();
    }

    @Override
    public void enviado(String respuesta)
    {
    }
    
    //Cuando se recibe un mensaje
    @Override
    public void recibido(String respuesta) 
    {
        //txtMensaje.setText(respuesta);
        
        if (vivo)
        {
            String[] temp = respuesta.split(";");

            if(temp[0].compareTo("0") == 0)
            {
                txtMensaje.setText("Router caido " + temp[1]);

                String puertoCaido = temp[1];

                int tamanoTabla = datos.getTablaNodoSize();
                for (int i = 0; i < tamanoTabla;  i++)   
                {
                    if (puertoCaido.compareTo(this.datos.getPuerto(i)) == 0) 
                    {
                        this.datos.setSalto(i, 16);
                    }  
                }
            }
            else
            {
                String direccion = temp[0];
                String mascara = temp[1];
                String puertoVecino = temp[2];
                String saltos = temp[3];
                String nombre = temp[4];

                int salt = Integer.parseInt(saltos) + 1;
                int name =Integer.parseInt(nombre);

                int tamanoTabla = datos.getTablaNodoSize();
                boolean encontrado = false;
                for (int i = 0; i < tamanoTabla;  i++)   
                {
                    if (direccion.compareTo(this.datos.getDireccion(i)) == 0) 
                    {
                        encontrado = true;

                        if ((salt) < this.datos.getSaltos(i)) 
                        {
                            this.datos.setSalto(i, salt);
                            this.datos.setPuerto(i, puertoVecino);
                            this.datos.setAprendido(i, name);
                        }
                    }  
                }

                if (encontrado == false) 
                {
                    datos.setNodo(direccion, mascara, puertoVecino, salt, name);
                }
            }
            taTabla.setText(datos.getTabla());
        }
    }

    @Override
    public void error(String respuesta) 
    {
    }
    
    
    //Metodo para enviar informacion a los vecinos automaticamente cada 10 segundos 
    public void pasoInfoVecinos() 
    {
        long tiempo = System.currentTimeMillis();
        
        while (vivo)
        {
            
            try 
            {
                TimeUnit.SECONDS.sleep(10);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Lista de vecinos
            ArrayList<Integer> vecinos = datos.getVecinos();
            int tamanoTabla = datos.getTablaNodoSize();

            //Ciclo para obtener los datos del nodo
            for (int i = 0; i < tamanoTabla;  i++) {    

                String direccion = this.datos.getDireccion(i);
                String mascara = this.datos.getMascara(i);
                String puerto = this.datos.getPuerto(i);
                int saltos = this.datos.getSaltos(i);
                int maestro = this.datos.getAprendido(i);

                //Ciclo para pasar mensaje a los vecinos
                for (int j = 0; j < vecinos.size(); j++) 
                {
                    String mensaje = direccion + ";" + mascara + ";";
                    mensaje += datos.getPuertoVecino(j) + ";" + saltos + ";" + datos.getNombre();
                    int vecino = vecinos.get(j);

                    if (!( vecino == maestro )) 
                    {
                        env.envMensaje(mensaje, vecino);
                    }

                }
            }
            
            if (datos.getNombre() == 3)
            {
                long actual = System.currentTimeMillis();
                
                if ((actual - tiempo) >= 60000)
                {
                    routerCaido();
                }
            }
        }
    }
    
    public void routerCaido()
    {
        vivo = false;
        taTabla.setText(" ");
        
        ArrayList<Integer> vecinos = datos.getVecinos();

        //Ciclo para pasar mensaje a los vecinos
        for (int j = 0; j < vecinos.size(); j++) 
        {
            String mensaje = "0;" + datos.getPuertoVecino(j);
            int vecino = vecinos.get(j);
            
            env.envMensaje(mensaje, vecino);

        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taTabla = new javax.swing.JTextArea();
        txtNumero = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Router Numero: ");

        taTabla.setEditable(false);
        taTabla.setColumns(20);
        taTabla.setRows(5);
        jScrollPane1.setViewportView(taTabla);

        txtNumero.setEditable(false);

        jLabel2.setText("Mensaje");

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEnviar)))
                        .addGap(0, 113, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEnviarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Router.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Router.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Router.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Router.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Router().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taTabla;
    private javax.swing.JTextField txtMensaje;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables

}
