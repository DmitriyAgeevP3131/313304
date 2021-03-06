package commands;

import data.Coordinates;
import data.FuelType;
import data.Vehicle;
import data.VehicleType;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongInputFormatException;
import utility.CollectionManager;
import utility.Console;
import utility.VehicleAsker;

import java.time.LocalDateTime;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final VehicleAsker vehicleAsker;

    public UpdateCommand(CollectionManager collectionManager, VehicleAsker vehicleAsker) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
        this.vehicleAsker = vehicleAsker;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) {
                Console.println("использование: '" + getName() + "'");
                return false;
            }
            if (collectionManager.collectionSize() == 0) {
                Console.printerror("Коллекция пуста!");
            }

            int id = Integer.parseInt(argument);
            Vehicle oldVehicle = collectionManager.getById(id);
            if (oldVehicle == null) {
                Console.printerror("Vehicle с таким ID в коллекции нет!");
                return false;
            }

            String name = oldVehicle.getName();
            Coordinates coordinates = oldVehicle.getCoordinates();
            LocalDateTime creationDate = oldVehicle.getCreationDate();
            int enginePower = oldVehicle.getEnginePower();
            int distanceTravelled = oldVehicle.getDistanceTravelled();
            VehicleType type = oldVehicle.getType();
            FuelType fuelType = oldVehicle.getFuelType();

            collectionManager.removeFromCollection(oldVehicle);

            if (vehicleAsker.askQuestion("Хотите изменить имя средства передвижения?"))
                name = vehicleAsker.inputVehicleName();
            if (vehicleAsker.askQuestion("Хотите изменить координаты средства передвижения?"))
                coordinates = vehicleAsker.getCoordinates();
            if (vehicleAsker.askQuestion("Хотите изменить силу двигателя передвижения?"))
                enginePower = vehicleAsker.inputEnPower();
            if (vehicleAsker.askQuestion("Хотите изменить дистанцию путешествия средства передвижения?"))
                distanceTravelled = vehicleAsker.inputDistTravel();
            if (vehicleAsker.askQuestion("Хотите изменить тип средства передвижения передвижения?"))
                type = vehicleAsker.inputVType();
            if (vehicleAsker.askQuestion("Хотите изменить тип топлива средства передвижения?"))
                fuelType = vehicleAsker.inputFType();

            collectionManager.addToCollection(new Vehicle(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    enginePower,
                    distanceTravelled,
                    type,
                    fuelType
            ));
            Console.println("Vehicle успешно изменен!");
            return true;
        } catch (NumberFormatException exception) {
            Console.printerror("ID должен быть представлен числом!");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Ошибка исполнения скрипта!");
        } catch (WrongInputFormatException exception) {
            Console.printerror("Не то");
        }
        return false;
    }
}
