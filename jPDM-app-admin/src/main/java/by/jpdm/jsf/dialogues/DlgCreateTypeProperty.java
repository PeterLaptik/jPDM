package by.jpdm.jsf.dialogues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class DlgCreateTypeProperty implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DLG_CREATE_TYPE_PROPERTY = "dlg/create-type-property";
    private String name;
    private String type;
    private boolean masterProperty;
    private boolean arrayProperty;

    @Inject
    ModelDriver drv;

    public void createTypePropertyShow() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .modal(true)
                .width("400px").responsive(true)
                .build();
        PrimeFaces.current().dialog().openDynamic(DLG_CREATE_TYPE_PROPERTY, options, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTypeValues() {
        List<String> typeValues = new ArrayList<>();
        Map<Integer, String> types = drv.getTypesMap();
        for (Map.Entry<Integer, String> me : types.entrySet()) {
            typeValues.add(me.getValue());
        }
        return typeValues;
    }

    public void create() {
        PrimeFaces.current().dialog().closeDynamic(name);
        clearData();
    }

    public void cancel() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void clearData() {
        name = "";
        type = null;
        masterProperty = false;
        arrayProperty = false;
    }

    public boolean isMasterProperty() {
        return masterProperty;
    }

    public void setMasterProperty(boolean masterProperty) {
        this.masterProperty = masterProperty;
    }

    public boolean isArrayProperty() {
        return arrayProperty;
    }

    public void setArrayProperty(boolean arrayProperty) {
        this.arrayProperty = arrayProperty;
    }
}
