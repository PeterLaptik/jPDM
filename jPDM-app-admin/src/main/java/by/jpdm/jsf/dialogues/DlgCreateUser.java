package by.jpdm.jsf.dialogues;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgCreateUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String DLG_CREATE_USER = "create-user";
    private static String PARAM_ITEM_DEPARTMENT = "itemDepartment";
    private String name;
    private String login;
    private String password;
    private UUID itemDepartmentId;

    public void createUserShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("350px").responsive(true)
                .build();

        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        itemDepartmentId = UUID.fromString(map.get(PARAM_ITEM_DEPARTMENT));
        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_USER, options, null);
    }

    public void create() {
        System.out.println("Create:");
        PrimeFaces.current().dialog().closeDynamic(null);
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
        return itemDepartmentId;
    }

    public void setItemDepartmentId(String itemDepartmentId) {
        this.itemDepartmentId = UUID.fromString(itemDepartmentId);
    }
}
