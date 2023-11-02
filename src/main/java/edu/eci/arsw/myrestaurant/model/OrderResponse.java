package edu.eci.arsw.myrestaurant.model;

import java.util.Map;

public class OrderResponse {
    private Order order;
    private int total;



    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderResponse(Order order, int total) {
        this.order = order;
        this.total = total;
    }

}
