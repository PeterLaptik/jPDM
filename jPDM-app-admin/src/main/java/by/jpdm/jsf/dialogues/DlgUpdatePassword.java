package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgUpdatePassword implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String DLG_UPDATE_PASSWORD = "dlg/update-pass";
    private String password;

    public void updatePasswordShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("350px").responsive(true)
                .build();
        PrimeFaces.current().dialog().openDynamic(DLG_UPDATE_PASSWORD, options, null);
    }

    public void update() {
        clearData();
        PrimeFaces.current().dialog().closeDynamic(password);
    }

    public void cancel() {
        clearData();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void clearData() {
        password = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
