# ParkSmart — Smart Parking Lot Management System

**Developed by Rahith Razmeen**

ParkSmart is a Java desktop application designed to manage everyday parking-lot operations. It helps staff register vehicles, automatically assign suitable parking spaces, issue tickets, calculate parking charges, and review parking activity through a simple graphical interface.

The project demonstrates **Object-Oriented Programming (OOP)** concepts using Java, with a Swing-based desktop interface.

## Features

- **Vehicle registration:** Park cars, bikes, and vans by entering the vehicle type, registration number, owner name, and entry time.
- **Automatic slot allocation:** Assign the smallest available parking slot that fits the vehicle.
- **Parking tickets:** Generate sequential ticket numbers beginning with `T1001`.
- **Vehicle lookup:** Find an active parking ticket using the vehicle's registration number.
- **Exit and fee calculation:** Calculate the parking fee, close the ticket, and release the occupied slot when a vehicle exits.
- **Parking-slot management:** View slot availability and add new small, medium, or large slots.
- **Live dashboard:** Display the total number of slots, available slots, parked vehicles, and total earnings.
- **Active tickets and history:** View currently parked vehicles and completed parking records, with the most recent exits displayed first.
- **Input validation:** Handle invalid entries, duplicate vehicle registrations, unavailable slots, and unknown tickets.

## Parking Rates

| Vehicle | Minimum slot size | Hourly rate | Additional charge |
| --- | --- | ---: | ---: |
| Bike | Small | Rs. 50 | None |
| Car | Medium | Rs. 100 | None |
| Van | Large | Rs. 150 | Rs. 100 one-time service charge |

**Billing rule:** Parking duration is rounded up to the next whole hour, with a minimum charge of one hour.

For example, a van parked for 1 hour and 30 minutes is billed for 2 hours: `(2 × Rs. 150) + Rs. 100 = Rs. 400`.

## Technologies Used

- **Java 17** — application logic and OOP
- **Java Swing** — graphical user interface
- **FlatLaf 3.5.4** — modern interface styling
- **Apache Maven** — dependency and project management
- **JUnit 5** — automated unit tests
- **IntelliJ IDEA** — recommended development environment

## How to Run the Project

### Requirements

- Java Development Kit (**JDK 17** or a compatible newer version)
- **IntelliJ IDEA** or another Java IDE with Maven support
- An internet connection for Maven to download dependencies on the first build

### Run with IntelliJ IDEA

1. Download or extract the ParkSmart project.
2. Open **IntelliJ IDEA** and select **Open**.
3. Select the **`ParkSmart`** folder that contains `pom.xml` and `src`.
4. Allow IntelliJ IDEA to import the project and download the Maven dependencies.
5. Open `src/main/java/com/parksmart/ui/Main.java`.
6. Click the **Run** button beside the `main()` method.
7. The **ParkSmart** desktop application will open.

> The application starts with nine parking slots: three small, four medium, and two large. More slots can be added from the **Slots** tab.

## How to Use

1. **Park Vehicle:** Choose a vehicle type and enter the plate number, owner name, and entry time. Click **Park Vehicle** to receive a ticket and an assigned slot.
2. **View Slots:** Open the **Slots** tab to check which spaces are free or occupied. Add new slots if needed.
3. **Check Active Tickets:** Open **Active Tickets** to view vehicles currently in the parking lot.
4. **Exit & Payment:** Enter a ticket ID, or find the ticket by plate number. Choose the exit time and click **Exit & Pay** to calculate the charge and release the parking space.
5. **View History:** Open **History** to see completed parking sessions and their fees.

**Date and time format:** `yyyy-MM-dd HH:mm`

## Project Structure

```text
ParkSmart/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/com/parksmart/
    │       ├── exception/
    │       │   ├── ParkingException.java
    │       │   ├── ParkingFullException.java
    │       │   ├── TicketNotFoundException.java
    │       │   └── VehicleAlreadyParkedException.java
    │       ├── model/
    │       │   ├── Vehicle.java
    │       │   ├── Car.java
    │       │   ├── Bike.java
    │       │   ├── Van.java
    │       │   ├── ParkingLot.java
    │       │   ├── ParkingSlot.java
    │       │   ├── SlotType.java
    │       │   └── Ticket.java
    │       └── ui/
    │           ├── Main.java
    │           └── ParkSmartApp.java
    └── test/
        └── java/com/parksmart/
            ├── ParkSmartTest.java
            └── MyOwnTests.java
```

## Object-Oriented Programming Concepts

| OOP concept | Use in ParkSmart |
| --- | --- |
| **Encapsulation** | Classes keep their fields private and expose methods for controlled access to data. |
| **Inheritance** | `Car`, `Bike`, and `Van` extend the common `Vehicle` class. |
| **Abstraction** | The abstract `Vehicle` class defines shared vehicle information and methods each vehicle type must provide. |
| **Polymorphism** | Different vehicle classes supply their own rates and required slot types; `Van` overrides `calculateFee()` to include a service charge. |

The project also uses **collections**, **enums**, **custom exceptions**, and **Java date/time APIs**.

## Testing

The project includes **20 JUnit tests** in `ParkSmartTest.java` for core functionality, including vehicle types, slot assignment, ticket creation, fee calculation, completed-history ordering, and exception handling.

To run the automated tests, use the IntelliJ IDEA test runner or execute the following command inside the folder containing `pom.xml`:

```bash
mvn test
```

`MyOwnTests.java` also contains three **disabled test templates** that have not yet been implemented.

## Current Limitations

- Parking records and earnings are stored **in memory only**; they reset when the application is closed.
- The **Export to CSV (bonus)** button is visible, but the export functionality is **not yet implemented**.
- The application records calculated charges, but it does not connect to an external payment gateway or process real payments.

## Future Improvements

Potential improvements include database storage for parking history, CSV report export, login access for staff, and additional management reports.

## Developer

**Rahith Razmeen**  
**Project:** ParkSmart — Smart Parking Lot Management System  
**Category:** Java Desktop Application / Object-Oriented Programming Project

---

*Project documentation based on the current ParkSmart source code.*
