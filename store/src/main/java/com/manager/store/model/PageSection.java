package com.manager.store.model;

import com.google.common.collect.ImmutableList;
import com.manager.store.entities.enums.RoleType;

import java.util.List;

import static com.manager.store.entities.enums.RoleType.*;

/**
 * A page section headings.
 *
 * @author Piotr
 */
public enum PageSection {

    SHOP("SHOP", "/shops", buildRolesList(ROLE_ADMIN)),
    WORKERS("WORKERS", "/users", buildRolesList(ROLE_ADMIN, ROLE_MANAGER)),
    PRODUCTS("PRODUCTS", "/products", buildRolesList(ROLE_ADMIN)),
    TRANSACTIONS("TRANSACTIONS", "/transactions", buildRolesList(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)),
    DELIVERY("DELIVERY", "/delivery", buildRolesList(ROLE_ADMIN, ROLE_MANAGER)),
    ANALYSIS("ANALYSIS", "/analysis", buildRolesList(ROLE_ADMIN, ROLE_MANAGER)),
    //SIMULATION("SIMULATION", "/simulations", buildRolesList(ROLE_ADMIN, ROLE_MANAGER)),
    WRITE_MAIL("WRITE MAIL", "/messages", buildRolesList(ROLE_ADMIN, ROLE_MANAGER)),
    CHANGE_PASSWORD("CHANGE PASSWORD", "/changePassword", buildRolesList(RoleType.values())),
    LOGOUT("LOG OUT", "/logout", buildRolesList(RoleType.values()));

    private final String name;
    private final String link;
    private final List<RoleType> roles;

    PageSection(String name, String link, List<RoleType> roles) {
        this.name = name;
        this.link = link;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public List<RoleType> getRoles() {
        return roles;
    }

    private static List<RoleType> buildRolesList(RoleType... roles) {
        return new ImmutableList.Builder<RoleType>()
                .add(roles)
                .build();
    }

}
