package Model;

public class OutsourcedPart extends Part {
    private String companyName;

    /**
     * OutsourcedPart constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get company name
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
