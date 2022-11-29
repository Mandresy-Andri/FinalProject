package com.company.capstoneproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "t_shirt")
public class TShirt implements Serializable {

        @Id
        @Column(name = "t_shirt_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotEmpty(message = "You must supply a value for size.")
        private String size;
        @NotEmpty(message = "You must supply a value for color.")
        private String color;
        @NotEmpty(message = "You must supply a value for description.")
        private String description;
        @NotEmpty(message = "You must supply a value for price.")
        private BigDecimal price;
        @NotEmpty(message = "You must supply a value for quantity.")
        private int quantity;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getSize() {
                return size;
        }

        public void setSize(String size) {
                this.size = size;
        }

        public String getColor() {
                return color;
        }

        public void setColor(String color) {
                this.color = color;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TShirt tShirt = (TShirt) o;
                return id == tShirt.id && quantity == tShirt.quantity && Objects.equals(size, tShirt.size) && Objects.equals(color, tShirt.color) && Objects.equals(description, tShirt.description) && Objects.equals(price, tShirt.price);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, size, color, description, price, quantity);
        }

        @Override
        public String toString() {
                return "TShirt{" +
                        "id=" + id +
                        ", size='" + size + '\'' +
                        ", color='" + color + '\'' +
                        ", description='" + description + '\'' +
                        ", price=" + price +
                        ", quantity=" + quantity +
                        '}';
        }
}
