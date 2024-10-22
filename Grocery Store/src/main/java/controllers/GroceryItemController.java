package controllers;

import entity.GroceryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.GroceryItemRepository;

import java.util.List;

@RestController
@RequestMapping("/grocery-items")
public class GroceryItemController {

	@Autowired
	private GroceryItemRepository groceryItemRepository;

	@GetMapping
	public List<GroceryItem> getAllGroceryItems() {
		return groceryItemRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable Long id) {
		return groceryItemRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public GroceryItem createGroceryItem(@RequestBody GroceryItem groceryItem) {
		return groceryItemRepository.save(groceryItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<GroceryItem> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem itemDetails) {
		return groceryItemRepository.findById(id)
				.map(item -> {
					item.setName(itemDetails.getName());
					item.setCategory(itemDetails.getCategory());
					item.setPrice(itemDetails.getPrice());
					item.setQuantity(itemDetails.getQuantity());
					return ResponseEntity.ok(groceryItemRepository.save(item));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteGroceryItem(@PathVariable Long id) {
		return groceryItemRepository.findById(id)
				.map(item -> {
					groceryItemRepository.delete(item);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}

