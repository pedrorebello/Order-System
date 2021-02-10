/**
 * File: DriverTestForJPAErrors.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @date 2020 04
 *
 */
package com.algonquincollege.cst8277;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DriverTestForJPAErrors {

    public static final String PU_NAME = "test-for-jpa-errors-PU";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
        emf.close();
    }

}
