package org.adsoftware.interfaces;

import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;

public class VMenuPrincipalAdministrador extends Pantalla {

    public JPanel pnlPrincipal;
    public WebButton btnGestion;
    public WebButton btnRegistroGrupo,btnVisualizarGrupo;
    public WebButton btnInscribirAlumno, btnVisualizarAlumnos, btnReinscribir;
    private WebCollapsiblePane btnUsuario;
    public WebButton btnGenerarInforme;
    private WebCollapsiblePane btnPersonal;
    private WebCollapsiblePane btnGrupo;
    private WebCollapsiblePane btnAlumno;

    public VMenuPrincipalAdministrador() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));

        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        btnGestion = new WebButton(StyleId.button,"Gestión de usuarios");
        btnGenerarInforme = new WebButton(StyleId.button,"Generar informe");
        btnRegistroGrupo = new WebButton(StyleId.button,"Registrar grupo");
        btnVisualizarGrupo = new WebButton(StyleId.button,"Visualizar grupos");
        btnInscribirAlumno = new WebButton(StyleId.button,"Inscribir alumno");
        btnVisualizarAlumnos = new WebButton(StyleId.button,"Visualizar alumnos");
        btnReinscribir = new WebButton(StyleId.button, "Reinscribir");

        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :600:");

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));

        btnUsuario = new WebCollapsiblePane(StyleId.collapsiblepane, "Usuarios",btnGestion);
        btnPersonal = new WebCollapsiblePane(StyleId.collapsiblepane,"Personal",btnGenerarInforme);
        btnGrupo = new WebCollapsiblePane(StyleId.collapsiblepane,"Grupo",panelGrupo());
        btnAlumno = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumno", panelAlumno());
        
        pnl.add(btnUsuario);
        pnl.add(btnPersonal);
        pnl.add(btnGrupo);
        pnl.add(btnAlumno);

        return pnl;
    }
    
    private JPanel panelGrupo(){
        JPanel pnl = new JPanel(new MigLayout("wrap","2[]2"));
        
        pnl.add(btnRegistroGrupo);
        pnl.add(btnVisualizarGrupo);
        
        return pnl;
    }
    
    private JPanel panelAlumno(){
        JPanel pnl = new JPanel(new MigLayout("wrap","2[]2"));
        
        pnl.add(btnInscribirAlumno);
        pnl.add(btnVisualizarAlumnos);
        pnl.add(btnReinscribir);
        
        return pnl;
    }
    
}
