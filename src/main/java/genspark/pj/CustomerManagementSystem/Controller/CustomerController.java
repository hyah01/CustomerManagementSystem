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

    @GetMapping("/")
    public String home(){
        return "<h1>Welcome to your customer management system</h1>";
    }

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

    @GetMapping("/customers/{id}")
    public Customer getCustomersById(@PathVariable Long id){
        return this.cs.getCustomerById(id);
    }

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

    @GetMapping("/customers/name/sorted")
    public List<Customer> getCustomerByNameSorted() {
        return this.cs.getCustomersByNameSort();
    }

    @GetMapping("/customers/email/sorted")
    public List<Customer> getCustomerByEmailSorted() {
        return this.cs.getCustomersByEmailSort();
    }

    @GetMapping("/customers/phone/sorted")
    public List<Customer> getCustomerByPhoneSorted() {
        return this.cs.getCustomersByPhoneSort();
    }
    @PostMapping("/customers")
    public List<Customer> addCustomers(@Valid @RequestBody List<Customer> customers, BindingResult result){
        logger.info("Attempting to Add Customers");
        validator.validate(customers,result);
        if (result.hasErrors()){
            logger.error(STR."Validation Failed: \{result.getAllErrors()}");
        } else {
            List<Customer> listOfCustomers = this.cs.addCustomers(customers);
            logger.info("Successfully Added Customers");
            return listOfCustomers;
        }
        return null;
    }
    @PutMapping("/customers")
    public List<Customer> updateCustomers(@Valid @RequestBody List<Customer> customers, BindingResult result){
        logger.info("Attempting to Update Customers");
        validator.validate(customers,result);
        if (result.hasErrors()){
            logger.error(STR."Validation Failed: \{result.getAllErrors()}");
        } else {
            List<Customer> listOfCustomers = this.cs.addCustomers(customers);
            logger.info("Successfully Updated Customers");
            return listOfCustomers;
        }
        return null;
    }
    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable long id){
        return this.cs.deleteCustomerById(id);
    }


}
