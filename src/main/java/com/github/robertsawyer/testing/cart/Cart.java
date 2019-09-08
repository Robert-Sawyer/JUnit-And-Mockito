package com.github.robertsawyer.testing.cart;

import com.github.robertsawyer.testing.Meal;
import com.github.robertsawyer.testing.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrderToCart(Order order) {
        this.orders.add(order);
    }

    public void clearCart(){
        this.orders.clear();
    }

    public void simulateLargeOrder() {

        for (int i = 0; i < 1000; i++) {
            Meal meal = new Meal(i%10, "Hamburger nr. " + i, 1);
            Order order = new Order();
            order.addMealToOrder(meal);
            addOrderToCart(order);
        }
        clearCart();
    }
}
