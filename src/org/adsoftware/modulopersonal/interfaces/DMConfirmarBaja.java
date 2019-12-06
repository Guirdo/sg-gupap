package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextArea;
import net.miginfocom.swing.MigLayout;

public class DMConfirmarBaja extends JDialog {

    public JButton confirmar, cancelar;
    WebTextArea mensaje1;
    public WebPasswordField contra;

    public DMConfirmarBaja() {
        this.setModal(true);
        this.setLayout(new MigLayout("wrap 1", "30[]30", "30[]10[]10[]10[]10[]30"));
        WebLabel lblTitulo = new WebLabel(StyleId.label, "Confirmar baja");
        lblTitulo.setFont(new Font("Arial", 0, 20));
        mensaje1 = new WebTextArea(StyleId.textareaTransparent, "¿Está seguro de la acción a ejecutar?\n\nEste movimiento es irreversible.\n\nPara continuar y confirmar, debe ingresar su contraseña.");
        mensaje1.setEditable(false);
        mensaje1.setFont(new Font("Arial", 0, 16));

        contra = new WebPasswordField(StyleId.passwordfield, 20);
        contra.setInputPrompt("Contraseña");
        contra.setFont(new Font("Arial", 0, 18));

        confirmar = new JButton("Confirmar");
        cancelar = new JButton("Cancelar");

        this.add(lblTitulo, "north,gapleft 30");
        this.add(mensaje1, "");
        this.add(contra, "");
        this.add(confirmar, "split 2");
        this.add(cancelar, "");
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
