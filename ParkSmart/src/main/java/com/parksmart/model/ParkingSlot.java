package com.parksmart.model;

/** One parking space. It can hold one vehicle at a time. */
public class ParkingSlot {

private String slotID;
private SlotType slotType;
private Vehicle parkedVehicle;

    /**
     * Creates an empty slot.
     * Rules: slot ID cannot be empty and slot type cannot be null -> throw IllegalArgumentException.
     *        Save the slot ID in capital letters.
     */
    public ParkingSlot(String slotId, SlotType slotType) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new IllegalArgumentException("Slot ID cannot be empty");
        }

        if (slotType == null ) {
            throw new IllegalArgumentException("Slot type cannot be null");
        }

        this.slotID = slotId.trim().toUpperCase();
        this.slotType = slotType;
        this.parkedVehicle = null;
    }

    public String getSlotId() {

        return slotID;
    }

    public SlotType getSlotType() {

        return slotType;
    }

    /** True when no vehicle is parked here. */
    public boolean isAvailable() {

        return parkedVehicle == null;
    }

    /** The vehicle parked here, or null if the slot is free. */
    public Vehicle getParkedVehicle() {

        return parkedVehicle;
    }

    /**
     * Puts a vehicle in this slot.
     * Rule: if the slot is already taken -> throw IllegalStateException.
     */
    public void park(Vehicle vehicle) {

        if (!isAvailable()) {
            throw new IllegalArgumentException("Slot is already occupied");
        }

        parkedVehicle = vehicle;

    }

    /** Empties the slot and returns the vehicle that was in it. */
    public Vehicle removeVehicle() {
        Vehicle vehicle = parkedVehicle;
        parkedVehicle = null;
        return parkedVehicle;
    }
}
