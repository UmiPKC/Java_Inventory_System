package Model;


public class InhousePart extends Part{
    private int machineId;

    /**
     * InhousePart constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param machineId
     */
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets machine ID
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Get machine ID
     * @return machine ID
     */
    public int getMachineId() {
        return machineId;
    }


}
