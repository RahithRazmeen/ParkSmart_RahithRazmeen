package com.parksmart.model;

import java.time.LocalDateTime;

/** A parking ticket. It is "active" until the vehicle leaves. */
public class Ticket {

    private String tickerID;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot, LocalDateTime entryTime) {

        this.tickerID = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.fee = 0.0;

    }

    public String getTicketId() {
        return tickerID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    /** Null while the vehicle is still parked. */
    public LocalDateTime getExitTime() {
        return exitTime;
    }

    /** 0 while the vehicle is still parked. */
    public double getFee() {

        return fee;
    }

    /** True until the ticket is closed. */
    public boolean isActive() {
        return exitTime == null;
    }

    /**
     * Closes the ticket when the vehicle leaves.
     * Rule: if the ticket is already closed -> throw IllegalStateException.
     */
    public void close(LocalDateTime exitTime, double fee) {

        if (!isActive()) {
            throw new IllegalArgumentException("Ticket already closed");
        }

        this.exitTime = exitTime;
        this.fee = fee;

    }
}
