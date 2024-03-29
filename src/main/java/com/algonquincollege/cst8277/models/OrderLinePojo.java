/**
 * File: OrderLinePojo.java
 * Course materials (20F) CST 8277
 *
  * @author (original) Mike Norman
 * 
 * update by : Maycon Morais
 *             Pedro Rebello
 *             Lillian Poon
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
*
* Description: model for the OrderLine object
*/
@Entity(name = "OrderLine")
@Table(name = "ORDERLINE")
@Access(AccessType.PROPERTY)
public class OrderLinePojo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected OrderLinePk primaryKey;
    protected OrderPojo owningOrder;
    protected Double amount;
    protected Double price;
    protected ProductPojo product;

    // JPA requires each @Entity class have a default constructor
    public OrderLinePojo() {
    }

    @EmbeddedId
    public OrderLinePk getPk() {
        return primaryKey;
    }
    public void setPk(OrderLinePk primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "OWNING_ORDER_ID", referencedColumnName = "ORDER_ID")
    @MapsId("owningOrderId")
    public OrderPojo getOwningOrder() {
        return owningOrder;
    }
    public void setOwningOrder(OrderPojo owningOrder) {
        this.owningOrder = owningOrder;
    }

    @Column(name="AMOUNT")
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    public ProductPojo getProduct() {
        return product;
    }
    public void setProduct(ProductPojo product) {
        this.product = product;
    }

}