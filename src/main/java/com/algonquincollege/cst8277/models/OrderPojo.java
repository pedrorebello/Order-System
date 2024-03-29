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

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
*
* Description: model for the Order object
*/
@Entity(name = "Order")
@Table(name = "ORDER_TBL")
@AttributeOverride(name = "id", column = @Column(name = "ORDER_ID"))
public class OrderPojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String description;
    protected List<OrderLinePojo> orderlines;
    protected CustomerPojo owningCustomer;
    
    // JPA requires each @Entity class have a default constructor
	public OrderPojo() {
	}

	
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "owningOrder", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	public List<OrderLinePojo> getOrderlines() {
		return this.orderlines;
	}
	public void setOrderlines(List<OrderLinePojo> orderlines) {
		this.orderlines = orderlines;
	}
	public OrderLinePojo addOrderline(OrderLinePojo orderline) {
		getOrderlines().add(orderline);
		orderline.setOwningOrder(this);
		return orderline;
	}
	public OrderLinePojo removeOrderline(OrderLinePojo orderline) {
		getOrderlines().remove(orderline);
        orderline.setOwningOrder(null);
		return orderline;
	}

	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "OWNING_CUST_ID")
	public CustomerPojo getOwningCustomer() {
		return this.owningCustomer;
	}
	public void setOwningCustomer(CustomerPojo owner) {
		this.owningCustomer = owner;
	}

}