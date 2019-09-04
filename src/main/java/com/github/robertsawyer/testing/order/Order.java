package com.github.robertsawyer.testing.order;

import com.github.robertsawyer.testing.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Meal> orders = new ArrayList<>();

    public void addMealToOrder(Meal meal) {
        this.orders.add(meal);
    }

    public void removeMealFromOrder(Meal meal) {
        this.orders.remove(meal);
    }

    public List<Meal> getOrders() {
        return orders;
    }

    public void cancel(){
        this.orders.clear();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orders=" + orders +
                '}';
    }
}
