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
<<<<<<< HEAD
    public WebButton btnVisualizarGrupo, btnVisualizarAlumnos;
    private WebCollapsiblePane btnPersonal;
    private WebCollapsiblePane btnGrupo;
    private WebCollapsiblePane btnAlumno;
    public WebButton btnRegistrarEva;
    
=======
    public WebButton btnVisualizarGrupo,btnRegistrarEva;
    private WebCollapsiblePane btnPersonal;
    private WebCollapsiblePane btnGrupo,btnAlumno;

>>>>>>> 86245f337d87803e092260b284c9c16b0fbbf8c1
    public VMenuPrincipalCoordinador() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));

        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        btnGenerarInforme = new WebButton(StyleId.button, "Generar informe");
<<<<<<< HEAD
        btnVisualizarGrupo = new WebButton(StyleId.button, "Visualizar grupos");
        btnVisualizarAlumnos = new WebButton(StyleId.button,"Gestión alumnos");

        btnRegistrarEva = new WebButton(StyleId.button, "Registrar evaluación");
=======
        btnRegistrarEva = new WebButton(StyleId.button, "Registrar evaluación");

>>>>>>> 86245f337d87803e092260b284c9c16b0fbbf8c1
        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :600:");

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));

        btnPersonal = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal", btnGenerarInforme);
<<<<<<< HEAD
        btnAlumno = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumno", btnVisualizarAlumnos);
        
        pnl.add(btnPersonal);
        btnAlumno = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumno", btnRegistrarEva);
        
        pnl.add(btnPersonal);
=======
        btnGrupo = new WebCollapsiblePane(StyleId.collapsiblepane, "Grupo", btnVisualizarGrupo);
        btnAlumno = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumno", btnRegistrarEva);
        
        pnl.add(btnPersonal);
        //pnl.add(btnGrupo);
>>>>>>> 86245f337d87803e092260b284c9c16b0fbbf8c1
        pnl.add(btnAlumno);

        return pnl;
    }

}
