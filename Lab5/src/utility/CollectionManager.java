package utility;

import java.time.LocalDateTime;
import java.util.Stack;
import data.Vehicle;
import exceptions.CollectionIsEmptyException;

/**
 * Operates the collection itself.
 */
public class CollectionManager {
    private Stack<Vehicle> vehicleCollection =  new Stack<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
        loadCollection();
    }
    
    /**
     * @return The collecton itself.
     */
    public Stack<Vehicle> getCollection() {
        return vehicleCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return vehicleCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return vehicleCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public Vehicle getFirst() {
        if (vehicleCollection.isEmpty()) return null;
        return vehicleCollection.firstElement();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public Vehicle getLast() {
        if (vehicleCollection.isEmpty()) return null;
        return vehicleCollection.lastElement();
    }

    /**
     * @param id ID of the vehicle.
     * @return A vehicle by his ID or null if vehicle isn't found.
     */
    public Vehicle getById(int id) {
        for (Vehicle vehicle : vehicleCollection) {
            if (vehicle.getId().equals(id)) return vehicle;
        }
        return null;
    }
    /**
     * @param enginePower EnginePower of the vehicle.
     * @return A vehicle by his enginePower or null if marine isn't found.
     */
    public Vehicle getByEnginePower(Integer enginePower) {
        for (Vehicle vehicle : vehicleCollection) {
            if (vehicle.getEnginePower().equals(enginePower)) return vehicle;
        }
        return null;
    }
    /**
     * @param vehicleToFind A Vehicle who's value will be found.
     * @return A vehicle by his value or null if vehicle isn't found.
     */
    public Vehicle getByValue(Vehicle vehicleToFind) {
        for (Vehicle vehicle : vehicleCollection) {
            if (vehicle.equals(vehicleToFind)) return vehicle;
        }
        return null;
    }


    /**
     * @return Vehicle, who has max EnginePower.
     * @throws CollectionIsEmptyException If collection is empty.
     */
    public Vehicle GetMax(){
        Vehicle maxVehicle = getFirst();
        for (Vehicle vehicle : vehicleCollection) {
            if (vehicle.compareTo(maxVehicle) > 0) {
                maxVehicle = vehicle;
            }
        }
        return maxVehicle;
    }
    /**
     * @return Vehicle, who has min distance travelled.
     * @throws CollectionIsEmptyException If collection is empty.
     */
    public Vehicle minByDistanceTravelled() throws CollectionIsEmptyException {
        if (vehicleCollection.isEmpty()) throw new CollectionIsEmptyException();

        Vehicle maxVehicle = getFirst();
        for (Vehicle vehicle : vehicleCollection) {
            if (vehicle.getDistanceTravelled() < maxVehicle.getDistanceTravelled()) {
                maxVehicle = vehicle;
            }
        }
        return maxVehicle;
    }

    /**
     * @param name filtered vehicle.
     * @return compare name with string.
     */

    public String vehicleFilteredInfo(String name) {
        String info = "";
        for (Vehicle vehicle : vehicleCollection) {
            if (vehicle.getName().contains(name)) {
                info += vehicle + "\n\n";
            }
        }
        return info.trim();
    }

    /**
     * Adds a new vehicle to collection.
     * @param vehicle to add.
     */
    public void addToCollection(Vehicle vehicle) {
        vehicleCollection.add(vehicle);
    }

    /**
     * Removes a new vehicle to collection.
     * @param vehicle A vehicle to remove.
     */
    public void removeFromCollection(Vehicle vehicle) {
        vehicleCollection.remove(vehicle);
    }

    /**
     * Remove vehicles greater than the selected one.
     * @param vehicleToCompare A vehicle to compare with.
     */
    public void removeGreater(Vehicle vehicleToCompare) {
        vehicleCollection.removeIf(vehicle -> vehicle.compareTo(vehicleToCompare) > 0);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        vehicleCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public int generateNextId() {
        if (vehicleCollection.isEmpty()) return 1;
        return vehicleCollection.lastElement().getId() + 1;
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
            fileManager.writeCollection(vehicleCollection);
            lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        vehicleCollection = fileManager.ReadFile();
        lastInitTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (vehicleCollection.isEmpty()) return "Коллекция пуста!";

        String info = "";
        for (Vehicle vehicle : vehicleCollection) {
            info += vehicle;
            if (vehicle != vehicleCollection.lastElement()) info += "\n\n";
        }
        return info;
    }
}