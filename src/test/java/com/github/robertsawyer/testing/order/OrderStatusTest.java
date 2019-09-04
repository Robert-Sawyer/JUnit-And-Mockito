package com.github.robertsawyer.testing.order;

import com.github.robertsawyer.testing.order.OrderStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

class OrderStatusTest {

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void allOrderStatusShouldBeShorterThan15Char(OrderStatus orderStatus) {
        assertThat(orderStatus.toString().length(), lessThan(15));
    }

}