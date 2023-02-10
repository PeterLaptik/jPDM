package by.jpdm.jsf.dialogues;

import java.io.Serializable;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.model.service.UserService;
import by.jpdm.test.qualifiers.TestViewMock;
import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgUpdatePassword implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String DLG_UPDATE_PASSWORD = "dlg/update-pass";
    private static String FIELD_SELECTION = "user-form:id-user-list_selection";
    private UUID userId;
    private String password;

    @Inject
    @TestViewMock
    private UserDAO userDao;

    @Inject
    @TestViewMock
    private UserService userService;

    public void updatePasswordShow() {
        String strSelectionValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get(FIELD_SELECTION);

        try {
            updateUserData(strSelectionValue);
        } catch (Exception e) {
            processError(e);
            return;
        }

        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("350px").responsive(true)
                .build();
        PrimeFaces.current().dialog().openDynamic(DLG_UPDATE_PASSWORD, options, null);
    }

    public void update() {
        try {
            User user = userDao.getUserById(userId);
            User dummy = userService.createUser(user.getLogin(), user.getName(), password);
            user.setSalt(dummy.getSalt());
            user.setPassword(dummy.getPassword());
            userDao.updateUser(user);

        } catch (Exception e) {
            processError(e);
        } finally {
            clearData();
        }
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void cancel() {
        clearData();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void clearData() {
        userId = null;
        password = "";
    }

    private void processError(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        FacesContext.getCurrentInstance().addMessage("sticky-key", message);
    }

    private void updateUserData(String strValue) throws Exception {
        // uuid values:
        String[] val = strValue.split(",");

        if (val.length == 0)
            throw new Exception("No user selected!");

        if (val.length > 1)
            throw new Exception("Only one user updating is allowed!");

        userId = UUID.fromString(val[0]);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
