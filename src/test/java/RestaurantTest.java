import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void beforeEach(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        // arrange
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime time = LocalTime.of(10,45);
        // act
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(time);
        // assert
        assertTrue(spyRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        // arrange
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime time = LocalTime.of(10,29);
        // act
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(time);
        // assert
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>COST<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void when_no_items_are_selected_order_cost_should_be_0(){
        List<String> selectedNames = new ArrayList<String>();
        assertEquals(0,restaurant.orderTotal(selectedNames));
    }

    @Test
    public void when_2_items_are_selected_the_total_cost_should_be_equal_to_the_sum_of_their_individual_cost(){
        List<String> selectedNames = new ArrayList<String>();
        selectedNames.add("Sweet corn soup");
        selectedNames.add("Vegetable lasagne");
        assertEquals(388,restaurant.orderTotal(selectedNames));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}