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

    private String category;

    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    private HomBoundRequest homBoundRequest;

    public Item(Long id, String name, String category, Integer quantity, HomBoundRequest homBoundRequest) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
