/*****************************************************************c******************o*******v******id********
 * File: StorePojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Maycon Morais - 040944820
 *             Pedro Rebello - 040960465
 *             Lillian Poon  - 040899245
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static com.algonquincollege.cst8277.models.StorePojo.ALL_STORES_QUERY_NAME;
import static com.algonquincollege.cst8277.models.StorePojo.STORE_BY_ID_QUERY_NAME;

import com.algonquincollege.cst8277.rest.ProductSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
*
* Description: model for the Store object
*/
@Entity(name = "Store")
@Table(name = "STORE")
@AttributeOverride(name = "id", column = @Column(name = "STORE_ID"))
@NamedQueries({
    @NamedQuery(name = ALL_STORES_QUERY_NAME, query = "select s from Store s"),
    @NamedQuery(name = STORE_BY_ID_QUERY_NAME, query = "select s from Store s where s.id = :id")
})
public class StorePojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ALL_STORES_QUERY_NAME = "allStores";
    public static final String STORE_BY_ID_QUERY_NAME = "storeById";

    protected String storeName;
    protected Set<ProductPojo> products = new HashSet<>();

    // JPA requires each @Entity class have a default constructor
    public StorePojo() {
    }

    @Column(name="STORENAME")
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    @JsonSerialize(using = ProductSerializer.class)
    @ManyToMany
    @JoinTable(
        name = "STORES_PRODUCTS",
        joinColumns = @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID"),
        inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID"))
    public Set<ProductPojo> getProducts() {
        return products;
    }
    public void setProducts(Set<ProductPojo> products) {
        this.products = products;
    }

}
