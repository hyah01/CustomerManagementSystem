package genspark.pj.CustomerManagementSystem.Services;

import genspark.pj.CustomerManagementSystem.Controller.CustomerController;
import genspark.pj.CustomerManagementSystem.Entity.Customer;
import genspark.pj.CustomerManagementSystem.Repository.CustomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    // Run query to get all customers
    @Override
    public List<Customer> getAllCustomers() {
        return this.customerDAO.findAll();
    }

    // Run query to get customer with id
    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> t = this.customerDAO.findById(id);
        Customer customer = null;
        // if customer exist, return it
            if (t.isPresent()) {
                customer = t.get();
            }
        return customer;
    }

    // Run query to get all customers with name
    @Override
    public List<Customer> getCustomerByName(String name) {
        return this.customerDAO.findByName(name);
    }

    // Run query to get all customers with email
    @Override
    public List<Customer> getCustomerByEmail(String email) {
        return this.customerDAO.findByEmail(email);
    }

    // Run query to get all customers with phone number
    @Override
    public List<Customer> getCustomerByPhone(String phonenumber) {
        return this.customerDAO.findByPhone(phonenumber);
    }

    // Run query to get all customers sorted by name
    @Override
    public List<Customer> getCustomersByNameSort() {
        return this.customerDAO.getCustomerNameSort();
    }

    // Run query to get all customers sorted by email
    @Override
    public List<Customer> getCustomersByEmailSort() {
        return this.customerDAO.getCustomerEmailSort();
    }

    // Run query to get all customers sorted by phone number
    @Override
    public List<Customer> getCustomersByPhoneSort() {
        return this.customerDAO.getCustomerPhoneSort();
    }

    // Delete query if valid id
    @Override
    public String deleteCustomerById(long id) {
        this.customerDAO.deleteById(id);
        return "Deleted Successfully";
    }

    // INSERT query
    public List<Customer> addCustomers(List<Customer> customers) {
        if (customers.size() == 1) {
            return List.of(this.customerDAO.save(customers.getFirst()));
        } else {
            return this.customerDAO.saveAll(customers);
        }
    }

    public List<Customer> updateCustomers(List<Customer> customers) {
        if (customers.size() == 1) {
            return List.of(this.customerDAO.save(customers.getFirst()));
        } else {
            return this.customerDAO.saveAll(customers);
        }
    }


}
