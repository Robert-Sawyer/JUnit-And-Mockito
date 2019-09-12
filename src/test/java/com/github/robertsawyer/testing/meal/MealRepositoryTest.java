package com.github.robertsawyer.testing.meal;

import com.github.robertsawyer.testing.meal.Meal;
import com.github.robertsawyer.testing.meal.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.contains;

public class MealRepositoryTest {

    private MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp(){
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldAddMeal(){
        //given
        Meal meal = new Meal(12, "Pizza", 1);

        //when
        mealRepository.add(meal);

        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    @Test
    void shouldRemoveMeal(){
        //given
        Meal meal = new Meal(12, "Pizza", 1);
        mealRepository.add(meal);

        //when
        mealRepository.delete(meal);


        //then
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));
    }

    @Test
    void shouldFindMealByExactName(){
        Meal meal = new Meal(12, "Pizza", 1);
        Meal meal2 = new Meal(12, "Pi", 1);
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> result = mealRepository.findByName("Pizza", true);

        //then
        assertThat(result.size(), is(1));
    }

    @Test
    void shouldFindMealByStartingLetters(){
        Meal meal = new Meal(12, "Pizza", 1);
        Meal meal2 = new Meal(12, "Pi", 1);
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> result = mealRepository.findByName("Pi", false);

        //then
        assertThat(result.size(), is(2));
    }

    @Test
    void shouldFindByPrice(){
        Meal meal = new Meal(12, "Pizza", 1);
        mealRepository.add(meal);

        //when
        List<Meal> result = mealRepository.findByPrice(12);

        //then
        assertThat(result.size(), is(1));

    }
}
