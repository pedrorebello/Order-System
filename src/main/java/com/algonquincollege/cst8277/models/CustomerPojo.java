/**
 * File: CustomerPojo.java
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

import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
*
* Description: model for the Customer object
*/
@Entity(name = "Customer")
@Table(name = "CUSTOMER")
@NamedQueries(
    @NamedQuery(name=ALL_CUSTOMERS_QUERY_NAME, query = "select c from Customer c")
)
public class CustomerPojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ALL_CUSTOMERS_QUERY_NAME = "allCustomers";

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected AddressPojo shippingAddress;
    protected AddressPojo billingAddress;
    protected List<OrderPojo> orders;
	
    // JPA requires each @Entity class have a default constructor
	public CustomerPojo() {
	}
	
    /**
     * @return the value for firstName
     */
    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */
    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PHONENUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "SHIPPING_ADDR")
    public AddressPojo getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(AddressPojo shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "BILLING_ADDR")
    public AddressPojo getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(AddressPojo billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    @JsonManagedReference
    @OneToMany(mappedBy = "owningCustomer", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public List<OrderPojo> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderPojo> orders) {
        this.orders = orders;
    }
    public void addOrder(OrderPojo order) {
        getOrders().add(order);
        order.setOwningCustomer(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Customer [id=")
            .append(id)
            .append(", ");
        if (firstName != null) {
            builder
                .append("firstName=")
                .append(firstName)
                .append(", ");
        }
        if (lastName != null) {
            builder
                .append("lastName=")
                .append(lastName)
                .append(", ");
        }
        if (phoneNumber != null) {
            builder
                .append("phoneNumber=")
                .append(phoneNumber)
                .append(", ");
        }
        if (email != null) {
            builder
                .append("email=")
                .append(email)
                .append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

}