package org.adsoftware.modulogrupo.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VVisualizarGrupos extends JPanel{
    
    public WebTable tabla;
    public WebLabel lblCurso,lblHorario,lblMaestro;
    public WebButton btnRegistrarEva,btnImprimir;

    public VVisualizarGrupos() {
        this.setLayout(new MigLayout("","",""));
    }
    
    

}
