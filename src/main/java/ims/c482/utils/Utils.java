package ims.c482.utils;

import ims.c482.models.InHouse;
import ims.c482.models.Inventory;
import ims.c482.models.Outsourced;
import ims.c482.models.Part;

public class Utils {
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
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;  // Valid integer
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
