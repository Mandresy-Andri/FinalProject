package com.company.capstoneproject.viewmodel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceViewModel {

    private int id;

    @NotEmpty(message = "You must supply a value for item_id.")
    private int item_id;
    @NotEmpty(message = "You must supply a value for item_type.")
    private String item_type;
    @NotEmpty(message = "You must supply a value for quantity.")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private int quantity;
    @NotEmpty(message = "You must supply a value for name.")
    private String name;
    @NotEmpty(message = "You must supply a value for street.")
    private String street;
    @NotEmpty(message = "You must supply a value for city.")
    private String city;
    @NotEmpty(message = "You must supply a value for state.")
    @Size(min = 2,max = 2)
    private String state;
    @NotEmpty(message = "You must supply a value for zipcode.")
    private String zipcode;

    //calculated by system
    private BigDecimal processing_fee; //basic fee + 15.49 if quantity>10
    private BigDecimal subtotal; //price * quantity
    private BigDecimal tax; // percentage of subtotal based on state
    private BigDecimal unit_price; //price of the item
    private BigDecimal total; // sum of everything

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public BigDecimal getProcessing_fee() {
        return processing_fee;
    }

    public void setProcessing_fee(BigDecimal processing_fee) {
        this.processing_fee = processing_fee;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return id == that.id && item_id == that.item_id && quantity == that.quantity && Objects.equals(item_type, that.item_type) && Objects.equals(name, that.name) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(zipcode, that.zipcode) && Objects.equals(processing_fee, that.processing_fee) && Objects.equals(subtotal, that.subtotal) && Objects.equals(tax, that.tax) && Objects.equals(unit_price, that.unit_price) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item_id, item_type, quantity, name, street, city, state, zipcode, processing_fee, subtotal, tax, unit_price, total);
    }

    @Override
    public String toString() {
        return "InvoiceViewModel{" +
                "id=" + id +
                ", item_id=" + item_id +
                ", item_type='" + item_type + '\'' +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", processing_fee=" + processing_fee +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", unit_price=" + unit_price +
                ", total=" + total +
                '}';
    }
}
