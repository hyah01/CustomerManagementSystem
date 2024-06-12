package genspark.pj.CustomerManagementSystem.Controller;

import genspark.pj.CustomerManagementSystem.Entity.Customer;
import genspark.pj.CustomerManagementSystem.ErrorHandler.CustomerValidator;
import genspark.pj.CustomerManagementSystem.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return this.cs.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomersById(@PathVariable Long id){
        return this.cs.getCustomerById(id);
    }

    @GetMapping("/customers/name")
    public List<Customer> getCustomerByName(@RequestParam String name) {
        return this.cs.getCustomerByName(name);
    }
    @GetMapping("/customers/email")
    public Customer getCustomerByEmail(@RequestParam String email) {
        return this.cs.getCustomerByEmail(email);
    }
    @GetMapping("/customers/phone")
    public Customer getCustomerByPhone(@RequestParam String phone) {
        return this.cs.getCustomerByPhone(phone);
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
        validator.validate(customers,result);
        if (result.hasErrors()){
            logger.error("Invalid Input");
        } else {
            return this.cs.addCustomers(customers);
        }
        return null;
    }
    @PutMapping("/customers")
    public List<Customer> updateCustomers(@RequestBody List<Customer> customers){
        return this.cs.updateCustomers(customers);
    }
    @DeleteMapping("/customer/{id}")
    public String deleteCustomer(@PathVariable long id){
        return this.cs.deleteCustomerById(id);
    }


}
