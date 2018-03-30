package com.manager.store.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 6986594741521369571L;

    @Column(name = "mail", length = 30, nullable = false, unique = true)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_staff", nullable = true)
    private Staff details;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = { @JoinColumn(name = "id_user", nullable = false, updatable = false) },
    inverseJoinColumns = { @JoinColumn(name = "id_role", nullable = false, updatable = false) })
    private Set<Role> roles = new HashSet<>();

    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Staff getDetails() {
        return details;
    }

    public void setDetails(final Staff details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(mail)
                .append(password)
                .append(details)
                .append(roles)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(mail, other.mail)
                .append(details, other.details)
                .append(roles, other.roles)
                .isEquals();
    }

}
