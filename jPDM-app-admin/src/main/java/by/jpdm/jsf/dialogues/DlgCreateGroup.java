package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.Group;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_GROUP = "dlg/create-group";
    private String name;
    private String description;

    public void createGroupShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("380px").responsive(true)
                .build();

        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_GROUP, options, null);
    }

    public void create() {
        Group group = new Group(name, description);
        clearData();
        PrimeFaces.current().dialog().closeDynamic(group);
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
