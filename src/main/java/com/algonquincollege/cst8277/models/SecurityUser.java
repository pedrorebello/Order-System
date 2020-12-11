/*****************************************************************c******************o*******v******id********
 * File: SecurityUser.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Maycon Morais - 040944820
 *             Pedro Rebello - 040960465
 *             Lillian Poon   - 040899245
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;
import static com.algonquincollege.cst8277.models.SecurityUser.USER_FOR_OWNING_CUST_QUERY;
import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algonquincollege.cst8277.rest.SecurityRoleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */
@Entity(name = "SecurityUser")
@Table(name = "SECURITY_USER")
@NamedQueries({
    @NamedQuery(name = USER_FOR_OWNING_CUST_QUERY,
                query = "SELECT u FROM SecurityUser u JOIN Customer c WHERE c.id = :id"), // TODO Verify that query!!!
    @NamedQuery(name = SECURITY_USER_BY_NAME_QUERY,
                query = " SELECT c FROM SecurityUser c WHERE c.username = :name"),
})
//maycon's version  NamedQuery(name = USER_FOR_OWNING_CUST_QUERY, query = "select c from SecurityUser c where c.CUST_ID = :id")
//maycon's version  NamedQuery(name = SECURITY_USER_BY_NAME_QUERY, query = "select c from SecurityUser c where c.USERNAME = :username")
public class SecurityUser implements Serializable, Principal {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    public static final String USER_FOR_OWNING_CUST_QUERY =
        "userForOwningCust";
    public static final String SECURITY_USER_BY_NAME_QUERY =
        "userByName";

    protected int id;
    protected String username;
    protected String pwHash;
    protected Set<SecurityRole> roles = new HashSet<>();
    protected CustomerPojo cust;

    public SecurityUser() {
        super();
    }

    @Id
    @Column(name = "USER_ID")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="USERNAME")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Column(name="PWHASH")
    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
    
    @JsonInclude(Include.NON_NULL)
    @JsonSerialize(using = SecurityRoleSerializer.class)
    @ManyToMany
    @JoinTable(
        name = "SECURITY_USER_SECURITY_ROLE",
        joinColumns = @JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
        inverseJoinColumns = @JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID")
    )
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    @OneToOne
    @JoinColumn(name = "CUST_ID")
    public CustomerPojo getCustomer() {
        return cust;
    }
    public void setCustomer(CustomerPojo cust) {
        this.cust = cust;
    }

    //Principal
    @JsonIgnore
    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityUser other = (SecurityUser)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}