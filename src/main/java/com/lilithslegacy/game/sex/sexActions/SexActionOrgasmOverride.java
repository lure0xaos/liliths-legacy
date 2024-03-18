package com.lilithslegacy.game.sex.sexActions;

/**
 * @author Innoxia
 * @version 0.3.1
 * @since 0.3
 */
public abstract class SexActionOrgasmOverride {

    private final boolean endsSex;

    public SexActionOrgasmOverride(boolean endsSex) {
        this.endsSex = endsSex;
    }

    public abstract void applyEffects();

    public void applyEndEffects() {
    }

    public boolean isEndsSex() {
        return endsSex;
    }

    public abstract String getDescription();
}
