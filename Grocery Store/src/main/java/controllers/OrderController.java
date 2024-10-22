package controllers;

import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderRepository       orderRepository;
	@Autowired
	private CustomerRepository    customerRepository;
	@Autowired
	private GroceryItemRepository groceryItemRepository;

	@GetMapping
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		return orderRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Customer customer = customerRepository.findById(order.getCustomer().getId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		order.setCustomer(customer);

		List<GroceryItem> items = groceryItemRepository.findAllById(
				order.getGroceryItems().stream().map(GroceryItem::getId).collect(Collectors.toList())
		);
		order.setGroceryItems(items);

		double totalPrice = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		order.setTotalPrice(totalPrice);

		return ResponseEntity.ok(orderRepository.save(order));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
		return orderRepository.findById(id)
				.map(order -> {
					orderRepository.delete(order);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}

