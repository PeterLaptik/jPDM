package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.jsf.model.GroupManager;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.dao.GroupDAO;
import by.jpdm.test.qualifiers.TestViewMock;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String DLG_CREATE_GROUP = "dlg/create-group";
    private String name;
    private String description;

    @Inject
    @TestViewMock
    private GroupDAO groupDao;
    
    @Inject
    private GroupManager groupManager;

    public void createGroupShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("380px").responsive(true)
                .build();

        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_GROUP, options, null);
    }

    public void cancel() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void create() {
        try {
            Group group = new Group(name, description);
            groupDao.createGroup(group);
            clearData();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "test");
            FacesContext.getCurrentInstance().addMessage("sticky-key", message);
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("test"));
        } catch (Exception e) {
            e.printStackTrace();
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(e.getMessage()));
            return;
        }
        groupManager.processError(new Exception("test"));
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
