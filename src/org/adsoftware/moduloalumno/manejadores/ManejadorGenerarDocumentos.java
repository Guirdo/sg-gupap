package org.adsoftware.moduloalumno.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.moduloalumno.interfaces.VGenerarDocumentos;
import org.adsoftware.superclases.Manejador;

public class ManejadorGenerarDocumentos extends Manejador implements ActionListener{
    
    private VGenerarDocumentos vista;
    
    private DefaultTableModel modelo;
    private ArrayList<Alumno> lista;
    
    public ManejadorGenerarDocumentos(JPanel pnlPrincipal) throws SQLException{
        super(pnlPrincipal);
        vista = new VGenerarDocumentos();
        modelo = new DefaultTableModel(new String[]{"No. Control","ApePat","ApeMat","Nombres"}, 0);
        
        vista.btnGenerar.addActionListener(this);
        
        consultarAlumnos();
        repintarPanelPrincipal(vista);
    }
    
    private void consultarAlumnos() throws SQLException {
//        lista = Alumno.todos();
//        
//        for(Alumno a : lista){
//            modelo.addRow(new Object[]{a.idAlumno,a.apellidoPatA,a.apellidoMatA,a.nombre});
//        }
        
        vista.tabla.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    

}
