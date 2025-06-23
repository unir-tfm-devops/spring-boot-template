package com.unir.template.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Book entity representing a book in the system")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Schema(
      description = "Unique identifier for the book",
      example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Title of the book", example = "Spring Boot in Action", required = true)
  private String name;

  @Schema(
      description = "Description of the book",
      example = "A comprehensive guide to Spring Boot development")
  private String description;

  @Schema(description = "Price of the book", example = "29.99")
  private Double price;

  @Schema(description = "Number of books available in stock", example = "100")
  private Integer stock;
}
