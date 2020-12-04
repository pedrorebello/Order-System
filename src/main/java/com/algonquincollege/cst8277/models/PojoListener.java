/*****************************************************************c******************o*******v******id********
 * File: PojoListener.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Maycon Morais - 040944820
 *             Pedro Rebello - 040960465
 *             Lillian Poon   - 040...
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PojoListener {
    @PrePersist
    public void setCreatedOnDate(PojoBase base) {
        LocalDateTime now = LocalDateTime.now();
        base.setCreatedDate(now);
        base.setUpdatedDate(now);
    }

    @PreUpdate
    public void setUpdatedDate(PojoBase base) {
        base.setUpdatedDate(LocalDateTime.now());
    }
}