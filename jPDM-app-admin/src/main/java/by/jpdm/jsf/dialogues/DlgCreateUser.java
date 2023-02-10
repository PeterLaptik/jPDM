package by.jpdm.jsf.dialogues;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.model.dao.exceptions.JpdmModelException;
import by.jpdm.model.service.UserFactory;
import by.jpdm.test.qualifiers.TestViewMock;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_USER = "dlg/create-user";
    private static final String PARAM_ITEM_DEPARTMENT = "itemDepartment";
    private String name;
    private String login;
    private String password;
    private UUID departmentId;

    @Inject
    @TestViewMock
    private UserFactory userService;

    @Inject
    @TestViewMock
    private UserDAO userDao;

    public void createUserShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("350px").responsive(true)
                .build();

        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        departmentId = UUID.fromString(map.get(PARAM_ITEM_DEPARTMENT));
        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_USER, options, null);
    }

    public void create() {
        Exception error = null;
        try {
            User user = userService.createUser(login, name, password);
            user.setDepartmentId(departmentId);
            userDao.createUser(user);
            clearData();
        } catch (Exception e) {
            error = e;
            return;
        }
        PrimeFaces.current().dialog().closeDynamic(error);
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getItemDepartmentId() {
        return departmentId;
    }

    public void setItemDepartmentId(String itemDepartmentId) {
        this.departmentId = UUID.fromString(itemDepartmentId);
    }

    private void clearData() {
        name = "";
        login = "";
        password = "";
        departmentId = null;
    }
}
