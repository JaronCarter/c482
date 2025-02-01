package ims.c482.utils;

import ims.c482.models.InHouse;
import ims.c482.models.Inventory;
import ims.c482.models.Outsourced;
import ims.c482.models.Part;

/**
 * Utils class for housing utilities to cut down on code duplication.
 */
public class Utils {
    /**
     * Part converter method for changing an in house part to outsourced part and vice versa.
     * @param part Takes a part to convert.
     * @param newInfo Takes the new info to add to either the machine id or company name.
     * @return Returns the newly converted part.
     */
    public static Part convertPart(Part part, String newInfo){
        Inventory invInstance = Inventory.getInstance();
        if (part instanceof InHouse) {
            Outsourced newPart = new Outsourced(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), newInfo);
            return newPart;
            //invInstance.updatePart(newPart.getId(),newPart);
        } else if (part instanceof Outsourced) {
            InHouse newPart = new InHouse(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), Integer.parseInt(newInfo));
            return newPart;
            //invInstance.updatePart(newPart.getId(),newPart);
        }
        return null;
    }

    /**
     * Checks if input is integer.
     * @param input Takes string input to verify.
     * @return Returns boolean.
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;  // Valid integer
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if input is double.
     * @param input Takes string input to verify.
     * @return Returns boolean.
     */
    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
