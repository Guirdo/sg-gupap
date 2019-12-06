package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
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
    public WebCheckBox cbCertificado, cbDiploma;
    public WebButton btnGenerar;
    public JPanel pnlDatos;
    
    public VGenerarDocumentos(){
        this.setLayout(new MigLayout("wrap 3","30[]30","30[]25[]30"));
        
        WebLabel titulo = new WebLabel("Generar documentos");
        WebLabel subtitulo = new WebLabel("Generar documentos");
        tabla = new JTable();
        lblNombre = new WebLabel();
        lblNumero = new WebLabel();
        lblCurso = new WebLabel();
        lblHorario = new WebLabel();
        cbCertificado = new WebCheckBox("Certificado",new ImageIcon(getClass().getResource(Galeria.DOCUMENTO64_ICON)));
        cbDiploma = new WebCheckBox("Diploma",new ImageIcon(getClass().getResource(Galeria.DOCUMENTO64_ICON)));
        btnGenerar = new WebButton("Generar");
        
        titulo.setFont(new Font("Arial",1,20));
        subtitulo.setFont(new Font("Arial",1,18));
        
        pnlDatos = new JPanel(new MigLayout("wrap 3","0[]20[]20[]0","0[]25[]15[]15[]15[]20[]0"));
        pnlDatos.add(subtitulo,"span 3");
        pnlDatos.add(lblNombre,"");
        pnlDatos.add(new WebLabel("Seleccione el documento a generar:"),"span 2");
        pnlDatos.add(lblNumero,"");
        pnlDatos.add(cbCertificado,"span 1 2");
        pnlDatos.add(cbDiploma,"span 1 2");
        pnlDatos.add(lblCurso,"");
        pnlDatos.add(lblHorario,"wrap");
        pnlDatos.add(btnGenerar, "span 3, right");
        
        this.add(titulo, "north,leftgap 30");
        this.add(new JScrollPane(tabla),"h 200, w 400,span 3");
        this.add(pnlDatos,"growx,growy,span 3");
    }

}
