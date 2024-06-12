package genspark.pj.CustomerManagementSystem.Services;


import genspark.pj.CustomerManagementSystem.Entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    List<Customer> getCustomerByName(String name);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByPhone(String phonenumber);
    List<Customer> getCustomersByNameSort();
    List<Customer> getCustomersByEmailSort();
    List<Customer> getCustomersByPhoneSort();
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    String deleteCustomerById(long id);
    List<Customer> addCustomers(List<Customer> customer);
    List<Customer> updateCustomers(List<Customer> customers);


}
