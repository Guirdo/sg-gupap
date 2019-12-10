package org.adsoftware.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.interfaces.VMenuPrincipal;
import org.adsoftware.interfaces.VMenuPrincipalAdministrador;
import org.adsoftware.interfaces.VMenuPrincipalCoordinador;
import org.adsoftware.interfaces.VMenuPrincipalDirector;
import org.adsoftware.interfaces.VMenuPrincipalRecepcionista;
import org.adsoftware.moduloalumno.manejadores.ManejadorGenerarDocumentos;
import org.adsoftware.moduloalumno.manejadores.ManejadorInscribirAlumno;
import org.adsoftware.moduloalumno.manejadores.ManejadorReinscribirAlumno_A;
import org.adsoftware.moduloalumno.manejadores.ManejadorVisualizarAlumno;
import org.adsoftware.modulogrupo.manejadores.ManejadorRegistrarGrupo;
import org.adsoftware.modulogrupo.manejadores.ManejadorVisualizarGrupos;
import org.adsoftware.modulopago.manejadores.ManejadorGestionPago;
import org.adsoftware.modulopersonal.manejadores.ManejadorAltaPersonal;
import org.adsoftware.modulopersonal.manejadores.ManejadorGenerarInforme;
import org.adsoftware.modulopersonal.manejadores.ManejadorLecturaInformes;
import org.adsoftware.modulopersonal.manejadores.ManejadorRegistroES;
import org.adsoftware.modulopersonal.manejadores.ManejadorVisualizarPersonal;
import org.adsoftware.modulousuario.manejadores.ManejadorModificarContrasena;
import org.adsoftware.modulousuario.manejadores.ManejadorValidarUsuario;

public class ManejadorPrincipal implements ActionListener {

    private VMenuPrincipal venMenu = null;
    private VMenuPrincipalAdministrador venAdmin = null;
    private VMenuPrincipalRecepcionista venRecep = null;
    private VMenuPrincipalCoordinador venCoor = null;
    private VMenuPrincipalDirector venDir = null;

    private int idUsuario;

    public ManejadorPrincipal() throws ClassNotFoundException, SQLException {
        new ManejadorValidarUsuario();
    }

