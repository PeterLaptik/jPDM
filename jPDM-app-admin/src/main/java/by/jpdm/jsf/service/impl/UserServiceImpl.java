package by.jpdm.jsf.service.impl;

import java.util.UUID;

import javax.inject.Inject;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.service.UserService;
import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.model.service.UserFactory;

public class UserServiceImpl implements UserService {
    @Inject
    private UserDAO userDao;

    @Inject
    private UserFactory userFactory;

    @Inject
    private ErrorProcessor errorProcessor;

    @Override
    public void createUser(User user, Department department) {
        try {
            User userToCreate = userFactory.createUser(user.getLogin(), user.getName(), user.getPassword());
            userToCreate.setDepartmentId(department.getId());
            userDao.createUser(userToCreate);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            userDao.deleteUser(user);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public User getUserById(UUID id) {
        User result = null;
        try {
            result = userDao.getUserById(id);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
        return result;
    }

    @Override
    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        try {
            User userObj = userDao.getUserById(user.getId());
            User dummy = userFactory.createUser(user.getLogin(), user.getName(), user.getPassword());
            userObj.setSalt(dummy.getSalt());
            userObj.setPassword(dummy.getPassword());
            userDao.updateUser(userObj);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }
}
