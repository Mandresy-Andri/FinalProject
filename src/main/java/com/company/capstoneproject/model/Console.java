package com.company.capstoneproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "console")

public class Console implements Serializable {

    @Id
    @Column(name = "console_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int console_id;

    //@NotEmpty(message = "You must a supply a value for Console")
    //private String console_name;

    @NotEmpty(message = "You must supply a value for model")
    private String model;

    @NotEmpty(message = "You must supply a value for memory amount")
    private String memory_amount;

    @NotEmpty(message = "You must supply a value for processor")
    private String processor;



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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMemory_amount() {
        return memory_amount;
    }

    public void setMemory_amount(String memory_amount) {
        this.memory_amount = memory_amount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return getConsole_id() == console.getConsole_id() && getQuantity() == console.getQuantity() && Objects.equals(getModel(), console.getModel()) && Objects.equals(getMemory_amount(), console.getMemory_amount()) && Objects.equals(getProcessor(), console.getProcessor()) && Objects.equals(getManufacturer(), console.getManufacturer()) && Objects.equals(getPrice(), console.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsole_id(), getModel(), getMemory_amount(), getProcessor(), getQuantity(), getManufacturer(), getPrice());
    }

    @Override
    public String toString() {
        return "Console{" +
                "console_id=" + console_id +
                ", model='" + model + '\'' +
                ", memory_amount='" + memory_amount + '\'' +
                ", processor='" + processor + '\'' +
                ", quantity=" + quantity +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                '}';
    }
}