    public ManejadorPrincipal(int idUsuario) {
        this.idUsuario = idUsuario;
        switch (idUsuario) {
            case Usuario.DIRECTOR:
                venDir = new VMenuPrincipalDirector();
                venDir.btnPersonalA.addActionListener(this);
                venDir.btnPersonalV.addActionListener(this);
                venDir.btnLeerInformes.addActionListener(this);
                venDir.btnVisualizarAlumnos.addActionListener(this);
                venDir.setVisible(true);
                break;
            case Usuario.COORDINADOR:
                venCoor = new VMenuPrincipalCoordinador();

                venCoor.btnGenerarInforme.addActionListener(this);
                venCoor.btnVisualizarGrupo.addActionListener(this);
                venCoor.btnVisualizarAlumnos.addActionListener(this);
                venCoor.btnRegistrarEva.addActionListener(this);
                venCoor.setVisible(true);
                break;
            case Usuario.ADMINISTRADOR:
                venAdmin = new VMenuPrincipalAdministrador();

                venAdmin.btnGestion.addActionListener(this);
                venAdmin.btnGenerarInforme.addActionListener(this);
                venAdmin.btnRegistroGrupo.addActionListener(this);
                venAdmin.btnVisualizarGrupo.addActionListener(this);
                venAdmin.btnInscribirAlumno.addActionListener(this);
                venAdmin.btnVisualizarAlumnos.addActionListener(this);
                venAdmin.btnReinscribir.addActionListener(this);
                venAdmin.btnGenerarDocumentos.addActionListener(this);
                venAdmin.btnPagos.addActionListener(this);
                venAdmin.setVisible(true);
                break;

            case Usuario.RECEPCIONISTA:
                venRecep = new VMenuPrincipalRecepcionista();

                venRecep.btnRegistroES.addActionListener(this);
                venRecep.btnRegistrarGrupo.addActionListener(this);
                venRecep.btnGestionGrupo.addActionListener(this);
                venRecep.btnRegistrarPago.addActionListener(this);

                venRecep.setVisible(true);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (venAdmin != null) {//Ventana del administrador
                if (e.getSource() == venAdmin.btnGestion) {
                    manejaEventoGestionUsuario();
                } else if (e.getSource() == venAdmin.btnGenerarInforme) {
                    manejaEventoGenerarInforme(venAdmin.pnlPrincipal);
                } else if (e.getSource() == venAdmin.btnRegistroGrupo) {
                    manejaEventoRegistroGrupo(venAdmin.pnlPrincipal);
                } else if (e.getSource() == venAdmin.btnVisualizarGrupo) {
                    manejaEventoVisualizarGrupos(false, venAdmin.pnlPrincipal);
                } else if (e.getSource() == venAdmin.btnInscribirAlumno) {
                    manejaEventoInscribirAlumno();
                } else if (e.getSource() == venAdmin.btnVisualizarAlumnos) {
                    manejaEventoVisualizarAlumnos(venAdmin.pnlPrincipal);
                } else if (e.getSource() == venAdmin.btnReinscribir) {
                    manejaEventoReinscribir();
                } else if (e.getSource() == venAdmin.btnGenerarDocumentos) {
                    manejaEventoGenerarDocumentos();
                } else if (e.getSource() == venAdmin.btnGenerarDocumentos) {
                    manejaEventoGenerarDocumentos();
                } else if (e.getSource() == venAdmin.btnPagos) {
                    manejaEventoRegistrarPago(venAdmin.pnlPrincipal);
                }
            } else if (venRecep != null) {//Ventana del recepcionista
                if (e.getSource() == venRecep.btnRegistroES) {
                    manejarEventoRegistroES();
                } else if (e.getSource() == venRecep.btnRegistrarGrupo) {
                    manejaEventoRegistroGrupo(venRecep.pnlPrincipal);
                } else if (e.getSource() == venRecep.btnGestionGrupo) {
                    manejaEventoVisualizarGrupos(false, venRecep.pnlPrincipal);
                } else if (e.getSource() == venRecep.btnRegistrarPago) {
                    manejaEventoRegistrarPago(venRecep.pnlPrincipal);
                }
            } else if (venCoor != null) {//Ventana del coordinador
                if (e.getSource() == venCoor.btnGenerarInforme) {
                    manejaEventoGenerarInforme(venCoor.pnlPrincipal);
                } else if (e.getSource() == venCoor.btnRegistrarEva) {
                    manejaEventoVisualizarGrupos(true, venCoor.pnlPrincipal);
                } else if (e.getSource() == venCoor.btnVisualizarAlumnos) {
                    manejaEventoVisualizarAlumnos(venCoor.pnlPrincipal);
                }
            } else if (venDir != null) {//Ventana del director
                if (e.getSource() == venDir.btnLeerInformes) {
                    manejaEventoLecturaInformes();
                } else if (e.getSource() == venDir.btnPersonalA) {
                    manejaEventoAltaPersonal();
                } else if (e.getSource() == venDir.btnPersonalV) {
                    manejaEventoVisualizarPersonal();
                } else if (e.getSource() == venDir.btnVisualizarAlumnos) {
                    manejaEventoVisualizarAlumnos(venDir.pnlPrincipal);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoGestionUsuario() throws SQLException {
        new ManejadorModificarContrasena(venAdmin.pnlPrincipal);
    }

    private void manejaEventoAltaPersonal() throws SQLException {
        new ManejadorAltaPersonal(venDir.pnlPrincipal);
    }

    private void manejaEventoVisualizarPersonal() throws SQLException {
        new ManejadorVisualizarPersonal(venDir.pnlPrincipal);
    }

    private void manejarEventoRegistroES() throws SQLException {
        new ManejadorRegistroES(venRecep.pnlPrincipal);
    }

    private void manejaEventoGenerarInforme(JPanel pnl) throws SQLException {
        new ManejadorGenerarInforme(idUsuario, pnl);
    }

    private void manejaEventoLecturaInformes() throws SQLException {
        new ManejadorLecturaInformes(venDir.pnlPrincipal);
    }

    private void manejaEventoRegistroGrupo(JPanel pnl) throws SQLException {
        new ManejadorRegistrarGrupo(pnl, Usuario.buscarPrimero("idUsuario", "" + idUsuario));
    }

    private void manejaEventoVisualizarGrupos(boolean esCoordinador, JPanel pnl) throws SQLException {
        new ManejadorVisualizarGrupos(esCoordinador, pnl, Usuario.buscarPrimero("idUsuario", idUsuario + ""));
    }

    private void manejaEventoInscribirAlumno() throws SQLException {
        new ManejadorInscribirAlumno(venAdmin.pnlPrincipal);
    }

    private void manejaEventoVisualizarAlumnos(JPanel pnl) throws SQLException {
        new ManejadorVisualizarAlumno(pnl);
    }

    private void manejaEventoReinscribir() throws SQLException {
        new ManejadorReinscribirAlumno_A(venAdmin.pnlPrincipal);
    }

    private void manejaEventoGenerarDocumentos() throws SQLException {
        new ManejadorGenerarDocumentos(venAdmin.pnlPrincipal);
    }

    private void manejaEventoRegistrarPago(JPanel pnl) throws SQLException {
        new ManejadorGestionPago(pnl);
    }
}
