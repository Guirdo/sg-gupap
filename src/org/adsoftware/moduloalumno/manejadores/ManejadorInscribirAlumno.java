package org.adsoftware.moduloalumno.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.moduloalumno.interfaces.VInscribirAlumno;
import org.adsoftware.superclases.Manejador;



public class ManejadorInscribirAlumno extends Manejador implements ActionListener{
    
    public JPanel panelPrincipal;
    public VInscribirAlumno pnlInscribir;
    private DefaultTableModel modelo;
    private Grupo grupo;
    private Horario horario;
    private ArrayList<Grupo> listaGrupos;
    private int grupoSeleccionado=0;

    private String mensajeError = "";
    
    public ManejadorInscribirAlumno(JPanel panelPrin) throws SQLException {
        super(panelPrin);
        this.panelPrincipal = panelPrin;

        modelo = new DefaultTableModel(new Object[]{"Clave", "Curso", "Horario"}, 0);
        pnlInscribir = new VInscribirAlumno();
        pnlInscribir.registrar.addActionListener(this);
        
        consultarGrupos();
        
        repintarPanelPrincipal(pnlInscribir);
    }
    
    private void consultarGrupos() throws SQLException {
        listaGrupos = Grupo.todos();
        for (Grupo g : listaGrupos) {
            Horario h = Horario.buscarPrimero("idHorario", g.idHorarioG);
            modelo.addRow(new Object[]{g.idGrupo, g.curso, darHorario(h)});
        }
        pnlInscribir.tabla.setModel(modelo);
    }
    private String darHorario(Horario horario) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String horarioString = "";

        horarioString += horario.lunes ? "L " : "";
        horarioString += horario.martes ? "M " : "";
        horarioString += horario.miercoles ? "Mi " : "";
        horarioString += horario.jueves ? "J " : "";
        horarioString += horario.viernes ? "V " : "";
        horarioString += horario.sabado ? "S " : "";
        horarioString += horario.domingo ? "D " : "";

        horarioString += df.format(horario.horaInicial) + " - " + df.format(horario.horaFinal);

        return horarioString;
    }
    
    //Validaciones
    private boolean verificarNombre() {
        String nombre = pnlInscribir.tfNombreA.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(nombre);

        if (!coincidor.matches()) {
            //pnlAlta.tfNombreP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Nombre(s) incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarApellidoPat() {
        String apellidoP = pnlInscribir.tfApellidoPatA.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoP);

        if (!coincidor.matches()) {
            //pnlAlta.tfApellidoPatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido Paterno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarApellidoMat() {
        String apellidoM = pnlInscribir.tfApellidoMatA.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoM);

        if (!coincidor.matches()) {
            //pnlAlta.tfApellidoMatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido Materno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarFecha() {
        if (pnlInscribir.fechaN.getDate() == null) {
            mensajeError += "\n- Fecha nula.";
            return false;
        } else {
            Calendar fechaActual = new GregorianCalendar();
            Calendar fechaNacimiento = new GregorianCalendar();
            fechaNacimiento.setTime(pnlInscribir.fechaN.getDate());

            int anoNacimiento = fechaNacimiento.get(Calendar.YEAR);
            int anoActual = fechaActual.get(Calendar.YEAR);

            if (anoActual - anoNacimiento >= 6) {
                if (fechaNacimiento.get(Calendar.MONTH) <= fechaActual.get(Calendar.MONTH)) {
                    return true;
                } else {
                    mensajeError += "\n- Fecha incorrecta: el alumno debe ser mayor a 6 años de edad.";
                    return false;
                }
            } else {
                mensajeError += "\n- Fecha incorrecta: el alumno debe ser mayor a 6 años de edad";
                return false;
            }
        }
    }//vf
   
    private boolean verificarTelefono() {
        String telefono = pnlInscribir.tfTelefono.getText();
        Pattern patron = Pattern.compile("[1-9]{3}[0-9]{7}");
        Matcher coincidor = patron.matcher(telefono);

        if (!coincidor.matches()) {
            mensajeError += "\n- Número telefónico incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarDomicilio() {
        mensajeError += pnlInscribir.tfDomicilioA.isEmpty() ? "\n- Domicilio nulo." : "";
        return !pnlInscribir.tfDomicilioA.isEmpty();
    }
    
    private boolean verificarCurso(){
        
        if(pnlInscribir.tabla.getSelectedRow()<0){
            mensajeError += "\n- Selecciona un curso.";
            return false;
        }else
        return pnlInscribir.tabla.getSelectedRow()>=0;
    }
    
    private boolean verificarCampos() {
        boolean nombreCorrecto = verificarNombre();
        boolean apellidoPatCorrecto = verificarApellidoPat();
        boolean apellidoMatCorrecto = verificarApellidoMat();
        boolean fechCorrecta = verificarFecha();
        boolean domicilioCorrecto = verificarDomicilio();
        boolean telefonoCorrecto = verificarTelefono();
        boolean cursoCorrecto = verificarCurso();

        return nombreCorrecto && apellidoPatCorrecto && apellidoMatCorrecto && domicilioCorrecto && telefonoCorrecto && fechCorrecta && cursoCorrecto;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {     
        if(e.getSource()== pnlInscribir.registrar){
            try {
                manejaEventoInscribir();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorInscribirAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void manejaEventoInscribir() throws SQLException{
        mensajeError="";
        NotificationManager.hideAllNotifications();
        if (verificarCampos()) {
            String genero = "";
            //Género
            if (pnlInscribir.generoF.isSelected()) {
                genero = Alumno.FEMENINO;
            } else {
                genero = Alumno.MASCULINO;
            }

            java.sql.Date fecha = new java.sql.Date(pnlInscribir.fechaN.getDate().getTime());    

            Alumno nuevoA = new Alumno (pnlInscribir.tfNombreA.getText(), pnlInscribir.tfApellidoPatA.getText(), pnlInscribir.tfApellidoMatA.getText(),
                    fecha, genero, pnlInscribir.tfDomicilioA.getText(), pnlInscribir.tfTelefono.getText(), grupoA());
            nuevoA.guardar();
            NotificationManager.showNotification(pnlInscribir.registrar,
                    "Alumno registrado con éxito", NotificationIcon.plus.getIcon());
            new ManejadorVisualizarAlumno(panelPrincipal);
        } else {
            NotificationManager.showNotification(pnlInscribir.registrar,
                    "Error al ingresar un dato: " + mensajeError, NotificationIcon.warning.getIcon());
        }
    }
    
    private int grupoA(){
        int cursoA = (int) pnlInscribir.tabla.getValueAt(pnlInscribir.tabla.getSelectedRow(), 0);
        return cursoA;
    }
}