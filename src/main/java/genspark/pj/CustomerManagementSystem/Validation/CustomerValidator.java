package genspark.pj.CustomerManagementSystem.Validation;

import genspark.pj.CustomerManagementSystem.Controller.CustomerController;
import genspark.pj.CustomerManagementSystem.Entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Pattern;

// Class that validate inputs for Customer Creation
@Component
public class CustomerValidator implements Validator {
    // Logger to log invalid inputs
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    // Match pattern with input emails to validate
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public boolean supports(Class<?> className) {
        return Customer.class.equals(className);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<Customer> customers = (List<Customer>) target;
        // Index so user who add more than 1 Customer can know where the error was
        int index = 0;
        // Validate each customer separately
        for (Customer customer : customers) {
            // Validate name
            if (customer.getName() == null || customer.getName().isEmpty()) {
                errors.reject("customer.empty", STR."Name must not be empty for customer at index \{index}");
                logger.error(STR."Name must not be empty for customer at index \{index}: \{customer}");
            }
            // Validate Email
            if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
                errors.reject("customer.empty", STR."Email must not be empty for customer at index \{index}");
                logger.error(STR."Email must not be empty for customer at index \{index}: \{customer}");
            } else if (!EMAIL_PATTERN.matcher(customer.getEmail()).matches()) {
                errors.reject("customer.empty", STR."Email must not be in valid email format at index \{index}");
                logger.error(STR."Invalid email format for customer at index \{index}: \{customer}");
            }
            // Validate Phone Number
            if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty()) {
                errors.reject("customer.empty", STR."Phone must not be empty for customer at index \{index}");
                logger.error(STR."Phone must not be empty for customer at index \{index}: \{customer}");
            }
            index++;
        }
    }
}
