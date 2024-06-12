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

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public List<Customer> getAllCustomers() {
        logger.info("Attempting to Retrieve All Customers");
        return this.customerDAO.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        logger.info(STR."Attempting to Retrieve Customers by ID: \{id}");
        Optional<Customer> t = this.customerDAO.findById(id);
        Customer customer = null;
        // if customer exist, return it
        if (t.isPresent()){
            customer=t.get();
        } else {
            logger.info(STR."Customer Not Found :\{id}");
            throw new RuntimeException(STR."Customer Not Found :\{id}");
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomerByName(String name) {
        logger.info(STR."Attempting to Retrieve Customers by Name: \{name}");
        return this.customerDAO.findByName(name);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        logger.info(STR."Attempting to Retrieve a Customer by Email: \{email}");
        return this.customerDAO.findByEmail(email);
    }

    @Override
    public Customer getCustomerByPhone(String phonenumber) {
        logger.info(STR."Attempting to Retrieve a Customer by Phone Number: \{phonenumber}");
        return this.customerDAO.findByPhone(phonenumber);
    }

    @Override
    public List<Customer> getCustomersByNameSort() {
        logger.info("Attempting to Retrieve Customers by Sorted Names");
        return this.customerDAO.getCustomerNameSort();
    }

    @Override
    public List<Customer> getCustomersByEmailSort() {
        logger.info("Attempting to Retrieve Customers by Sorted Emails");
        return this.customerDAO.getCustomerEmailSort();
    }

    @Override
    public List<Customer> getCustomersByPhoneSort() {
        logger.info("Attempting to Retrieve Customers by Sorted Phone Numbers");
        return this.customerDAO.getCustomerPhoneSort();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        logger.info("Attempting to Add a Customer");
        return this.customerDAO.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        logger.info("Attempting to Update a Customer");
        return this.customerDAO.save(customer);
    }

    @Override
    public String deleteCustomerById(long id) {
        logger.warn(STR."Attempting to Delete Customer with id:\{id}");
        this.customerDAO.deleteById(id);
        logger.info(STR."Successfully Deleted Customer with id:\{id}");
        return "Deleted Successfully";
    }

    @Override
    public List<Customer> addCustomers(List<Customer> customers) {
        logger.info("Attempting to Add Multiple Customers");
        return this.customerDAO.saveAll(customers);
    }

    @Override
    public List<Customer> updateCustomers(List<Customer> customers) {
        logger.info("Attempting to Update Multiple Customers");
        return this.customerDAO.saveAll(customers);
    }


}
