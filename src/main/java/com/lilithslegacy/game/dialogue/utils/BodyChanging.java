package com.lilithslegacy.game.dialogue.utils;

import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.BodyPartInterface;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringCategory;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.FaceType;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.body.types.TailType;
import com.lilithslegacy.game.character.body.types.WingType;
import com.lilithslegacy.game.character.body.valueEnums.BodyMaterial;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
import com.lilithslegacy.game.character.npc.misc.Elemental;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.character.race.RacialBody;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.DialogueNodeType;
import com.lilithslegacy.game.dialogue.places.dominion.shoppingArcade.SuccubisSecrets;
import com.lilithslegacy.game.dialogue.places.dominion.slaverAlley.ScarlettsShop;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.SVGImages;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * @author Innoxia
 * @version 0.3.9.1
 * @since 0.1.90
 */
public class BodyChanging {

    private static final List<AbstractRace> allRaces = new ArrayList<>(Race.getAllRaces());
    public static String loadConfirmationName = "";
    public static String overwriteConfirmationName = "";
    public static String deleteConfirmationName = "";
    private static GameCharacter target;
    private static DialogueNode coreNode;
    private static int defaultResponseTab;
    private static boolean debugMenu;
    /**
     * Mapping file location to a Value of saved name and Body.
     */
    private static Map<String, Value<String, Body>> presetTransformationsMap = new HashMap<>();

    public static boolean isDebugMenu() {
        return debugMenu;
    }

    public static GameCharacter getTarget() {
        if (target == null) {
            return Main.game.getPlayer();
        }
        return target;
    }

    public static void setTarget(GameCharacter target) {
        BodyChanging.target = target;
        BodyChanging.coreNode = null;
        BodyChanging.debugMenu = false;
    }

    private static String getPowers() {
        return (isDemonTFMenu() ? "demonic" : "innate") + " transformative powers on changing";
    }

    public static void setTarget(GameCharacter target, DialogueNode coreNode, int defaultResponseTab) {
        BodyChanging.target = target;
        BodyChanging.coreNode = coreNode;
        BodyChanging.defaultResponseTab = defaultResponseTab;
        BodyChanging.debugMenu = false;
    }

    /**
     * @param target
     * @param debugMenu If this menu is accessed from the debug menu.
     */
    public static void setTarget(GameCharacter target, boolean debugMenu) {
        BodyChanging.target = target;
        BodyChanging.coreNode = null;
        BodyChanging.debugMenu = debugMenu;
    }

    private static Response getBodyChangingResponse(int responseTab, int index) {
        if (index == 1) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_CORE) {
                return new Response("Core", "You are already in this screen!", null);
            }
            return new Response("Core",
                    UtilText.parse(getTarget(), "Change core aspects of [npc.namePos] body."),
                    BODY_CHANGING_CORE);

        } else if (index == 2) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_EYES) {
                return new Response("Eyes", "You are already in this screen!", null);
            }
            return new Response("Eyes",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] eyes."),
                    BODY_CHANGING_EYES);

        } else if (index == 3) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_HAIR) {
                return new Response("Hair", "You are already in this screen!", null);
            }
            return new Response("Hair",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] hair."),
                    BODY_CHANGING_HAIR);

        } else if (index == 4) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_HEAD) {
                return new Response("Head", "You are already in this screen!", null);
            }
            return new Response("Head",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] face and head."),
                    BODY_CHANGING_HEAD);

        } else if (index == 5) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_ASS) {
                return new Response("Ass", "You are already in this screen!", null);
            }
            return new Response("Ass",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] ass."),
                    BODY_CHANGING_ASS);

        } else if (index == 6) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_BREASTS) {
                return new Response("Breasts", "You are already in this screen!", null);
            } else if (!BodyChanging.getTarget().hasNipples()) {
                return new Response("Breasts",
                        UtilText.parse(getTarget(), "[npc.Name] [npc.do] not have any breasts!"),
                        null);
            }
            return new Response("Breasts",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] breasts."),
                    BODY_CHANGING_BREASTS);

        } else if (index == 7) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_VAGINA) {
                return new Response("Vagina", "You are already in this screen!", null);
            }
            return new Response("Vagina",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] vagina."),
                    BODY_CHANGING_VAGINA);

        } else if (index == 8) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_PENIS) {
                return new Response("Penis", "You are already in this screen!", null);
            }
            return new Response("Penis",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] penis."),
                    BODY_CHANGING_PENIS);

        } else if (index == 9) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_SPINNERET) {
                return new Response("Spinneret", "You are already in this screen!", null);
            } else if (!BodyChanging.getTarget().hasSpinneret()) {
                return new Response("Spinneret",
                        UtilText.parse(getTarget(), "[npc.Name] [npc.do] not have a spinneret!<br/><i>Spinnerets are gained via certain tail or leg types.</i>"),
                        null);
            }
            return new Response("Spinneret",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] spinneret."),
                    BODY_CHANGING_SPINNERET);

        } else if (index == 10) {
            String title = BodyChanging.getTarget().getBreastCrotchShape() == BreastShape.UDDERS ? "Udders" : "Crotch-boobs";
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_BREASTS_CROTCH) {
                return new Response(title, "You are already in this screen!", null);
            }
            return new Response(title,
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] [npc.crotchBoobs]."),
                    BODY_CHANGING_BREASTS_CROTCH);

        } else if (index == 11) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_SAVE_LOAD) {
                return new Response("Save/Load", "You are already in this screen!", null);
            }
            return new Response("Save/Load",
                    UtilText.parse(getTarget(), "Save or load transformation presets, allowing you to quickly switch your appearance."),
                    BODY_CHANGING_SAVE_LOAD) {
                @Override
                public void effects() {
                    initSaveLoadMenu();
                }
            };

        } else if (index == 12 && debugMenu) {
            if (Main.game.getCurrentDialogueNode() == BODY_CHANGING_MAKEUP) {
                return new Response("Makeup (debug)", "You are already in this screen!", null);
            }
            return new Response("Makeup (debug)",
                    UtilText.parse(getTarget(), "Change aspects of [npc.namePos] makeup. (This transformation menu is only available in the debug screen.)"),
                    BODY_CHANGING_MAKEUP);

        } else if (index == 0) {
            if (debugMenu) {
                return new Response("Back", "Return to the previous screen.", DebugDialogue.DEBUG_MENU);

            } else if (coreNode != null) {
                return new Response("Back", "Return to the previous screen.", coreNode) {
                    @Override
                    public void effects() {
                        Main.game.setResponseTab(defaultResponseTab);
                    }
                };

            } else {
                return new ResponseEffectsOnly("Back", "Return to the previous screen.") {
                    @Override
                    public void effects() {
                        Main.game.restoreSavedContent(false);
                    }
                };
            }

        } else {
            return null;
        }
    }

    private static List<AbstractRace> getFaceSkinDemonRaces() {
        List<AbstractRace> faceSkinOptions = Util.newArrayListOfValues();
        GameCharacter target = BodyChanging.getTarget();

        if (target.isElemental()) {
            return allRaces;

        } else if (isHalfDemon()) {
            faceSkinOptions.add(target.getHalfDemonSubspecies().getRace());
            faceSkinOptions.add(Race.HUMAN);

        } else if (isSelfTFMenu()) {
            faceSkinOptions.add(target.getRace());
            if (target.isYouko()) {
                faceSkinOptions.addAll(target.getSelfTransformationRaces());
            }

        } else {
            faceSkinOptions.addAll(target.getSelfTransformationRaces());
        }
        return faceSkinOptions;
    }

    private static List<AbstractRace> getArmLegDemonRaces() {
        List<AbstractRace> armLegOptions = Util.newArrayListOfValues();
        GameCharacter target = BodyChanging.getTarget();

        if (target.isElemental()) {
            return allRaces;

        } else if (isHalfDemon()) {
            armLegOptions.add(target.getHalfDemonSubspecies().getRace());

        } else if (isSelfTFMenu()) {
            armLegOptions.add(target.getRace());
            if (target.isYouko()) {
                armLegOptions.addAll(target.getSelfTransformationRaces());
            }
        } else {
            armLegOptions.addAll(target.getSelfTransformationRaces());
        }
        return armLegOptions;
    }

    /**
     * @param isHalfSpeciesReplacement True if this is a part that should always be of the core race type (if not human). This is so that things like hellhounds will still have dog tails and ears.
     * @return List of races available to the target.
     */
    private static List<AbstractRace> getMinorPartsDemonRaces(boolean isHalfSpeciesReplacement) {
        List<AbstractRace> minorPartsOptions = Util.newArrayListOfValues();
        GameCharacter target = BodyChanging.getTarget();

        if (target.isElemental()) {
            return allRaces;

        } else if (isHalfDemon()) {
            if (isHalfSpeciesReplacement && target.getHalfDemonSubspecies().getRace() != Race.HUMAN) {
                minorPartsOptions.add(target.getHalfDemonSubspecies().getRace());
            } else {
                minorPartsOptions.add(Race.DEMON);
            }

        } else if (isSelfTFMenu()) {
            minorPartsOptions.add(target.getRace());
            if (target.isYouko()) {
                minorPartsOptions.addAll(target.getSelfTransformationRaces());
            }
        } else {
            minorPartsOptions.addAll(target.getSelfTransformationRaces());
        }

        return minorPartsOptions;
    }

    private static boolean removeNoneFromTailChoices() {
        if (isHalfDemon() && !(BodyChanging.getTarget().isElemental())) {
            return !RacialBody.valueOfRace(target.getHalfDemonSubspecies().getRace()).getTailType().contains(TailType.NONE);
        }
        return false;
    }

