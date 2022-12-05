package com.company.capstoneproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties
@Table(name = "console")

public class Console implements Serializable {

    @Id
    @Column(name = "console_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int console_id;

    //@NotEmpty(message = "You must a supply a value for Console")
    //private String console_name;

    @NotEmpty(message = "You must supply a value for description")
    private String description;

    @NotEmpty(message = "You must supply a value for quantity")
    private int quantity;

    @NotEmpty(message = "You must supply a value for manufacturer")
    private String manufacturer;

    @NotEmpty(message = "You must supply a value for price")
    private BigDecimal price;

    public int getConsole_id() {
        return console_id;
    }

    public void setConsole_id(int console_id) {
        this.console_id = console_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return getConsole_id() == console.getConsole_id() && getQuantity() == console.getQuantity()  && getDescription().equals(console.getDescription()) && getPrice().equals(console.getPrice()) && getManufacturer().equals(console.getManufacturer());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getConsole_id(),getDescription(),getPrice(),getManufacturer(),getQuantity());
    }

    @Override
    public String toString() {
        return "Console{" +
                "console_id=" + console_id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                '}';
    }
}