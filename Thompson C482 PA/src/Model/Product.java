package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Get id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     * @param id id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get name
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set price
     * @param price price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set stock
     * @param stock stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get min
     * @return minimum number
     */
    public int getMin() {
        return min;
    }

    /**
     * Set min
     * @param min min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get max
     * @return maximum number
     */
    public int getMax() {
        return max;
    }

    /**
     * Set max.
     * @param max max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets all associated parts.
     * @return list of associated parts;
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Adds a part to associatedParts.
     * @param part Part object to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes the selected part from the associated parts list.
     * @param selectedAssociatedPart associated part to delete
     * @return boolean that determines if the selectedAssociatedPart exists
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        boolean exists;
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            exists = true;
        }
        else {
            exists = false;
        }
        return exists;
    }
}
