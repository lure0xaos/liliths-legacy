package com.lilithslegacy.world;

/**
 * @author Innoxia
 * @version 0.3.5
 * @since 0.3.5
 */
public enum TeleportPermissions {

    NONE(false, false),

    INCOMING_ONLY(true, false),

    OUTGOING_ONLY(false, true),

    BOTH(true, true);

    private final boolean incoming;
    private final boolean outgoing;

    TeleportPermissions(boolean incoming, boolean outgoing) {
        this.incoming = incoming;
        this.outgoing = outgoing;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public boolean isOutgoing() {
        return outgoing;
    }

}
