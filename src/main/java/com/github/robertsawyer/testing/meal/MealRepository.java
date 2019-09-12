package com.github.robertsawyer.testing.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealRepository {

    List<Meal> meals = new ArrayList<Meal>();

    public void add(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void delete(Meal meal) {
        meals.remove(meal);
    }

    public List<Meal> findByName(String name, boolean exactMatch) {

        List<Meal> result = new ArrayList<>();

        if (exactMatch) {
           result = meals.stream()
                   .filter(meal -> meal.getName().equals(name))
                   .collect(Collectors.toList());
       } else {
           result = meals.stream()
                   .filter(meal -> meal.getName().startsWith(name))
                   .collect(Collectors.toList());
       }
        return result;
    }

    public List<Meal> findByPrice(int price) {
        return meals.stream()
                .filter(meal -> meal.getPrice()==price)
                .collect(Collectors.toList());
    }
}
