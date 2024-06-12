package genspark.pj.CustomerManagementSystem;

import genspark.pj.CustomerManagementSystem.Controller.CustomerController;
import genspark.pj.CustomerManagementSystem.Entity.Customer;
import genspark.pj.CustomerManagementSystem.Repository.CustomerDAO;
import genspark.pj.CustomerManagementSystem.Services.CustomerService;
import genspark.pj.CustomerManagementSystem.Services.CustomerServiceImpl;
import genspark.pj.CustomerManagementSystem.Validation.CustomerValidator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CustomerManagementSystemApplicationTests {

	@Autowired
	private CustomerService cs;

	@Autowired
	private CustomerServiceImpl csi;
	@Autowired
	private Customer customer; // Inject the Customer bean

	// TEST ADD CUSTOMERS

	@Test
	void testAddMultipleCustomer() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		List<Customer> mockCustomers = new ArrayList<>();
		Customer customer1 = new Customer();
		customer1.setName("John Doe");
		customer1.setEmail("john.doe@example.com");
		customer1.setPhoneNumber("1234567890");
		mockCustomers.add(customer1);
		Customer customer2 = new Customer();
		customer2.setName("Jane Marry");
		customer2.setEmail("jane.marry@example.com");
		customer2.setPhoneNumber("1234567899");
		mockCustomers.add(customer2);

		when(customerDAO.findAll()).thenReturn(mockCustomers);
		List<Customer> customers = csi.getAllCustomers();

		assertEquals(2,customers.size());
		assertEquals("John Doe", customers.getFirst().getName());
		assertEquals("Jane Marry", customers.get(1).getName());
	}

	@Test
	public void testDatabaseConnectionFailureDuringRetrieval() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		when(customerDAO.findAll()).thenThrow(new RuntimeException("Database connection failure"));

		try {
			csi.getAllCustomers();
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			assertEquals("Database connection failure", e.getMessage());
		}
	}
	@Test
	public void test_return_empty_list_if_no_customers_found() {
		List<Customer> customers = cs.getAllCustomers();
		assertNotNull(customers);
		assertEquals(0, customers.size());
	}

	@Test
	public void test_get_all_customers_large_number() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);
		List<Customer> mockCustomers = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Customer customer = new Customer();
			customer.setName("Customer " + i);
			customer.setEmail("customer" + i + "@example.com");
			customer.setPhoneNumber("1234567" + i);
			mockCustomers.add(customer);
		}
		when(customerDAO.findAll()).thenReturn(mockCustomers);
		List<Customer> customers = csi.getAllCustomers();


		assertEquals(1000, customers.size());
	}


	// TEST ADD GET ALL CUSTOMERS
	@Test
	public void test_logs_info_message_retrieval_attempt() {
		// Setup
		Logger logger = mock(Logger.class);
		csi.logger = logger;
		// Test
		List<Customer> customers = csi.getAllCustomers();
		// Verify
		verify(logger).info("Attempting to Retrieve All Customers");
	}

	@Test
	public void test_retrieve_all_customers_with_multiple_entries() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		List<Customer> mockCustomers = new ArrayList<>();
		Customer customer1 = new Customer();
		customer1.setName("John Doe");
		customer1.setEmail("john.doe@example.com");
		customer1.setPhoneNumber("1234567890");
		mockCustomers.add(customer1);
		Customer customer2 = new Customer();
		customer2.setName("Jane Marry");
		customer2.setEmail("jane.marry@example.com");
		customer2.setPhoneNumber("1234567899");
		mockCustomers.add(customer2);

		when(customerDAO.findAll()).thenReturn(mockCustomers);

		List<Customer> customers = csi.getAllCustomers();

		assertEquals(2, customers.size());
		assertEquals("John Doe", customers.get(0).getName());
		assertEquals("Jane Marry", customers.get(1).getName());
	}

	@Test
	public void test_database_connection_failure() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		when(customerDAO.findAll()).thenThrow(new RuntimeException("Database connection failure"));

		try {
			csi.getAllCustomers();
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			assertEquals("Database connection failure", e.getMessage());
		}
	}

	// Test GET BY ID

	@Test
	public void test_retrieves_existing_customer_by_valid_id() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		Customer expectedCustomer = new Customer();
		expectedCustomer.setId(1L);
		expectedCustomer.setName("John Doe");
		expectedCustomer.setEmail("john.doe@example.com");
		expectedCustomer.setPhoneNumber("1234567890");

		when(customerDAO.findById(1L)).thenReturn(Optional.of(expectedCustomer));

		Customer actualCustomer = csi.getCustomerById(1L);

		assertNotNull(actualCustomer);
		assertEquals(expectedCustomer.getId(), actualCustomer.getId());
		assertEquals(expectedCustomer.getName(), actualCustomer.getName());
		assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
		assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
	}

	@Test
	public void test_id_does_not_exist_in_database() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		when(customerDAO.findById(1L)).thenReturn(Optional.empty());

		Customer actualCustomer = csi.getCustomerById(1L);

		assertNull(actualCustomer);
	}

	@Test
	public void test_logs_customer_not_found_when_id_does_not_exist() {
		// Setup
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);
		Long nonExistentId = 1000L;
		when(customerDAO.findById(nonExistentId)).thenReturn(Optional.empty());
		Logger logger = mock(Logger.class);
		csi.logger = logger;
		// Execution
		Customer result = csi.getCustomerById(nonExistentId);

		// Assertion
		assertNull(result);
		verify(logger).info("Customer Not Found ID: " + nonExistentId);
	}

	// Test GET BY NAME || Phone and Email are basically the same

	@Test
	public void test_retrieve_customers_with_exact_name_match() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		Customer customer1 = new Customer();
		customer1.setName("John Doe");
		customer1.setEmail("john.doe@example.com");
		customer1.setPhoneNumber("1234567890");
		List<Customer> expectedCustomers = new ArrayList<>();
		expectedCustomers.add(customer1);
		when(customerDAO.findByName("John Doe")).thenReturn(expectedCustomers);

		List<Customer> actualCustomers = csi.getCustomerByName("John Doe");

		assertEquals(expectedCustomers, actualCustomers);
	}

	@Test
	public void test_retrieve_customers_with_empty_name_string() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);

		List<Customer> expectedCustomers = Collections.emptyList();
		when(customerDAO.findByName("")).thenReturn(expectedCustomers);

		List<Customer> actualCustomers = csi.getCustomerByName("");

		assertEquals(expectedCustomers, actualCustomers);
	}

	@Test
	public void test_retrieve_customers_no_matching_name_exists() {
		// Setup
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		String name = "NonExistentName";
		List<Customer> emptyList = new ArrayList<>();
		when(customerDAO.findByName(name)).thenReturn(emptyList);

		// Execution
		List<Customer> result = csi.getCustomerByName(name);

		// Assertion
		assertEquals(0, result.size());
	}

	@Test
	public void test_logger_message_on_get_customer_by_name() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(csi, "customerDAO", customerDAO);
		Logger logger = mock(Logger.class);
		csi.logger = logger;

		Customer customer1 = new Customer();
		customer1.setName("John Doe");
		customer1.setEmail("john.doe@example.com");
		customer1.setPhoneNumber("1234567890");
		List<Customer> expectedCustomers = new ArrayList<>();
		expectedCustomers.add(customer1);
		when(customerDAO.findByName("John Doe")).thenReturn(expectedCustomers);

		List<Customer> actualCustomers = csi.getCustomerByName("John Doe");
		verify(logger).info("Attempting to Retrieve Customers by Name: " + "John Doe");
		assertEquals(expectedCustomers, actualCustomers);
	}

	// Test Update Customer
	@Test
	public void test_update_single_customer() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		CustomerServiceImpl customerService = new CustomerServiceImpl();
		ReflectionTestUtils.setField(customerService, "customerDAO", customerDAO);
		Logger logger = mock(Logger.class);
		customerService.logger = logger;

		Customer customer = new Customer();
		customer.setName("John Doe");
		customer.setEmail("john.doe@example.com");
		customer.setPhoneNumber("1234567890");
		List<Customer> customers = List.of(customer);

		when(customerDAO.save(customer)).thenReturn(customer);

		List<Customer> updatedCustomers = customerService.updateCustomers(customers);

		verify(logger).info("Updating a Single Customer");
		assertEquals(1, updatedCustomers.size());
		assertEquals(customer, updatedCustomers.get(0));
	}


	@Test
	public void test_update_empty_customer_list() {
		CustomerDAO customerDAO = mock(CustomerDAO.class);
		CustomerServiceImpl customerService = new CustomerServiceImpl();
		ReflectionTestUtils.setField(customerService, "customerDAO", customerDAO);
		Logger logger = mock(Logger.class);
		customerService.logger = logger;

		List<Customer> customers = List.of();

		List<Customer> updatedCustomers = customerService.updateCustomers(customers);

		verify(logger).info("Updating Multiple Customers");
		assertTrue(updatedCustomers.isEmpty());
	}



	}
