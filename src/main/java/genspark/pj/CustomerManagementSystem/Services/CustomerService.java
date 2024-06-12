package genspark.pj.CustomerManagementSystem.Services;


import genspark.pj.CustomerManagementSystem.Entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    List<Customer> getCustomerByName(String name);
    List<Customer> getCustomerByEmail(String email);
    List<Customer> getCustomerByPhone(String phonenumber);
    List<Customer> getCustomersByNameSort();
    List<Customer> getCustomersByEmailSort();
    List<Customer> getCustomersByPhoneSort();
    List<Customer> addCustomers(List<Customer> customer);
    List<Customer> updateCustomers(List<Customer> customers);
    String deleteCustomerById(long id);


}
