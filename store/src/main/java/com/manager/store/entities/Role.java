package com.manager.store.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Role entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    private static final long serialVersionUID = 6475391804939860871L;

    @Column(name = "role_name", length = 20, nullable = false, unique = true)
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(roleName)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Role)) {
            return false;
        }
        Role other = (Role) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(roleName, other.roleName)
                .isEquals();
    }

}
