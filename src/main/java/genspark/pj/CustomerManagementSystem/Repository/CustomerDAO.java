package genspark.pj.CustomerManagementSystem.Repository;

import genspark.pj.CustomerManagementSystem.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO extends JpaRepository<Customer,Long> {

    @Query("SELECT c from Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByName(String name);

    @Query("SELECT c from Customer c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<Customer> findByEmail(String email);

    @Query("SELECT c from Customer c WHERE c.phoneNumber LIKE LOWER(CONCAT('%', :phone, '%'))")
    List<Customer> findByPhone(String phone);

    @Query(value= "SELECT * FROM Customer ORDER BY name", nativeQuery = true) // Native query
    List<Customer> getCustomerNameSort();

    @Query(value= "SELECT * FROM Customer ORDER BY email", nativeQuery = true) // Native query
    List<Customer> getCustomerEmailSort();

    @Query(value= "SELECT * FROM Customer ORDER BY phone_number", nativeQuery = true) // Native query
    List<Customer> getCustomerPhoneSort();
}
