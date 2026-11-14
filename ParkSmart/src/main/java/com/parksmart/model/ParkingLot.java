package com.parksmart.model;

import com.parksmart.exception.ParkingException;
import com.parksmart.exception.TicketNotFoundException;
import com.parksmart.exception.ParkingFullException;
import com.parksmart.exception.VehicleAlreadyParkedException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ParkingLot {

    private String name;
    private List<ParkingSlot> slots = new ArrayList<>();
    private List<Ticket> activeTickets = new ArrayList<>();
    private List<Ticket> completedTickets = new ArrayList<>();
    private double totalEarnings = 0;
    private int nextTicketNumber = 1001;

    public ParkingLot(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public void addSlot(String slotId, SlotType type) {
        ParkingSlot newSlot = new ParkingSlot(slotId, type);

        for (ParkingSlot slot : slots) {
            if (slot.getSlotId().equalsIgnoreCase(newSlot.getSlotId())) {
                throw new IllegalArgumentException("Duplicate slot ID");
            }
        }

        slots.add(newSlot);
    }

    public Ticket parkVehicle(Vehicle vehicle, LocalDateTime entryTime)
            throws ParkingException {

        if (vehicle == null || entryTime == null) {
            throw new IllegalArgumentException("Vehicle and entry time are required");
        }

        // Check whether the vehicle is already parked
        for (Ticket ticket : activeTickets) {
            if (ticket.getVehicle().getPlateNumber()
                    .equalsIgnoreCase(vehicle.getPlateNumber())) {
                throw new VehicleAlreadyParkedException(
                        "Vehicle already parked");
            }
        }

        // Find the smallest suitable available slot
        ParkingSlot bestSlot = null;

        for (ParkingSlot slot : slots) {
            if (slot.isAvailable()
                    && slot.getSlotType().canFit(vehicle.getRequiredSlotType())) {

                if (bestSlot == null ||
                        slot.getSlotType().ordinal()
                                < bestSlot.getSlotType().ordinal()) {
                    bestSlot = slot;
                }
            }
        }

        if (bestSlot == null) {
            throw new ParkingFullException(
                    "No suitable parking slot available");
        }

        // Park the vehicle
        bestSlot.park(vehicle);

        // Generate ticket
        Ticket ticket = new Ticket(
                "T" + nextTicketNumber++,
                vehicle,
                bestSlot,
                entryTime
        );

        activeTickets.add(ticket);

        return ticket;
    }

    public double exitVehicle(String ticketId, LocalDateTime exitTime)
            throws TicketNotFoundException {

        Iterator<Ticket> iterator = activeTickets.iterator();

        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();

            if (ticket.getTicketId().equalsIgnoreCase(ticketId)) {

                long hours = calculateChargedHours(
                        ticket.getEntryTime(), exitTime);

                double fee = ticket.getVehicle().calculateFee(hours);

                // Free the parking slot
                ticket.getSlot().removeVehicle();

                // Close the ticket
                ticket.close(exitTime, fee);

                // Move ticket to completed history
                iterator.remove();
                completedTickets.add(ticket);

                // Update earnings
                totalEarnings += fee;

                return fee;
            }
        }

        throw new TicketNotFoundException("Ticket not found");
    }

    public static long calculateChargedHours(
            LocalDateTime entryTime, LocalDateTime exitTime) {

        if (entryTime == null || exitTime == null
                || exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Invalid entry or exit time");
        }

        Duration duration = Duration.between(entryTime, exitTime);

        long seconds = duration.getSeconds();

        long hours = seconds / 3600;

        if (seconds % 3600 != 0 || duration.getNano() > 0) {
            hours++;
        }

        return Math.max(1, hours);
    }

    public Ticket findTicketByPlate(String plateNumber)
            throws TicketNotFoundException {

        if (plateNumber != null) {
            for (Ticket ticket : activeTickets) {
                if (ticket.getVehicle().getPlateNumber()
                        .equalsIgnoreCase(plateNumber.trim())) {
                    return ticket;
                }
            }
        }

        throw new TicketNotFoundException("Vehicle ticket not found");
    }

    public List<ParkingSlot> getAllSlots() {
        return new ArrayList<>(slots);
    }

    public List<ParkingSlot> getAvailableSlots() {
        List<ParkingSlot> available = new ArrayList<>();

        for (ParkingSlot slot : slots) {
            if (slot.isAvailable()) {
                available.add(slot);
            }
        }

        return available;
    }

    public int getAvailableSlotCount() {
        return getAvailableSlots().size();
    }

    public List<Ticket> getActiveTickets() {
        return new ArrayList<>(activeTickets);
    }

    public List<Ticket> getCompletedTickets() {
        List<Ticket> history = new ArrayList<>(completedTickets);

        history.sort(
                Comparator.comparing(Ticket::getExitTime).reversed()
        );

        return history;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void exportHistoryToCsv(String filePath) throws IOException {
        throw new UnsupportedOperationException(
                "CSV export is not written yet.");
    }
}