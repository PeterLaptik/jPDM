package by.jpdm.model.dao.scheme;

import java.util.List;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.beans.scheme.UpdateAction;

public interface UpdateActionDAO {

    List<UpdateAction> getAllUpdateActions();
    
    List<UpdateAction> getUpdateActionsForScheme(Scheme scheme);
    
    void setUpdateActionsForScheme(List<UpdateAction> actions, Scheme scheme);
    
    void appendSingleAction(UpdateAction updateAction);
    
    int getNumberOfUpdates();
}
