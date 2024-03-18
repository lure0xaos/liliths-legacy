package com.lilithslegacy.game.character.body;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;

/**
 * @author Innoxia
 * @version 0.3.1
 * @since 0.1.0
 */
public interface BodyPartInterface {

    BodyPartTypeInterface getType();

    String getDeterminer(GameCharacter gc);

    String getName(GameCharacter gc);

    String getNameSingular(GameCharacter gc);

    String getNamePlural(GameCharacter gc);

    String getDescriptor(GameCharacter gc);

    /**
     * @return The name of this body part with its descriptor.
     */
    default String getName(GameCharacter gc, boolean withDescriptor) {
        if (withDescriptor) {
            if (getType().getDescriptor(gc) == null) {
                return getType().getName(gc);
            }

            return (!getType().getDescriptor(gc).isEmpty() ? getType().getDescriptor(gc) + " " : "") + getType().getName(gc);
        } else {
            return getType().getName(gc);
        }
    }

    /**
     * @return The name of this body part with its descriptor.
     */
    default String getNameSingular(GameCharacter gc, boolean withDescriptor) {
        if (withDescriptor) {
            if (getType().getDescriptor(gc) == null)
                return getType().getNameSingular(gc);

            return (!getType().getDescriptor(gc).isEmpty() ? getType().getDescriptor(gc) + " " : "") + getType().getNameSingular(gc);
        } else {
            return getType().getNameSingular(gc);
        }
    }

    default AbstractBodyCoveringType getBodyCoveringType(GameCharacter gc) {
        return getType().getBodyCoveringType(gc.getBody());
    }

    default AbstractBodyCoveringType getBodyCoveringType(Body body) {
        return getType().getBodyCoveringType(body);
    }

    /**
     * @return true if this part is (near enough to) 100% animal-like, with no anthropomorphic qualities at all. This will almost certainly only ever be seen on feral characters or characters who have a non-bipedal body.
     */
    boolean isFeral(GameCharacter owner);

}
