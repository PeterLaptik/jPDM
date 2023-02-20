package by.jpdm.jsf.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.service.GroupService;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.GroupDAO;

public class GroupServiceImpl implements GroupService {
    @Inject
    private GroupDAO groupDao;

    @Inject
    private ErrorProcessor errorProcessor;

    @Override
    public void createGroup(Group group) {
        try {
            groupDao.createGroup(group);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public void deleteGroup(Group group) {
        if (group == null)
            return;

        try {
            groupDao.deleteGroup(group);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public List<User> getGroupUsers(Group group) {
        List<User> result = new ArrayList<>();
        try {
            result = groupDao.getUsers(group);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
        return result;
    }

    @Override
    public void clearGroup(Group group) {
        try {
            groupDao.clearGroup(group);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public void appendUsers(Group group, List<User> users) {
        try {
            groupDao.addUsers(group, users);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }
    
    @Override
    public void removeUsers(Group group, List<User> users) {
        try {
            groupDao.removeUsers(group, users);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }
}
