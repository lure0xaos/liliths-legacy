package com.lilithslegacy.game.character.body.types;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.OrificeDepth;
import com.lilithslegacy.game.character.body.valueEnums.OrificeElasticity;
import com.lilithslegacy.game.character.body.valueEnums.OrificeModifier;
import com.lilithslegacy.game.character.body.valueEnums.OrificePlasticity;
import com.lilithslegacy.game.character.body.valueEnums.Wetness;

/**
 * @author Innoxia
 * @version 0.3.9
 * @since 0.1.83
 */
public interface OrificeInterface {

    Wetness getWetness(GameCharacter owner);

    String setWetness(GameCharacter owner, int wetness);

    Capacity getCapacity();

    float getRawCapacityValue();

    String setCapacity(GameCharacter owner, float capacity, boolean setStretchedValueToNewValue);

    float getStretchedCapacity();

    boolean setStretchedCapacity(float stretchedCapacity);

    /**
     * @return The minimum OrificeDepth which this orifice needs in order to comfortably accommodate the provided insertionSize.
     */
    default OrificeDepth getMinimumDepthForSizeComfortable(GameCharacter owner, int insertionSize) {
        OrificeDepth depth = OrificeDepth.ONE_SHALLOW;
        while (getMaximumPenetrationDepthComfortable(owner, depth) < insertionSize) {
            if (depth == OrificeDepth.SEVEN_FATHOMLESS) {
                return depth;
            }
            depth = OrificeDepth.getDepthFromInt(depth.getValue() + 1);
        }
        return depth;
    }

    /**
     * @return The minimum OrificeDepth which this orifice needs in order to uncomfortably accommodate the provided insertionSize.
     */
    default OrificeDepth getMinimumDepthForSizeUncomfortable(GameCharacter owner, int insertionSize) {
        OrificeDepth depth = OrificeDepth.ONE_SHALLOW;
        while (getMaximumPenetrationDepthUncomfortable(owner, depth) < insertionSize) {
            if (depth == OrificeDepth.SEVEN_FATHOMLESS) {
                return depth;
            }
            depth = OrificeDepth.getDepthFromInt(depth.getValue() + 1);
        }
        return depth;
    }

    /**
     * @return The maximum depth at which penetration size can be considered comfortable. (Uses the getDepth(owner) depth for calculation.)
     */
    default int getMaximumPenetrationDepthComfortable(GameCharacter owner) {
        return getMaximumPenetrationDepthComfortable(owner, getDepth(owner));
    }

    /**
     * @return The maximum depth at which penetration size can be considered comfortable when this orifice has the provided depth.<br/>
     * <b>You should probably be using getMaximumPenetrationDepthComfortable(GameCharacter owner)</b>
     */
    int getMaximumPenetrationDepthComfortable(GameCharacter owner, OrificeDepth depth);

    /**
     * @return The maximum depth at which penetration size can be accommodated. (Uses the getDepth(owner) depth for calculation.)
     */
    default int getMaximumPenetrationDepthUncomfortable(GameCharacter owner) {
        return getMaximumPenetrationDepthUncomfortable(owner, getDepth(owner));
    }

    /**
     * @return The maximum depth at which penetration size can be accommodated when this orifice has the provided depth.<br/>
     * <b>You should probably be using getMaximumPenetrationDepthUncomfortable(GameCharacter owner)</b>
     */
    int getMaximumPenetrationDepthUncomfortable(GameCharacter owner, OrificeDepth depth);

    OrificeDepth getDepth(GameCharacter owner);

    String setDepth(GameCharacter owner, int depth);

    OrificeElasticity getElasticity();

    String setElasticity(GameCharacter owner, int elasticity);

    OrificePlasticity getPlasticity();

    String setPlasticity(GameCharacter owner, int plasticity);

    boolean isVirgin();

    void setVirgin(boolean virgin);

    boolean hasOrificeModifier(OrificeModifier modifier);

    String addOrificeModifier(GameCharacter owner, OrificeModifier modifier);

    String removeOrificeModifier(GameCharacter owner, OrificeModifier modifier);

}
