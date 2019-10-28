package org.adsoftware.modulogrupo.manejadores;

import com.alee.laf.checkbox.WebCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulogrupo.interfaces.VRegistroGrupo;
import org.adsoftware.superclases.Manejador;

public class ManejadorRegistrarGrupo extends Manejador implements ActionListener {

    private VRegistroGrupo vistaRegistrar;
    
    private ArrayList<Personal> listaMaestros;
    private DefaultTableModel modelo;
    private Grupo grupoNuevo;
    private int maestroSeleccionado;
    private int conDias = 0;

    public ManejadorRegistrarGrupo(JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        vistaRegistrar = new VRegistroGrupo();

        vistaRegistrar.btnRegistrar.addActionListener(this);
        for(WebCheckBox c : vistaRegistrar.chDiasSemana) c.addActionListener(this);
        
        consultarMaestros();

        repintarPanelPrincipal(vistaRegistrar);

    }
    
    private void consultarMaestros() throws SQLException {
        listaMaestros = Personal.buscar("cargo",Personal.cDOCENTE+"");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRegistrar.btnRegistrar) {
            manejaEventoRegistrarGrupo();
        } else {
            for (int i = 0; i < vistaRegistrar.chDiasSemana.length; i++) {
                if (e.getSource() == vistaRegistrar.chDiasSemana[i]) {
                    if (vistaRegistrar.chDiasSemana[i].isSelected()) {
                        conDias++;
                    } else {
                        if (conDias == 4) {
                            for (WebCheckBox cb : vistaRegistrar.chDiasSemana) {
                                cb.setEnabled(true);
                            }
                        }
                        conDias--;
                    }

                    if (conDias == 4) {
                        for (WebCheckBox cb : vistaRegistrar.chDiasSemana) {
                            if (!cb.isSelected()) {
                                cb.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void manejaEventoRegistrarGrupo() {
        
        

    }

}
