package genspark.pj.CustomerManagementSystem.Controller;

import genspark.pj.CustomerManagementSystem.Entity.Customer;
import genspark.pj.CustomerManagementSystem.Validation.CustomerValidator;
import genspark.pj.CustomerManagementSystem.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class CustomerController {
    @Autowired
    private CustomerService cs;
    @Autowired
    private CustomerValidator validator;
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    // Home page
    @GetMapping("/")
    public String home(){
        return "<h1>Welcome to your customer management system</h1>";
    }

    // Return all customers
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        List<Customer> listOfCustomers = this.cs.getAllCustomers();
        if (listOfCustomers.isEmpty()){
            logger.info("There is currently no customer in the database");
        } else {
            logger.info("Successfully Retrieved All Customers");
        }
        return listOfCustomers;
    }

    // Return customer with that ID
    @GetMapping("/customers/{id}")
    public Customer getCustomersById(@PathVariable Long id){
        return this.cs.getCustomerById(id);
    }

    // Get all user with matching name or name that includes :name
    @GetMapping("/customers/name")
    public List<Customer> getCustomerByName(@RequestParam String name) {
        List<Customer> listOfCustomers = this.cs.getCustomerByName(name);
        if (listOfCustomers.isEmpty()){
            logger.info(STR."There is currently no Customers with the name: \{name}");
        } else {
            logger.info(STR."Successfully Retrieved Customers with the name: \{name}");
        }
        return listOfCustomers;
    }

    // Get all user with matching email or email that includes :email
    @GetMapping("/customers/email")
    public List<Customer> getCustomerByEmail(@RequestParam String email) {
        List<Customer> listOfCustomers = this.cs.getCustomerByEmail(email);
        if (listOfCustomers.isEmpty()){
            logger.info(STR."There is currently no Customers with the email: \{email}");
        } else {
            logger.info(STR."Successfully Retrieved Customers with the email: \{email}");
        }
        return listOfCustomers;
    }

    // Get all user with matching phone number or phone number  that includes :phone
    @GetMapping("/customers/phone")
    public List<Customer> getCustomerByPhone(@RequestParam String phone) {
        List<Customer> listOfCustomers = this.cs.getCustomerByPhone(phone);
        if (listOfCustomers.isEmpty()){
            logger.info(STR."There is currently no Customers with the phone number: \{phone}");
        } else {
            logger.info(STR."Successfully Retrieved Customers with the phone number: \{phone}");
        }
        return listOfCustomers;
    }

    // Return all customers sorted by name
    @GetMapping("/customers/name/sorted")
    public List<Customer> getCustomerByNameSorted() {
        return this.cs.getCustomersByNameSort();
    }

    // Return all customers sorted by email
    @GetMapping("/customers/email/sorted")
    public List<Customer> getCustomerByEmailSorted() {
        return this.cs.getCustomersByEmailSort();
    }

    // Return all customers sorted by phone number
    @GetMapping("/customers/phone/sorted")
    public List<Customer> getCustomerByPhoneSorted() {
        return this.cs.getCustomersByPhoneSort();
    }

    // Add customer to database if it passes validation
    @PostMapping("/customers")
    public List<Customer> addCustomers(@Valid @RequestBody List<Customer> customers, BindingResult result){
        logger.info("Attempting to Add Customers");
        // Validate the inputs to see if there's any error
        validator.validate(customers,result);
        if (result.hasErrors()){
            // if fails print error and its location
            logger.error(STR."Validation Failed: \{result.getAllErrors()}");
        } else {
            List<Customer> listOfCustomers = this.cs.addCustomers(customers);
            logger.info("Successfully Added Customers");
            return listOfCustomers;
        }
        return null;
    }

    // Update customer if it passes validation
    @PutMapping("/customers")
    public List<Customer> updateCustomers(@Valid @RequestBody List<Customer> customers, BindingResult result){
        logger.info("Attempting to Update Customers");
        validator.validate(customers,result);
        if (result.hasErrors()){
            logger.error(STR."Validation Failed: \{result.getAllErrors()}");
        } else {
            List<Customer> listOfCustomers = this.cs.updateCustomers(customers);
            logger.info("Successfully Updated Customers");
            return listOfCustomers;
        }
        return null;
    }

    // Delete Customer based on id
    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable long id){
        return this.cs.deleteCustomerById(id);
    }


}
