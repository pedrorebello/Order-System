/**
 * File: CustomerService.java
 * Course materials (20F) CST 8277
 *
  * @author (original) Mike Norman
 * 
 * update by : Maycon Morais
 *             Pedro Rebello
 *             Lillian Poon
 *
 */
package com.algonquincollege.cst8277.ejb;

import static com.algonquincollege.cst8277.models.SecurityRole.ROLE_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.models.SecurityUser.USER_FOR_OWNING_CUST_QUERY;
import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;
import static com.algonquincollege.cst8277.models.StorePojo.ALL_STORES_QUERY_NAME;
import static com.algonquincollege.cst8277.models.ProductPojo.ALL_PRODUCTS_QUERY_NAME;

import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_SALTSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PU_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.algonquincollege.cst8277.models.StorePojo;

/**
 * Stateless Singleton Session Bean - CustomerService
 */
@Singleton
public class CustomerService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    
    public List<CustomerPojo> getAllCustomers() {
        return em.createNamedQuery(ALL_CUSTOMERS_QUERY_NAME, CustomerPojo.class).getResultList();
    }

    public CustomerPojo getCustomerById(int custPK) {
        return em.find(CustomerPojo.class, custPK);
    }
    
    @Transactional
    public CustomerPojo persistCustomer(CustomerPojo newCustomer) {
        em.persist(newCustomer);
        return newCustomer;
    }
    
    @Transactional
    public void buildUserForNewCustomer(CustomerPojo newCustomer) {
        SecurityUser userForNewCustomer = new SecurityUser();
        userForNewCustomer.setUsername(DEFAULT_USER_PREFIX + "_" + newCustomer.getFirstName() + "." + newCustomer.getLastName());
        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
        userForNewCustomer.setPwHash(pwHash);
        userForNewCustomer.setCustomer(newCustomer);
        SecurityRole userRole = em.createNamedQuery(ROLE_BY_NAME_QUERY,
            SecurityRole.class).setParameter(PARAM1, USER_ROLE).getSingleResult();
        userForNewCustomer.getRoles().add(userRole);
        userRole.getUsers().add(userForNewCustomer);
        em.persist(userForNewCustomer);
    }

    @Transactional
    public CustomerPojo setAddressFor(int custId, AddressPojo newAddress) {
        CustomerPojo updatedCustomer = em.find(CustomerPojo.class, custId);
        if (newAddress instanceof ShippingAddressPojo) {
            updatedCustomer.setShippingAddress(newAddress);
        }
        else {
            updatedCustomer.setBillingAddress(newAddress);
        }
        em.merge(updatedCustomer);
        return updatedCustomer;
    }

    @Transactional
    public CustomerPojo updateCustomerById(int id, CustomerPojo customerWithUpdates) {
        CustomerPojo customerToBeUpdated = getCustomerById(id);
        if (customerToBeUpdated != null) {
            em.refresh(customerToBeUpdated);
            em.merge(customerWithUpdates);
            em.flush();
        }
        return customerToBeUpdated;
    }

    @Transactional
    public void deleteCustomerById(int id) {
        CustomerPojo customer = getCustomerById(id);
        if (customer != null) {
            em.refresh(customer);
            TypedQuery<SecurityUser> findUser = em.createNamedQuery(USER_FOR_OWNING_CUST_QUERY, SecurityUser.class).setParameter(PARAM1, customer.getId());
            SecurityUser sUser = findUser.getSingleResult();
            em.remove(sUser);
            em.remove(customer);
        }
    }
    
    public List<ProductPojo> getAllProducts() {
        return em.createNamedQuery(ALL_PRODUCTS_QUERY_NAME, ProductPojo.class).getResultList();
    }

    public ProductPojo getProductById(int prodId) {
        return em.find(ProductPojo.class, prodId);
    }

    public List<StorePojo> getAllStores() {
        return em.createNamedQuery(ALL_STORES_QUERY_NAME, StorePojo.class).getResultList();
    }

    public StorePojo getStoreById(int storeId) {
        return em.find(StorePojo.class, storeId);
    }
    
    @Transactional
    public ProductPojo persistProduct(ProductPojo newProduct) {
        em.persist(newProduct);
        return newProduct;
    }

    @Transactional
    public ProductPojo updateProduct(int id, ProductPojo productWithUpdates) {
        ProductPojo productToBeUpdated = getProductById(id);
        if (productToBeUpdated != null) {
            em.refresh(productToBeUpdated);
            em.merge(productWithUpdates);
            em.flush();
        }
        return productToBeUpdated;
    }
    
    @Transactional
    public StorePojo persistStore(StorePojo newStore) {
        em.persist(newStore);
        return newStore;
    }
    
    @Transactional
    public StorePojo updateStore(int id, StorePojo storeWithUpdates) {
        StorePojo storeToBeUpdated = getStoreById(id);
        if (storeToBeUpdated != null) {
            em.refresh(storeToBeUpdated);
            em.merge(storeWithUpdates);
            em.flush();
        }
        return storeToBeUpdated;
    }
}