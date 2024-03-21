package com.lilithslegacy.game.character.body;

import java.util.List;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractFluidType;
import com.lilithslegacy.game.character.body.valueEnums.FluidFlavour;
import com.lilithslegacy.game.character.body.valueEnums.FluidModifier;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;

/**
 * @author Innoxia
 * @version 0.3.8.2
 * @since 0.2.6
 */
public interface FluidInterface extends BodyPartInterface {

    @Override
    public AbstractFluidType getType();

    public FluidFlavour getFlavour();

    public String setFlavour(GameCharacter owner, FluidFlavour flavour);


    public List<FluidModifier> getFluidModifiers();

    public boolean hasFluidModifier(FluidModifier fluidModifier);

    public String addFluidModifier(GameCharacter owner, FluidModifier fluidModifier);

    public String removeFluidModifier(GameCharacter owner, FluidModifier fluidModifier);


    public List<ItemEffect> getTransformativeEffects();

    public void addTransformativeEffect(ItemEffect ie);


    public float getValuePerMl();

}
