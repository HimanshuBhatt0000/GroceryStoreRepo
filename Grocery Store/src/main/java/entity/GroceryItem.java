package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GroceryItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String category;
	private Double price;
	private Integer quantity;

}
