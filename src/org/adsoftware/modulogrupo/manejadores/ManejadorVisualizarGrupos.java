package org.adsoftware.modulogrupo.manejadores;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulogrupo.interfaces.VVisualizarGrupos;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Galeria;

public class ManejadorVisualizarGrupos extends Manejador implements ActionListener, ListSelectionListener {

    private VVisualizarGrupos vistaGrupos;

    private DefaultTableModel modelo;
    private Grupo grupo;
    private Personal maestro;
    private ArrayList<Grupo> listaGrupos;

    public ManejadorVisualizarGrupos(boolean esCoordinador, JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        this.vistaGrupos = new VVisualizarGrupos(esCoordinador);
        modelo = new DefaultTableModel(new Object[]{"Clave","Dias", "Curso", "Horario", "NumEstudiantes"}, 0);

        vistaGrupos.btnRegistrarEva.addActionListener(this);
        vistaGrupos.btnImprimir.addActionListener(this);
        vistaGrupos.tabla.getSelectionModel().addListSelectionListener(this);
        vistaGrupos.datos.setVisible(false);

        consultarGrupos();

        repintarPanelPrincipal(vistaGrupos);
    }

    private void consultarGrupos() throws SQLException {
        listaGrupos = Grupo.todos();

        for(Grupo g : listaGrupos)
            modelo.addRow(new Object[]{g.idGrupo,g.curso, g.horario, g.diasSemana, g.numEstudiantes});
        
        vistaGrupos.tabla.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaGrupos.btnRegistrarEva) {
            manejaEventoRegistrarGrupo();
        } else if (e.getSource() == vistaGrupos.btnImprimir) {
            try {
                manejaEventoImprimir();
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(ManejadorVisualizarGrupos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(ManejadorVisualizarGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void manejaEventoImprimir() throws FileNotFoundException, IOException {
        //Lineas fundamentales para generar un PDF
        PdfDocument pdf = new PdfDocument(new PdfWriter("archivos/listaAsistencia.pdf"));
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 30, 35, 30);

        Table pdfTable = new Table(new float[]{10, 30,
            5, 5, 5, 5, 5, 5, 5});
        pdfTable.setWidthPercent(100);

        pdfTable.addHeaderCell(new Cell().add("No."));
        pdfTable.addHeaderCell(new Cell().add("Nombre          "));

        for (int i = 0; i < 7; i++) {
            pdfTable.addHeaderCell(new Cell().add("--"));
        }

//        for (Object[] fila : listaGrupo) {
//            pdfTable.addCell((int) fila[0] + "");
//            pdfTable.addCell((String) fila[1] + " " + (String) fila[2] + " " + (String) fila[3]);
//            for (int i = 0; i < 7; i++) {
//                pdfTable.addCell("");
//            }
//        }
        for (int i = 0; i < 20; i++) {
            pdfTable.addCell(""+(i+1));
            pdfTable.addCell("\n");
            for (int j = 0; j < 7; j++) {
                pdfTable.addCell("\n");
            }
        }

        document.add(new Paragraph("Grupo: " + grupo.idGrupo));
        document.add(new Paragraph("Horario: " + grupo.horario));
        document.add(new Paragraph("Curso: " + grupo.curso));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Maestro asignado: " + maestro.nombreP+" "+maestro.apellidoPatP+" "+maestro.apellidoMatP));
        document.add(new Paragraph("\n"));
        document.add(pdfTable);

        document.close();

        Desktop.getDesktop().open(new File("archivos/listaAsistencia.pdf"));
    }

    private void manejaEventoRegistrarGrupo() {
        //TODO 
    }

    private void manejaEventoDatosCompletos(int id) throws SQLException {
        grupo = Grupo.buscarPrimero("idGrupo", "" + id);
        maestro = Personal.buscarPrimero("idPersonal", grupo.idPersonalG + "");

        vistaGrupos.lblIcono.setIcon(new ImageIcon(getClass().getResource(Galeria.GRUPO128_ICON)));
        vistaGrupos.lblCurso.setText(grupo.curso);
        vistaGrupos.lblHorario.setText(grupo.horario);
        vistaGrupos.lblMaestro.setText(maestro.nombreP + " " + maestro.apellidoPatP + " " + maestro.apellidoMatP);
        
        vistaGrupos.datos.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int idG = (int) modelo.getValueAt(vistaGrupos.tabla.getSelectedRow(), 0);
        try {
            manejaEventoDatosCompletos(idG);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
