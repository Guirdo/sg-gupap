package org.adsoftware.interfaces;

import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;

public class VMenuPrincipalCoordinador extends Pantalla{

    public JPanel pnlPrincipal;
    public WebButton btnGenerarInforme;
    public WebButton btnVisualizarGrupo, btnVisualizarAlumnos;
    private WebCollapsiblePane btnPersonal;
    private WebCollapsiblePane btnGrupo;
    private WebCollapsiblePane btnAlumno;
    public WebButton btnRegistrarEva;

    public VMenuPrincipalCoordinador() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));

        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        btnGenerarInforme = new WebButton(StyleId.button, "Generar informe");
        btnVisualizarGrupo = new WebButton(StyleId.button, "Visualizar grupos");
        btnVisualizarAlumnos = new WebButton(StyleId.button,"Gestión alumnos");

        btnRegistrarEva = new WebButton(StyleId.button, "Registrar evaluación");
        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :600:");

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));

        btnPersonal = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal", btnGenerarInforme);
        btnAlumno = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumno", panelAlumno());
        
        
        pnl.add(btnPersonal);
        pnl.add(btnAlumno);

        return pnl;
    }
    private JPanel panelAlumno(){
        JPanel pnl = new JPanel(new MigLayout("wrap","2[]2"));
        
        pnl.add(btnVisualizarAlumnos);
        pnl.add(btnRegistrarEva);
        
        return pnl;
    }

}
