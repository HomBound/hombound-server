package HOMBOUND.Models;

import HOMBOUND.Models.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private ItemType category;

    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    private HomBoundRequest homBoundRequest;

    public Item(Long id, String name, ItemType category, Integer quantity, HomBoundRequest homBoundRequest) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.homBoundRequest = homBoundRequest;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getCategory() {
        return category;
    }

    public void setCategory(ItemType category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public HomBoundRequest getHomBoundRequest() {
        return homBoundRequest;
    }

    public void setHomBoundRequest(HomBoundRequest homBoundRequest) {
        this.homBoundRequest = homBoundRequest;
    }
}
