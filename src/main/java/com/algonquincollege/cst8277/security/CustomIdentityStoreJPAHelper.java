/*****************************************************************c******************o*******v******id********
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 */
package com.algonquincollege.cst8277.security;

import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

/*
 * Stateless Session bean should also be a Singleton
 */
public class CustomIdentityStoreJPAHelper {
    
    public static final String CUSTOMER_PU = "20f-groupProject-PU";

    @PersistenceContext(name = CUSTOMER_PU)
    protected EntityManager em;

    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            //TODO
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
        return user;
    }

    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getRoles().stream().map(s -> s.getRoleName()).collect(Collectors.toSet());
        }
        return roleNames;
    }

    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        //TODO
    }

    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        //TODO
    }
}