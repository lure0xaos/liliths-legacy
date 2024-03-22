package com.lilithslegacy.game.sex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lilithslegacy.utils.Util;

/**
 * @author Innoxia
 * @version 0.3.3.10
 * @since 0.2.0
 */
public class SexActionInteractions {

    private Map<SexAreaInterface, List<SexAreaInterface>> interactions;

    private List<OrgasmCumTarget> availableCumTargets;
    private List<OrgasmCumTarget> providedCumTargets;

    //    @Deprecated
    public SexActionInteractions(
            Map<SexAreaInterface, List<SexAreaInterface>> interactions,
            List<OrgasmCumTarget> availableCumTargets) {
        this(interactions, availableCumTargets, null);
    }

    /**
     * A container for sex-interaction data.
     *
     * @param interactions        A map of performer sex areas to the target's available sex areas.
     * @param availableCumTargets A list of cum targets that the performer can use when orgasming.
     * @param providedCumTargets  A list of cum targets that this performer makes available to the target when the target is orgasming.
     */
    public SexActionInteractions(
            Map<SexAreaInterface, List<SexAreaInterface>> interactions,
            List<OrgasmCumTarget> availableCumTargets,
            List<OrgasmCumTarget> providedCumTargets) {

        if (interactions == null) {
            this.interactions = new HashMap<>();
        } else {
            this.interactions = interactions;
        }

        if (availableCumTargets == null) {
            this.availableCumTargets = Util.newArrayListOfValues(OrgasmCumTarget.FLOOR);
        } else {
            this.availableCumTargets = availableCumTargets;
        }

        if (providedCumTargets == null) {
            this.providedCumTargets = Util.newArrayListOfValues(OrgasmCumTarget.FLOOR);
        } else {
            this.providedCumTargets = providedCumTargets;
        }
    }

    /**
     * @return interactions map. The keys correspond to the performing characters area, while the values are lists of areas (owned by the targeted character) that can be targeted by the key.
     */
    public Map<SexAreaInterface, List<SexAreaInterface>> getInteractions() {
        return interactions;
    }

    public void setInteractions(Map<SexAreaInterface, List<SexAreaInterface>> interactions) {
        this.interactions = interactions;
    }

    public List<OrgasmCumTarget> getAvailableCumTargets() {
        return availableCumTargets;
    }

    public void setAvailableCumTargets(List<OrgasmCumTarget> availableCumTargets) {
        this.availableCumTargets = availableCumTargets;
    }

    public List<OrgasmCumTarget> getProvidedCumTargets() {
        return providedCumTargets;
    }
}
