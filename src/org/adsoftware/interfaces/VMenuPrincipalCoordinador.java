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
    public WebButton btnVisualizarGrupo,btnRegistrarEva;
    private WebCollapsiblePane btnPersonal;
    private WebCollapsiblePane btnGrupo,btnAlumno;

    public VMenuPrincipalCoordinador() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));

        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        btnGenerarInforme = new WebButton(StyleId.button, "Generar informe");
        btnRegistrarEva = new WebButton(StyleId.button, "Registrar evaluación");

        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :600:");

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));

        btnPersonal = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal", btnGenerarInforme);
        btnGrupo = new WebCollapsiblePane(StyleId.collapsiblepane, "Grupo", btnVisualizarGrupo);
        btnAlumno = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumno", btnRegistrarEva);
        
        pnl.add(btnPersonal);
        //pnl.add(btnGrupo);
        pnl.add(btnAlumno);

        return pnl;
    }

}
