package com.manager.store.mapper;

import com.google.common.collect.Sets;
import com.manager.store.entities.Role;
import com.manager.store.entities.Staff;
import com.manager.store.entities.Store;
import com.manager.store.entities.User;
import com.manager.store.entities.enums.RoleType;
import com.manager.store.model.ContactModel;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.UserModel;
import com.manager.store.service.RoleService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.manager.store.utils.DateUtils.fromDate;
import static com.manager.store.utils.DateUtils.toDate;

/**
 * A mapper for User entity and model objects.
 *
 * @author Piotr
 */
@Component
public class UserMapper implements Mapper<User, UserModel> {

    @Value("${new.user.password}")
    private String defaultPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public User toEntity(UserModel model, User existingEntity, SessionFactory sessionFactory) {
        User ent = Optional.ofNullable(existingEntity).orElse(new User());

        ent.setId(model.getId());
        ent.setMail(model.getMail());
        ent.setActive(model.isActive());
        ent.setPassword(Optional.ofNullable(ent.getPassword()).orElse(passwordEncoder.encode(defaultPassword)));

        Set<Role> roles = null;
        switch (model.getType()) {
            case Worker:
                roles = Sets.newHashSet(makeRolesSets(RoleType.ROLE_USER));
                break;
            case Manager:
                roles = Sets.newHashSet(makeRolesSets(RoleType.ROLE_USER, RoleType.ROLE_MANAGER));
                break;
            case Admin:
                roles = Sets.newHashSet(makeRolesSets(RoleType.ROLE_USER, RoleType.ROLE_MANAGER, RoleType.ROLE_ADMIN));
                break;
        }
        ent.setRoles(roles);

        if (model.isActive()) {
            Staff staff = Optional.ofNullable(ent.getDetails()).orElse(new Staff());
            staff.setBonus(new BigDecimal(model.getBonus()).doubleValue());
            staff.setActive(model.isActive());
            staff.setDateFrom(toDate(model.getDateFrom()));
            staff.setDateUntil(toDate(model.getDateUntil()));
            staff.setFirstName(model.getFirstName());
            staff.setLastName(model.getLastName());
            staff.setSalary(new BigDecimal(model.getSalary()).movePointRight(2).intValue());

            Store store = (Store) sessionFactory.getCurrentSession().get(Store.class, model.getStore().getId());
            staff.setStore(store);
            ent.setDetails(staff);
        }

        return ent;
    }

    @Override
    public UserModel toModel(User entity, SessionFactory sessionFactory) {
        UserModel mod = new UserModel();

        mod.setId(entity.getId());
        mod.setMail(entity.getMail());
        mod.setActive(entity.isActive());

        Set<Role> roles = entity.getRoles();
        if (roles.contains(roleService.getByRoleName(RoleType.ROLE_ADMIN.toString()))) {
            mod.setType(UserModel.UserType.Admin);
        } else if (roles.contains(roleService.getByRoleName(RoleType.ROLE_MANAGER.toString()))) {
            mod.setType(UserModel.UserType.Manager);
        } else {
            mod.setType(UserModel.UserType.Worker);
        }

        Staff staff = entity.getDetails();
        if (staff != null) {
            mod.setWorkerId(staff.getId());

            final Double bonus = staff.getBonus();
            if (bonus != null) {
                mod.setBonus(new BigDecimal(bonus).toPlainString());
            }

            mod.setDateFrom(fromDate(staff.getDateFrom()));
            mod.setDateUntil(fromDate(staff.getDateUntil()));
            mod.setFirstName(staff.getFirstName());
            mod.setLastName(staff.getLastName());
            mod.setSalary(new BigDecimal(staff.getSalary()).movePointLeft(2).toString());

            ModelSummary storeSummary = new ModelSummary();
            final Store store = staff.getStore();
            storeSummary.setId(store.getId());
            storeSummary.setName(store.getName());
            storeSummary.setDescription(store.getAddress());

            mod.setStore(storeSummary);
        }

        return mod;
    }

    public ModelSummary workerSummary(final User entity) {
        return new ModelSummary(entity.getDetails().getId(),
                entity.getMail(),
                entity.getDetails().getFirstName() + " " + entity.getDetails().getLastName());
    }

    @Override
    public ModelSummary summaryFromEntity(final User entity) {
        return new ModelSummary(entity.getId(),
                entity.getMail(),
                entity.getDetails().getFirstName() + " " + entity.getDetails().getLastName());
    }

    @Override
    public ModelSummary summaryFromModel(final UserModel model) {
        return new ModelSummary(model.getId(),
                model.getMail(),
                model.getFirstName() + " " + model.getLastName());
    }

    public ContactModel contactModel(User user) {
        ContactModel model = new ContactModel(user.getMail(), "");

        Staff staff = user.getDetails();
        if (staff != null) {
            model.setName(staff.getFirstName() + " " + staff.getLastName());
        }
        return model;
    }

    private Set<Role> makeRolesSets(RoleType... types) {
        Set<Role> roles = new HashSet<>();
        for (RoleType type : types) {
            roles.add(roleService.getByRoleName(type.toString()));
        }
        return roles;
    }
}
