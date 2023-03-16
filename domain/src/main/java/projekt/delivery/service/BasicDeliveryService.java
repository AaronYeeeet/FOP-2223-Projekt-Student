package projekt.delivery.service;

import projekt.delivery.event.Event;
import projekt.delivery.routing.ConfirmedOrder;

import projekt.delivery.routing.Vehicle;
import projekt.delivery.routing.VehicleManager;

import java.util.ArrayList;

import java.util.List;

/**
 * A very simple delivery service that distributes orders to compatible vehicles in a FIFO manner.
 */
public class BasicDeliveryService extends AbstractDeliveryService {

    // List of orders that have not yet been loaded onto delivery vehicles
    protected final List<ConfirmedOrder> pendingOrders = new ArrayList<>();

    public BasicDeliveryService(
        VehicleManager vehicleManager
    ) {
        super(vehicleManager);
    }

    @Override
    protected List<Event> tick(long currentTick, List<ConfirmedOrder> newOrders) {
        List<Event> asdf = vehicleManager.tick(currentTick);
        pendingOrders.addAll(newOrders);

        for (int i = 0; i < pendingOrders.size(); i++) {
            for (int j = 0; j < pendingOrders.size() - i - 1; j++)
                if (pendingOrders.get(j).getDeliveryInterval().start() > pendingOrders.get(j + 1).getDeliveryInterval().start()) {

                    ConfirmedOrder asas = pendingOrders.get(j);
                    pendingOrders.set(j,pendingOrders.get(j + 1));
                    pendingOrders.set((j+1),asas);
                }
        }
        for (Vehicle vehicle : vehicleManager.getVehicles()) {
            double weight = 0;
            List<ConfirmedOrder> tempstorage = new ArrayList<>();
            for (ConfirmedOrder order : pendingOrders) {
                if (vehicle.getCapacity() >= (order.getWeight()+weight) && order.getRestaurant()==vehicle.getOccupied()) {
                    vehicle.getOrders().add(order);
                    weight+=order.getWeight();
                    tempstorage.add(order);
                }
            }
            pendingOrders.removeAll(tempstorage);
        }
        return asdf;
    }

    @Override
    public List<ConfirmedOrder> getPendingOrders() {
        return pendingOrders;
    }

    @Override
    public void reset() {
        super.reset();
        pendingOrders.clear();
    }

    public interface Factory extends DeliveryService.Factory {

        BasicDeliveryService create(VehicleManager vehicleManager);
    }
}
