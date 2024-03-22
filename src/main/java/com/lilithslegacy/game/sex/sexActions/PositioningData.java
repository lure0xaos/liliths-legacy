package com.lilithslegacy.game.sex.sexActions;

import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;

import java.util.List;

/**
 * For use in position request SexActions.
 *
 * @author Innoxia
 * @version 0.3.1
 * @since 0.3.1
 */
public class PositioningData {

    private AbstractSexPosition position;
    private List<SexSlot> performerSlots;
    private List<SexSlot> partnerSlots;

    /**
     * @param performerSlots Slot 0 is always used as the slot for the one performing the associated action.
     * @param partnerSlots   Slot 0 is always used as the slot for the one targeted by the associated action.
     */
    public PositioningData(AbstractSexPosition position, List<SexSlot> performerSlots, List<SexSlot> partnerSlots) {
        this.position = position;
        this.performerSlots = performerSlots;
        this.partnerSlots = partnerSlots;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof PositioningData)
                && ((PositioningData) o).getPosition() == this.getPosition()
                && ((PositioningData) o).getPerformerSlots().equals(this.getPerformerSlots())
                && ((PositioningData) o).getPartnerSlots().equals(this.getPartnerSlots());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getPosition().hashCode();
        result = 31 * result + this.getPerformerSlots().hashCode();
        result = 31 * result + this.getPartnerSlots().hashCode();
        return result;
    }

    public AbstractSexPosition getPosition() {
        return position;
    }

    /**
     * Slot 0 is always used as the slot for the one performing the associated action.
     */
    public List<SexSlot> getPerformerSlots() {
        return performerSlots;
    }

    /**
     * Slot 0 is always used as the slot for the one targeted by the associated action.
     */
    public List<SexSlot> getPartnerSlots() {
        return partnerSlots;
    }

}
