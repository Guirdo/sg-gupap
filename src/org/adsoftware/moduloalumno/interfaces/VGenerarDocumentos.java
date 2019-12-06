package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.utilidades.Galeria;

public class VGenerarDocumentos extends JPanel{
    
    public JTable tabla;
    public WebLabel lblNombre, lblNumero, lblCurso, lblHorario;
    public WebToggleButton cbCertificado, cbDiploma;
    public WebButton btnGenerar;
    public JPanel pnlDatos;
    
    public VGenerarDocumentos(){
        this.setLayout(new MigLayout("wrap 3","30[]30","30[]25[]30"));
        
        WebLabel titulo = new WebLabel("Generar documentos");
        WebLabel subtitulo = new WebLabel("Datos del alumno");
        tabla = new JTable();
        lblNombre = new WebLabel("David Cuatzun Gomez");
        lblNumero = new WebLabel("7471727374");
        lblCurso = new WebLabel("Ingles B1");
        lblHorario = new WebLabel("L M Mi J 14:00 - 15:00");
        cbCertificado = new WebToggleButton(StyleId.togglebutton,"Certificado");
        cbDiploma = new WebToggleButton(StyleId.togglebutton,"Diploma");
        btnGenerar = new WebButton("Generar");
        
        titulo.setFont(new Font("Arial",1,20));
        subtitulo.setFont(new Font("Arial",1,18));
        cbCertificado.setIcon(new ImageIcon(getClass().getResource(Galeria.DOCUMENTO64_ICON)));
        cbDiploma.setIcon(new ImageIcon(getClass().getResource(Galeria.DOCUMENTO64_ICON)));
        
        pnlDatos = new JPanel(new MigLayout("wrap 3","0[]80[]20[]0","0[]25[]20[]20[]20[]25[]0"));
        pnlDatos.add(subtitulo,"span 3");
        pnlDatos.add(lblNombre,"");
        pnlDatos.add(new WebLabel("Seleccione el documento a generar:"),"span 2");
        pnlDatos.add(lblNumero,"");
        pnlDatos.add(cbCertificado,"span 1 2");
        pnlDatos.add(cbDiploma,"span 1 2");
        pnlDatos.add(lblCurso,"");
        pnlDatos.add(lblHorario,"wrap");
        pnlDatos.add(btnGenerar, "span 3, right,w 100");
        
        //pnlDatos.setVisible(false);
        
        this.add(titulo, "north,gapleft 30");
        this.add(new JScrollPane(tabla),"h 200, w 450,span 3");
        this.add(pnlDatos,"growx,growy,span 3");
    }

}
