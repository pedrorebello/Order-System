/*****************************************************************c******************o*******v******id********
 * File: ShippingAddressPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Lillian Poon 
 *             Mayconjohny Morais 
 *             Pedro Mar Rebello 040960465
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

public class ShippingAddressPojo extends AddressPojo implements Serializable  {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    // JPA requires each @Entity class have a default constructor
    public ShippingAddressPojo() {
    }

}