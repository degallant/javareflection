package dev.degallant.print;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parses objects to a sensible string representation.
 */
public class ObjectParser {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Parse an object ot a string representation
     *
     * @param object the object to parse
     * @return a string representation of the object
     * @throws IllegalAccessException in case object's fields cannot be queried
     */
    public String parse(Object object) throws IllegalAccessException {

        var output = new StringBuilder();
        var name = object.getClass().getSimpleName();
        output.append(name).append("[");

        for (Field field : object.getClass().getDeclaredFields()) {

            if (field.getName().equals("email")) {
                continue;
            }

            field.setAccessible(true);
            var fieldName = field.getName();
            var fieldValue = field.get(object);

            //TODO deal with objects within objects (e.g. an order has a customer, so customer should be parsed too)

            //TODO format product price from cents to dollars (e.g. 5000 -> 50.00)

            if (field.getType().equals(Date.class)) {
                fieldValue = dateFormat.format(field.get(object));
            }

            output.append(fieldName).append("=").append(fieldValue).append(", ");

        }

        output.delete(output.length() - 2, output.length());
        output.append("]");

        return output.toString();

    }

}
