package com.github.robertsawyer.testing.cart;

import com.github.robertsawyer.testing.order.Order;
import com.github.robertsawyer.testing.order.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService; //Bo Cart Serwice ma jakąś zależnośc, którą potem mockujemy (w postaci CartHandlera)

    @Mock
    private CartHandler cartHandler; //zapis pola tożsamy z metodą mock(CartHandler.class)

    @Captor
    private ArgumentCaptor<Cart> argumentCaptor; //możemy teraz nie tworzyć captora w metodzie poniżęj

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //zapis poniżej byłby potrzebny, gdyby nie było pół z adnotacjami na samej górze
//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //jeżeli będziemy mieć na obiekcie cartHandler wywołaną metodę canHandleCart
        //to wtedy powinniśmy mieć zwróconą wartość true
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);
        //analogiczny zapis poniżej
        then(cartHandler).should().sendToPrepare(cart);
        //inne wariacje
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        //sprawdzamy kolejnośc wykonania metod
        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldNotSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //jeżeli będziemy mieć na obiekcie cartHandler wywołaną metodę canHandleCart
        //to wtedy powinniśmy mieć zwróconą wartość true
        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //jeżeli będziemy mieć na obiekcie cartHandler wywołaną metodę canHandleCart
        //to wtedy powinniśmy mieć zwróconą wartość true
        given(cartHandler.canHandleCart(any())).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(any()); //ANY - PRZYJMIE DOWOLNY ARGUMENT, GDYBYŚMY NIE WIEDZIELI JAKI JEST

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //zwróci wartość true jeżeli element c (cart) będzie miał metodę getOrders i lista będzie miała co najmniej jeden element
        given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //zwróci wartość true jeżeli element c (cart) będzie miał metodę getOrders i lista będzie miała co najmniej jeden element
        given(cartHandler.canHandleCart(cart)).willThrow(IllegalStateException.class);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        //jeżeli będziemy mieć na obiekcie cartHandler wywołaną metodę canHandleCart
        //to wtedy powinniśmy mieć zwróconą wartość true
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void shouldDoNothingWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //jeżeli będziemy mieć na obiekcie cartHandler wywołaną metodę canHandleCart
        //to wtedy powinniśmy mieć zwróconą wartość true
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        doNothing().when(cartHandler).sendToPrepare(cart);
        willDoNothing().given(cartHandler).sendToPrepare(cart);
        //gdy za któymsś(drugim) razem został rzucony wyjątek
        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void shouldAnswerWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

        //gdy nie znamy wartości przekazywanych argumentów do metody, ale wiemy jakie operacje mają zostać wykonane
        doAnswer(invocationOnMock -> {
            Cart cartArgument = invocationOnMock.getArgument(0);
            cartArgument.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);

        //alternatywa
        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart cartArgument = i.getArgument(0);
            cartArgument.clearCart();
            return true;
        });

        //alternatywa
        willAnswer(invocationOnMock -> {
            Cart cartArgument = invocationOnMock.getArgument(0);
            cartArgument.clearCart();
            return true;
        }).given(cartHandler).canHandleCart(cart);

        //alternatywa
        given(cartHandler.canHandleCart(cart)).will(i -> {
            Cart cartArgument = i.getArgument(0);
            cartArgument.clearCart();
            return true;
        });

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().size(), equalTo(0));
    }

    @Test
    void deliveryShouldBeFree(){
        //given
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

//        CartHandler cartHandler = mock(CartHandler.class);
//        given(cartHandler.isDeliveryFree(cart)).willCallRealMethod();
        //alternatywa
        doCallRealMethod().when(cartHandler).isDeliveryFree(cart);

        //when
        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);

        //then
        assertTrue(isDeliveryFree);
    }
}