/*****************************************************************c******************o*******v******id********
 * File: OrderPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Maycon Morais - 040944820
 *             Pedro Rebello - 040960465
 *             Lillian Poon  - 040...
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.ProductPojo.ALL_PRODUCTS_QUERY_NAME;
import static com.algonquincollege.cst8277.models.ProductPojo.PRODUCT_BY_ID_QUERY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
*
* Description: model for the Product object
*/
@Entity(name = "Product")
@Table(name = "PRODUCT")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
@NamedQueries({
    @NamedQuery(name = ALL_PRODUCTS_QUERY_NAME, query = "select c from Product c"),
    @NamedQuery(name = PRODUCT_BY_ID_QUERY, query = "select c from Product c where c.id = :id")
})
public class ProductPojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ALL_PRODUCTS_QUERY_NAME = "allCustomers";
    public static final String PRODUCT_BY_ID_QUERY = "customersById";
    
    protected String description;
    protected String serialNo;
    protected Set<StorePojo> stores = new HashSet<>();

    // JPA requires each @Entity class have a default constructor
    public ProductPojo() {
    }
    
    /**
     * @return the value for firstName
     */
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }
    /**
     * @param description new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SERIALNUMBER")
    public String getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    
    @JsonInclude(Include.NON_NULL)
    @ManyToMany(mappedBy = "products")
    public Set<StorePojo> getStores() {
        return stores;
    }
    public void setStores(Set<StorePojo> stores) {
        this.stores = stores;
    }

}