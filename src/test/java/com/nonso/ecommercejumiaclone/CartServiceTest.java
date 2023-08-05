package com.nonso.ecommercejumiaclone;

import com.nonso.ecommercejumiaclone.exception.CartServiceException;
import com.nonso.ecommercejumiaclone.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("should return the correct error message when cartId is invalid")
    void given_invalid_cartId_when_getAllCartItems_then_fail() {
        //Arrange
//        Long cartId = 1L;
//        //Act
//        Exception exception = assertThrows(CartServiceException.class, () ->
//                cartService.getAllCartItems(cartId));
//        String expectedMsg = "Cart not found";
//        String actualMsg = exception.getMessage();
//        //Assert
//        assertEquals(expectedMsg, actualMsg);
    }
}
