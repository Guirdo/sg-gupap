package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.goodies.Credencial;
import org.adsoftware.goodies.Foto;

public class DMCredencial extends JDialog{
    
    public WebButton btnAgregar,btnGenerar,btnCancelar;
    public Credencial pnlCredencial;
    public Foto foto;
    public WebLabel lblNombre, lblCurso,lblHorario;
    
    public DMCredencial(){
        this.setModal(true);
        this.setTitle("Generar credencial");
        this.setLayout(new MigLayout("wrap 3","30[]100[]30[]30","30[]25[]30"));
        
        pnlCredencial = new Credencial(new MigLayout("wrap 3","10[]20[]70[]10","10[]10[]30[]10[]25[]10 []10"));
        foto = new Foto();
        lblNombre = new WebLabel("Guillermo Melendez Francisco");
        lblCurso = new WebLabel("InglesA1");
        lblHorario = new WebLabel("L M Mi J 15:00 - 16:00");
        btnAgregar = new WebButton("Agregar foto...");
        btnGenerar = new WebButton("Generar");
        btnCancelar = new WebButton("Cancelar");
        JPanel firma = new JPanel();
        JPanel firma1 = new JPanel();
        
        lblNombre.setFont(new Font("Arial",1,14));
        lblCurso.setFont(new Font("Arial",1,14));
        lblHorario.setFont(new Font("Arial",1,14));
        firma.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        firma1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pnlCredencial.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        foto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        pnlCredencial.add(foto,"w 150, h 183, span 1 4");
        pnlCredencial.add(new WebLabel("Nombre: "),"wrap");
        pnlCredencial.add(lblNombre,"span 2");
        pnlCredencial.add(new WebLabel("Curso: "));
        pnlCredencial.add(new WebLabel("Horario: "));
        pnlCredencial.add(lblCurso);
        pnlCredencial.add(lblHorario);
        pnlCredencial.add(firma,"w 150, h 35");
        pnlCredencial.add(new WebLabel(""));
        pnlCredencial.add(firma1,"w 150, h 35");
        pnlCredencial.add(new WebLabel("Firma estudiante"),"center");
        pnlCredencial.add(new WebLabel(""));
        pnlCredencial.add(new WebLabel("Firma Administrador"),"center");
        
        
        this.add(pnlCredencial,"w 515, h 285,span 3");
        this.add(btnAgregar);
        this.add(btnCancelar);
        this.add(btnGenerar);
        
        this.pack();
        this.setLocationRelativeTo(null);
        
    }

}
