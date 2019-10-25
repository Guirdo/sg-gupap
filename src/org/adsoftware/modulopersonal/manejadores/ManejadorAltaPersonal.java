
package org.adsoftware.modulopersonal.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import org.adsoftware.entidades.Personal;
import javax.swing.JPanel;
import org.adsoftware.modulopersonal.interfaces.VAltaPersonal;
import org.adsoftware.utilidades.Fecha;

public class ManejadorAltaPersonal implements ActionListener {
    public JPanel panelPrincipal;
    public VAltaPersonal pnlAlta;
    
    
    
    
     public ManejadorAltaPersonal(JPanel panelPrin) throws SQLException {
        this.panelPrincipal = panelPrin;

        pnlAlta = new VAltaPersonal();
        pnlAlta.registrar.addActionListener(this);
        
        repintarPanelPrincipal(pnlAlta);
    }
     private void repintarPanelPrincipal(JPanel panel) {
        panelPrincipal.removeAll();
        panelPrincipal.add(panel, "growx,growy,pushx,pushy");
        panelPrincipal.repaint();
    }
     
     
     private boolean verificarNombre(){
         String nombre = pnlAlta.tfNombreP.getText();
         Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
         Matcher coincidor = patron.matcher(nombre);
         
         if(!coincidor.matches()){
             pnlAlta.tfNombreP.setBorder(BorderFactory.createLineBorder(Color.red));
             return false;
         }
         return coincidor.matches();
     }
     
     private boolean verificarApellidoPat(){
         String apellidoP = pnlAlta.tfApellidoPatP.getText();
         Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
         Matcher coincidor = patron.matcher(apellidoP);
         
         if(!coincidor.matches()){
             pnlAlta.tfApellidoPatP.setBorder(BorderFactory.createLineBorder(Color.red));
             return false;
         }
         return coincidor.matches();
     }
     
     private boolean verificarApellidoMat(){
         String apellidoM = pnlAlta.tfApellidoMatP.getText();
         Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
         Matcher coincidor = patron.matcher(apellidoM);
         
         if(!coincidor.matches()){
             pnlAlta.tfApellidoMatP.setBorder(BorderFactory.createLineBorder(Color.red));
             return false;
         }
         return coincidor.matches();
     }
//     
//     private boolean verificarCorreo(){
//         String correo = pnlAlta.tfCorreoP.getText();
//         Pattern patron = Pattern.compile("^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{1,4})$");
//         Matcher coincidor = patron.matcher(correo);
//         
//         if(!coincidor.matches()){
//             pnlAlta.tfCorreoP.setBorder(BorderFactory.createLineBorder(Color.red));
//             return false;
//         }
//         return coincidor.matches();
//     }
     
     
     
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource()== pnlAlta.registrar){
            manejaEventoRegistrar();
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorAltaPersonal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void manejaEventoRegistrar() throws SQLException{
        
        if(verificarNombre() && verificarApellidoMat() && verificarApellidoPat() && !pnlAlta.tfDomicilioP.isEmpty() && !pnlAlta.tfCorreoP.isEmpty() && pnlAlta.fechaN.getDate()!=null){
            String genero ="", cargo="";
        //Género
        if(pnlAlta.generoF.isSelected())
            genero= Personal.FEMENINO;
        else
            genero = Personal.MASCULINO; 
        
        java.sql.Date fecha = new java.sql.Date(pnlAlta.fechaN.getDate().getTime());
                   
        Personal nuevoP = new Personal(pnlAlta.tfNombreP.getText(), pnlAlta.tfApellidoPatP.getText(), pnlAlta.tfApellidoMatP.getText(), 
                fecha, pnlAlta.tfDomicilioP.getText(), 
                pnlAlta.cmbCargo.getSelectedItem().toString(), pnlAlta.tfCorreoP.getText(), genero);
        nuevoP.guardar();
         NotificationManager.showNotification(pnlAlta.registrar,
                        "Personal registrado con éxito", NotificationIcon.warning.getIcon());
        }else{
            NotificationManager.showNotification(pnlAlta.registrar,
                        "Error al ingresar un dato", NotificationIcon.warning.getIcon());
        }  
        
    }
    
}
