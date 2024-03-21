package com.lilithslegacy.world.population;

/**
 * @author Innoxia
 * @version 0.3.7.7
 * @since 0.3.7.7
 */
public abstract class AbstractPopulationType {

    private String name;
    private String namePlural;

    public AbstractPopulationType(String name, String namePlural) {
        this.name = name;
        this.namePlural = namePlural;
    }

    public String getName() {
        return name;
    }

    public String getNamePlural() {
        return namePlural;
    }
}
