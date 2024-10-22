package controllers;

import entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CustomerRepository;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		return customerRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Customer createCustomer(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
		return customerRepository.findById(id)
				.map(customer -> {
					customer.setName(customerDetails.getName());
					customer.setEmail(customerDetails.getEmail());
					customer.setAddress(customerDetails.getAddress());
					customer.setPhone(customerDetails.getPhone());
					return ResponseEntity.ok(customerRepository.save(customer));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
		return customerRepository.findById(id)
				.map(customer -> {
					customerRepository.delete(customer);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}

