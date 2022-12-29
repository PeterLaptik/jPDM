package controllers.jsf.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;
import org.springframework.stereotype.Component;

@Component
@RequestScoped
@ManagedBean
public class UserDialogues implements Serializable {
    private static final long serialVersionUID = -7564515641058266138L;

    public void createDepartment() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .modal(true)
                .width("330px")
                .responsive(true)
                .build();

        PrimeFaces.current().dialog().openDynamic("create-dep", options, null);
    }
    
    public void test() {
    	System.out.println("OK");
    }
}
