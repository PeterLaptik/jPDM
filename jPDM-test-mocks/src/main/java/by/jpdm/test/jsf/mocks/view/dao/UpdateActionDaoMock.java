package by.jpdm.test.jsf.mocks.view.dao;

import java.util.ArrayList;
import java.util.List;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.beans.scheme.UpdateAction;
import by.jpdm.model.dao.scheme.UpdateActionDAO;

public class UpdateActionDaoMock implements UpdateActionDAO {
    private static List<UpdateAction> actions = new ArrayList<>();

    @Override
    public List<UpdateAction> getUpdateActionsForScheme(Scheme scheme) {
        List<UpdateAction> result = new ArrayList<>();
        for (UpdateAction action : actions) {
            if (action.getSchemeName().equals(scheme.getFullName())) {
                result.add(action);
            }
        }
        return result;
    }

    @Override
    public void appendAction(UpdateAction updateAction) {
        actions.add(updateAction);
    }

    @Override
    public int getNumberOfUpdates() {
        return actions.size();
    }
}
