package by.jpdm.jsf.dialogues;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import jakarta.inject.Named;
import jpdm.db.modeller.tree.ModelDriver;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateType implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_TYPE = "dlg/create-type";
    private String name;
    
    @Inject
    ModelDriver drv;
    
    public void createTypeShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("340px").responsive(true)
                .build();
        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_TYPE, options, null);
    }

    public void create() {
        PrimeFaces.current().dialog().closeDynamic(name);
        clearData();
    }
    
    public void cancel() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void clearData() {
        name = "";
    }
}
