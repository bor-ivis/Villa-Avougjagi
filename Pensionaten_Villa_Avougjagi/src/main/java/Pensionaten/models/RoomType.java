package Pensionaten.models;

import lombok.Getter;

// Enum för de rumstyper systemet stödjer
@Getter
public enum RoomType {

    SINGLE("Enkelrum", 1),
    DOUBLE("Dubbelrum", 2);

    // Namn som visas i gränssnittet
    private final String displayName;

    // Grundkapacitet innan extrasängar räknas in
    private final int baseCapacity;

    RoomType(String displayName, int baseCapacity) {
        this.displayName = displayName;
        this.baseCapacity = baseCapacity;
    }

}