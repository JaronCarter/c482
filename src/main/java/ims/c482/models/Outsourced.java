package ims.c482.models;

/**
 * Outsourced class that inherits the Part class.
 */
public class Outsourced extends Part {
    /** Company Name variable for use in outsourced part instantiation. **/
    private String companyName;

    /**
     * Outsourced constructor. Takes the following parameters:
     * @param id Outsourced part ID
     * @param name Outsourced part name
     * @param price Outsourced part price
     * @param stock Outsourced part stock / inventory
     * @param min Outsourced part minimum stock
     * @param max Outsourced part maximum stock
     * @param companyName Outsourced part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter method for returning the company name.
     * @return Returns a string of the company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter method for setting the company name to an outsourced part object.
     * @param companyName Takes in the new company name for setting.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
