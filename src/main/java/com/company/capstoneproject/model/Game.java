package com.company.capstoneproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "game")
public class Game implements Serializable{

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int game_id;

    @NotEmpty(message = "You must supply a value for title.")
    private String title;

    @NotEmpty(message = "You must supply a value for esrb_rating.")
    @Column(name = "esrb_rating", nullable = false)
    private String esrbRating;

    @NotEmpty(message = "You must supply a value for description.")
    private String description;

    @NotEmpty(message = "You must supply a value for price.")
    private BigDecimal price;

    @NotEmpty(message = "You must supply a value for studio.")
    private String studio;

    @NotEmpty(message = "You must supply a value for quantity.")
    private int quantity;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
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

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
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
        Game game = (Game) o;
        return getGame_id() == game.getGame_id() &&
                getQuantity() == game.getQuantity() &&
                getTitle().equals(game.getTitle()) &&
                getEsrbRating().equals(game.getEsrbRating()) &&
                getDescription().equals(game.getDescription()) &&
                getPrice().equals(game.getPrice()) &&
                getStudio().equals(game.getStudio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame_id(), getTitle(), getEsrbRating(), getDescription(), getPrice(), getStudio(),
                getQuantity());
    }

    @Override
    public String toString() {
        return "game{" +
                "game_id=" + game_id +
                ", title='" + title + '\'' +
                ", esrb_rating='" + esrbRating + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", studio='" + studio + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}