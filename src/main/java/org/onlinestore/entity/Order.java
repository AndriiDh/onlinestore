package org.onlinestore.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private int id;
    private User user;
    private Map<Item, Integer> items;
    private BigDecimal priceOfOrder;
    private String status;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public  Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems( Map<Item, Integer> items) {
        this.items = new HashMap<>();
        this.items.putAll(items);
    }

    public BigDecimal getPriceOfOrder() {
        return priceOfOrder;
    }

    public void setPriceOfOrder(BigDecimal priceOfOrder) {
        this.priceOfOrder = priceOfOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                ", priceOfOrder=" + priceOfOrder +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
