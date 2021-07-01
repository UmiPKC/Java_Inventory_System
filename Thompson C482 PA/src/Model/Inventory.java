package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a Part to the allParts observable list.
     * @param newPart Part object that will be added to the allParts observable list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a Product to the allProducts observable list.
     * @param newProduct Product object that will be added to the allProducts observable list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches through allParts to find a Part object whose id matches the partId parameter.
     * @param partId Part id (int) that user is searching for
     * @return the specified Part object
     */
    public static Part lookupPart(int partId) {
        //leave like this for now
        Part searchedPart = null;
        for (Part p:allParts) {
            if (p.getId() == partId) {
                searchedPart = p;
            }
        }
        return searchedPart;
    }

    /**
     * Searches through allProducts to find a Product object whose id matches the productId parameter.
     * @param productId Product id (int) that user is searching for
     * @return the specified Product object
     */
    public static Product lookupProduct(int productId) {
        //leave like this for now
        Product searchedProduct = null;
        for (Product p:allProducts) {
            if (p.getId() == productId) {
                searchedProduct = p;
            }
        }
        return searchedProduct;
    }

    /**
     * Searches through allParts to find Part objects whose names contain the partName string param.
     * @param partName Text to match to any Part names
     * @return a list of Parts whose names contain partName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        //leave like this for now
        ObservableList<Part> searchedList = FXCollections.observableArrayList();
        for (Part p:allParts) {
            if (p.getName().contains(partName)) {
                searchedList.add(p);
            }
        }
        return searchedList;
    }

    /**
     * Searches through allProducts to find Product objects whose names contain the productName string param.
     * @param productName Text to match to any Product names
     * @return a list of Products whose names contain productName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        //leave like this for now
        ObservableList<Product> searchedList = FXCollections.observableArrayList();
        for (Product p:allProducts) {
            if (p.getName().contains(productName)) {
                searchedList.add(p);
            }
        }
        return searchedList;
    }

    /**
     * Updates a Part object by replacing the Part at the specified index with the new version of the Part.
     * @param index index of the Part to be updated
     * @param selectedPart replacement Part object with the modifications
     */
    public static void updatePart(int index, Part selectedPart) {
        //replace old version of part at index with new version of part
        getAllParts().remove(index);
        getAllParts().add(index, selectedPart);
    }

    /**
     * Update a Product object by replacing the Product at the specified index with the new version of the Product.
     * @param index index of the Product to be updated
     * @param newProduct replacement Product object with the modifications
     */
    public static void updateProduct(int index, Product newProduct) {
        getAllProducts().remove(index);
        getAllProducts().add(index, newProduct);
    }

    /**
     * Deletes part from allParts.
     * @param selectedPart Part object that is being passed into the method
     * @return exists boolean object to check if selectedPart exists
     */
    public static boolean deletePart(Part selectedPart) {
        boolean exists;
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            exists = true;
        }
        else {
            exists = false;
        }
        return exists;
    }

    /**
     * Deletes product from allProducts.
     * @param selectedProduct Product object that is being passed into the method
     * @return hasParts boolean object to check if selectedProduct still contains associated parts
     */
    public static boolean deleteProduct(Product selectedProduct) {
        boolean hasParts;
        if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            allProducts.remove(selectedProduct);
            hasParts = false;
        }
        else {
            hasParts = true;
        }
        return hasParts;
    }

    /**
     * Gets the allParts list.
     * @return allParts observable list of Part objects
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets the allProducts list.
     * @return allProducts observable list of Product objects
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
