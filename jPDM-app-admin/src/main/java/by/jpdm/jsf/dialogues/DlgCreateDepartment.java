package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.Department;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_DEP = "dlg/create-dep";
    private String name;
    private String description;

    public void createDepartmentShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("380px").responsive(true)
                .build();

        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_DEP, options, null);
    }

    public void create() {
        Department dep = new Department(name, description);
        clearData();
        PrimeFaces.current().dialog().closeDynamic(dep);
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

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void clearData() {
        name = "";
        description = "";
    }
}