//	private static Map<AbstractBodyCoveringType, List<String>> getMainCoveringsMap() {
//		Map<AbstractBodyCoveringType, List<String>> coveringsNamesMap = new LinkedHashMap<>();
//
////		if(getTarget().isElemental()) {
////			switch(getTarget().getBodyMaterial()) {
////				case AIR:
////					coveringsNamesMap.put(BodyCoveringType.AIR, Util.newArrayListOfValues("AIR"));
////					break;
////				case ARCANE:
////					coveringsNamesMap.put(BodyCoveringType.ARCANE, Util.newArrayListOfValues("ARCANE"));
////					break;
////				case FIRE:
////					coveringsNamesMap.put(BodyCoveringType.FIRE, Util.newArrayListOfValues("FIRE"));
////					break;
////				case FLESH:
////					break;
////				case ICE:
////					coveringsNamesMap.put(BodyCoveringType.ICE, Util.newArrayListOfValues("ICE"));
////					break;
////				case RUBBER:
////					coveringsNamesMap.put(BodyCoveringType.RUBBER, Util.newArrayListOfValues("RUBBER"));
////					break;
////				case SLIME:
////					break;
////				case STONE:
////					coveringsNamesMap.put(BodyCoveringType.STONE, Util.newArrayListOfValues("STONE"));
////					break;
////				case WATER:
////					coveringsNamesMap.put(BodyCoveringType.WATER, Util.newArrayListOfValues("WATER"));
////					break;
////			}
////
////		} else if(getTarget().getBodyMaterial()==BodyMaterial.SLIME) {
////			coveringsNamesMap.put(BodyCoveringType.SLIME, Util.newArrayListOfValues("SLIME"));
////
////		} else {
//			for(BodyPartInterface bp : getTarget().getAllBodyParts()){
//				if(bp.getBodyCoveringType(getTarget())!=null
//						&& !(bp instanceof Hair)
//						&& !(bp instanceof Eye)
//						&& !(bp instanceof Mouth)
//						&& !(bp instanceof Vagina)
//						&& !(bp instanceof Ass)
//						&& !(bp instanceof Nipples)
//						&& !(bp instanceof Breast)
//						&& !(bp instanceof Penis)
//						&& !(bp instanceof Antenna)
//						&& !(bp instanceof Horn)) {
//					String name = bp.getName(getTarget());
//					if(bp instanceof Torso) {
//						name = "torso";
//					}
//
//					if(coveringsNamesMap.containsKey(bp.getBodyCoveringType(getTarget()))) {
//						coveringsNamesMap.get(bp.getBodyCoveringType(getTarget())).add(name);
//					} else {
//						coveringsNamesMap.put(bp.getBodyCoveringType(getTarget()), Util.newArrayListOfValues(name));
//					}
//				}
//			}
//			if(getTarget().getTailType()==TailType.DEMON_HAIR_TIP && !coveringsNamesMap.containsKey(BodyCoveringType.HAIR_DEMON)) {
//				coveringsNamesMap.put(BodyCoveringType.HAIR_DEMON, Util.newArrayListOfValues(BodyCoveringType.HAIR_DEMON.getName(getTarget())));
//			}
////		}
//
//		// Return an altered map for if the target's body is not made of flesh:
//		if(getTarget().getBodyMaterial()!=BodyMaterial.FLESH) {
//			Map<AbstractBodyCoveringType, List<String>> altMaterialCoveringsNamesMap = new LinkedHashMap<>();
//			for(Entry<AbstractBodyCoveringType, List<String>> entry : coveringsNamesMap.entrySet()) {
//				if(entry.getKey().getCategory().isInfluencedByMaterialType()) {
//					altMaterialCoveringsNamesMap.put(BodyCoveringType.getMaterialBodyCoveringType(getTarget().getBodyMaterial(), entry.getKey().getCategory()), entry.getValue());
//				}
//			}
//			return altMaterialCoveringsNamesMap;
//		}
//
//
//		return coveringsNamesMap;
//	}

    private static boolean removeNoneFromWingChoices() {
        if (isHalfDemon() && !(BodyChanging.getTarget().isElemental())) {
            return !RacialBody.valueOfRace(target.getHalfDemonSubspecies().getRace()).getWingTypes().contains(WingType.NONE);
        }
        return false;
    }

    private static boolean isDemonTFMenu() {
        return !debugMenu
                && BodyChanging.getTarget().getBodyMaterial() != BodyMaterial.SLIME
                && (BodyChanging.getTarget().getRace() == Race.DEMON
                || BodyChanging.getTarget().getSubspeciesOverride() == Subspecies.DEMON
                || BodyChanging.getTarget().getSubspeciesOverride() == Subspecies.LILIN
                || BodyChanging.getTarget().getSubspeciesOverride() == Subspecies.ELDER_LILIN
                || BodyChanging.getTarget().isElemental());
    }

    private static boolean isSelfTFMenu() {
        return !debugMenu
                && !isDemonTFMenu()
                && BodyChanging.getTarget().getBodyMaterial() != BodyMaterial.SLIME
                && BodyChanging.getTarget().getTrueSubspecies().isAbleToSelfTransform();
    }

    private static boolean isHalfDemon() {
        return BodyChanging.getTarget().getSubspeciesOverride() == Subspecies.HALF_DEMON;
    }

    public static final DialogueNode BODY_CHANGING_CORE = new DialogueNode("Core", "", true) {
        @Override
        public void applyPreParsingEffects() {
            SuccubisSecrets.initCoveringsMap(BodyChanging.getTarget());
        }

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            if (ScarlettsShop.isSlaveCustomisationMenu()) {
                SuccubisSecrets.initCoveringsMap(BodyChanging.getTarget());
                UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getAgeAppearanceChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFemininityChoiceDiv()).append(CharacterModificationUtils.getHeightChoiceDiv()).append("</div>").append("<div class='cosmetics-container' style='background:transparent;'>").append(CharacterModificationUtils.getBodySizeChoiceDiv()).append(CharacterModificationUtils.getMuscleChoiceDiv()).append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(),
                        "[npc.NamePos] muscle and body size values give [npc.herHim] the body shape: "
                                + "<b style='color:" + BodyChanging.getTarget().getBodyShape().toWebHexStringColour() + ";'>" + Util.capitaliseSentence(BodyChanging.getTarget().getBodyShape().getName(false)) + "</b>")).append("</div>").append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFaceChoiceDiv(getSlaveCustomisationRaceOptions())).append(CharacterModificationUtils.getSelfTransformBodyChoiceDiv(getSlaveCustomisationRaceOptions())).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformArmChoiceDiv(getSlaveCustomisationRaceOptions())).append(CharacterModificationUtils.getSelfTransformLegChoiceDiv(getSlaveCustomisationRaceOptions(), isDebugMenu())).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformArmCountDiv()).append(CharacterModificationUtils.getSelfTransformFootStructureChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLegConfigurationChoiceDiv()).append(CharacterModificationUtils.getSelfTransformGenitalArrangementChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailChoiceDiv(getSlaveCustomisationRaceOptions(), false)).append(CharacterModificationUtils.getSelfTransformTailLengthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailCountDiv()).append(CharacterModificationUtils.getSelfTransformTailGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTentacleLengthDiv()).append(CharacterModificationUtils.getSelfTransformTentacleGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformWingChoiceDiv(getSlaveCustomisationRaceOptions(), false)).append(CharacterModificationUtils.getSelfTransformWingSizeDiv()).append("</div>");

            } else if (isDemonTFMenu() || isSelfTFMenu()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your " + getPowers() + " core aspects of your body.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] " + getPowers() + " core aspects of [npc.her] body.</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getAgeAppearanceChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFemininityChoiceDiv()).append(CharacterModificationUtils.getHeightChoiceDiv()).append("</div>").append(BodyChanging.getTarget().isElemental()
                        ? CharacterModificationUtils.getSelfTransformBodyMaterialChoiceDiv(BodyChanging.getTarget())
                        : "").append("<div class='cosmetics-container' style='background:transparent;'>").append(CharacterModificationUtils.getBodySizeChoiceDiv()).append(CharacterModificationUtils.getMuscleChoiceDiv()).append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "[npc.NamePos] muscle and body size values give [npc.herHim] the body shape: "
                        + "<b style='color:" + getTarget().getBodyShape().toWebHexStringColour() + ";'>" + Util.capitaliseSentence(getTarget().getBodyShape().getName(false)) + "</b>")).append("</div>").append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFaceChoiceDiv(getFaceSkinDemonRaces())).append(CharacterModificationUtils.getSelfTransformBodyChoiceDiv(getFaceSkinDemonRaces())).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformArmChoiceDiv(getArmLegDemonRaces())).append(CharacterModificationUtils.getSelfTransformLegChoiceDiv(getArmLegDemonRaces(), isDebugMenu())).append("</div>");

                if (!BodyChanging.getTarget().isYouko()) {
                    UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformArmCountDiv()).append(CharacterModificationUtils.getSelfTransformFootStructureChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLegConfigurationChoiceDiv()).append(CharacterModificationUtils.getSelfTransformGenitalArrangementChoiceDiv()).append("</div>");

                } else {
                    UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFootStructureChoiceDiv()).append(CharacterModificationUtils.getSelfTransformLegConfigurationChoiceDiv()).append("</div>");
                }

                UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailChoiceDiv(
                        (getTarget().isElemental()
                                ? allRaces
                                : getMinorPartsDemonRaces(true)),
//											:(removeNoneFromTailChoices()||isSelfTFMenu()
//												?getMinorPartsDemonRaces(true)
//												:Util.newArrayListOfValues(Race.DEMON))),
                        removeNoneFromTailChoices())).append(CharacterModificationUtils.getSelfTransformTailLengthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailCountDiv()).append(CharacterModificationUtils.getSelfTransformTailGirthDiv()).append("</div>");

                if (!BodyChanging.getTarget().isYouko()) {
                    UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTentacleLengthDiv()).append(CharacterModificationUtils.getSelfTransformTentacleGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformWingChoiceDiv(
                            (getTarget().isElemental()
                                    ? allRaces
                                    : getMinorPartsDemonRaces(true)),
//											:(!removeNoneFromWingChoices()
//												?Util.newArrayListOfValues(Race.DEMON)
//												:getMinorPartsDemonRaces(true)),
                            removeNoneFromWingChoices())).append(CharacterModificationUtils.getSelfTransformWingSizeDiv()).append("</div>");
                }

                // Doll menu:
            } else if (BodyChanging.getTarget().isDoll()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "<i>With the D.E.C.K.'s cable plugged into the port on the rear of [npc.namePos] neck, you're able to customise [npc.her] body...</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getAgeAppearanceChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFemininityChoiceDiv()).append(CharacterModificationUtils.getHeightChoiceDiv()).append("</div>").append("<div class='cosmetics-container' style='background:transparent;'>").append(CharacterModificationUtils.getBodySizeChoiceDiv()).append(CharacterModificationUtils.getMuscleChoiceDiv()).append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "[npc.NamePos] muscle and body size values give [npc.herHim] the body shape: "
                        + "<b style='color:" + getTarget().getBodyShape().toWebHexStringColour() + ";'>" + Util.capitaliseSentence(getTarget().getBodyShape().getName(false)) + "</b>")).append("</div>").append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFootStructureChoiceDiv()).append(CharacterModificationUtils.getSelfTransformWingSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailLengthDiv()).append(CharacterModificationUtils.getSelfTransformTailGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTentacleLengthDiv()).append(CharacterModificationUtils.getSelfTransformTentacleGirthDiv()).append("</div>");

                // Slime/debug:
            } else {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing core aspects of your body.</i>"
                        : UtilText.parse(BodyChanging.getTarget(),
                        "<i>Get [npc.name] to focus [npc.her] efforts on changing core aspects of [npc.her] body.</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getAgeAppearanceChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFemininityChoiceDiv()).append(CharacterModificationUtils.getHeightChoiceDiv()).append("</div>").append("<div class='cosmetics-container' style='background:transparent;'>").append(CharacterModificationUtils.getBodySizeChoiceDiv()).append(CharacterModificationUtils.getMuscleChoiceDiv()).append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "[npc.NamePos] muscle and body size values give [npc.herHim] the body shape: "
                        + "<b style='color:" + getTarget().getBodyShape().toWebHexStringColour() + ";'>" + Util.capitaliseSentence(getTarget().getBodyShape().getName(false)) + "</b>")).append("</div>").append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformFaceChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformBodyChoiceDiv(allRaces)).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformArmChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformLegChoiceDiv(allRaces, isDebugMenu())).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformArmCountDiv()).append(CharacterModificationUtils.getSelfTransformFootStructureChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLegConfigurationChoiceDiv()).append(CharacterModificationUtils.getSelfTransformGenitalArrangementChoiceDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailChoiceDiv(allRaces, false)).append(CharacterModificationUtils.getSelfTransformTailLengthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTailCountDiv()).append(CharacterModificationUtils.getSelfTransformTailGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTentacleLengthDiv()).append(CharacterModificationUtils.getSelfTransformTentacleGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformWingChoiceDiv(allRaces, false)).append(CharacterModificationUtils.getSelfTransformWingSizeDiv()).append("</div>");
            }

            for (Entry<AbstractBodyCoveringType, Value<AbstractRace, List<String>>> entry : SuccubisSecrets.coveringsNamesMap.entrySet()) {
                AbstractBodyCoveringType bct = entry.getKey();
                AbstractRace race = entry.getValue().getKey();

                Value<String, String> titleDescription = SuccubisSecrets.getCoveringTitleDescription(target, bct, entry.getValue().getValue());

                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivCoveringsNew(
                        false,
                        race,
                        bct,
                        titleDescription.getKey(),
                        UtilText.parse(target, titleDescription.getValue()),
                        true,
                        true));
            }

            if (Main.game.isBodyHairEnabled() && (!BodyChanging.getTarget().isDoll() || debugMenu)) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivUnderarmHair(false, "Underarm hair",
                        UtilText.parse(BodyChanging.getTarget(), "Change the amount of hair in [npc.namePos] underarms."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, getTarget().getUnderarmHairType().getType(), "Underarm hair colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] underarm hair."),
                        true, true));
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    private static List<AbstractRace> getSlaveCustomisationRaceOptions() {
        List<AbstractRace> list = new ArrayList<>();

        list.add(Race.NONE);

        for (AbstractRace race : Race.getAllRaces()) {
            if (!race.isAbleToSelfTransform()
                    && (Subspecies.getWorldSpecies(WorldType.DOMINION, PlaceType.SLAVER_ALLEY_SCARLETTS_SHOP, false).keySet().stream().anyMatch(s -> s.getRace() == race)
                    || Subspecies.getWorldSpecies(WorldType.SUBMISSION, PlaceType.SLAVER_ALLEY_SCARLETTS_SHOP, false).keySet().stream().anyMatch(s -> s.getRace() == race))) {
                list.add(race);
            }
        }

        return list;
    }

    public static final DialogueNode BODY_CHANGING_EYES = new DialogueNode("Eyes", "", true) {
        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            if (ScarlettsShop.isSlaveCustomisationMenu()) {
                UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformEyeChoiceDiv(getSlaveCustomisationRaceOptions())).append(CharacterModificationUtils.getSelfTransformEyeCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformIrisChoiceDiv()).append(CharacterModificationUtils.getSelfTransformPupilChoiceDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getEyeCovering()).getType(),
                        "Iris colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] irises."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, BodyChanging.getTarget().getCovering(BodyCoveringType.EYE_PUPILS).getType(),
                        "Pupil colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] pupils."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, BodyChanging.getTarget().getCovering(BodyCoveringType.EYE_SCLERA).getType(),
                        "Sclerae colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] sclerae."),
                        true, true));

            } else if (debugMenu) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing aspects of your eyes.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] efforts on changing aspects of [npc.her] eyes.</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformEyeChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformEyeCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformIrisChoiceDiv()).append(CharacterModificationUtils.getSelfTransformPupilChoiceDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getEyeCovering()).getType(),
                        "Iris colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] irises."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, BodyChanging.getTarget().getCovering(BodyCoveringType.EYE_PUPILS).getType(),
                        "Pupil colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] pupils."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, BodyChanging.getTarget().getCovering(BodyCoveringType.EYE_SCLERA).getType(),
                        "Sclerae colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] sclerae."),
                        true, true));

            } else if (isDemonTFMenu() || isSelfTFMenu()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your " + getPowers() + " aspects of your eyes.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] " + getPowers() + " aspects of [npc.her] eyes.</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformEyeChoiceDiv(
                        (getMinorPartsDemonRaces(false)))).append(BodyChanging.getTarget().isYouko() ? "" : CharacterModificationUtils.getSelfTransformEyeCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformIrisChoiceDiv()).append(CharacterModificationUtils.getSelfTransformPupilChoiceDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getEyeCovering()).getType(),
                        "Iris colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] irises."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, BodyChanging.getTarget().getCovering(BodyCoveringType.EYE_PUPILS).getType(),
                        "Pupil colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] pupils."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, BodyChanging.getTarget().getCovering(BodyCoveringType.EYE_SCLERA).getType(),
                        "Sclerae colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] sclerae."),
                        true, true));
                // Doll:
            } else if (BodyChanging.getTarget().isDoll()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "<i>With the D.E.C.K.'s cable plugged into the port on the rear of [npc.namePos] neck, you're able to customise [npc.her] eyes...</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformIrisChoiceDiv()).append(CharacterModificationUtils.getSelfTransformPupilChoiceDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.EYE_IRIS),
                        "Iris colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] irises."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.EYE_PUPIL),
                        "Pupil colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] pupils."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.EYE_SCLERA),
                        "Sclerae colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] sclerae."),
                        true, true));

                // Slime:
            } else {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing aspects of your slimy eyes.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] efforts on changing aspects of [npc.her] slimy eyes.</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformEyeChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformEyeCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformIrisChoiceDiv()).append(CharacterModificationUtils.getSelfTransformPupilChoiceDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.EYE_IRIS),
                        "Iris colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] irises."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.EYE_PUPIL),
                        "Pupil colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] pupils."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getEyeType().getRace(), BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.EYE_SCLERA),
                        "Sclerae colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour and pattern of [npc.namePos] sclerae."),
                        true, true));
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    private static List<AbstractRace> getRacesForMinorPartSelfTransform() {
        if (ScarlettsShop.isSlaveCustomisationMenu()) {
            return getSlaveCustomisationRaceOptions();
        }
        if (isDemonTFMenu() || isSelfTFMenu()) {
            return getTarget().isElemental()
                    ? allRaces
                    : getMinorPartsDemonRaces(false);
        }
        return allRaces;
    }

    public static final DialogueNode BODY_CHANGING_HAIR = new DialogueNode("Hair", "", true) {
        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            if (ScarlettsShop.isSlaveCustomisationMenu()) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformHairChoiceDiv(getSlaveCustomisationRaceOptions())).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHairLengthDiv()).append(CharacterModificationUtils.getNeckFluffDiv()).append("</div>").append(CharacterModificationUtils.getSelfDivHairStyles("Hair Style", UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] hair style."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHairRace(),
                        BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHairCovering()).getType(), "Hair colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.her] hair."), true, true));

            } else if (debugMenu) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing aspects of your hair.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] efforts on changing aspects of [npc.her] hair.</i>")).append("</div>").append(CharacterModificationUtils.getSelfTransformHairChoiceDiv(allRaces)).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHairLengthDiv()).append(CharacterModificationUtils.getNeckFluffDiv()).append("</div>").append(CharacterModificationUtils.getSelfDivHairStyles("Hair Style", UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] hair style."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHairRace(),
                        BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHairCovering()).getType(), "Hair colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "Change the colour of your hair."
                                : UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.her] hair.")), true, true));

            } else if (isDemonTFMenu() || isSelfTFMenu()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your " + getPowers() + " aspects of your hair.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] " + getPowers() + " on changing aspects of [npc.her] hair.</i>")).append("</div>").append(CharacterModificationUtils.getSelfTransformHairChoiceDiv((getMinorPartsDemonRaces(true)))).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHairLengthDiv()).append(CharacterModificationUtils.getNeckFluffDiv()).append("</div>").append(CharacterModificationUtils.getSelfDivHairStyles("Hair Style", UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] hair style."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHairRace(),
                        BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHairCovering()).getType(), "Hair colour",
                        (isDemonTFMenu()
                                ? UtilText.parse(BodyChanging.getTarget(), "[npc.Name] can harness the power of [npc.her] demonic form to change the colour of [npc.her] hair.")
                                : UtilText.parse(BodyChanging.getTarget(), "[npc.Name] can harness [npc.her] innate powers to change the colour of [npc.her] hair."))
                        , true, true));

                // Doll:
            } else if (BodyChanging.getTarget().isDoll()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "<i>With the D.E.C.K.'s cable plugged into the port on the rear of [npc.namePos] neck, you're able to customise [npc.her] hair...</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHairLengthDiv()).append(CharacterModificationUtils.getNeckFluffDiv()).append("</div>").append(CharacterModificationUtils.getSelfDivHairStyles("Hair Style", UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] hair style."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHairRace(),
                        BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.HAIR), "Hair colour",
                        UtilText.parse(BodyChanging.getTarget(), "[npc.Name] can freely change the colour of [npc.her] slimy hair."), true, true));

                // Slime:
            } else {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing aspects of your slimy hair.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] efforts on changing aspects of [npc.her] slimy hair.</i>")).append("</div>").append(CharacterModificationUtils.getSelfTransformHairChoiceDiv(allRaces)).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHairLengthDiv()).append(CharacterModificationUtils.getNeckFluffDiv()).append("</div>").append(CharacterModificationUtils.getSelfDivHairStyles("Hair Style", UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] hair style."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHairRace(),
                        BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.HAIR), "Hair colour",
                        UtilText.parse(BodyChanging.getTarget(), "[npc.Name] can freely change the colour of [npc.her] slimy hair."), true, true));
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    private static String getSelfTransformDescription(String area) {
        if (ScarlettsShop.isSlaveCustomisationMenu()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("<div class='container-full-width' style='text-align:center;'>");
        if (isDemonTFMenu()) {
            sb.append(UtilText.parse(BodyChanging.getTarget(), "<i>[npc.Name] can harness the power of [npc.her] demonic form to self-transform aspects of [npc.her] " + area + ".</i>"));

        } else if (isSelfTFMenu()) {
            sb.append(UtilText.parse(BodyChanging.getTarget(), "<i>[npc.Name] can harness [npc.her] innate powers to self-transform aspects of [npc.her] " + area + ".</i>"));

        } else if (debugMenu) {
            sb.append(UtilText.parse(BodyChanging.getTarget(), "<i>[npc.Name] can harness the power of the debugging tool to self-transform aspects of [npc.her] " + area + ".</i>"));

        } else if (BodyChanging.getTarget().isDoll()) {
            sb.append(UtilText.parse(BodyChanging.getTarget(), "<i>With the D.E.C.K.'s cable plugged into the port on the rear of [npc.namePos] neck, you're able to customise [npc.her] " + area + "...</i>"));

        } else {
            sb.append(UtilText.parse(BodyChanging.getTarget(), "<i>[npc.Name] can take advantage of [npc.her] morphable, slimy body to self-transform aspects of [npc.her] " + area + ".</i>"));
        }
        sb.append("</div>");

        return sb.toString();
    }

    public static final DialogueNode BODY_CHANGING_HEAD = new DialogueNode("Head", "", true) {
        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            if (ScarlettsShop.isSlaveCustomisationMenu()) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformEarChoiceDiv(getSlaveCustomisationRaceOptions())).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornChoiceDiv(getSlaveCustomisationRaceOptions())).append(CharacterModificationUtils.getSelfTransformHornSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornCountDiv()).append(CharacterModificationUtils.getSelfTransformHornsPerRowCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaChoiceDiv(getSlaveCustomisationRaceOptions())).append(CharacterModificationUtils.getSelfTransformAntennaSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaCountDiv()).append(CharacterModificationUtils.getSelfTransformAntennaePerRowCountDiv()).append("</div>").append(BodyChanging.getTarget().hasHorns()
                        ? CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHornRace(),
                        BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHornCovering()).getType(), "Horn Colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] horns."),
                        true, true)
                        : "").append(CharacterModificationUtils.getSelfTransformLipSizeDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatModifiersDiv()).append(CharacterModificationUtils.getSelfTransformThroatWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatCapacityDiv()).append(CharacterModificationUtils.getSelfTransformThroatDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatElasticityDiv()).append(CharacterModificationUtils.getSelfTransformThroatPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTongueSizeDiv()).append(CharacterModificationUtils.getSelfTransformTongueModifiersDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getMouthType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.MOUTH).getType(),
                        "Lip & Throat colour",
                        UtilText.parse(BodyChanging.getTarget(),
                                "The natural colour of [npc.namePos] " + (getTarget().getFaceType() == FaceType.HARPY ? "beak" : "lips") + " (top options) and [npc.her] throat (bottom options)."
                                        + "Lipstick can be used to conceal [npc.her] natural lip colour."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getTongueType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.TONGUE).getType(),
                        "Tongue colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your tongue."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] tongue.")),
                        true, true));

            } else if (debugMenu) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing aspects of your head and face.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] efforts on changing aspects of [npc.her] head and face.</i>")).append("</div>").append(CharacterModificationUtils.getSelfTransformEarChoiceDiv(allRaces)).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformHornSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornCountDiv()).append(CharacterModificationUtils.getSelfTransformHornsPerRowCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformAntennaSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaCountDiv()).append(CharacterModificationUtils.getSelfTransformAntennaePerRowCountDiv()).append("</div>").append(BodyChanging.getTarget().getHornType() != HornType.NONE
                        ? CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHornRace(), BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHornCovering()).getType(),
                        "Horn Colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your horns."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] horns.")),
                        true, true)
                        : "").append(CharacterModificationUtils.getSelfTransformLipSizeDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatModifiersDiv()).append(CharacterModificationUtils.getSelfTransformThroatWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatCapacityDiv()).append(CharacterModificationUtils.getSelfTransformThroatDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatElasticityDiv()).append(CharacterModificationUtils.getSelfTransformThroatPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTongueSizeDiv()).append(CharacterModificationUtils.getSelfTransformTongueModifiersDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getMouthType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.MOUTH).getType(),
                        "Lip & Throat colour",
                        UtilText.parse(BodyChanging.getTarget(),
                                "The natural colour of [npc.namePos] " + (getTarget().getFaceType() == FaceType.HARPY ? "beak" : "lips") + " (top options) and [npc.her] throat (bottom options)."
                                        + "Lipstick can be used to conceal [npc.her] natural lip colour."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getTongueType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.TONGUE).getType(),
                        "Tongue colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your tongue."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] tongue.")),
                        true, true));

            } else if (isDemonTFMenu() || isSelfTFMenu()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your " + getPowers() + " aspects of your head and face.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] " + getPowers() + " aspects of [npc.her] head and face.</i>")).append("</div>").append(CharacterModificationUtils.getSelfTransformEarChoiceDiv(
                        (getMinorPartsDemonRaces(true))));

                if (!BodyChanging.getTarget().isYouko()) {
                    UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornChoiceDiv(
                            (getTarget().isElemental())
                                    ? allRaces
                                    : Util.mergeLists(getMinorPartsDemonRaces(true), Util.newArrayListOfValues(Race.NONE, Race.DEMON)))).append(CharacterModificationUtils.getSelfTransformHornSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornCountDiv()).append(CharacterModificationUtils.getSelfTransformHornsPerRowCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaChoiceDiv((getMinorPartsDemonRaces(true)))).append(CharacterModificationUtils.getSelfTransformAntennaSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaCountDiv()).append(CharacterModificationUtils.getSelfTransformAntennaePerRowCountDiv()).append("</div>").append(BodyChanging.getTarget().getHornType() != HornType.NONE
                            ? CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHornRace(), BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHornCovering()).getType(),
                            "Horn Colour",
                            (BodyChanging.getTarget().isPlayer()
                                    ? "The colour of your horns."
                                    : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] horns.")),
                            true, true)
                            : "");
                }

                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformLipSizeDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatModifiersDiv()).append(CharacterModificationUtils.getSelfTransformThroatWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatCapacityDiv()).append(CharacterModificationUtils.getSelfTransformThroatDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatElasticityDiv()).append(CharacterModificationUtils.getSelfTransformThroatPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTongueSizeDiv()).append(CharacterModificationUtils.getSelfTransformTongueModifiersDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getMouthType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.MOUTH).getType(),
                        "Lip & Throat colour",
                        UtilText.parse(BodyChanging.getTarget(), "The natural colour of [npc.namePos] slimy " + (getTarget().getFaceType() == FaceType.HARPY ? "beak" : "lips") + " (top options) and [npc.her] throat (bottom options)."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getTongueType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.TONGUE).getType(),
                        "Tongue colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your tongue."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] tongue.")),
                        true, true));

                // Doll:
            } else if (BodyChanging.getTarget().isDoll()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(UtilText.parse(BodyChanging.getTarget(), "<i>With the D.E.C.K.'s cable plugged into the port on the rear of [npc.namePos] neck, you're able to customise [npc.her] head and face...</i>")).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornSizeDiv()).append(CharacterModificationUtils.getSelfTransformAntennaSizeDiv()).append("</div>").append(BodyChanging.getTarget().getHornType() != HornType.NONE
                        ? CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getHornRace(), BodyChanging.getTarget().getCovering(BodyChanging.getTarget().getHornCovering()).getType(),
                        "Horn Colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your horns."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] horns.")),
                        true, true)
                        : "").append(CharacterModificationUtils.getSelfTransformLipSizeDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatModifiersDiv()).append(CharacterModificationUtils.getSelfTransformThroatWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatCapacityDiv()).append(CharacterModificationUtils.getSelfTransformThroatDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatElasticityDiv()).append(CharacterModificationUtils.getSelfTransformThroatPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTongueSizeDiv()).append(CharacterModificationUtils.getSelfTransformTongueModifiersDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getMouthType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.MOUTH).getType(),
                        "Lip & Throat colour",
                        UtilText.parse(BodyChanging.getTarget(),
                                "The natural colour of [npc.namePos] " + (getTarget().getFaceType() == FaceType.HARPY ? "beak" : "lips") + " (top options) and [npc.her] throat (bottom options)."
                                        + "Lipstick can be used to conceal [npc.her] natural lip colour."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, BodyChanging.getTarget().getTongueType().getRace(), BodyChanging.getTarget().getCovering(BodyCoveringType.TONGUE).getType(),
                        "Tongue colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your tongue."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] tongue.")),
                        true, true));

                // Slime:
            } else {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>").append(BodyChanging.getTarget().isPlayer()
                        ? "<i>Focus your efforts on changing aspects of your slimy head and face.</i>"
                        : UtilText.parse(BodyChanging.getTarget(), "<i>Get [npc.name] to focus [npc.her] efforts on changing aspects of [npc.her] slimy head and face.</i>")).append("</div>").append(CharacterModificationUtils.getSelfTransformEarChoiceDiv(allRaces)).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformHornSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformHornCountDiv()).append(CharacterModificationUtils.getSelfTransformHornsPerRowCountDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaChoiceDiv(allRaces)).append(CharacterModificationUtils.getSelfTransformAntennaSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAntennaCountDiv()).append(CharacterModificationUtils.getSelfTransformAntennaePerRowCountDiv()).append("</div>").append(CharacterModificationUtils.getSelfTransformLipSizeDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatModifiersDiv()).append(CharacterModificationUtils.getSelfTransformThroatWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatCapacityDiv()).append(CharacterModificationUtils.getSelfTransformThroatDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformThroatElasticityDiv()).append(CharacterModificationUtils.getSelfTransformThroatPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTongueSizeDiv()).append(CharacterModificationUtils.getSelfTransformTongueModifiersDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.SLIME,
                        BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.MOUTH),
                        "Lip & Throat colour",
                        UtilText.parse(BodyChanging.getTarget(), "The natural colour of [npc.namePos] slimy " + (getTarget().getFaceType() == FaceType.HARPY ? "beak" : "lips") + " (top options) and [npc.her] throat (bottom options)."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.SLIME,
                        BodyChanging.getTarget().getCovering(BodyCoveringType.getMaterialBodyCoveringType(BodyMaterial.SLIME, BodyCoveringCategory.TONGUE)).getType(),
                        "Tongue colour",
                        (BodyChanging.getTarget().isPlayer()
                                ? "The colour of your tongue."
                                : UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] tongue.")),
                        true, true));
            }

            if (Main.game.isFacialHairEnabled() && (!getTarget().isFeminine() || Main.game.isFemaleFacialHairEnabled())) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivFacialHair(false, "Beard length",
                        UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] beard."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, getTarget().getFacialHairType().getType(), "Beard colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] beard."),
                        true, true));
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    public static void initSaveLoadMenu() {
        presetTransformationsMap = new HashMap<>();

        for (Path f : getSavedBodies()) {
            try {
                String name = Util.getFileIdentifier(f);
                String nameReadable = Util.capitaliseSentence(name.replaceAll("_", " "));
                Body loadedBody = loadBody(name);
                Femininity fem = Femininity.valueOf(loadedBody.getFemininity());
                AbstractSubspecies subspecies = loadedBody.getLoadedSubspecies();
                String subspeciesName = loadedBody.isFeminine() ? subspecies.getSingularFemaleName(loadedBody) : subspecies.getSingularMaleName(loadedBody);
                String displayName;
                if (isPresetTransformationAvailable(loadedBody)) {
                    displayName = "<b>" + nameReadable + "</b>"
                            + " (<span style='color:" + fem.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(fem.getNames().get(0)) + "</span>"
                            + " <span style='color:" + subspecies.getColour(null).toWebHexString() + ";'>" + subspeciesName + "</span>)";
                } else {
                    displayName = "[style.boldDisabled(" + nameReadable + ")] [style.colourDisabled((" + Util.capitaliseSentence(fem.getNames().get(0)) + " " + subspeciesName + "))]";
                }
                presetTransformationsMap.put(name, new Value<>(displayName, loadedBody));
            } catch (Exception ex) {
            }
        }
    }

    public static final DialogueNode BODY_CHANGING_ASS = new DialogueNode("Ass", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getSelfTransformDescription("ass and hips")).append(BodyChanging.getTarget().isDoll() && !debugMenu
                    ? ""
                    : CharacterModificationUtils.getSelfTransformAssChoiceDiv(getRacesForMinorPartSelfTransform())).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAssSizeDiv()).append(CharacterModificationUtils.getSelfTransformHipSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAnusModifiersDiv()).append(CharacterModificationUtils.getSelfTransformAnusWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAnusCapacityDiv()).append(CharacterModificationUtils.getSelfTransformAnusDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformAnusElasticityDiv()).append(CharacterModificationUtils.getSelfTransformAnusPlasticityDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                    BodyChanging.getTarget().getAssRace(),
                    BodyChanging.getTarget().getCovering(BodyCoveringType.ANUS).getType(),
                    "Anus Colour",
                    UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] asshole."),
                    true, true));

            if (Main.game.isAssHairEnabled() && (!BodyChanging.getTarget().isDoll() || debugMenu)) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivAssHair(false, "Ass hair",
                        UtilText.parse(BodyChanging.getTarget(), "Change the amount of hair around [npc.namePos] anus."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, getTarget().getAssHairType().getType(), "Ass hair colour",
                        UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] ass hair."),
                        true, true));
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    public static Map<String, Value<String, Body>> getPresetTransformationsMap() {
        return presetTransformationsMap;
    }

    public static final DialogueNode BODY_CHANGING_BREASTS = new DialogueNode("Breasts", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getSelfTransformDescription("breasts"));

            if (!BodyChanging.getTarget().isDoll() || debugMenu) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformBreastChoiceDiv(getRacesForMinorPartSelfTransform()));
            }

            UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformBreastSizeDiv()).append(CharacterModificationUtils.getSelfTransformBreastShapeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformBreastRowsDiv()).append(CharacterModificationUtils.getSelfTransformNippleModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLactationDiv()).append(CharacterModificationUtils.getSelfTransformLactationRegenerationDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLactationFlavourDiv()).append(CharacterModificationUtils.getSelfTransformLactationModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleCountDiv()).append(CharacterModificationUtils.getSelfTransformNippleShapeDiv()).append("</div>");

            UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleSizeDiv()).append(CharacterModificationUtils.getSelfTransformAreolaeSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleCapacityDiv()).append(CharacterModificationUtils.getSelfTransformNippleDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleElasticityDiv()).append(CharacterModificationUtils.getSelfTransformNipplePlasticityDiv()).append("</div>");

            UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                    BodyChanging.getTarget().getBreastRace(),
                    BodyChanging.getTarget().getCovering(BodyCoveringType.NIPPLES).getType(),
                    "Nipple Colour",
                    UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] nipples."),
                    true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                    Race.NONE,
                    BodyChanging.getTarget().getCovering(BodyCoveringType.MILK).getType(),
                    "Milk Colour",
                    UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] [npc.milk]."),
                    true, true));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    public static List<Path> getSavedBodies() {
        List<Path> filesList = new ArrayList<>();

        Path dir = FS.newPath("data/transformation_presets");
        if (Files.isDirectory(dir)) {
            Path[] directoryListing;
            BiPredicate<Path, String> filter = (path, name) -> name.endsWith(".xml");
            try (Stream<Path> stream = Files.list(dir)) {
                directoryListing = stream.filter(dir1 -> filter.test((dir1), dir1.getFileName().toString())).toList().toArray(new Path[0]);
            } catch (IOException e) {
                directoryListing = null;
            }
            if (directoryListing != null) {
                filesList.addAll(Arrays.asList(directoryListing));
            }
        }

        filesList.sort(Comparator.comparing(Path::getFileName).reversed());

        return filesList;
    }

    public static final DialogueNode BODY_CHANGING_VAGINA = new DialogueNode("Vagina", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getSelfTransformDescription("vagina"));

            if (!getTarget().isDoll() || debugMenu) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformVaginaChoiceDiv(getRacesForMinorPartSelfTransform()));
            }

            if (getTarget().hasVagina()) {
                if (!BodyChanging.getTarget().isDoll() || debugMenu) {
                    UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformGirlcumFlavourDiv()).append(CharacterModificationUtils.getSelfTransformGirlcumModifiersDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaSquirterDiv()).append(CharacterModificationUtils.getSelfTransformVaginaHymenDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLabiaSizeDiv()).append(CharacterModificationUtils.getSelfTransformVaginaEggLayerDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaModifiersDiv()).append(CharacterModificationUtils.getSelfTransformVaginaWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaCapacityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaElasticityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformClitorisSizeDiv()).append(CharacterModificationUtils.getSelfTransformClitorisGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformClitorisModifiersDiv()).append(CharacterModificationUtils.getSelfTransformVaginaUrethraModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaUrethraCapacityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaUrethraDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaUrethraElasticityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaUrethraPlasticityDiv()).append("</div>");

                    // Doll:
                } else {
                    UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformGirlcumFlavourDiv()).append(CharacterModificationUtils.getSelfTransformGirlcumModifiersDiv()).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaSquirterDiv()).append(CharacterModificationUtils.getSelfTransformLabiaSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaModifiersDiv()).append(CharacterModificationUtils.getSelfTransformVaginaWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaCapacityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaElasticityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaPlasticityDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformClitorisSizeDiv()).append(CharacterModificationUtils.getSelfTransformClitorisGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformClitorisModifiersDiv()).append(CharacterModificationUtils.getSelfTransformVaginaUrethraModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaUrethraCapacityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaUrethraDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformVaginaUrethraElasticityDiv()).append(CharacterModificationUtils.getSelfTransformVaginaUrethraPlasticityDiv()).append("</div>");
                }

                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                        BodyChanging.getTarget().getVaginaRace(),
                        BodyChanging.getTarget().getCovering(BodyCoveringType.VAGINA).getType(),
                        "Vagina Colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] vagina."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                        Race.NONE,
                        BodyChanging.getTarget().getCovering(BodyCoveringType.GIRL_CUM).getType(),
                        "Girlcum Colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] [npc.girlcum]."),
                        true, true));

                if (Main.game.isPubicHairEnabled() && (!BodyChanging.getTarget().isDoll() || debugMenu)) {
                    UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivPubicHair(false, "Pubic hair",
                            UtilText.parse(BodyChanging.getTarget(), "Change the amount of hair around [npc.namePos] genitals."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, getTarget().getPubicHairType().getType(), "Pubic hair colour",
                            UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] pubic hair."),
                            true, true));
                }
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    private static String getSaveLoadRow(String baseName, String displayName, Body body, boolean altColour) {

        if (body != null) {
            String fileName = (baseName + ".xml");

            boolean canTransform = isPresetTransformationAvailable(body);

//			System.out.println(body.getLoadedSubspecies().getName(body));

            return "<div class='container-full-width' style='padding:0; margin:0 0 4px 0;" + (altColour ? "background:#222;" : "") + " position:relative;'>"
                    + "<div class='container-full-width' style='width:calc(75% - 16px); background:transparent;'>"
                    + "<div class='container-full-width' style='width:10%; margin:0; padding:0; background:transparent; position:relative; float:left;'>"
                    + "<div class='inventoryImage' style='width:100%;'>"
                    + "<div class='inventoryImage-content'" + (canTransform ? "" : " style='opacity:0.25;'") + ">"
                    + body.getLoadedSubspecies().getSVGStringFromBody(body)
                    + "</div>"
                    + "<div class='overlay no-pointer' id='LOADED_BODY_" + baseName + "'></div>"
                    + "</div>"
                    + "</div>"

                    + "<div style='width:calc(90% - 8px); padding:0; margin:0 0 0 8px; position:relative; float:left;'>"
                    + "<p style='margin:0; padding:2px;'>" + displayName + "</p>"
                    + "<p style='margin:0; padding:2px;'>[style.colourDisabled(data/transformation_presets/)]" + baseName + "[style.colourDisabled(.xml)]</p>"
                    + "</div>"
                    + "</div>"
                    + "<div class='container-full-width' style='width:calc(25% - 16px);text-align:center; background:transparent;'>"
                    + (Main.game.isStarted()
                    ? (fileName.equals(overwriteConfirmationName)
                    ? "<div class='square-button saveIcon' id='OVERWRITE_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSaveConfirm() + "</div></div>"
                    : "<div class='square-button saveIcon' id='OVERWRITE_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskOverwrite() + "</div></div>")
                    : "<div class='square-button saveIcon disabled'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSaveDisabled() + "</div></div>")

                    + (canTransform
                    ? (fileName.equals(loadConfirmationName)
                    ? "<div class='square-button saveIcon' id='LOAD_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskLoadConfirm() + "</div></div>"
                    : "<div class='square-button saveIcon' id='LOAD_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskLoad() + "</div></div>")
                    : "<div class='square-button saveIcon disabled' id='LOAD_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskLoadDisabled() + "</div></div>")


                    + (fileName.equals(deleteConfirmationName)
                    ? "<div class='square-button saveIcon' id='DELETE_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskDeleteConfirm() + "</div></div>"
                    : "<div class='square-button saveIcon' id='DELETE_" + baseName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskDelete() + "</div></div>")
                    + "</div>"
                    + "</div>";

        } else {
            String svgString = BodyChanging.getTarget().getSubspecies().getSVGString(BodyChanging.getTarget());

            return "<div class='container-full-width' style='padding:0; margin:0 0 4px 0;" + (altColour ? "background:#222;" : "") + "'>"

                    + "<div class='container-full-width' style='width:calc(75% - 16px); background:transparent;'>"

                    + "<div class='container-full-width' style='width:10%; margin:0; padding:0; background:transparent; position:relative; float:left;'>"
                    + "<div class='inventoryImage' style='width:100%;'>"
                    + "<div class='inventoryImage-content'>"
                    + svgString
                    + "</div>"
                    + "<div class='overlay no-pointer' id='LOADED_BODY_CURRENT'></div>"
                    + "</div>"
                    + "</div>"

                    + "<div style='width:calc(90% - 8px); padding:0; margin:0 0 0 8px; position:relative; float:left;'>"
                    + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='new_save_name' placeholder='Enter File Name' style='padding:0;margin:0;width:100%;'></form>"
                    + "</div>"

                    + "</div>"

                    + "<div class='container-full-width' style='width:calc(25% - 16px); text-align:center; background:transparent;'>"
                    + "<div class='square-button saveIcon' id='NEW_SAVE' style='float:left;'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSave() + "</div></div>"
                    + "</div>"
                    + "</div>";
        }
    }

    public static final DialogueNode BODY_CHANGING_PENIS = new DialogueNode("Penis", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getSelfTransformDescription("penis"));

            if (!getTarget().isDoll() || debugMenu) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformPenisChoiceDiv(getRacesForMinorPartSelfTransform(), false));
            }

            if (getTarget().hasPenis()) {
                UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformPenisSizeDiv()).append(CharacterModificationUtils.getSelfTransformPenisGirthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformPenisModifiersDiv()).append(CharacterModificationUtils.getSelfTransformCumExplusionDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformCumProductionDiv()).append(CharacterModificationUtils.getSelfTransformCumRegenerationDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformCumFlavourDiv()).append(CharacterModificationUtils.getSelfTransformCumModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTesticleCountDiv()).append(CharacterModificationUtils.getSelfTransformInternalTesticleDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformTesticleSizeDiv()).append(CharacterModificationUtils.getSelfTransformUrethraModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformUrethraCapacityDiv()).append(CharacterModificationUtils.getSelfTransformUrethraDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformUrethraElasticityDiv()).append(CharacterModificationUtils.getSelfTransformUrethraPlasticityDiv()).append("</div>");

                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                        BodyChanging.getTarget().getPenisRace(),
                        BodyChanging.getTarget().getCovering(BodyCoveringType.PENIS).getType(),
                        "Penis Colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] penis."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                        Race.NONE,
                        BodyChanging.getTarget().getCovering(BodyCoveringType.CUM).getType(),
                        "Cum Colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] [npc.cum]."),
                        true, true));

                if (Main.game.isPubicHairEnabled() && (!BodyChanging.getTarget().isDoll() || debugMenu)) {
                    UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivPubicHair(false, "Pubic hair",
                            UtilText.parse(BodyChanging.getTarget(), "Change the amount of hair around [npc.namePos] genitals."))).append(CharacterModificationUtils.getKatesDivCoveringsNew(false, Race.NONE, getTarget().getPubicHairType().getType(), "Pubic hair colour",
                            UtilText.parse(BodyChanging.getTarget(), "The colour of [npc.namePos] pubic hair."),
                            true, true));
                }
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    public static void saveBody(String name, boolean allowOverwrite) {
        name = Main.checkFileName(name);
        if (name.isEmpty()) {
            return;
        }

        Body body = BodyChanging.getTarget().getBody();

        Path dir = FS.newPath("data/");
        try {
            Files.createDirectory(dir);
        } catch (IOException e1) {
        }

        dir = FS.newPath("data/transformation_presets");
        try {
            Files.createDirectory(dir);
        } catch (IOException e) {
        }

        if (Files.isDirectory(dir)) {
            Path[] directoryListing;
            BiPredicate<Path, String> filter = (path, filename) -> filename.endsWith(".xml");
            try (Stream<Path> stream = Files.list(dir)) {
                directoryListing = stream.filter(dir1 -> filter.test((dir1), dir1.getFileName().toString())).toList().toArray(new Path[0]);
            } catch (IOException e) {
                directoryListing = null;
            }
            if (directoryListing != null) {
                for (Path child : directoryListing) {
                    if (child.getFileName().toString().equals(name + ".xml")) {
                        if (!allowOverwrite) {
                            Main.game.flashMessage(PresetColour.GENERIC_BAD, "Name already exists!");
                            return;
                        }
                    }
                }
            }
        }

        try {
            // Starting stuff:
            Document doc = Main.getDocBuilder().newDocument();

            Element coreElement = doc.createElement("body");
            doc.appendChild(coreElement);

            body.saveAsXML(coreElement, doc);

            // Ending stuff:

            Transformer transformer1 = Main.transformerFactory.newTransformer();
            transformer1.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();

            transformer1.transform(new DOMSource(doc), new StreamResult(writer));

            // Save this xml:
            Transformer transformer = Main.transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            String saveLocation = "data/transformation_presets/" + name + ".xml";
            StreamResult result = new StreamResult(saveLocation);

            transformer.transform(source, result);

        } catch (TransformerException tfe) {
            System.getLogger("").log(System.Logger.Level.ERROR, tfe.getMessage(), tfe);
        }

        BodyChanging.initSaveLoadMenu();
        Main.game.setContent(new Response("", "", BodyChanging.BODY_CHANGING_SAVE_LOAD));
    }

    public static final DialogueNode BODY_CHANGING_BREASTS_CROTCH = new DialogueNode("Crotch-boobs", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getSelfTransformDescription("[npc.crotchBoobs]"));

            if (!getTarget().isDoll() || debugMenu) {
                UtilText.nodeContentSB.append(CharacterModificationUtils.getSelfTransformBreastCrotchChoiceDiv(getRacesForMinorPartSelfTransform()));
            }

            if (BodyChanging.getTarget().hasBreastsCrotch()) {
                UtilText.nodeContentSB.append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformBreastCrotchSizeDiv()).append(CharacterModificationUtils.getSelfTransformBreastCrotchShapeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformBreastCrotchRowsDiv()).append(CharacterModificationUtils.getSelfTransformNippleCrotchModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLactationCrotchDiv()).append(CharacterModificationUtils.getSelfTransformLactationCrotchRegenerationDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformLactationCrotchFlavourDiv()).append(CharacterModificationUtils.getSelfTransformLactationCrotchModifiersDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleCrotchCountDiv()).append(CharacterModificationUtils.getSelfTransformNippleCrotchShapeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleCrotchSizeDiv()).append(CharacterModificationUtils.getSelfTransformAreolaeCrotchSizeDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleCrotchCapacityDiv()).append(CharacterModificationUtils.getSelfTransformNippleCrotchDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformNippleCrotchElasticityDiv()).append(CharacterModificationUtils.getSelfTransformNippleCrotchPlasticityDiv()).append("</div>");

                UtilText.nodeContentSB.append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                        BodyChanging.getTarget().getBreastCrotchRace(),
                        BodyChanging.getTarget().getCovering(BodyCoveringType.NIPPLES_CROTCH).getType(),
                        "Nipple Colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] [npc.crotchNipples]."),
                        true, true)).append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                        Race.NONE,
                        BodyChanging.getTarget().getCovering(BodyCoveringType.MILK).getType(),
                        "Milk Colour",
                        UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] [npc.milk]."),
                        true, true));
            }
            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    public static Body loadBody(String name) {
        if (isLoadBodyAvailable(name)) {
            Path file = FS.newPath("data/transformation_presets/" + name + ".xml");

            if (Files.exists(file)) {
                try {
                    DocumentBuilder fsDocumentBuilder = Main.getDocBuilder();
                    Document doc = fsDocumentBuilder.parse(Files.newInputStream(file));

                    // Cast magic:
                    doc.getDocumentElement().normalize();

                    Body body = Body.loadFromXML(null, doc.getDocumentElement(), doc);
                    body.calculateRace(null);

                    return body;

                } catch (Exception e) {
                    System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                }
            }
        }

        return null;
    }

    public static final DialogueNode BODY_CHANGING_SPINNERET = new DialogueNode("Spinneret", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getSelfTransformDescription("spinneret")).append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformSpinneretModifiersDiv()).append(CharacterModificationUtils.getSelfTransformSpinneretWetnessDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformSpinneretCapacityDiv()).append(CharacterModificationUtils.getSelfTransformSpinneretDepthDiv()).append("</div>").append("<div style='clear:left;'>").append(CharacterModificationUtils.getSelfTransformSpinneretElasticityDiv()).append(CharacterModificationUtils.getSelfTransformSpinneretPlasticityDiv()).append("</div>").append(CharacterModificationUtils.getKatesDivCoveringsNew(false,
                    BodyChanging.getTarget().hasLegSpinneret()
                            ? BodyChanging.getTarget().getLegRace()
                            : BodyChanging.getTarget().getTailRace(),
                    BodyChanging.getTarget().getCovering(BodyCoveringType.SPINNERET).getType(),
                    "Spinneret Colour",
                    UtilText.parse(BodyChanging.getTarget(), "Change the colour of [npc.namePos] spinneret."),
                    true, true));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };

    public static boolean isLoadBodyAvailable(String name) {
        Path file = FS.newPath("data/transformation_presets/" + name + ".xml");

        return Files.exists(file);
    }

    public static final DialogueNode BODY_CHANGING_MAKEUP = new DialogueNode("Makeup", "", true) {
        @Override
        public String getHeaderContent() {
            return getSelfTransformDescription("makeup")

                    + CharacterModificationUtils.getKatesDivCoveringsNew(
                    false, Race.NONE, BodyCoveringType.MAKEUP_BLUSHER, "Blusher", "Blusher (also called rouge) is used to colour the cheeks so as to provide a more youthful appearance, and to emphasise the cheekbones.", true, true)

                    + CharacterModificationUtils.getKatesDivCoveringsNew(
                    false, Race.NONE, BodyCoveringType.MAKEUP_LIPSTICK, "Lipstick", "Lipstick is used to provide colour, texture, and protection to the wearer's lips.", true, true)

                    + CharacterModificationUtils.getKatesDivCoveringsNew(
                    false, Race.NONE, BodyCoveringType.MAKEUP_EYE_LINER, "Eyeliner", "Eyeliner is applied around the contours of the eyes to help to define shape or highlight different features.", true, true)

                    + CharacterModificationUtils.getKatesDivCoveringsNew(
                    false, Race.NONE, BodyCoveringType.MAKEUP_EYE_SHADOW, "Eye shadow", "Eye shadow is used to make the wearer's eyes stand out or look more attractive.", true, true)

                    + CharacterModificationUtils.getKatesDivCoveringsNew(
                    false, Race.NONE, BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, "Nail polish", "Nail polish is used to colour and protect the nails on your [pc.hands].", true, true)

                    + CharacterModificationUtils.getKatesDivCoveringsNew(
                    false, Race.NONE, BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, "Toenail polish", "Toenail polish is used to colour and protect the nails on your [pc.feet].", true, true);
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getBodyChangingResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };


    // Save/Load dialogue and associated methods/variables:

    public static void deleteBody(String name) {
        Path file = FS.newPath("data/transformation_presets/" + name + ".xml");

        if (Files.exists(file)) {
            try {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                }
                Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex.getMessage(), ex);
            }

        } else {
            Main.game.flashMessage(PresetColour.GENERIC_BAD, "File not found...");
        }
    }

    public static boolean isPresetTransformationAvailable(Body body) {
        String unavailabilityText = getPresetTransformationUnavailabilityText(body);
        return unavailabilityText == null || unavailabilityText.isEmpty();
    }

    public static String getPresetTransformationUnavailabilityText(Body body) {
        if (debugMenu) {
            return "";
        }

        // Height limitation:
        if (BodyChanging.getTarget().isShortStature() != body.isShortStature()) {
            if (BodyChanging.getTarget().isShortStature()) {
                return UtilText.parse(BodyChanging.getTarget(), "[npc.NameIsFull] too short to be able to transform into this form!");
            } else {
                return UtilText.parse(BodyChanging.getTarget(), "[npc.NameIsFull] too tall to be able to transform into this form!");
            }
        }

        // Material limitations:
        Set<BodyMaterial> materialsAllowed = Util.newHashSetOfValues(BodyChanging.getTarget().getBodyMaterial());
        if (BodyChanging.getTarget() instanceof Elemental) {
            switch (BodyChanging.getTarget().getBodyMaterial()) {
                // Air:
                case AIR:
                    break;
                // Arcane:
                case ARCANE:
                    break;
                // Fire:
                case FIRE:
                    break;
                // Earth:
                case RUBBER:
                    materialsAllowed.add(BodyMaterial.STONE);
                    break;
                case STONE:
                    materialsAllowed.add(BodyMaterial.RUBBER);
                    break;
                // Water:
                case WATER:
                    materialsAllowed.add(BodyMaterial.ICE);
                    break;
                case ICE:
                    materialsAllowed.add(BodyMaterial.WATER);
                    break;
                // Non-elemental materials:
                case FLESH:
                case SLIME:
                case SILICONE:
                    break;
            }
        }
        if (!materialsAllowed.contains(body.getBodyMaterial())) {
            BodyMaterial matCurrent = BodyChanging.getTarget().getBodyMaterial();
            BodyMaterial matTarget = body.getBodyMaterial();
            return UtilText.parse(BodyChanging.getTarget(),
                    "[npc.Name] cannot transform into a body that has a different material than [npc.her] current one!"
                            + "<br/>Current material: <span style='color:" + matCurrent.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(matCurrent.getName()) + "</span>"
                            + "<br/>Transformation target's material: <span style='color:" + matTarget.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(matTarget.getName()) + "</span>");
        }

        // Feral limitations:
        if (body.isFeral() != BodyChanging.getTarget().isFeral()) {
            if (body.isFeral()) {
                return UtilText.parse(BodyChanging.getTarget(), "[npc.Name] cannot transform into a [style.colourFeral(feral body)] as [npc.her] current body isn't feral!");
            }
            return UtilText.parse(BodyChanging.getTarget(), "[npc.Name] cannot transform into a [style.colourHuman(non-feral body)] as [npc.her] current body is feral!");
        }

        // Demon/Youko limitations:
        if (isDemonTFMenu() || BodyChanging.getTarget().isYouko() || BodyChanging.getTarget() instanceof Elemental) {
            StringBuilder sb = new StringBuilder();
            List<String> partsList = new ArrayList<>();
            //TODO handle half-demons!
            // .getHalfDemonSubspecies()
            if (BodyChanging.getTarget().getSubspecies() == Subspecies.HALF_DEMON) {
                if (body.getRace() == Race.DEMON && body.getSubspecies() != Subspecies.HALF_DEMON) {
                    return UtilText.parse(BodyChanging.getTarget(), "As [npc.sheIsFull] a half-demon, [npc.name] cannot transform into a full demon!");
                }
            }

            for (BodyPartInterface part : body.getAllBodyParts()) {
                if (!BodyChanging.getTarget().getSelfTransformationRaces().contains(part.getType().getRace())) {
                    if (sb.isEmpty()) {
                        sb.append("[npc.NameIsFull] unable to transform into the associated race for the following parts:");
                        sb.append("<br/>");
                    }
                    partsList.add(part.getType().getName(BodyChanging.getTarget()));
                }
            }
            if (!sb.isEmpty()) {
                sb.append(Util.capitaliseSentence(Util.stringsToStringList(partsList, false)));
                sb.append(".");
                return UtilText.parse(BodyChanging.getTarget(), sb.toString());
            }

            if (BodyChanging.getTarget().isYouko()) { // Youko self-TF limitations:
                if (body.getArm().getArmRows() != BodyChanging.getTarget().getArmRows()) {
                    sb.append("<br/>Arm row count");
                }
                if (body.getGenitalArrangement() != BodyChanging.getTarget().getGenitalArrangement()) {
                    sb.append("<br/>Genital arrangement");
                }
                if (body.getTentacle().getType() != BodyChanging.getTarget().getTentacleType()) {
                    sb.append("<br/>Tentacle type");
                }
                if (body.getWing().getType() != BodyChanging.getTarget().getWingType()) {
                    sb.append("<br/>Wing type");
                }
                if (body.getEye().getEyePairs() != BodyChanging.getTarget().getEyePairs()) {
                    sb.append("<br/>Eye count");
                }
                if (body.getHorn().getType() != BodyChanging.getTarget().getHornType()) {
                    sb.append("<br/>Horn type");
                }
                if (body.getAntenna().getType() != BodyChanging.getTarget().getAntennaType()) {
                    sb.append("<br/>Antennae type");
                }
                if (!sb.isEmpty()) {
                    sb.insert(0, "Youkos' limited transformation powers prevent [npc.name] from transforming:");
                    return UtilText.parse(BodyChanging.getTarget(), sb.toString());
                }
            }

        } else if (body.getBodyMaterial() == BodyMaterial.SILICONE) {
            StringBuilder sb = new StringBuilder();
            List<String> partsList = new ArrayList<>();
            for (BodyPartInterface part : body.getAllBodyParts()) {
                BodyPartInterface currentPartEquivalent = null;
                for (BodyPartInterface partEquivalent : BodyChanging.getTarget().getBody().getAllBodyParts()) {
                    if (part.getClass() == partEquivalent.getClass()) {
                        currentPartEquivalent = partEquivalent;
                        break;
                    }
                }
                if (currentPartEquivalent == null) {
                    continue;
                }
                if (currentPartEquivalent.getType().getRace() != part.getType().getRace()) {
                    if (sb.isEmpty()) {
                        sb.append("[npc.NameIsFull] unable to transform into the associated race for the following parts:");
                        sb.append("<br/>");
                    }
                    partsList.add(part.getType().getName(BodyChanging.getTarget()));
                }
            }
            if (!sb.isEmpty()) {
                sb.append(Util.capitaliseSentence(Util.stringsToStringList(partsList, false)));
                sb.append(".");
                return UtilText.parse(BodyChanging.getTarget(), sb.toString());
            }


        } else if (body.getBodyMaterial() != BodyMaterial.SLIME) {
            StringBuilder sb = new StringBuilder();
            List<String> partsList = new ArrayList<>();
            for (BodyPartInterface part : body.getAllBodyParts()) {
                if (BodyChanging.getTarget().getRace() != part.getType().getRace()) {
                    if (sb.isEmpty()) {
                        sb.append("[npc.NameIsFull] unable to transform into the associated race for the following parts:");
                        sb.append("<br/>");
                    }
                    partsList.add(part.getType().getName(BodyChanging.getTarget()));
                }
            }
            if (!sb.isEmpty()) {
                sb.append(Util.capitaliseSentence(Util.stringsToStringList(partsList, false)));
                sb.append(".");
                return UtilText.parse(BodyChanging.getTarget(), sb.toString());
            }
        }
        return "";
    }

    /**
     * Sets the supplied body as the BodyChanging.getTarget()'s new body.
     * <br/>Retains all BodyChanging.getTarget()'s covering colours which are not actively used by the new body, replacing the loaded body's unused covering colours.
     * <br/>If the BodyChanging.getTarget() does not have covering colours saved which are present in the loaded body, then these loaded body's covering colours are retained.
     */
    public static void applyLoadedBody(Body body) {
        AbstractSubspecies subspeciesOverride = BodyChanging.getTarget().getSubspeciesOverride();
        Map<AbstractBodyCoveringType, Covering> oldCoverings = BodyChanging.getTarget().getBody().getCoverings();

        BodyChanging.getTarget().setBody(body, false);

        BodyChanging.getTarget().setSubspeciesOverride(subspeciesOverride);
        List<AbstractBodyCoveringType> currentlyActiveCoverings = new ArrayList<>();
        for (BodyPartInterface part : body.getAllBodyPartsWithAllOrifices()) {
            AbstractBodyCoveringType bct = BodyChanging.getTarget().getCovering(part);
            if (bct == null) {
//				System.out.println(part.getName(BodyChanging.getTarget()));
                continue;
            }
            if (BodyCoveringType.getAllMakeupTypes().contains(bct) || bct == BodyCoveringType.DILDO) {
                continue;
            }
            if (body.getBodyMaterial() != BodyMaterial.FLESH) {
                currentlyActiveCoverings.add(BodyCoveringType.getMaterialBodyCoveringType(body.getBodyMaterial(), bct.getCategory()));
            }
            currentlyActiveCoverings.add(bct);
        }

        for (Entry<AbstractBodyCoveringType, Covering> entry : oldCoverings.entrySet()) {
            if (!currentlyActiveCoverings.contains(entry.getKey())) {
                BodyChanging.getTarget().getBody().getCoverings().put(entry.getKey(), entry.getValue());
            }
        }

    }


    public static final DialogueNode BODY_CHANGING_SAVE_LOAD = new DialogueNode("Save transformation files", "", true) {
        @Override
        public String getContent() {
            return "";
        }

        @Override
        public String getHeaderContent() {
            StringBuilder saveLoadSB = new StringBuilder();

            saveLoadSB.append("<div class='container-full-width' style='padding:0; margin:0 0 8px 0;'>" + "<p>" + "Only standard characters (letters and numbers) will work for save file names." + "<br/>Hover over each transformation preset's icon to see the body overview.").append(UtilText.parse(BodyChanging.getTarget(), "<br/>If a name is [style.colourDisabled(greyed-out)], then [npc.name] [npc.do]n't have the ability to transform into that preset,"
                    + " and you can hover over the greyed-out load button to find out why it's unavailable.")).append("</p>").append("</div>").append("<div class='container-full-width' style='padding:0; margin:0;'>").append("<div class='container-full-width' style='width:calc(75% - 16px); text-align:center; background:transparent;'>").append("Name").append("</div>").append("<div class='container-full-width' style='width:calc(25% - 16px); text-align:center; background:transparent;'>").append("Save | Load | Delete").append("</div>").append("</div>");

            int i = 0;

            saveLoadSB.append(getSaveLoadRow(null, "", null, i % 2 == 0));
            i++;

            for (Entry<String, Value<String, Body>> entry : presetTransformationsMap.entrySet()) {
                saveLoadSB.append(getSaveLoadRow(entry.getKey(), entry.getValue().getKey(), entry.getValue().getValue(), i % 2 == 0));
                i++;
            }

            saveLoadSB.append("<p id='hiddenPField' style='display:none;'></p>");

            return saveLoadSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Confirmations: ",
                        "Toggle confirmations being shown when you click to load, overwrite, or delete a saved transformation."
                                + " When turned on, it will take two clicks to apply any button press."
                                + " When turned off, it will only take one click.",
                        BODY_CHANGING_SAVE_LOAD) {
                    @Override
                    public String getTitle() {
                        return "Confirmations: " + (Main.getProperties().hasValue(PropertyValue.overwriteWarning)
                                ? "<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</span>"
                                : "<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>OFF</span>");
                    }

                    @Override
                    public void effects() {
                        loadConfirmationName = "";
                        overwriteConfirmationName = "";
                        deleteConfirmationName = "";
                        Main.getProperties().setValue(PropertyValue.overwriteWarning, !Main.getProperties().hasValue(PropertyValue.overwriteWarning));
                        Main.getProperties().savePropertiesAsXML();
                    }
                };

            } else if (index == 0) {
                return new Response("Back", "Back to the transformation menu.", BODY_CHANGING_CORE);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.PHONE;
        }
    };


}
