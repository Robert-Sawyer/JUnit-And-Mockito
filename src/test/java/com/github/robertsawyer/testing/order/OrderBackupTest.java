package com.github.robertsawyer.testing.order;

import com.github.robertsawyer.testing.meal.Meal;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;

class OrderBackupTest {

    private static OrderBackup orderBackup;

    @BeforeAll
    static void shouldCreateFile() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();

    }

    @BeforeEach
    void shouldStartTheLine() throws IOException {
        orderBackup.getWriter().append("Nowe zamówienie: ");
    }

    @AfterEach
    void shouldEndTheLine() throws IOException {
        orderBackup.getWriter().append(" zarchwizowano");
    }

    @Tag("frytki")
    @Test
    void backupOrderWithOneMeal() throws IOException {
        //given
        Meal meal = new Meal(5, "Frytki", 1);
        Order order = new Order();
        order.addMealToOrder(meal);

        //when
        orderBackup.backup(order);

        //then
        System.out.println("Zamówienie " + order.toString() + " zarchiwizowane.");
    }

    @AfterAll
    static void shouldCloseFile() throws IOException {
        orderBackup.closeFile();
    }
}