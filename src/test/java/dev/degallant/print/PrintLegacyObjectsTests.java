package dev.degallant.print;

import dev.degallant.legacy.Customer;
import dev.degallant.legacy.Order;
import dev.degallant.legacy.Product;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintLegacyObjectsTests {

    @Test
    public void parseCustomerClass() throws IllegalAccessException {

        var parser = new ObjectParser();

        var customer = new Customer(
                "039550934435358943",
                "Jhon Doe",
                "email@gmail.com",
                Date.from(Instant.parse("2007-12-03T10:15:30.00-04:00"))
        );

        String parsed = parser.parse(customer);

        String expected = "Customer[code=039550934435358943, name=Jhon Doe, registrationDate=2007-12-03]";
        assertEquals(expected, parsed);

    }

    @Test
    public void parseOrderClass() throws IllegalAccessException {

        var parser = new ObjectParser();

        var customer = new Customer(
                "9287498327493423",
                "Jhon Doe",
                "email@gmail.com",
                Date.from(Instant.parse("2007-12-03T10:15:30.00-04:00"))
        );

        var product = new Product("ProductA", "description A", 5000);

        var order = new Order(customer, "234902342309", product);

        var parsed = parser.parse(order);

        var customerString = "Customer[code=9287498327493423, name=Jhon Doe, registrationDate=2007-12-03]";
        var productString = "Product[name=ProductA, description=description A, price=50.00]";
        var expected = "Order[customer=" + customerString + ", code=234902342309, product=" + productString + "]";
        assertEquals(expected, parsed);

    }

}
