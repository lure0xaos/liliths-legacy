package com.lilithslegacy.game.dialogue.places.dominion.nightlife;

import com.lilithslegacy.world.places.AbstractPlaceType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.3.1
 * @since 0.2.10
 */
public enum ClubberBehaviour {

    INTRODUCTION() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_MAIN_AREA;
        }
    },

    LEAVES() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_MAIN_AREA;
        }
    },

    BAR_DRINK() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_BAR;
        }
    },
    BAR_TALK() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_BAR;
        }
    },
    BAR_FLIRT() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_BAR;
        }
    },
    BAR_KISS() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_BAR;
        }
    },
    BAR_GROPE() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_BAR;
        }
    },
    BAR_INVITE_HOME() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_BAR;
        }
    },

    DANCE() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_DANCE_FLOOR;
        }
    },
    DANCE_KISS() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_DANCE_FLOOR;
        }
    },
    DANCE_GROPE() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_DANCE_FLOOR;
        }
    },

    SIT_DOWN_TALK() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_SEATING_AREA;
        }
    },
    SIT_DOWN_FLIRT() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_SEATING_AREA;
        }
    },
    SIT_DOWN_KISS() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_SEATING_AREA;
        }
    },
    SIT_DOWN_FOOTSIE() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_SEATING_AREA;
        }
    },
    SIT_DOWN_SEX() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_SEATING_AREA;
        }
    },
    SIT_DOWN_INVITE_HOME() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_SEATING_AREA;
        }
    },

    TOILETS() {
        @Override
        public AbstractPlaceType getPlaceType() {
            return PlaceType.WATERING_HOLE_TOILETS;
        }
    };

    public abstract AbstractPlaceType getPlaceType();

}
