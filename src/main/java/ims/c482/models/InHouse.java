package ims.c482.models;

/**
 * The In House class inherits the part class and constructs itself by adding a machine ID to its properties.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor used for instantiating In House parts.
     * @param id In House Part ID
     * @param name In House Part Name
     * @param price In House Part Price
     * @param stock In House Part Stock / Inventory
     * @param min In House Part Minimum Allowed Stock
     * @param max In House Part Maximum Allowed Stock
     * @param machineId In House Part Machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter method for returning the machine id.
     * @return Returns the machine id.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter method for setting the machine id.
     * @param machineId Takes the integer of the machine ID in for saving to the current instance's machine id.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
