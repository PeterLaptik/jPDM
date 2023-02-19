package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.model.service.UserFactory;
import by.jpdm.test.jsf.qualifiers.TestViewMock;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_USER = "dlg/create-user";
    private String name;
    private String login;
    private String password;

    @Inject
    @TestViewMock
    private UserFactory userService;

    @Inject
    @TestViewMock
    private UserDAO userDao;

    public void createUserShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("350px").responsive(true)
                .build();
        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_USER, options, null);
    }

    public void create() {
        User user = new User(login, name);
        user.setPassword(password);
        clearData();
        PrimeFaces.current().dialog().closeDynamic(user);
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

    private void clearData() {
        name = "";
        login = "";
        password = "";
    }
}
