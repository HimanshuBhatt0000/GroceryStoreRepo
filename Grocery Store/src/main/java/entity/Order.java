package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Customer customer;

	@ManyToMany
	private List<GroceryItem> groceryItems;

	private LocalDate orderDate;
	private Double    totalPrice;

}
