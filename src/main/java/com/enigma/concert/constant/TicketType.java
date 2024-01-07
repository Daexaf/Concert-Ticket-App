package com.enigma.concert.constant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum TicketType {
    REGULAR("Regular"),
    VIP("VIP"),
    VVIP("VVIP");

    private final String displayName;

    TicketType(String displayName) {
        this.displayName = displayName;
    }


}
