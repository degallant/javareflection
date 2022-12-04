package dev.degallant.print;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parses objects to a sensible string representation.
 */
public class ObjectParser {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DecimalFormat numberFormat = new DecimalFormat("0.00");

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

            //we only want to convert objects that belongs to our domain, i.e. are within our package
            //if we don't use the package, we could end up getting objects from the JDK such as String
            if (field.getType().getPackageName().equals("dev.degallant.legacy")) {
                fieldValue = parse(fieldValue);
            }

            //everytime we see a price field, we assume is in cents
            //so we will convert it and format it accordingly
            if (field.getName().equals("price")) {
                fieldValue = numberFormat.format((int)fieldValue / 100.00);
            }

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
