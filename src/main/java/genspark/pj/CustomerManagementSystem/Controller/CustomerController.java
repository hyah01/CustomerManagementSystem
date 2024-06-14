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

    // Home page
    @GetMapping("/")
    public String home(){
        return "<h1>Welcome to your customer management system</h1>";
    }

    // Return all customers
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return this.cs.getAllCustomers();
    }

    // Return customer with that ID
    @GetMapping("/customers/{id}")
    public Customer getCustomersById(@PathVariable Long id){
        return this.cs.getCustomerById(id);
    }

    // Get all user with matching name or name that includes :name
    @GetMapping("/customers/name")
    public List<Customer> getCustomerByName(@RequestParam String name) {
        return this.cs.getCustomerByName(name);
    }

    // Get all user with matching email or email that includes :email
    @GetMapping("/customers/email")
    public List<Customer> getCustomerByEmail(@RequestParam String email) {
        return this.cs.getCustomerByEmail(email);
    }

    // Get all user with matching phone number or phone number  that includes :phone
    @GetMapping("/customers/phone")
    public List<Customer> getCustomerByPhone(@RequestParam String phone) {
        return this.cs.getCustomerByPhone(phone);
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
        // Validate the inputs to see if there's any error
        validator.validate(customers,result);
        if (!result.hasErrors()){
            return this.cs.addCustomers(customers);
        }
        return null;
    }

    // Update customer if it passes validation
    @PutMapping("/customers")
    public List<Customer> updateCustomers(@Valid @RequestBody List<Customer> customers, BindingResult result){
        // Validate the inputs to see if there's any error
        validator.validate(customers,result);
        if (!result.hasErrors()){
            return this.cs.updateCustomers(customers);
        }
        return null;
    }

    // Delete Customer based on id
    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable long id){
        return this.cs.deleteCustomerById(id);
    }


}
