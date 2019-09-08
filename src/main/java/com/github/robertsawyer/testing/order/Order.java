package com.github.robertsawyer.testing.order;

import com.github.robertsawyer.testing.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private OrderStatus orderStatus;

    private List<Meal> orders = new ArrayList<>();

    public void addMealToOrder(Meal meal) {
        this.orders.add(meal);
    }

    public void removeMealFromOrder(Meal meal) {
        this.orders.remove(meal);
    }


    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<Meal> getOrders() {
        return orders;
    }

    public void cancel(){
        this.orders.clear();
    }

    int totalPrice(){

        int sum = this.orders.stream().mapToInt(meal -> meal.getPrice()).sum();

        if (sum < 0) {
            throw new IllegalStateException("Błąd");
        } else return sum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orders=" + orders +
                '}';
    }
}
