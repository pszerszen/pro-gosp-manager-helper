package com.manager.store.service.impl;

import com.google.common.collect.ImmutableList;
import com.manager.store.dao.UserDAO;
import com.manager.store.entities.User;
import com.manager.store.mapper.Mapper;
import com.manager.store.mapper.UserMapper;
import com.manager.store.model.ContactModel;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.UserModel;
import com.manager.store.service.UserService;
import com.manager.store.validators.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.manager.store.validators.ValidationMessagesConstants.*;

/**
 * {@link UserService} implementation.
 *
 * @author Piotr
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractService<User, UserModel> implements UserService {

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getByMail(String mail) {
        return userDAO.getByMail(mail, false);
    }

    public List<ContactModel> getEmails(String term) {
        return userDAO.search(term, 10, null).stream()
                .map(userMapper::contactModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean setPasswordToUser(Long id, String password) {
        User user = userDAO.get(id);
        if (user == null) {
            return false;
        }
        String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);
        userDAO.merge(user);

        return true;
    }

    @Override
    protected UserDAO getDao() {
        return userDAO;
    }

    @Override
    protected Mapper<User, UserModel> getMapper() {
        return userMapper;
    }

    public List<ModelSummary> getWorkersSummaries(){
        return userDAO.getWorkers().stream()
                .map(userMapper::workerSummary)
                .collect(Collectors.toList());
    }

    @Override
    protected List<Validator> getValidators(final UserModel model) {
        return new ImmutableList.Builder<Validator>()
                .addAll(super.getValidators(model))
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if (!model.getFirstName().matches(ALPHANUMERIC_MIN_3_SIGN_REGEX)) {
                        errors.add(MESSAGE_INVALID_FIRSTNAME);
                    }
                    return errors;
                })
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if (!model.getLastName().matches(ALPHANUMERIC_MIN_3_SIGN_REGEX)) {
                        errors.add(MESSAGE_INVALID_LASTNAME);
                    }
                    return errors;
                })
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if (!model.getDateFrom().matches(DATE_REGEX)) {
                        errors.add(MESSAGE_INVALID_DATE);
                    }
                    return errors;
                })
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if (!model.getDateUntil().matches(DATE_REGEX) && StringUtils.isNotBlank(model.getDateUntil())) {
                        errors.add(MESSAGE_INVALID_DATE);
                    }
                    return errors;
                })
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if(!EmailValidator.getInstance().isValid(model.getMail())){
                        errors.add(MESSAGE_E_MAIL_FORMAT);
                    }
                    User user = userDAO.getByMail(model.getMail(), true);
                    if (!(user == null || Objects.equals(user.getId(), model.getId()))) {
                        errors.add(MESSAGE_E_MAIL_IN_USE);
                    }
                    return errors;
                })
                .build();

        /*
        validators.add(() -> {
            Set<String> errors = new HashSet<>();
            if (model.getPassword().matches(PASSWORD_REGEX)) {
                errors.add(MESSAGE_INVALID_PASSWORD);
            }
            return errors;
        });
*/
    }

}
