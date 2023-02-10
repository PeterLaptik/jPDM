package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.dao.DepartmentDAO;
import by.jpdm.test.qualifiers.TestViewMock;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateDep implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_DEP = "dlg/create-dep";
    private static final String PARENT_ERROR_RECIEVER = "sticky-key";
    
    private String name;
    private String description;
    private Exception error;

    @Inject
    @TestViewMock
    private DepartmentDAO departmentDao;

    public void createDepartmentShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("380px").responsive(true)
                .build();

        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_DEP, options, null);
    }

    public void create() {
        try {
            Department dep = new Department(name, description);
            departmentDao.createDepartment(dep);
            clearData();
            error = new Exception("Test dep");
        } catch (Exception e) {
            error = e;
            return;
        }
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    /**
     * Post-process error message in a main view
     */
    public void handleReturn() {
        if(error==null)
            return;
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", error.getMessage());
        FacesContext.getCurrentInstance().addMessage(PARENT_ERROR_RECIEVER, message);
        error = null;
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
