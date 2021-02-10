/**
 * File: OrderPojo.java
 * Course materials (20F) CST 8277
 *
  * @author (original) Mike Norman
 * 
 * update by : Maycon Morais
 *             Pedro Rebello
 *             Lillian Poon
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.ProductPojo.ALL_PRODUCTS_QUERY_NAME;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
@NamedQuery(name = ALL_PRODUCTS_QUERY_NAME, query = "select p from Product p")
public class ProductPojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ALL_PRODUCTS_QUERY_NAME = "allProducts";
    
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
    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST)
    public Set<StorePojo> getStores() {
        return stores;
    }
    public void setStores(Set<StorePojo> stores) {
        this.stores = stores;
    }

}