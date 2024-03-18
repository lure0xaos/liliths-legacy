package com.lilithslegacy.game.dialogue.utils;

import com.lilithslegacy.controller.eventListeners.tooltips.TooltipInformationEventListener;
import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.body.valueEnums.AgeCategory;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.Lactation;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.fetishes.FetishPreference;
import com.lilithslegacy.game.character.gender.AndrogynousIdentification;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.gender.GenderNames;
import com.lilithslegacy.game.character.gender.GenderPronoun;
import com.lilithslegacy.game.character.gender.PronounType;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.persona.SexualOrientationPreference;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.FurryPreference;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.character.race.SubspeciesPreference;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.DialogueNodeType;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithslegacy.game.dialogue.story.CharacterCreation;
import com.lilithslegacy.game.settings.ContentPreferenceValue;
import com.lilithslegacy.game.settings.DifficultyLevel;
import com.lilithslegacy.game.settings.ForcedFetishTendency;
import com.lilithslegacy.game.settings.ForcedTFTendency;
import com.lilithslegacy.game.settings.KeyCodeWithModifiers;
import com.lilithslegacy.game.settings.KeyboardAction;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.Artist;
import com.lilithslegacy.rendering.ArtistWebsite;
import com.lilithslegacy.rendering.Artwork;
import com.lilithslegacy.rendering.SVGImages;
import com.lilithslegacy.utils.CreditsSlot;
import com.lilithslegacy.utils.Units;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;

import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * @author Innoxia, Maxis
 * @version 0.4.2
 * @since 0.1.0
 */
public class OptionsDialogue {

    public static boolean startingNewGame = false;
    public static String loadConfirmationName = "";
    public static String overwriteConfirmationName = "";
    public static String deleteConfirmationName = "";
    public static final DialogueNode MENU = new DialogueNode("Menu", "Menu", true) {

        @Override
        public String getLabel() {
            return "";
        }

        @Override
        public String getContent() {
            return "<h1 class='special-text' style='font-size:48px; line-height:52px; text-align:center;'>" + Main.GAME_NAME + "</h1>"
                    + (Main.game.isSillyMode()
                    ? "<p class='special-text' style='text-align:center; margin:0 0; padding:0 0;'><i>Or, I can't believe I fell into a magic mirror and entered a world in which my aunt is a demon?!</i></p>"
                    : "")
                    + "<h5 class='special-text' style='text-align:center;'>Created by " + Main.AUTHOR + "</h5>"
                    + "<br/>"
                    + "<p>"
                    + "This game is a text-based erotic RPG, and contains a lot of graphic sexual content. You must agree to the game's disclaimer before playing this game!"
                    + "</p>"
                    + "<p>"
                    + "You can visit my blog (https://lilithsthrone.blogspot.co.uk) to check on development progress (use the 'Blog' button below to open the blog in your default browser)."
                    + " [style.italicsMinorBad(<b>Note:</b> Intrusive age verification is being rolled out on blogspot, so I will likely create a new blog soon.)]"
                    + "</p>"
                    + "<p style='text-align:center'>"
                    + "<b>Please use either my blog or github to get the latest official version of Lilith's Throne!</b>"
                    + "</p>"
                    + getJavaVersionInformation()
                    + (Toolkit.getDefaultToolkit().getScreenSize().getHeight() < 800
                    ? "<p style='text-align:center; color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>"
                    + "If the game's resolution isn't fitting to your screen, press the keys: 'Windows' + 'Up Arrow' to maximise!"
                    + "</p>"
                    : "")
                    + "<br/>"
                    + (Main.game.isStarted() || Main.getProperties().name.isEmpty()
                    ? ""
                    : "<h4 style='text-align:center;'>Last save:</h4>"
                    + "<h5 style='color:" + Main.getProperties().nameColour + ";text-align:center;'>" + Main.getProperties().name + "</h5>"
                    + "<p style='text-align:center;'><b>Level " + Main.getProperties().level + " " + Util.capitaliseSentence(Main.getProperties().race) + "</b></p>"
                    + "<p style='text-align:center;'>" + UtilText.formatAsMoney(Main.getProperties().money, "b") + "</p>"
                    + "<div style='text-align:center; display:block; margin:auto;'>" + UtilText.formatAsEssences(Main.getProperties().arcaneEssences, "b", false) + "</div>"
                    + "<p style='text-align:center;'>Quest: " + Util.capitaliseSentence(Main.getProperties().quest) + "</p>");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                if (confirmNewGame || !Main.game.isStarted()) {
                    return new ResponseEffectsOnly(
                            (!Main.game.isStarted()
                                    ? "New Game"
                                    : "<b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Confirm</b>"),
                            "Start a new game."
                                    + (Main.game.isStarted()
                                    ? "<br/><br/>[style.italicsMinorBad(Remember to save your game first!)]</b>"
                                    : "")) {
                        @Override
                        public void effects() {
                            if (!startingNewGame) {
                                startingNewGame = true;

                                //Fixes a bug where inventory would stay on screen
                                if (Main.game.isStarted()) {
                                    Main.game.setInCombat(false);
                                    Main.game.setInSex(false);
                                }

                                Main.mainController.setAttributePanelContent("");
                                Main.mainController.setRightPanelContent("");
                                Main.mainController.setButtonsLeftContent("");
                                Main.mainController.setButtonsRightContent("");
                                Main.game.setRenderMap(false);
                                Main.startNewGame(CharacterCreation.CHARACTER_CREATION_START);
                                confirmNewGame = false;
                            }
                        }
                    };

                } else {
                    return new Response(
                            "New Game",
                            "Start a new game."
                                    + (Main.game.isStarted()
                                    ? "<br/><br/>[style.italicsMinorBad(Remember to save your game first!)]"
                                    : ""),
                            MENU) {
                        @Override
                        public void effects() {
                            confirmNewGame = true;
                        }
                    };
                }

            } else if (index == 2) {
                return new Response("Save/Load", "Open the save/load game window.", SAVE_LOAD) {
                    @Override
                    public void effects() {
                        loadConfirmationName = "";
                        overwriteConfirmationName = "";
                        deleteConfirmationName = "";
                        confirmNewGame = false;
                    }
                };

            } else if (index == 3) {
                return new Response("Export character", "Open the character export game window.", IMPORT_EXPORT) {
                    @Override
                    public void effects() {
                        loadConfirmationName = "";
                        overwriteConfirmationName = "";
                        deleteConfirmationName = "";
                        confirmNewGame = false;
                    }
                };

            } else if (index == 4) {
                return new Response("Disclaimer", "View the game's disclaimer.", DISCLAIMER) {
                    @Override
                    public void effects() {
                        confirmNewGame = false;
                    }
                };

            } else if (index == 5) {
                return new ResponseEffectsOnly("Quit", "Quits your current game and closes the program.<br/><br/><b>Remember to save your game first!</b>") {
                    @Override
                    public void effects() {
                        Main.primaryStage.close();
                        confirmNewGame = false;
                        System.exit(0);
                    }
                };

            } else if (index == 6) {
                return new Response("Options", "Open the options page.", OPTIONS) {
                    @Override
                    public void effects() {
                        confirmNewGame = false;

                    }
                };

            } else if (index == 7) {
                return new Response("Content Options", "Set your preferred content settings.", MISCELLANEOUS) {
                    @Override
                    public void effects() {
                        confirmNewGame = false;
                    }
                };

            } else if (index == 8) {
                return new Response("Patch notes", "View the patch notes for this version.", PATCH_NOTES);

            } else if (index == 9) {
                return new Response("Credits", "View the game's credits screen.", CREDITS);

            } else if (index == 11) {
                return new ResponseEffectsOnly("Blog", "Opens the page:<br/><br/><i>https://lilithsthrone.blogspot.co.uk/</i><br/><br/><b>Externally in your default browser.</b>") {
                    @Override
                    public void effects() {
                        Util.openLinkInDefaultBrowser("https://lilithsthrone.blogspot.co.uk/");
                        confirmNewGame = false;
                    }
                };

            } else if (index == 12) {
                return new ResponseEffectsOnly("Github", "Opens the page:<br/><br/><i>https://github.com/Innoxia/liliths-throne-public</i><br/><br/><b>Externally in your default browser.</b>") {
                    @Override
                    public void effects() {
                        Util.openLinkInDefaultBrowser("https://github.com/Innoxia/liliths-throne-public");
                        confirmNewGame = false;
                    }
                };

            } else if (index == 13) {
                return new ResponseEffectsOnly("Wiki", "Opens the page:<br/><br/><i>https://www.lilithsthrone.com/wiki/doku.php</i><br/><br/><b>Externally in your default browser.</b>") {
                    @Override
                    public void effects() {
                        Util.openLinkInDefaultBrowser("https://www.lilithsthrone.com/wiki/doku.php");
                        confirmNewGame = false;
                    }
                };

            } else if (index == 0) {
                if (Main.game.isStarted()) {
                    return new ResponseEffectsOnly("Resume", "Return to whatever you were doing before opening this menu.") {
                        @Override
                        public void effects() {
                            Main.mainController.openOptions();
                            confirmNewGame = false;

                        }
                    };

                } else {
                    if (Main.isLoadGameAvailable(Main.getProperties().lastSaveLocation)) {
                        return new ResponseEffectsOnly("Resume", "Continue playing from your last save.") {
                            @Override
                            public void effects() {
                                Main.loadGame(Main.getProperties().lastSaveLocation);
                                confirmNewGame = false;

                            }
                        };
                    } else if ("".equals(Main.getProperties().lastSaveLocation)) {
                        return new Response("Resume", "There is no previously saved game to resume.", null);
                    } else {
                        return new Response("Resume", "Previously saved game (by the title '" + Main.getProperties().lastSaveLocation + "') not found in 'data/saves' folder.", null);
                    }
                }

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };
    private static boolean confirmNewGame = false;
    private static boolean alphabeticalFileSort = false;

    private static String getJavaVersionInformation() {

        String sb = "<p style='text-align:center;'>" + "Your java version: " + System.getProperty("java.version") +
//				+" | ");

//		String[] version = System.getProperty("java.version").split("\\.");
//		if(version[0]!=null) {
//			if(Integer.valueOf(version[0])<9) {
//				sb.append("<span style='color:"+PresetColour.GENERIC_BAD.toWebHexString()+";'>You have an old version of java!</span> This game needs at least 9.0.1 to work correctly!");
//			} else {
//				sb.append("<span style='color:"+PresetColour.GENERIC_GOOD.toWebHexString()+";'>Your java is up to date!</span>");
//			}
//		}
//		if(version.length>=2) {
//			if(Integer.valueOf(version[1])<8) {
//				sb.append("<span style='color:"+PresetColour.GENERIC_BAD.toWebHexString()+";'>You have an old version of java!</span> This game needs at least v1.8.0_131 to work correctly!");
//
//			} else {
//				if(version.length==3){
//					String[] versionMinor = version[2].split("_");
//					if(versionMinor.length>=2)
//						if(Integer.valueOf(versionMinor[1])<131) {
//							sb.append("<span style='color:"+PresetColour.GENERIC_BAD.toWebHexString()+";'>You have an old version of java!</span> This game needs at least v1.8.0_131 to work correctly!");
//
//						} else {
//							sb.append("<span style='color:"+PresetColour.GENERIC_GOOD.toWebHexString()+";'>Your java is up to date!</span>");
//						}
//				} else {
//					sb.append("This game needs at least v1.8.0_131 to work correctly!");
//				}
//			}
//		}

                "</p>";

        return sb;
    }

    private static String getSaveLoadRow(String date, String name, boolean altColour) {
        if (name != null) {
            String baseName = Util.getFileName(name);
            String identifierName = Util.getFileIdentifier(name);

            return "<div class='container-full-width' style='padding:0; margin:0 0 4px 0;" + (altColour ? "background:" + PresetColour.BACKGROUND_ALT.toWebHexString() + ";" : "") + "'>"
                    + "<div class='container-full-width' style='width:calc(25% - 16px); background:transparent;'>"
                    + date
                    + "</div>"
                    + "<div class='container-full-width' style='width:calc(50% - 16px); background:transparent;'>"
                    + baseName
                    + "</div>"
                    + "<div class='container-full-width' style='width:calc(25% - 16px);text-align:center; background:transparent;'>"
                    + (Main.isSaveGameAvailable()
                    ? (name.equals(overwriteConfirmationName)
                    ? "<div class='square-button saveIcon' id='OVERWRITE_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSaveConfirm() + "</div></div>"
                    : "<div class='square-button saveIcon' id='OVERWRITE_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskOverwrite() + "</div></div>")
                    : "<div class='square-button saveIcon disabled' id='OVERWRITE_" + identifierName + "_DISABLED'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSaveDisabled() + "</div></div>")

                    + (name.equals(loadConfirmationName)
                    ? "<div class='square-button saveIcon' id='LOAD_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskLoadConfirm() + "</div></div>"
                    : "<div class='square-button saveIcon' id='LOAD_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskLoad() + "</div></div>")


                    + (name.equals(deleteConfirmationName)
                    ? "<div class='square-button saveIcon' id='DELETE_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskDeleteConfirm() + "</div></div>"
                    : "<div class='square-button saveIcon' id='DELETE_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskDelete() + "</div></div>")
                    + "</div>"
                    + "</div>";

        } else {
            return "<div class='container-full-width' style='padding:0; margin:0 0 4px 0;" + (altColour ? "background:" + PresetColour.BACKGROUND_ALT.toWebHexString() + ";" : "") + "'>"
                    + "<div class='container-full-width' style='width:calc(25% - 16px); background:transparent;'>"
                    + "-"
                    + "</div>"
                    + "<div class='container-full-width' style='width:calc(50% - 16px); background:transparent;'>"
                    + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='new_save_name' value='New Save' style='padding:0;margin:0;width:100%;'></form>"
                    + "</div>"
                    + "<div class='container-full-width' style='width:calc(25% - 16px); text-align:center; background:transparent;'>"
                    + (Main.isSaveGameAvailable()
                    ? "<div class='square-button saveIcon' id='NEW_SAVE' style='float:left;'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSave() + "</div></div>"
                    : "<div class='square-button saveIcon disabled' id='NEW_SAVE_DISABLED' style='float:left;'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskSaveDisabled() + "</div></div>")
                    + "</div>"
                    + "</div>";

        }
    }

    private static String getImportRow(String date, String name, boolean altColour) {
        String baseName = Util.getFileName(name);
        String identifierName = Util.getFileIdentifier(name);

        return "<div class='container-full-width' style='padding:0; margin:0 0 4px 0;" + (altColour ? "background:#222;" : "") + "'>"
                + "<div class='container-quarter-width' style='background:transparent;'>"
                + date
                + "</div>"
                + "<div class='container-half-width' style='width: calc(55% - 16px); background:transparent;'>"
                + baseName
                + "</div>"
                + "<div class='container-quarter-width' style='padding:auto 0; margin:auto 0; width:20%; text-align:center; background:transparent;'>"
                + (name.equals(deleteConfirmationName)
                ? "<div class='square-button big' id='DELETE_CHARACTER_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskDeleteConfirm() + "</div></div>"
                : "<div class='square-button big' id='DELETE_CHARACTER_" + identifierName + "'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getDiskDelete() + "</div></div>")
                + "</div>"
                + "</div>";
    }

    public static final DialogueNode SAVE_LOAD = new DialogueNode("Save game files", "", true) {

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public String getHeaderContent() {
            StringBuilder saveLoadSB = new StringBuilder();

            saveLoadSB.append("<p style='text-align:center;'>" + "<b>Please Note:</b>" + "</p>" + "<p>" + "1. Only standard characters (letters and numbers) will work for save file names.<br/>" + "2. The 'AutoSave' file is automatically overwritten every time you move between maps.<br/>" + "3. The 'QuickSave' file is automatically overwritten every time you quick save (binding is ").append(Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.QUICKSAVE).getFullName()).append(").<br/>").append("4. You cannot save during scenes which restrict your movement, including combat and sex.").append("</p>").append("<div class='container-full-width' style='padding:0; margin:0;'>").append("<div class='container-full-width' style='width:calc(25% - 16px); background:transparent;'>").append("Time").append("</div>").append("<div class='container-full-width' style='width:calc(50% - 16px); text-align:center; background:transparent;'>").append("Name").append("</div>").append("<div class='container-full-width' style='width:calc(25% - 16px); text-align:center; background:transparent;'>").append("Save | Load | Delete").append("</div>").append("</div>");


            int i = 0;

            if (Main.game.isStarted()) {
                saveLoadSB.append(getSaveLoadRow(null, null, i % 2 == 0));
                i++;
            }

//			Main.getSavedGames(alphabeticalFileSort).sort(Comparator.comparingLong(File::lastModified).reversed());

            for (Path f : Main.getSavedGames(alphabeticalFileSort)) {
                saveLoadSB.append(getSaveLoadRow("<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>" + Util.getFileTime(f) + "</span>", f.getFileName().toString(), i % 2 == 0));
                i++;
            }

            saveLoadSB.append("<p id='hiddenPField' style='display:none;'></p>");

            return saveLoadSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Confirmations: ",
                        "Toggle confirmations being shown when you click to load, overwrite, or delete a saved game."
                                + " When turned on, it will take two clicks to apply any button press."
                                + " When turned off, it will only take one click.",
                        SAVE_LOAD) {
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

            } else if (index == 2) {
                return new Response("Sort: Date", "Sort all of your saved games by their date.", SAVE_LOAD) {
                    @Override
                    public void effects() {
                        alphabeticalFileSort = false;
                    }
                };

            } else if (index == 3) {
                return new Response("Sort: Name", "Sort all of your saved games by their name.", SAVE_LOAD) {
                    @Override
                    public void effects() {
                        alphabeticalFileSort = true;
                    }
                };

            } else if (index == 0) {
                return new Response("Back", "Back to the main menu.", MENU);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getKeybindTableRow(KeyboardAction action) {
        return "<tr>"
                + "<td>"
                + action.getName()
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<div class='bindingButton"
                + (Main.mainController.getActionToBind() == action
                && Main.mainController.isPrimaryBinding() ? " active" : "")
                + "' id='KB_PRIMARY_"
                + action
                + "'>"
                + (Main.getProperties().hotkeyMapPrimary.get(action) == null ? "<span class='option-disabled'>-</span>" : Main.getProperties().hotkeyMapPrimary.get(action).getFullName())
                + "</div>"
                + "<div class='bindingClearButton"
                + (Main.getProperties().hotkeyMapPrimary.get(action) == null ? " empty" : "")
                + "' id='KB_PRIMARY_CLEAR_"
                + action
                + "'><b>x</b></div>"
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<div class='bindingButton"
                + (Main.mainController.getActionToBind() == action
                && !Main.mainController.isPrimaryBinding() ? " active" : "")
                + "' id='KB_SECONDARY_"
                + action
                + "'>"
                + (Main.getProperties().hotkeyMapSecondary.get(action) == null ? "<span class='option-disabled'>-</span>" : Main.getProperties().hotkeyMapSecondary.get(action).getFullName())
                + "</div>"
                + "<div class='bindingClearButton"
                + (Main.getProperties().hotkeyMapSecondary.get(action) == null ? " empty" : "")
                + "' id='KB_SECONDARY_CLEAR_"
                + action
                + "'><b>x</b></div>"
                + "</td>"
                + "</tr>";
    }

    public static final DialogueNode IMPORT_EXPORT = new DialogueNode("Export character", "", true) {

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public String getHeaderContent() {
            StringBuilder saveLoadSB = new StringBuilder();

            saveLoadSB.append("<p>"
                    + "Here you can export your current character, or delete any characters that you've exported in the past."
                    + " Any NPC can be exported in-game by viewing their information screen (either from the 'characters present' or your phone's 'contacts' screen), and then pressing the 'export character' button (in the bottom-right of the UI)."
                    + "</p>"
                    + "<p>"
                    + "Exported characters can be used as a playable character when starting a new game (choose 'Start (Import)'), or as an importable slave at the Auction Block in Slaver Alley."
                    + "</p>"
                    + "<div class='container-full-width' style='padding:0; margin:0;'>"
                    + "<div class='container-quarter-width' style='text-align:center;'>"
                    + "Time"
                    + "</div>"
                    + "<div class='container-half-width' style='width:calc(55% - 16px); text-align:center; background:transparent;'>"
                    + "Name"
                    + "</div>"
                    + "<div class='container-quarter-width' style='width:calc(20% - 16px); text-align:center; background:transparent;'>"
                    + "Functions"
                    + "</div>"
                    + "</div>");

            Main.getCharactersForImport().sort(Comparator.comparingLong((Path path) -> {
                try {
                    return Files.getLastModifiedTime(path).toMillis();
                } catch (IOException e) {
                    return -1;
                }
            }).reversed());
            int i = 0;
            for (Path f : Main.getCharactersForImport()) {
                saveLoadSB.append(OptionsDialogue.getImportRow("<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>" + Util.getFileTime(f) + "</span>", f.getFileName().toString(), i % 2 == 0));
            }

            saveLoadSB.append("<p id='hiddenPField' style='display:none;'></p>");

            return saveLoadSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Confirmations: ",
                        "Toggle confirmations being shown when you click to load, overwrite, or delete a saved game."
                                + " When turned on, it will take two clicks to apply any button press."
                                + " When turned off, it will only take one click.",
                        IMPORT_EXPORT) {
                    @Override
                    public String getTitle() {
                        return "Confirmations: " + (Main.getProperties().hasValue(PropertyValue.overwriteWarning)
                                ? "<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</span>"
                                : "<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>OFF</span>");
                    }

                    @Override
                    public void effects() {
                        OptionsDialogue.loadConfirmationName = "";
                        OptionsDialogue.overwriteConfirmationName = "";
                        OptionsDialogue.deleteConfirmationName = "";
                        Main.getProperties().setValue(PropertyValue.overwriteWarning, !Main.getProperties().hasValue(PropertyValue.overwriteWarning));
                        Main.getProperties().savePropertiesAsXML();
                    }
                };

            } else if (index == 2) {
                if (Main.game.isStarted()) {
                    return new Response("Export character", "Exports your character file to the 'data/characters/' folder.", IMPORT_EXPORT) {
                        @Override
                        public void effects() {
                            Main.game.getCharacterUtils().saveCharacterAsXML(Main.game.getPlayer());
                            Main.game.flashMessage(PresetColour.GENERIC_GOOD, "Character exported!");
                        }
                    };
                } else {
                    return new Response("Export character", "You'll need to start a game first!", null);
                }

            } else if (index == 0) {
                return new Response("Back", "Back to the main menu.", OptionsDialogue.MENU);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getGenderNameTableRow(GenderNames name) {
        return "<tr>"
                + "<td>"
                + (name.isHasPenis() ? "[style.colourGood(Penis)]" : "[style.colourDisabled(Penis)]")
                + " " + (name.isHasVagina() ? "[style.colourGood(Vagina)]" : "[style.colourDisabled(Vagina)]")
                + " " + (name.isHasBreasts() ? "[style.colourGood(Breasts)]" : "[style.colourDisabled(Breasts)]")
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='GENDER_NAME_MASCULINE_" + name + "' value='"
                + UtilText.parseForHTMLDisplay(Main.getProperties().genderNameMale.get(name))
                + "'>"
                + "</form>"
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='GENDER_NAME_ANDROGYNOUS_" + name + "' value='"
                + UtilText.parseForHTMLDisplay(Main.getProperties().genderNameNeutral.get(name))
                + "'>"
                + "</form>"
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='GENDER_NAME_FEMININE_" + name + "' value='"
                + UtilText.parseForHTMLDisplay(Main.getProperties().genderNameFemale.get(name))
                + "'>"
                + "</form>"
                + "</td>"
                + "</tr>";
    }

    private static String getPronounTableRow(GenderPronoun pronoun) {
        return "<tr>"
                + "<td>"
                + pronoun.getName()
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='masculine_" + pronoun + "' value='" + UtilText.parseForHTMLDisplay(Main.getProperties().genderPronounMale.get(pronoun)) + "'>"
                + "</form>"
                + "</td>"
                + "<td style='min-width:160px;'>"
                + "<form style='padding:0;margin:0;text-align:center;'><input type='text' id='feminine_" + pronoun + "' value='" + UtilText.parseForHTMLDisplay(Main.getProperties().genderPronounFemale.get(pronoun)) + "'></form>"
                + "</td>"
                + "</tr>";
    }

    private static String getGenderPreferencesPanel(PronounType type) {
        int count = 0;
        Colour colour = PresetColour.MASCULINE;
        switch (type) {
            case FEMININE:
                colour = PresetColour.FEMININE;
                break;
            case MASCULINE:
                colour = PresetColour.MASCULINE;
                break;
            case NEUTRAL:
                colour = PresetColour.ANDROGYNOUS;
                break;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("<div class='container-full-width' style='text-align:center;'>" + "<p><b style='color:").append(type.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(type.getName())).append("</b></p>");

        for (Gender g : Gender.values()) {
            if (g.getType() == type) {
                sb.append("<div style='display:inline-block; margin:4px auto;width:100%;'>" + "<div style='display:inline-block; margin:0 auto;'>" + "<div style='width:140px; float:left;'><b style='color:").append(colour.getShades(8)[count]).append(";'>").append(Util.capitaliseSentence(g.getName())).append("</b></div>");

                for (ContentPreferenceValue preference : ContentPreferenceValue.values()) {
                    sb.append("<div id='").append(preference).append("_").append(g).append("' class='preference-button").append(Main.getProperties().genderPreferencesMap.get(g) == preference.getValue() ? " selected" : "").append("'>").append(Util.capitaliseSentence(preference.getName())).append("</div>");
                }

                sb.append("<p><br/>" + "<span style='color:").append(colour.getShades(8)[count]).append(";'>").append(Util.capitaliseSentence(g.getName())).append("s</span> have ").append(g.getGenderName().isHasVagina() ? "a [style.colourGood(vagina)]" : "no [style.colourBad(vagina)]").append(", ").append(g.getGenderName().isHasPenis() ? "a [style.colourGood(penis)]" : "no [style.colourBad(penis)]").append(", and ").append(g.getGenderName().isHasBreasts() ? "[style.colourGood(breasts)]" : "no [style.colourBad(breasts)]").append(".").append("</p>").append("</div>").append("</div>").append("<hr/>");
                count++;
            }
        }

        sb.append(getGenderRepresentation()).append("</div>");

        return sb.toString();
    }

    public static final DialogueNode OPTIONS = new DialogueNode("Options", "Options", true) {

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append("<p>" + "<b>Light/Dark theme:</b>" + "<br/>This switches the main display between a light and dark theme. (Work in progress!)" + "</p>" + "<p>" + "<b>Font-size:</b><br/>" + "This cycles the game's base font size. This currently only affects the size of the text in the main dialogue, but in the future I'll expand it to include every display element.<br/>" + "Minimum font size is " + Game.FONT_SIZE_MINIMUM + ". Default font size is " + Game.FONT_SIZE_NORMAL + ". Maximum font size is " + Game.FONT_SIZE_HUGE + ".<br/>" + "Current font size: ").append(Main.getProperties().fontSize).append(".").append("</p>").append("<p>").append("<b>Fade-in:</b>").append("<br/>This option is responsible for fading in the main part of the text each time a new scene is displayed.").append(" Although it makes scene transitions a little prettier, it is off by default, as it can cause some annoying lag in inventory screens.").append("</p>").append("<p>").append("<b>Difficulty (Currently set to ").append(Main.getProperties().difficultyLevel.getName()).append("):</b>");

            for (DifficultyLevel dl : DifficultyLevel.values()) {
                UtilText.nodeContentSB.append("<br/>").append(Main.getProperties().difficultyLevel == dl
                        ? "<b style='color:" + dl.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(dl.getName()) + "</b> " + dl.getDescription()
                        : "<span style='color:" + dl.getColour().getShades()[0] + ";'>" + Util.capitaliseSentence(dl.getName()) + "</span> [style.colourDisabled(" + dl.getDescription() + ")]");
            }

            UtilText.nodeContentSB.append("</p>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Keybinds", "Open the keybinds page, where you can customise all the game's key bindings.", KEYBINDS);

            } else if (index == 2) {

                if (Main.getProperties().hasValue(PropertyValue.lightTheme)) {
                    return new Response("Dark theme", "Switch the theme to the dark variant.", OPTIONS) {
                        @Override
                        public void effects() {
                            Main.mainController.switchTheme();

                        }
                    };
                } else {
                    return new Response("Light theme (WIP)", "Switch the theme to the light variant.<br/><br/><b>This is still a work in progress...</b>.", OPTIONS) {
                        @Override
                        public void effects() {
                            Main.mainController.switchTheme();

                        }
                    };
                }

            } else if (index == 3) {
                return new Response("Font-size -",
                        "Decrease the size of the game's font. Default value is 18. Current value is " + Main.getProperties().fontSize + ".",
                        OPTIONS) {
                    @Override
                    public void effects() {
                        if (Main.getProperties().fontSize > Game.FONT_SIZE_MINIMUM) {
                            Main.getProperties().fontSize--;
                        }
                        Main.saveProperties();

                    }
                };

            } else if (index == 4) {
                return new Response("Font-size +",
                        "Increase the size of the game's font. Default value is 18. Current value is " + Main.getProperties().fontSize + ".",
                        OPTIONS) {
                    @Override
                    public void effects() {
                        if (Main.getProperties().fontSize < Game.FONT_SIZE_HUGE) {
                            Main.getProperties().fontSize++;
                        }
                        Main.saveProperties();

                    }
                };

            } else if (index == 5) {
                return new Response("Fade-in: " + (Main.getProperties().hasValue(PropertyValue.fadeInText)
                        ? "[style.boldGood(ON)]"
                        : "[style.boldBad(OFF)]"), "Toggle the fading in of the game's text. If turned on, it may cause some minor lag in inventory screens.", OPTIONS) {
                    @Override
                    public void effects() {
                        Main.getProperties().setValue(PropertyValue.fadeInText, !Main.getProperties().hasValue(PropertyValue.fadeInText));
                        Main.saveProperties();
                    }
                };

            } else if (index == 6) {
                return new Response("Gender pronouns", "Customise all gender pronouns and names.", OPTIONS_PRONOUNS);

            } else if (index == 7) {
                return new Response("Unit preferences", "Set your preferred measurement units.", UNIT_PREFERENCE);
            } else if (index == 8) {
                return new Response("Difficulty: " + Main.getProperties().difficultyLevel.getName(), "Cycle the game's difficulty.", OPTIONS) {
                    @Override
                    public void effects() {
                        switch (Main.getProperties().difficultyLevel) {
                            case NORMAL:
                                Main.getProperties().difficultyLevel = DifficultyLevel.LEVEL_SCALING;
                                break;
                            case LEVEL_SCALING:
                                Main.getProperties().difficultyLevel = DifficultyLevel.HARD;
                                break;
                            case HARD:
                                Main.getProperties().difficultyLevel = DifficultyLevel.NIGHTMARE;
                                break;
                            case NIGHTMARE:
                                Main.getProperties().difficultyLevel = DifficultyLevel.HELL;
                                break;
                            case HELL:
                                Main.getProperties().difficultyLevel = DifficultyLevel.NORMAL;
                                break;
                        }
                        Main.saveProperties();

                        for (NPC npc : Main.game.getAllNPCs()) {
                            if (!Main.game.isInCombat() || !Main.combat.getAllCombatants(false).contains(npc)) {
                                npc.setMana(npc.getAttributeValue(Attribute.MANA_MAXIMUM));
                                npc.setHealth(npc.getAttributeValue(Attribute.HEALTH_MAXIMUM));
                            }
                        }
                    }
                };
            } else if (index == 0) {
                return new Response("Back", "Back to the main menu.", MENU);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getOrientationRepresentation() {
        float total = 0;
        for (SexualOrientation o : SexualOrientation.values()) {
            total += Main.getProperties().orientationPreferencesMap.get(o);
        }

        StringBuilder sb = new StringBuilder();

        if (total == 0) {
            sb.append("<div style='width:100%;height:12px;background:").append(PresetColour.ANDROGYNOUS.toWebHexString()).append(";float:left;margin:4vw 0 0 0;border-radius: 2px;'>");

        } else {
            sb.append("<div style='width:100%;height:12px;background:#222;float:left;margin:4vw 0 0 0;border-radius: 2px;'>");

            for (SexualOrientation o : SexualOrientation.values()) {
                sb.append("<div style='width:").append((Main.getProperties().orientationPreferencesMap.get(o) / total) * (100)).append("%; height:12px; background:").append(o.getColour().toWebHexString()).append("; float:left; border-radius: 2;'></div>");
            }
        }

        sb.append("</div>");

        return sb.toString();
    }

    public static final DialogueNode KEYBINDS = new DialogueNode("Options", "Options", true) {

        ArrayList<Properties> presets;

        @Override
        public String getHeaderContent() {
            return "<p>"
                    + "<table align='center'>"
                    + "<tr><th>Action</th><th>Primary binding</th><th>Secondary binding</th></tr>"
                    + getKeybindTableRow(KeyboardAction.MENU)
                    + "<tr style='height:8px;'></tr>"

                    + getKeybindTableRow(KeyboardAction.QUICKSAVE)
                    + getKeybindTableRow(KeyboardAction.QUICKLOAD)
                    + "<tr style='height:8px;'></tr>"

                    + getKeybindTableRow(KeyboardAction.MENU_SELECT)
                    + getKeybindTableRow(KeyboardAction.INVENTORY)
                    + getKeybindTableRow(KeyboardAction.JOURNAL)
                    + getKeybindTableRow(KeyboardAction.MAP)
                    + getKeybindTableRow(KeyboardAction.CHARACTERS)
                    + getKeybindTableRow(KeyboardAction.ZOOM)
                    + "<tr style='height:8px;'></tr>"

                    + getKeybindTableRow(KeyboardAction.MOVE_NORTH)
                    + getKeybindTableRow(KeyboardAction.MOVE_WEST)
                    + getKeybindTableRow(KeyboardAction.MOVE_SOUTH)
                    + getKeybindTableRow(KeyboardAction.MOVE_EAST)
                    + "<tr style='height:8px;'></tr>"

                    + getKeybindTableRow(KeyboardAction.RESPOND_1)
                    + getKeybindTableRow(KeyboardAction.RESPOND_2)
                    + getKeybindTableRow(KeyboardAction.RESPOND_3)
                    + getKeybindTableRow(KeyboardAction.RESPOND_4)
                    + getKeybindTableRow(KeyboardAction.RESPOND_5)
                    + getKeybindTableRow(KeyboardAction.RESPOND_6)
                    + getKeybindTableRow(KeyboardAction.RESPOND_7)
                    + getKeybindTableRow(KeyboardAction.RESPOND_8)
                    + getKeybindTableRow(KeyboardAction.RESPOND_9)
                    + getKeybindTableRow(KeyboardAction.RESPOND_10)
                    + getKeybindTableRow(KeyboardAction.RESPOND_11)
                    + getKeybindTableRow(KeyboardAction.RESPOND_12)
                    + getKeybindTableRow(KeyboardAction.RESPOND_13)
                    + getKeybindTableRow(KeyboardAction.RESPOND_14)
                    + getKeybindTableRow(KeyboardAction.RESPOND_0)
                    + "<tr style='height:8px;'></tr>"

                    + getKeybindTableRow(KeyboardAction.RESPOND_NEXT_PAGE)
                    + getKeybindTableRow(KeyboardAction.RESPOND_PREVIOUS_PAGE)

                    + getKeybindTableRow(KeyboardAction.RESPOND_NEXT_TAB)
                    + getKeybindTableRow(KeyboardAction.RESPOND_PREVIOUS_TAB)
                    + "<tr style='height:8px;'></tr>"

                    + getKeybindTableRow(KeyboardAction.MOVE_RESPONSE_CURSOR_NORTH)
                    + getKeybindTableRow(KeyboardAction.MOVE_RESPONSE_CURSOR_WEST)
                    + getKeybindTableRow(KeyboardAction.MOVE_RESPONSE_CURSOR_SOUTH)
                    + getKeybindTableRow(KeyboardAction.MOVE_RESPONSE_CURSOR_EAST)
                    + "</table>"
                    + "</p>";
        }

        @Override
        public String getContent() {
            return "";
        }

        private void loadPresets() {
            presets = new ArrayList<>();

            // Load all text files in the folder as properties
            Path presetFolder = FS.newPath("/res/keybinds");
            if (Files.exists(presetFolder) && Files.isDirectory(presetFolder)) {
                Path[] result1;
                BiPredicate<Path, String> filter = (dir, name) -> name.endsWith(".txt");
                try (Stream<Path> stream = Files.list(presetFolder)) {
                    result1 = stream.filter(dir1 -> filter.test((dir1), dir1.getFileName().toString())).toList().toArray(new Path[0]);
                } catch (IOException e1) {
                    result1 = null;
                }
                for (Path f : result1) {
                    try {
                        InputStream result;
                        try {
                            result = Files.newInputStream(f);
                        } catch (IOException e) {
                            throw new FileNotFoundException(f.toString());
                        }
                        try (InputStream input = result) {
                            Properties preset = new Properties();
                            preset.load(input);
                            presets.add(preset);
                        }
                    } catch (IOException e) {
                        System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                    }
                }
            } else {
                try {
                    Files.createDirectories(presetFolder);
                } catch (IOException e) {
                    System.out.println("mkdirs returned false");
                }
            }
        }

        private void loadPreset(Properties preset) {
            // Clear existing mappings
            Main.getProperties().hotkeyMapPrimary.clear();
            Main.getProperties().hotkeyMapSecondary.clear();

            // Create a key mapping for every action contained in the given property
            for (KeyboardAction ka : KeyboardAction.values()) {
                if (preset.containsKey(ka.name())) {
                    String[] keys = preset.getProperty(ka.name()).split(" or ");
                    Main.getProperties().hotkeyMapPrimary.put(ka, KeyCodeWithModifiers.fromString(keys[0]));
                    if (keys.length == 2)
                        Main.getProperties().hotkeyMapSecondary.put(ka, KeyCodeWithModifiers.fromString(keys[1]));
                }
            }
        }

        private void savePreset(int index) {
            // Create new properties containing current key mappings
            Properties preset = new Properties();
            preset.setProperty("NAME", "Custom " + index);
            preset.setProperty("DESCRIPTION", "Reapply your previously saved key bindings.");

            for (Map.Entry<KeyboardAction, KeyCodeWithModifiers> e : Main.getProperties().hotkeyMapPrimary.entrySet())
                if (e.getValue() != null)
                    preset.setProperty(e.getKey().name(), e.getValue().toString());

            for (Map.Entry<KeyboardAction, KeyCodeWithModifiers> e : Main.getProperties().hotkeyMapSecondary.entrySet()) {
                if (e.getValue() != null) {
                    // Write or append to existing entry
                    String primary = preset.getProperty(e.getKey().name());
                    primary = primary == null ? e.getValue().toString() : primary + " or " + e.getValue().toString();
                    preset.setProperty(e.getKey().name(), primary);
                }
            }

            // Write properties to file
            Path name = FS.newPath("/res/keybinds/custom" + index + ".txt");
            try {
                OutputStream result;
                try {
                    result = Files.newOutputStream(name);
                } catch (IOException e) {
                    throw new FileNotFoundException(name.toString());
                }
                try (OutputStream output = result) {
                    preset.store(output, "");
                }
            } catch (IOException e) {
                System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
            }
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            // Load the presets if uninitialized
            if (presets == null) loadPresets();

            if (index == 0) {
                return new Response("Back", "Go back to the options menu.", OPTIONS);

            } else if (index <= presets.size()) {
                Properties preset = presets.get(index - 1);
                return new Response(preset.getProperty("NAME", "Custom " + index), preset.getProperty("DESCRIPTION", "Reapply your previously saved key bindings."), KEYBINDS) {
                    @Override
                    public void effects() {
                        loadPreset(preset);
                        Main.saveProperties();
                    }
                };

            } else if (index == 14) {
                return new Response("Save preset",
                        "Store the current key bindings in a file. If you want to delete any saved presets, navigate to the 'res/keybinds' folder and delete the .txt files that you no longer want."
                                + " (They will stop showing up in this list after a game restart.)",
                        KEYBINDS) {
                    @Override
                    public void effects() {
                        savePreset(presets.size() - 2);
                        loadPresets();
                    }
                };
            }
            return null;
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getOrientationPreferencesPanel(SexualOrientation orient) {
        Colour colour = orient.getColour();

        StringBuilder sb = new StringBuilder();

        sb.append("<div style='display:inline-block; margin:4px auto;width:100%;'>" + "<div style='display:inline-block; margin:0 auto;'>" + "<div style='width:140px; float:left;'><b style='color:").append(colour.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orient.getName())).append("</b></div>");

        for (SexualOrientationPreference preference : SexualOrientationPreference.values()) {
            sb.append("<div id='").append(preference).append("_").append(orient).append("' class='preference-button").append(Main.getProperties().orientationPreferencesMap.get(orient) == preference.getValue() ? " selected" : "").append("'>").append(Util.capitaliseSentence(preference.getName())).append("</div>");
        }

        sb.append("</div>"
                + "</div>"
                + "<hr></hr>");

        return sb.toString();
    }

    public static String getInformationDiv(String id, TooltipInformationEventListener information) {
        Game.informationTooltips.put(id, information);
        return "<div class='title-button no-select' id='" + id + "' style='position:relative; float:left; background:transparent; padding:0; margin:0;'>"
                + SVGImages.SVG_IMAGE_PROVIDER.getInformationIcon()
                + "</div>";
    }

    public static final DialogueNode OPTIONS_PRONOUNS = new DialogueNode("Options", "Options", true) {

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();

            sb.append("<p>" + "<h5 style='text-align:center;'>Global gender names:</h5>" + "<table align='center'>" + "<tr>" + "<th>Body Parts</th>" + "<th style='color:").append(PresetColour.MASCULINE.toWebHexString()).append(";'>Masculine</th>").append("<th style='color:").append(PresetColour.ANDROGYNOUS.toWebHexString()).append(";'>Androgynous</th>").append("<th style='color:").append(PresetColour.FEMININE.toWebHexString()).append(";'>Feminine</th>").append("</tr>");

            for (GenderNames gn : GenderNames.values()) {
                sb.append(getGenderNameTableRow(gn));
            }

            sb.append("</table>" + "</p>" + "<p>" + "<h5 style='text-align:center;'>Player-specific pronouns:</h5>" + "<table align='center'>" + "<tr>" + "<th>Pronoun</th>" + "<th style='color:").append(PresetColour.MASCULINE.toWebHexString()).append(";'>Masculine</th>").append("<th style='color:").append(PresetColour.FEMININE.toWebHexString()).append(";'>Feminine</th>").append("</tr>").append(getPronounTableRow(GenderPronoun.NOUN)).append(getPronounTableRow(GenderPronoun.YOUNG_NOUN)).append(getPronounTableRow(GenderPronoun.SECOND_PERSON)).append(getPronounTableRow(GenderPronoun.THIRD_PERSON)).append(getPronounTableRow(GenderPronoun.POSSESSIVE_BEFORE_NOUN)).append(getPronounTableRow(GenderPronoun.POSSESSIVE_ALONE)).append("</table>").append("</p>").append("<h5 style='text-align:center;'><span style='color:").append(PresetColour.ANDROGYNOUS.toWebHexString()).append(";'>Androgynous bodies</span> (option 3)</h5>").append("<p>").append("<b style='color:").append(PresetColour.FEMININE.toWebHexString()).append(";'>Feminine:</b> Treated as <b style='color:").append(PresetColour.FEMININE.toWebHexString()).append(";'>feminine</b>.<br/>").append("<b style='color:").append(PresetColour.ANDROGYNOUS.toWebHexString()).append(";'>Clothing feminine:</b> Treated according to clothing femininity.").append(" If clothing is neutral, treated as <b style='color:").append(PresetColour.FEMININE.toWebHexString()).append(";'>feminine</b>.<br/>").append("<b style='color:").append(PresetColour.ANDROGYNOUS.toWebHexString()).append(";'>Clothing masculine:</b> Treated according to clothing femininity.").append(" If clothing is neutral, treated as <b style='color:").append(PresetColour.MASCULINE.toWebHexString()).append(";'>masculine</b>.<br/>").append("<b style='color:").append(PresetColour.MASCULINE.toWebHexString()).append(";'>Masculine:</b> Treated as <b style='color:").append(PresetColour.MASCULINE.toWebHexString()).append(";'>masculine</b>.<br/>").append("</p>");

            return sb.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new ResponseEffectsOnly("Save", "Save all the pronouns that are currently displayed.") {
                    @Override
                    public void effects() {
                        for (GenderNames gn : GenderNames.values()) {
                            Main.getProperties().genderNameMale.put(gn, ((String) Main.mainController.getWebEngine().executeScript("document.getElementById('GENDER_NAME_MASCULINE_" + gn + "').value")).toLowerCase());
                            Main.getProperties().genderNameNeutral.put(gn, ((String) Main.mainController.getWebEngine().executeScript("document.getElementById('GENDER_NAME_ANDROGYNOUS_" + gn + "').value")).toLowerCase());
                            Main.getProperties().genderNameFemale.put(gn, ((String) Main.mainController.getWebEngine().executeScript("document.getElementById('GENDER_NAME_FEMININE_" + gn + "').value")).toLowerCase());
                        }
                        for (GenderPronoun gp : GenderPronoun.values()) {
                            Main.getProperties().genderPronounFemale.put(gp, ((String) Main.mainController.getWebEngine().executeScript("document.getElementById('feminine_" + gp + "').value")).toLowerCase());
                            Main.getProperties().genderPronounMale.put(gp, ((String) Main.mainController.getWebEngine().executeScript("document.getElementById('masculine_" + gp + "').value")).toLowerCase());
                        }
                        Main.saveProperties();
                        Main.game.flashMessage(PresetColour.GENERIC_GOOD, "Pronouns saved!");
                    }
                };

            } else if (index == 2) {
                return new Response("Defaults", "Resets all pronouns to their default values.", OPTIONS_PRONOUNS) {
                    @Override
                    public void effects() {
                        for (GenderNames gn : GenderNames.values()) {
                            Main.getProperties().genderNameMale.put(gn, gn.getMasculine());
                            Main.getProperties().genderNameNeutral.put(gn, gn.getNeutral());
                            Main.getProperties().genderNameFemale.put(gn, gn.getFeminine());
                        }
                        for (GenderPronoun gp : GenderPronoun.values()) {
                            Main.getProperties().genderPronounFemale.put(gp, gp.getFeminine());
                            Main.getProperties().genderPronounMale.put(gp, gp.getMasculine());
                        }
                        Main.saveProperties();

                    }
                };

            } else if (index == 3) {
                return new Response("<span style='color:" + Main.getProperties().androgynousIdentification.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(Main.getProperties().androgynousIdentification.getName()) + "</span>",
                        "Cycle the way the game treats androgynous bodies as described above.", OPTIONS_PRONOUNS) {
                    @Override
                    public void effects() {
                        switch (Main.getProperties().androgynousIdentification) {
                            case FEMININE:
                                Main.getProperties().androgynousIdentification = AndrogynousIdentification.CLOTHING_FEMININE;
                                break;
                            case CLOTHING_FEMININE:
                                Main.getProperties().androgynousIdentification = AndrogynousIdentification.CLOTHING_MASCULINE;
                                break;
                            case CLOTHING_MASCULINE:
                                Main.getProperties().androgynousIdentification = AndrogynousIdentification.MASCULINE;
                                break;
                            case MASCULINE:
                                Main.getProperties().androgynousIdentification = AndrogynousIdentification.FEMININE;
                                break;
                            default:
                                break;
                        }

                        Main.saveProperties();
                    }
                };

            } else if (index == 0) {
                return new Response("Back", "Go back to the options menu.", OPTIONS);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getFetishPreferencesPanel(AbstractFetish fetish) {
        StringBuilder sb = new StringBuilder();

        Colour highlightColour = FetishPreference.valueOf(Main.getProperties().fetishPreferencesMap.get(fetish)).getColour();

        sb.append("<div style='display:inline-block; margin:4px auto;width:100%;'>" + "<div style='display:inline-block; margin:0 auto;'>").append(getInformationDiv(fetish.getId() + "_INFO", new TooltipInformationEventListener().setInformation(Util.capitaliseSentence(fetish.getName(Main.game.getPlayer())), fetish.getDescription(null)))).append("<div style='width:150px; float:left;'><b style='color:").append(highlightColour.toWebHexString()).append(";'>").append(Util.capitaliseSentence(fetish.getName(null))).append("</b></div>");

        for (FetishPreference preference : FetishPreference.values()) {
            String disabledMsg = null;
            if (!fetish.isContentEnabled()) {
                if (Fetish.FETISH_SIZE_QUEEN.equals(fetish)) {
                    disabledMsg = "Penetrative size-difference";
                } else if (Fetish.FETISH_NON_CON_DOM.equals(fetish) || Fetish.FETISH_NON_CON_SUB.equals(fetish)) {
                    disabledMsg = "Non-consent";
                } else if (Fetish.FETISH_INCEST.equals(fetish)) {
                    disabledMsg = "Incest";
                } else if (Fetish.FETISH_LACTATION_SELF.equals(fetish) || Fetish.FETISH_LACTATION_OTHERS.equals(fetish)) {
                    disabledMsg = "Lactation";
                } else if (Fetish.FETISH_ANAL_RECEIVING.equals(fetish) || Fetish.FETISH_ANAL_GIVING.equals(fetish)) {
                    disabledMsg = "Anal Content";
                } else if (Fetish.FETISH_FOOT_RECEIVING.equals(fetish) || Fetish.FETISH_FOOT_GIVING.equals(fetish)) {
                    disabledMsg = "Foot Content";
                } else if (Fetish.FETISH_ARMPIT_RECEIVING.equals(fetish) || Fetish.FETISH_ARMPIT_GIVING.equals(fetish)) {
                    disabledMsg = "Armpit Content";
                } else {
                    disabledMsg = "Unspecified Content";
                }
            }
            if (disabledMsg != null) {
                // Disabled fetishes to default, the fetish won't be a valid option for the generator anyway
                Main.getProperties().fetishPreferencesMap.put(fetish, fetish.getFetishPreferenceDefault().getValue());
                sb.append("<div style='display:inline-block;'><span class='option-disabled'>Fetish forcibly disabled due to ").append(disabledMsg).append(" setting!</span></div>");
                break;
            } else {
                sb.append("<div id='").append(preference).append("_").append(Fetish.getIdFromFetish(fetish)).append("' class='preference-button").append(Main.getProperties().fetishPreferencesMap.get(fetish) == preference.getValue() ? " selected" : "").append("'").append(" style='width:70px;'").append(">").append(Util.capitaliseSentence(preference.getName())).append("</div>");
            }
        }

        sb.append("</div>"
                + "</div>"
                + "<hr></hr>");

        return sb.toString();
    }

    private static String getGenderRepresentation() {

        float total = 0;
        for (Gender g : Gender.values()) {
            total += Main.getProperties().genderPreferencesMap.get(g);
        }

        StringBuilder sb = new StringBuilder();

        if (total == 0) {
            sb.append("<div style='width:100%;height:12px;background:").append(PresetColour.FEMININE.getShades()[3]).append(";float:left;margin:4vw 0 0 0;border-radius: 2px;'>");

        } else {
            sb.append("<div style='width:100%;height:12px;background:#222;float:left;margin:4vw 0 0 0;border-radius: 2px;'>");

            int f = 0, m = 0, n = 0;
            for (Gender g : Gender.values()) {
                switch (g.getType()) {
                    case MASCULINE:
                        if (Main.getProperties().genderPreferencesMap.get(g) > 0) {
                            sb.append("<div style='width:calc(").append((Main.getProperties().genderPreferencesMap.get(g) / total) * (100)).append("% - 1px); height:12px;").append(" background:").append(PresetColour.MASCULINE.getShades(8)[m]).append("; float:left; border-radius: 2;'></div>");
                            sb.append("<div style='width:1px; height:12px; background:#000; float:left; border-radius: 2;'></div>");
                        }
                        m++;
                        break;
                    case NEUTRAL:
                        if (Main.getProperties().genderPreferencesMap.get(g) > 0) {
                            sb.append("<div style='width:calc(").append((Main.getProperties().genderPreferencesMap.get(g) / total) * (100)).append("% - 1px); height:12px;").append(" background:").append(PresetColour.ANDROGYNOUS.getShades(8)[n]).append("; float:left; border-radius: 2;'></div>");
                            sb.append("<div style='width:1px; height:12px; background:#000; float:left; border-radius: 2;'></div>");
                        }
                        n++;
                        break;
                    case FEMININE:
                        if (Main.getProperties().genderPreferencesMap.get(g) > 0) {
                            sb.append("<div style='width:calc(").append((Main.getProperties().genderPreferencesMap.get(g) / total) * (100)).append("% - 1px); height:12px;").append(" background:").append(PresetColour.FEMININE.getShades(8)[f]).append("; float:left; border-radius: 2;'></div>");
                            sb.append("<div style='width:1px; height:12px; background:#000; float:left; border-radius: 2;'></div>");
                        }
                        f++;
                        break;
                    default:
                        break;
                }
            }
        }

        sb.append("</div>");

        return sb.toString();
    }

    private static String getAgePreferencesPanel(PronounType type) {

        StringBuilder sb = new StringBuilder();

        sb.append("<div class='container-full-width' style='text-align:center;'>" + "<p><b style='color:").append(type.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(type.getName())).append("</b></p>");

        int i = AgeCategory.values().length - 1;
        for (AgeCategory ageCat : AgeCategory.values()) {
            sb.append("<div style='display:inline-block; margin:4px auto;width:100%;'>" + "<div style='display:inline-block; margin:0 auto;'>" + "<div style='width:140px; float:left;'><b style='color:").append(type.getColour().getShades(AgeCategory.values().length)[i]).append(";'>").append(Util.capitaliseSentence(ageCat.getName())).append("</b></div>");

            for (ContentPreferenceValue preference : ContentPreferenceValue.values()) {
                sb.append("<div id='").append(type).append("_").append(preference).append("_").append(ageCat).append("' class='preference-button").append(Main.getProperties().agePreferencesMap.get(type).get(ageCat) == preference.getValue() ? " selected" : "").append("'>").append(Util.capitaliseSentence(preference.getName())).append("</div>");
            }

            sb.append("</div>"
                    + "</div>"
                    + "<hr/>");
            i--;
        }

        sb.append(getAgeRepresentation(type)).append("</div>");

        return sb.toString();
    }

    public static final DialogueNode PATCH_NOTES = new DialogueNode("Patch Notes", "Patch notes", true) {

        @Override
        public String getContent() {
            return Main.getPatchNotes();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                return new Response("Back", "Go back to the options menu.", MENU);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }

        @Override
        public boolean isContentParsed() {
            return false;
        }
    };

    private static String getAgeRepresentation(PronounType type) {

        float total = 0;
        for (AgeCategory ageCat : AgeCategory.values()) {
            total += Main.getProperties().agePreferencesMap.get(type).get(ageCat);
        }

        StringBuilder sb = new StringBuilder();

        if (total == 0) {
            sb.append("<div style='width:100%;height:12px;background:").append(type.getColour().getShades()[3]).append(";float:left;margin:4vw 0 0 0;border-radius: 2px;'>");

        } else {
            sb.append("<div style='width:100%;height:12px;background:#222;float:left;margin:4vw 0 0 0;border-radius: 2px;'>");

            int i = (AgeCategory.values().length * 2) - 1;
            for (AgeCategory ageCat : AgeCategory.values()) {
                if (Main.getProperties().agePreferencesMap.get(type).get(ageCat) > 0) {
                    sb.append("<div style='width:calc(").append((Main.getProperties().agePreferencesMap.get(type).get(ageCat) / total) * (100)).append("% - 1px); height:12px;").append(" background:").append(type.getColour().getShades(AgeCategory.values().length * 2)[i]).append("; float:left; border-radius: 2;'></div>");
                    sb.append("<div style='width:1px; height:12px; background:#000; float:left; border-radius: 2;'></div>");
                }
                i--;
                i--;
            }
        }

        sb.append("</div>");

        return sb.toString();
    }

    public static final DialogueNode DISCLAIMER = new DialogueNode("", "", true) {

        @Override
        public String getContent() {
            return Main.disclaimer;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                return new Response("Back", "Go back to the options menu.", MENU);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getSpawnRateDiv(String id, Colour colour, String valueDisplay, int value, int minimum, int maximum) {

        String contentSB = "<div class='container-full-width' style='padding:0; margin:2px 0;'>" +
                "<div id='" + id + "_INCREASE_LARGE' class='normal-button" + (value == maximum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == maximum ? "[style.boldDisabled(+)]" : "[style.boldGood(+)]") + "</div>" + "<div id='" + id + "_INCREASE' class='normal-button" + (value == maximum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == maximum ? "[style.boldDisabled(+)]" : "[style.boldMinorGood(+)]") + "</div>" + "<div class='container-full-width' style='text-align:center; width:40%; float:right; margin:0;'>" + "<b>" + valueDisplay + "</b>" + "</div>" + "<div id='" + id + "_DECREASE' class='normal-button" + (value == minimum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == minimum ? "[style.boldDisabled(-)]" : "[style.boldMinorBad(-)]") + "</div>" + "<div id='" + id + "_DECREASE_LARGE' class='normal-button" + (value == minimum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == minimum ? "[style.boldDisabled(-)]" : "[style.boldBad(-)]") + "</div>" +
                "</div>";

        return contentSB;
    }

    public static final DialogueNode GENDER_PREFERENCE = new DialogueNode("Gender preferences", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<div class='container-full-width'>"
                            + "These options will determine the gender encounter rates of random NPCs."
                            + " Some NPCs, such as random succubi attackers, have restrictions on their gender, but your preferences will be taken into account wherever possible.<br/>"
                            + "<b>A visual representation of the encounter chances can be seen in the bars at the bottom of each section.</b>"
                            + " (The different shades of each gender are solely for recognition in the bars, and don't mean anything other than that.)"
                            + "<br/>"
                            + "A character is considered to have breasts if they are at least an AA-cup."
                            + "</div>");

            UtilText.nodeContentSB.append(getGenderPreferencesPanel(PronounType.MASCULINE));
            UtilText.nodeContentSB.append(getGenderPreferencesPanel(PronounType.NEUTRAL));
            UtilText.nodeContentSB.append(getGenderPreferencesPanel(PronounType.FEMININE));


            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 11) {
                return new Response("Defaults", "Restore all gender preferences to their default values.", GENDER_PREFERENCE) {
                    @Override
                    public void effects() {
                        Main.getProperties().resetGenderPreferences();
                        Main.getProperties().savePropertiesAsXML();
                    }
                };
            }
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getEntryBackgroundColour(boolean alternative) {
        if (Main.getProperties().hasValue(PropertyValue.lightTheme)) {
            if (alternative) {
                return "#d9d9d9";
            }
            return "#dddddd";
        } else {
            if (alternative) {
                return "#222222";
            }
            return "#1f1f1f";
        }
    }

    private static String getSubspeciesPreferencesPanel(AbstractSubspecies s, boolean altColour) {
        StringBuilder sb = new StringBuilder();
        String baseStyle = "max-width:30px; width:14%; margin:0 1%; padding:0;";
        String subspeciesId = Subspecies.getIdFromSubspecies(s);

        sb.append("<div class='container-full-width' style='text-align:center; background:").append(getEntryBackgroundColour(altColour)).append("; padding:0; margin:0 0 6px 0; border-left:solid 4px ").append(s.getColour(null).toWebHexString()).append(";'>");

        // Feminine:
        sb.append("<div class='container-full-width' style='text-align:center; width:40%; background:transparent; margin:0; padding:0;'>" + "<b style='color:").append(PresetColour.FEMININE.toWebHexString()).append("; float:left; width:100%; text-align:center;'>").append(Util.capitaliseSentence(s.getSingularFemaleName(null))).append("</b>").append("</div>");

        sb.append("<div class='container-full-width' style='text-align:center; width:30%; background:transparent; margin:2px 0; padding:0;'>");

        for (FurryPreference preference : FurryPreference.values()) {
            sb.append("<div id='FEMININE_").append(preference).append("_").append(subspeciesId).append("' class='square-button small").append(!s.isFurryPreferencesEnabled() ? " disabled" : "").append(Main.getProperties().getSubspeciesFeminineFurryPreferencesMap().get(s) == preference && s.isFurryPreferencesEnabled()
                    ? " selected' style='" + baseStyle + " border-color:" + preference.getColour().toWebHexString() + ";'><div class='square-button-content'>" + preference.getSVGImage(false) + "</div></div>"
                    : "' style='" + baseStyle + "'><div class='square-button-content'>" + preference.getSVGImage(true) + "</div></div>");
        }
        sb.append("</div>");
        sb.append("<div class='container-full-width' style='text-align:center; width:30%; background:transparent; margin:2px 0; padding:0;'>");
        for (SubspeciesPreference preference : SubspeciesPreference.values()) {
            sb.append("<div id='FEMININE_SPAWN_").append(preference).append("_").append(subspeciesId).append("' class='square-button small").append(!s.isSpawnPreferencesEnabled() ? " disabled" : "").append(Main.getProperties().getSubspeciesFemininePreferencesMap().get(s) == preference && s.isSpawnPreferencesEnabled()
                    ? " selected' style='" + baseStyle + " border-color:" + PresetColour.FEMININE_PLUS.toWebHexString() + ";'><div class='square-button-content'>" + preference.getSVGImage(false) + "</div></div>"
                    : "' style='" + baseStyle + "'><div class='square-button-content'>" + preference.getSVGImage(true) + "</div></div>");
        }

        sb.append("</div>");

        // Masculine:
        sb.append("<div class='container-full-width' style='text-align:center; width:40%; background:transparent; margin:0; padding:0;'>" + "<b style='color:").append(PresetColour.MASCULINE.toWebHexString()).append("; float:left; width:100%; text-align:center;'>").append(Util.capitaliseSentence(s.getSingularMaleName(null))).append("</b>").append("</div>");

        sb.append("<div class='container-full-width' style='text-align:center; width:30%; background:transparent; margin:2px 0; padding:0;'>");

        for (FurryPreference preference : FurryPreference.values()) {
            sb.append("<div id='MASCULINE_").append(preference).append("_").append(subspeciesId).append("' class='square-button small").append(!s.isFurryPreferencesEnabled() ? " disabled" : "").append(Main.getProperties().getSubspeciesMasculineFurryPreferencesMap().get(s) == preference && s.isFurryPreferencesEnabled()
                    ? " selected' style='" + baseStyle + " border-color:" + preference.getColour().toWebHexString() + ";'><div class='square-button-content'>" + preference.getSVGImage(false) + "</div></div>"
                    : "' style='" + baseStyle + "'><div class='square-button-content'>" + preference.getSVGImage(true) + "</div></div>");
        }
        sb.append("</div>");
        sb.append("<div class='container-full-width' style='text-align:center; width:30%; background:transparent; margin:2px 0; padding:0;'>");
        for (SubspeciesPreference preference : SubspeciesPreference.values()) {
            sb.append("<div id='MASCULINE_SPAWN_").append(preference).append("_").append(subspeciesId).append("' class='square-button small").append(!s.isSpawnPreferencesEnabled() ? " disabled" : "").append(Main.getProperties().getSubspeciesMasculinePreferencesMap().get(s) == preference && s.isSpawnPreferencesEnabled()
                    ? " selected' style='" + baseStyle + " border-color:" + PresetColour.MASCULINE_PLUS.toWebHexString() + ";'><div class='square-button-content'>" + preference.getSVGImage(false) + "</div></div>"
                    : "' style='" + baseStyle + "'><div class='square-button-content'>" + preference.getSVGImage(true) + "</div></div>");
        }

        sb.append("</div>");

        sb.append("<div class='title-button no-select' id='SUBSPECIES_PREFERENCE_INFO_").append(subspeciesId).append("' style='position:absolute; margin:0; padding:0; left:1%; right:auto; top:auto; bottom:auto;'>").append(SVGImages.SVG_IMAGE_PROVIDER.getInformationIcon()).append("</div>");
        sb.append("</div>");

        return sb.toString();
    }

    /**
     * To be followed by two closing div elements.
     */
    private static String getCustomContentPreferenceDivStart(Colour colour, String title, String description) {

        return "<div class='container-full-width' style='padding:0; margin:2px 0;'>" + "<div class='container-half-width' style='width:calc(55% - 16px);'>" + "<b style='text-align:center; color:" + colour.toWebHexString() + ";'>" + title + "</b><b>:</b> " + description + "</div>" + "<div class='container-half-width' style='width:calc(45% - 16px);'>";
    }

    public static final DialogueNode ORIENTATION_PREFERENCE = new DialogueNode("Orientation preferences", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<div class='container-full-width'>"
                            + "These options will determine the sexual orientation encounter rates of random NPCs."
                            + " Note that the race and femininity of NPCs can have an influence on their orientation, and that some NPCs have pre-determined orientations, but your preferences will be taken into account wherever possible.</br>"
                            + "<b>A visual representation of the encounter chances can be seen in the bars at the bottom.</b>"
                            + " (The different shades of each orientation are solely for recognition in the bars, and don't mean anything other than that.)"
                            + "</div>"

                            + "<div class='container-full-width' style='text-align:center;'>");

            UtilText.nodeContentSB.append(getOrientationPreferencesPanel(SexualOrientation.ANDROPHILIC));
            UtilText.nodeContentSB.append(getOrientationPreferencesPanel(SexualOrientation.AMBIPHILIC));
            UtilText.nodeContentSB.append(getOrientationPreferencesPanel(SexualOrientation.GYNEPHILIC));

            UtilText.nodeContentSB.append(getOrientationRepresentation()).append("</div>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 11) {
                return new Response("Defaults", "Restore all orientation preferences to their default values.", ORIENTATION_PREFERENCE) {
                    @Override
                    public void effects() {
                        Main.getProperties().resetOrientationPreferences();
                        Main.getProperties().savePropertiesAsXML();
                    }
                };
            }
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getContentPreferenceDiv(String id, Colour colour, String title, String description, boolean enabled) {
        StringBuilder contentSB = new StringBuilder();

        contentSB.append("<div class='container-full-width' style='padding:0; margin:2px 0;'>" + "<div class='container-half-width' style='width:calc(55% - 16px);'>" + "<b style='text-align:center; color:").append(colour.toWebHexString()).append(";'>").append(title).append("</b><b>:</b> ").append(description).append("</div>").append("<div class='container-half-width' style='width:calc(45% - 16px);'>");

        if (enabled) {
            contentSB.append("<div class='normal-button selected' style='width:25%; margin-right:4%; text-align:center; float:right;'>" + "[style.boldGood(ON)]" + "</div>" + "<div id='").append(id).append("_OFF' class='normal-button' style='width:25%; margin-right:4%; text-align:center; float:right;'>").append("[style.colourDisabled(OFF)]").append("</div>");
        } else {
            contentSB.append("<div id='").append(id).append("_ON' class='normal-button' style='width:25%; margin-right:4%; text-align:center; float:right;'>").append("[style.colourDisabled(ON)]").append("</div>").append("<div class='normal-button selected' style='width:25%; margin-right:4%; text-align:center; float:right;'>").append("[style.boldBad(OFF)]").append("</div>");
        }

        contentSB.append("</div>"
                + "</div>");

        return contentSB.toString();
    }

    public static final DialogueNode FETISH_PREFERENCE = new DialogueNode("Fetish preferences", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<div class='container-full-width'>"
                            + "These options will determine the likelihood of random NPCs having these fetishes & preferences."
                            + " Some races are more likely to get specific fetishes, but your preferences will be taken into account wherever possible.<br/>"
                            + " Content settings will enable/disable related fetishes."
                            + "</div>"

                            + "<div class='container-full-width' style='text-align:center;'>");
            for (AbstractFetish fetish : Fetish.getAllFetishes()) {
                if (fetish.getFetishesForAutomaticUnlock().isEmpty()) {
                    UtilText.nodeContentSB.append(getFetishPreferencesPanel(fetish));
                }
            }

            UtilText.nodeContentSB.append("</div>");
            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 11) {
                return new Response("Defaults", "Reset all fetish preferences to their default settings.", FETISH_PREFERENCE) {
                    @Override
                    public void effects() {
                        Main.getProperties().resetFetishPreferences();
                        Main.getProperties().savePropertiesAsXML();
                    }
                };
            }
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    private static String getBreastsContentPreferenceVariableDiv(
            String id,
            Colour colour,
            String title,
            String description,
            String valueDisplay,
            int value, int minimum, int maximum,
            String valueDisplayUdders,
            int valueUdders, int minimumUdders, int maximumUdders) {

        String contentSB = "<div class='container-full-width' style='padding:0; margin:2px 0;'>" + "<div class='container-half-width' style='width:calc(55% - 16px);'>" + "<b style='text-align:center; color:" + colour.toWebHexString() + ";'>" + title + "</b><b>:</b> " + description + "</div>" + "<div class='container-half-width' style='width:calc(45% - 16px);'>" +
                "<div class='container-full-width' style='width:100%; margin:0; padding:0; text-align:right;'>" + "Breasts: " + "<div id='" + id + "_ON' class='normal-button" + (value == maximum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == maximum ? "[style.boldDisabled(+)]" : "[style.boldGood(+)]") + "</div>" + "<div class='container-full-width' style='text-align:center; width:calc(30%); float:right; margin:0;'>" + "<b>" + valueDisplay + "</b>" + "</div>" + "<div id='" + id + "_OFF' class='normal-button" + (value == minimum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == minimum ? "[style.boldDisabled(-)]" : "[style.boldBad(-)]") + "</div>" + "</div>" +
                "<div class='container-full-width' style='width:100%; margin:0; padding:0; text-align:right;'>" + "Udders: " + "<div id='" + id + "_UDDERS_ON' class='normal-button" + (valueUdders == maximumUdders ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (valueUdders == maximumUdders ? "[style.boldDisabled(+)]" : "[style.boldGood(+)]") + "</div>" + "<div class='container-full-width' style='text-align:center; width:calc(30%); float:right; margin:0;'>" + "<b>" + valueDisplayUdders + "</b>" + "</div>" + "<div id='" + id + "_UDDERS_OFF' class='normal-button" + (valueUdders == minimumUdders ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (valueUdders == minimumUdders ? "[style.boldDisabled(-)]" : "[style.boldBad(-)]") + "</div>" + "</div>" +
                "</div>"
                + "</div>";

        return contentSB;
    }

    private static String getSkinColourContentPreferenceVariableDiv(
            String id,
            Colour colour,
            String title,
            String description) {

        StringBuilder contentSB = new StringBuilder();
        int minimum = 0;
        int maximum = 10;

        contentSB.append("<div class='container-full-width' style='padding:0; margin:2px 0;'>");
        contentSB.append("<div class='container-half-width' style='width:calc(55% - 16px);'>" + "<b style='text-align:center; color:").append(colour.toWebHexString()).append(";'>").append(title).append("</b><b>:</b> ").append(description).append("</div>");

        contentSB.append("<div class='container-half-width' style='width:calc(45% - 16px);'>");

        for (Entry<Colour, Integer> entry : Main.getProperties().skinColourPreferencesMap.entrySet()) {
            Colour skinColour = entry.getKey();
            int value = entry.getValue();
            contentSB.append("<div class='container-full-width' style='width:100%; margin:0; padding:0; text-align:right;'>" + "<span style='color:").append(skinColour.toWebHexString()).append(";'>").append(Util.capitaliseSentence(skinColour.getName())).append(":</span> ").append("<div id='").append(id).append("_").append((skinColour).getId()).append("_ON' class='normal-button").append(value == maximum ? " disabled" : "").append("' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>").append(value == maximum ? "[style.boldDisabled(+)]" : "[style.boldGood(+)]").append("</div>").append("<div class='container-full-width' style='text-align:center; width:calc(30%); float:right; margin:0;'>").append("[style.colourSize").append(value).append("(").append(value).append(")]").append("</div>").append("<div id='").append(id).append("_").append((skinColour).getId()).append("_OFF' class='normal-button").append(value == minimum ? " disabled" : "").append("' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>").append(value == minimum ? "[style.boldDisabled(-)]" : "[style.boldBad(-)]").append("</div>").append("</div>");
        }

        contentSB.append("</div>");
        contentSB.append("</div>");

        return contentSB.toString();
    }

    private static String getContentPreferenceVariableDiv(String id, Colour colour, String title, String description, String valueDisplay, int value, int minimum, int maximum) {

        String contentSB = "<div class='container-full-width' style='padding:0; margin:2px 0;'>" + "<div class='container-half-width' style='width:calc(55% - 16px);'>" + "<b style='text-align:center; color:" + colour.toWebHexString() + ";'>" + title + "</b><b>:</b> " + description + "</div>" + "<div class='container-half-width' style='width:calc(45% - 16px);'>" +
                "<div id='" + id + "_ON' class='normal-button" + (value == maximum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == maximum ? "[style.boldDisabled(+)]" : "[style.boldGood(+)]") + "</div>" + "<div class='container-full-width' style='text-align:center; width:calc(30%); float:right; margin:0;'>" + "<b>" + valueDisplay + "</b>" + "</div>" + "<div id='" + id + "_OFF' class='normal-button" + (value == minimum ? " disabled" : "") + "' style='width:10%; margin:0 2.5%; text-align:center; float:right;'>" + (value == minimum ? "[style.boldDisabled(-)]" : "[style.boldBad(-)]") + "</div>" +
                "</div>"
                + "</div>";

        return contentSB;
    }

    private static Response getContentOptionsResponse(int responseTab, int index) {
        if (index == 1) {
            if (Main.game.getCurrentDialogueNode().equals(MISCELLANEOUS)) {
                return new Response("Misc.", "You are already viewing the miscellaneous content options!", null);
            }
            return new Response("Misc.", "View the miscellaneous content options.", MISCELLANEOUS);
        } else if (index == 2) {
            if (Main.game.getCurrentDialogueNode().equals(GAMEPLAY)) {
                return new Response("Gameplay", "You are already viewing the gameplay content options!", null);
            }
            return new Response("Gameplay", "View the game's gameplay content options.", GAMEPLAY);
        } else if (index == 3) {
            if (Main.game.getCurrentDialogueNode().equals(SEX)) {
                return new Response("Sex & Fetishes", "You are already viewing the sex & fetishes content options!", null);
            }
            return new Response("Sex & Fetishes", "View the game's sex & fetishes content options.", SEX);
        } else if (index == 4) {
            if (Main.game.getCurrentDialogueNode().equals(BODIES)) {
                return new Response("Bodies", "You are already viewing the bodies content options!", null);
            }
            return new Response("Bodies", "View the game's bodies content options.", BODIES);
        } else if (index == 5) {
            return new Response("[style.colourMinorBad(Reset)]",
                    "Resets <b>all 'Misc.', 'Gameplay', 'Sex & Fetishes', and 'Bodies'</b> content preferences to their default values!"
                            + "<br/>Does <b>not</b> reset Gender, Orientation, Age, Furry, or Fetish preferences.",
                    MISCELLANEOUS) {
                @Override
                public void effects() {
                    for (PropertyValue pv : PropertyValue.values()) {
                        Main.getProperties().setValue(pv, pv.getDefaultValue());
                    }
                    Main.getProperties().resetContentOptions();
                    Main.saveProperties();
                }
            };
        } else if (index == 6) {
            if (Main.game.getCurrentDialogueNode().equals(GENDER_PREFERENCE)) {
                return new Response("Gender preferences", "You are already viewing the gender preferences screen!", null);
            }
            return new Response("Gender preferences", "Set your preferred gender encounter rates.", GENDER_PREFERENCE);
        } else if (index == 7) {
            if (Main.game.getCurrentDialogueNode().equals(ORIENTATION_PREFERENCE)) {
                return new Response("Orientation preferences", "You are already viewing the sexual orientation preferences screen!", null);
            }
            return new Response("Orientation preferences", "Set your preferred sexual orientation encounter rates.", ORIENTATION_PREFERENCE);
        } else if (index == 8) {
            if (Main.game.getCurrentDialogueNode().equals(AGE_PREFERENCE)) {
                return new Response("Age preferences", "You are already viewing the age preferences screen!", null);
            }
            return new Response("Age preferences", "Set your preferred age encounter rates.", AGE_PREFERENCE);
        } else if (index == 9) {
            if (Main.game.getCurrentDialogueNode().equals(FURRY_PREFERENCE)) {
                return new Response("Furry preferences", "You are already viewing the furry preferences screen!", null);
            }
            return new Response("Furry preferences", "Set your preferred furry level for encounters.", FURRY_PREFERENCE);
        } else if (index == 10) {
            if (Main.game.getCurrentDialogueNode().equals(FETISH_PREFERENCE)) {
                return new Response("Fetish preferences", "You are already viewing the fetish preferences screen!", null);
            }
            return new Response("Fetish preferences", "Set your preferred fetish encounter rates.", FETISH_PREFERENCE);
        } else if (index == 0) {
            return new Response("Back", "Go back to the main menu.", MENU);
        }
        return null;
    }

    public static final DialogueNode AGE_PREFERENCE = new DialogueNode("Age preferences", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<div class='container-full-width'>"
                            + "These options will determine the age encounter rates of random NPCs, based on their femininity."
                            + " Some NPCs, such as demons and harpies, may appear to be younger than they actually are, but your preferences will be taken into account wherever possible.<br/>"
                            + "<b>A visual representation of the age chances can be seen in the bars at the bottom of each section.</b>"
                            + "</div>");

            UtilText.nodeContentSB.append(getAgePreferencesPanel(PronounType.MASCULINE));
            UtilText.nodeContentSB.append(getAgePreferencesPanel(PronounType.NEUTRAL));
            UtilText.nodeContentSB.append(getAgePreferencesPanel(PronounType.FEMININE));


            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 11) {
                return new Response("Defaults", "Restore all age preferences to their default values.", AGE_PREFERENCE) {
                    @Override
                    public void effects() {
                        Main.getProperties().resetAgePreferences();
                        Main.getProperties().savePropertiesAsXML();
                    }
                };
            }
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };


    public static final DialogueNode FURRY_PREFERENCE = new DialogueNode("Furry preferences", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<div class='container-full-width'>"
                            + "These options determine the amount of furry content that you'll encounter in the game."
                            + " The 'Human encounters' option determines what the chance is for random NPCs to be fully human."
                            + " <b>These options only affect random NPCs at the moment, but I'll do my best to add reduced-furry versions of each major NPC as well!</b>"

                            + "<br/>[style.italicsGood(Hover over the buttons to see what each option means!)]"

                            + "<br/>Please note that some races, such as demons and harpies, are limited in their available furry preference options."
                            + "</div>"

                            + "<span style='height:16px;width:800px;float:left;'></span>");

            UtilText.nodeContentSB.append("<div class='container-full-width'>");

            UtilText.nodeContentSB.append("<div class='container-half-width inner' style='width:31.3%; margin:1%; padding:1%;'>");
            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RACE_HUMAN.toWebHexString()).append("; float:left; width:100%; text-align:center;'>Human Spawn Rate</b>");
            UtilText.nodeContentSB.append("<div style='display:inline-block; padding-left:5%; width:100%;'>");
            UtilText.nodeContentSB.append(getSpawnRateDiv(
                    "HUMAN_SPAWN_RATE",
                    PresetColour.RACE_HUMAN,
                    Main.getProperties().humanSpawnRate + "%",
                    Main.getProperties().humanSpawnRate,
                    0,
                    100));
            UtilText.nodeContentSB.append("</div>");
            UtilText.nodeContentSB.append("</div>");

            UtilText.nodeContentSB.append("<div class='container-half-width inner' style='width:31.3%; margin:1%; padding:1%;'>");
            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RACE_CENTAUR.toWebHexString()).append("; float:left; width:100%; text-align:center;'>Taur Spawn Rate</b>");
            UtilText.nodeContentSB.append("<div style='display:inline-block; padding-left:5%; width:100%;'>");
            UtilText.nodeContentSB.append(getSpawnRateDiv(
                    "TAUR_SPAWN_RATE",
                    PresetColour.RACE_CENTAUR,
                    Main.getProperties().taurSpawnRate + "%",
                    Main.getProperties().taurSpawnRate,
                    0,
                    100));
            UtilText.nodeContentSB.append("</div>");
            UtilText.nodeContentSB.append("</div>");

            UtilText.nodeContentSB.append("<div class='container-half-width inner' style='width:31.3%; margin:1%; padding:1%;'>");
            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RACE_HALF_DEMON.toWebHexString()).append("; float:left; width:100%; text-align:center;'>Half-Demon Spawn Rate</b>");
            UtilText.nodeContentSB.append("<div style='display:inline-block; padding-left:5%; width:100%;'>");
            UtilText.nodeContentSB.append(getSpawnRateDiv(
                    "HALF_DEMON_SPAWN_RATE",
                    PresetColour.RACE_HALF_DEMON,
                    Main.getProperties().halfDemonSpawnRate + "%",
                    Main.getProperties().halfDemonSpawnRate,
                    0,
                    100));
            UtilText.nodeContentSB.append("</div>");
            UtilText.nodeContentSB.append("</div>");
            UtilText.nodeContentSB.append("</div>");

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.RACE_CENTAUR, "Tauric Upper-body Furriness", "Set how furry you prefer the upper bodies of taurs to be."));
            UtilText.nodeContentSB.append(Main.getProperties().taurFurryLevel == 2
                    ? "<div id='TAUR_FURRY_LIMIT_" + 2 + "' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:" + FurryPreference.MINIMUM.getColour().toWebHexString() + ";'>"
                    + FurryPreference.MINIMUM.getName()
                    + "</div>"
                    : "<div id='TAUR_FURRY_LIMIT_" + 2 + "' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>"
                    + "[style.colourDisabled(" + FurryPreference.MINIMUM.getName() + ")]"
                    + "</div>").append(Main.getProperties().taurFurryLevel == 1
                    ? "<div id='TAUR_FURRY_LIMIT_" + 1 + "' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:" + FurryPreference.HUMAN.getColour().toWebHexString() + ";'>"
                    + "Human"
                    + "</div>"
                    : "<div id='TAUR_FURRY_LIMIT_" + 1 + "' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>"
                    + "[style.colourDisabled(Human)]"
                    + "</div>").append(Main.getProperties().taurFurryLevel == 0
                    ? "<div id='TAUR_FURRY_LIMIT_" + 0 + "' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:" + PresetColour.TRANSFORMATION_GENERIC.toWebHexString() + ";'>"
                    + "Untouched"
                    + "</div>"
                    : "<div id='TAUR_FURRY_LIMIT_" + 0 + "' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>"
                    + "[style.colourDisabled(Untouched)]"
                    + "</div>").append(Main.getProperties().taurFurryLevel == 5
                    ? "<div id='TAUR_FURRY_LIMIT_" + 5 + "' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:" + FurryPreference.MAXIMUM.getColour().toWebHexString() + ";'>"
                    + FurryPreference.MAXIMUM.getName()
                    + "</div>"
                    : "<div id='TAUR_FURRY_LIMIT_" + 5 + "' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>"
                    + "[style.colourDisabled(" + FurryPreference.MAXIMUM.getName() + ")]"
                    + "</div>").append(Main.getProperties().taurFurryLevel == 4
                    ? "<div id='TAUR_FURRY_LIMIT_" + 4 + "' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:" + FurryPreference.NORMAL.getColour().toWebHexString() + ";'>"
                    + FurryPreference.NORMAL.getName()
                    + "</div>"
                    : "<div id='TAUR_FURRY_LIMIT_" + 4 + "' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>"
                    + "[style.colourDisabled(" + FurryPreference.NORMAL.getName() + ")]"
                    + "</div>").append(Main.getProperties().taurFurryLevel == 3
                    ? "<div id='TAUR_FURRY_LIMIT_" + 3 + "' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:" + FurryPreference.REDUCED.getColour().toWebHexString() + ";'>"
                    + FurryPreference.REDUCED.getName()
                    + "</div>"
                    : "<div id='TAUR_FURRY_LIMIT_" + 3 + "' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>"
                    + "[style.colourDisabled(" + FurryPreference.REDUCED.getName() + ")]"
                    + "</div>");
            UtilText.nodeContentSB.append("</div></div>");


            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align: center;'>"
                    + "<div style='display:inline-block; margin:4px auto;'>"
                    + "<div style='float:left; text-align:right; margin-right:16px;'>"
                    + "<b>Set all furry preferences:</b>"
                    + "</div>");
            for (FurryPreference fp : FurryPreference.values()) {
                UtilText.nodeContentSB.append("<div id='ALL_FURRY_").append(fp).append("' class='normal-button' style='width:80px; margin:0 2px;'>").append(fp.getName()).append("</div>");
            }
            UtilText.nodeContentSB.append("</div>"
                    + "<div style='display:inline-block; margin:4px auto;'>"
                    + "<div style='float:left; text-align:right; margin-right:16px;'>"
                    + "<b>Set all spawn frequencies:</b>"
                    + "</div>");
            for (SubspeciesPreference sp : SubspeciesPreference.values()) {
                UtilText.nodeContentSB.append("<div id='ALL_SPAWN_").append(sp).append("' class='normal-button' style='width:80px; margin:0 2px;'>").append(Util.capitaliseSentence(sp.getName())).append("</div>");
            }
            UtilText.nodeContentSB.append("</div>"
                    + "</div>");

            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align: center;'>" + "<div class='container-full-width' style='text-align:center; background:").append(getEntryBackgroundColour(false)).append(";'>").append("<div class='container-full-width' style='text-align:center; width:calc(60% - 16px);background:transparent; margin:0 0 0 40%; padding:0;'>").append("<b style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append("; float:left; width:50%; text-align:center;'>Furry Preference</b>").append("<b style='color:").append(PresetColour.BASE_YELLOW_LIGHT.toWebHexString()).append("; float:left; width:50%; text-align:center;'>Spawn frequency</b>").append("</div>").append("</div>");

            int i = 0;
            for (AbstractSubspecies subspecies : Subspecies.getAllSubspecies()) {
                if (subspecies.isDisplayedInFurryPreferences()) {
                    UtilText.nodeContentSB.append(getSubspeciesPreferencesPanel(subspecies, i % 2 == 0));
                    i++;
                }
            }
            UtilText.nodeContentSB.append("</div>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 11) {
                return new Response("Defaults", "Reset all furry and spawn preferences to their default settings.", FURRY_PREFERENCE) {
                    @Override
                    public void effects() {
                        for (AbstractSubspecies subspecies : Subspecies.getAllSubspecies()) {
                            Main.getProperties().setFeminineFurryPreference(subspecies, subspecies.getDefaultFemininePreference());
                            Main.getProperties().setMasculineFurryPreference(subspecies, subspecies.getDefaultMasculinePreference());

                            Main.getProperties().setFeminineSubspeciesPreference(subspecies, subspecies.getSubspeciesPreferenceDefault());
                            Main.getProperties().setMasculineSubspeciesPreference(subspecies, subspecies.getSubspeciesPreferenceDefault());
                        }
                        Main.saveProperties();
                    }
                };
            }
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };


    public static final DialogueNode UNIT_PREFERENCE = new DialogueNode("Unit preferences", "", true) {

        @Override
        public String getHeaderContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<div class='container-full-width'>"
                            + "These options determine the units used throughout the game."
                            + " Using auto detect will attempt to identify the correct settings for your system language."
                            + " <br/><b>Overriding any option disables auto detect.</b>"
                            + "</div>"

                            + "<span style='height:16px;width:800px;float:left;'></span>");

            UtilText.nodeContentSB.append(getContentPreferenceDiv("AUTO_LOCALE",
                    PresetColour.BASE_BLUE_LIGHT,
                    "Automatic",
                    "When enabled, the system locale is used. Otherwise, the following options are applied.",
                    Main.getProperties().hasValue(PropertyValue.autoLocale)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("METRIC_SIZES",
                    PresetColour.BASE_BLUE_STEEL,
                    "Metric sizes",
                    "The game will use metres and centimetres instead of feet and inches.",
                    Main.getProperties().hasValue(PropertyValue.metricSizes)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("METRIC_FLUIDS",
                    PresetColour.BASE_BLUE_STEEL,
                    "Metric fluids",
                    "The game will use litres and millilitres instead of gallons and ounces.",
                    Main.getProperties().hasValue(PropertyValue.metricFluids)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("METRIC_WEIGHTS",
                    PresetColour.BASE_BLUE_STEEL,
                    "Metric weights",
                    "The game will use kilograms and grams instead of pounds and ounces.",
                    Main.getProperties().hasValue(PropertyValue.metricWeights)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("TWENTYFOUR_HOUR_TIME",
                    PresetColour.BASE_LILAC_LIGHT,
                    "24 hour time",
                    "The time will be displayed as 24 hours instead of AM/PM.",
                    Main.getProperties().hasValue(PropertyValue.twentyFourHourTime)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("INTERNATIONAL_DATE",
                    PresetColour.BASE_LILAC_LIGHT,
                    "International date",
                    "The abbreviated date will be displayed as day.month.year instead of month/day/year.",
                    Main.getProperties().hasValue(PropertyValue.internationalDate)));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                return new Response("Back", "Go back to the options menu.", OPTIONS);

            } else {
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };


    public static final DialogueNode CREDITS = new DialogueNode("Credits", "", true) {

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(
                    "<p>"
                            + "Thank you for playing " + Main.GAME_NAME + ", I hope you enjoy it just as much as I do making it!"
                            + " Thank you so much to all of you who offer financial support! Thanks to you, I'm able to spend more time working on " + Main.GAME_NAME + ", and I promise that I'll make this game the very best that I can!"
                            + "</p>"
                            + "<p style='text-align:center;'>"
                            + Main.GAME_NAME + " has been created by:<br/>"
                            + "<b style='color:#9b78fa;'>Innoxia</b>"
                            + "<br/><br/>"
                            + "Artists whose character art can be found in the game:<br/>");

            for (Artist artist : Artwork.allArtists) {
                if (!artist.getName().equals("Custom")) {
                    UtilText.nodeContentSB.append("<b style='color:").append(artist.getColour().toWebHexString()).append(";'>").append(artist.getName()).append("</b><br/>");
                }
            }

            UtilText.nodeContentSB.append("<br/>"
                    + "Contributors:</br>" // In alphabetical order:
                    + "<b style='color:#21bfc5;'>AceXP</b></br>"
                    + "<b style='color:#21bfc5;'>DJ Addi</b></br>"
                    + "<b style='color:#21bfc5;'>DSG</b></br>"
                    + "<b style='color:#21bfc5;'>Irbynx</b></br>"
                    + "<b style='color:#21bfc5;'>Maxis010</b></br>"
                    + "<b style='color:#21bfc5;'>Nnxx</b></br>"
                    + "<b style='color:#21bfc5;'>Norin</b></br>"
                    + "<b style='color:#21bfc5;'>NoStepOnSnek</b></br>"
                    + "<b style='color:#21bfc5;'>Phlarx</b></br>"
                    + "<b style='color:#21bfc5;'>Pimgd</b></br>"
                    + "<b style='color:#21bfc5;'>PoyntFury</b></br>"
                    + "<b style='color:#21bfc5;'>Rfpnj</b></br>"
                    + "<b style='color:#21bfc5;'>Tukaima</b></br>");

            UtilText.nodeContentSB.append("<br/>" + "Special thanks to:<br/>" + "<b>Sensei</b>,<br/>" + "<b style='color:#fa0063;'>loveless</b>, <b style='color:#c790b2;'>Blue999</b>, and <b style='color:#ec9538;'>DesuDemona</b><br/>" + "<b style='color:#21bec4;'>Github & wiki contributors</b><br/>" + "<b style='color:#e06e5f;'>Everyone who's financially supported me</b>,<br/>" + "<b>Bug reporters</b>,<br/>" + "and<br/>" + "<b>Everyone for playing Lilith's Throne!</b>" + "</p>" + "<br/>" + "<h5 style='text-align:center; color:").append(PresetColour.RARITY_LEGENDARY.toWebHexString()).append(";'>Legendary Patrons</h5>").append("<p style='text-align:center;'>");

            for (CreditsSlot cs : Main.credits) {
                if (cs.getLegendaryCount() > 0) {
                    UtilText.nodeContentSB.append("<br/>");
                    UtilText.nodeContentSB.append("<div style='width:50%; display:inline-block; text-align:right;'>");
                    if (cs.getName().equals("Anonymous")) {
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_UNCOMMON.toWebHexString()).append(";'>?</b> ");
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_RARE.toWebHexString()).append(";'>?</b> ");
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>?</b> ");
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_LEGENDARY.toWebHexString()).append(";'>?</b> ");
                    } else {
                        for (int i = 0; i < cs.getUncommonCount() % 5; i++) {
                            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_UNCOMMON.toWebHexString()).append(";'>&#9679</b> ");
                        }

                        for (int i = 0; i < cs.getRareCount() % 5; i++) {
                            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_RARE.toWebHexString()).append(";'>&#9679</b> ");
                        }

                        for (int i = 0; i < cs.getEpicCount() / 5; i++) {// 5-marks:
                            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>&#127775</b> ");
                        }
                        for (int i = 0; i < cs.getEpicCount() % 5; i++) {
                            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>&#9679</b> ");
                        }

                        for (int i = 0; i < cs.getLegendaryCount() / 5; i++) {// 5-marks:
                            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_LEGENDARY.toWebHexString()).append(";'>&#127775</b> ");
                        }
                        for (int i = 0; i < cs.getLegendaryCount() % 5; i++) {
                            UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_LEGENDARY.toWebHexString()).append(";'>&#9679</b> ");
                        }
                    }
                    UtilText.nodeContentSB.append("</div>");
                    UtilText.nodeContentSB.append("<div style='width:50%; display:inline-block; text-align:left;'>");
                    UtilText.nodeContentSB.append("&nbsp;").append(cs.getSubspeciesTier() != null ? "<b style='color:" + cs.getSubspeciesTier().getColour(null).toWebHexString() + ";'>" + cs.getName() + "</b>" : cs.getName());
                    UtilText.nodeContentSB.append("</div>");
                }
            }

            UtilText.nodeContentSB.append("</p>" + "<br/>" + "<h5 style='text-align:center; color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>Epic Patrons</h5>").append("<p style='text-align:center;'>");

            for (CreditsSlot cs : Main.credits) {
                if (cs.getLegendaryCount() == 0 && cs.getEpicCount() > 0) {
                    UtilText.nodeContentSB.append("<br/>");
                    UtilText.nodeContentSB.append("<div style='width:50%; display:inline-block; text-align:right;'>");
                    for (int i = 0; i < cs.getUncommonCount() % 5; i++) {
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_UNCOMMON.toWebHexString()).append(";'>&#9679</b> ");
                    }

                    for (int i = 0; i < cs.getRareCount() % 5; i++) {
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_RARE.toWebHexString()).append(";'>&#9679</b> ");
                    }

                    for (int i = 0; i < cs.getEpicCount() / 5; i++) {// 5-marks:
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>&#127775</b> ");
                    }
                    for (int i = 0; i < cs.getEpicCount() % 5; i++) {
                        UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>&#9679</b> ");
                    }
                    UtilText.nodeContentSB.append("</div>");
                    UtilText.nodeContentSB.append("<div style='width:50%; display:inline-block; text-align:left;'>");
                    UtilText.nodeContentSB.append("&nbsp;").append(cs.getSubspeciesTier() != null ? "<b style='color:" + cs.getSubspeciesTier().getColour(null).toWebHexString() + ";'>" + cs.getName() + "</b>" : cs.getName());
                    UtilText.nodeContentSB.append("</div>");
                }
            }

            UtilText.nodeContentSB.append("</p>" + "<br/>" + "<h5 style='text-align:center; color:").append(Subspecies.DEMON.getColour(null).toWebHexString()).append(";'>Demonic backers</h5>").append("<p style='text-align:center;'>");

            for (CreditsSlot cs : Main.credits) {
                if (cs.getLegendaryCount() == 0 && cs.getEpicCount() == 0) {
                    UtilText.nodeContentSB.append("<br/>");
                    UtilText.nodeContentSB.append("&nbsp;").append(cs.getSubspeciesTier() != null ? "<b style='color:" + cs.getSubspeciesTier().getColour(null).toWebHexString() + ";'>" + cs.getName() + "</b>" : cs.getName());
                }
            }

            UtilText.nodeContentSB.append("</p>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                return new Response("Back", "Go back to the options menu.", MENU);

            } else {
                int i = 1;
                for (Artist artist : Artwork.allArtists) {
                    for (ArtistWebsite website : artist.getWebsites()) {
                        if (index == i) {
                            return new ResponseEffectsOnly(website.getName(), "Opens the page:<br/><br/><i>" + website.getURL() + "</i><br/><br/><b>Externally in your default browser.</b>") {
                                @Override
                                public void effects() {
                                    Util.openLinkInDefaultBrowser(website.getURL());
                                }
                            };
                        }
                        i++;
                    }
                }
                return null;
            }
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };


    public static final DialogueNode MISCELLANEOUS = new DialogueNode("Content Options (Misc.)", "", true) {
        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.GENERIC_GOOD, "Autosave Frequency", "Choose how often want the game to autosave when you transition from one map to another."));
            for (int i = 2; i >= 0; i--) {
                UtilText.nodeContentSB.append("<div id='AUTOSAVE_FREQUENCY_").append(i).append("' class='normal-button").append(Main.getProperties().autoSaveFrequency == i ? " selected" : "").append("' style='width:calc(33% - 8px); margin-right:8px; text-align:center; float:right;'>").append(Main.getProperties().autoSaveFrequency == i
                        ? "[style.boldGood("
                        : "[style.colourDisabled(").append(com.lilithslegacy.game.Properties.autoSaveLabels[i]).append(")]</div>");
            }
            UtilText.nodeContentSB.append("</div></div>");
            UtilText.nodeContentSB.append(getContentPreferenceDiv("ARTWORK",
                    PresetColour.BASE_BLUE_LIGHT,
                    "Artwork",
                    "Enables artwork to be displayed in characters' information screens.",
                    Main.getProperties().hasValue(PropertyValue.artwork)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("THUMBNAIL",
                    PresetColour.BASE_BLUE_STEEL,
                    "Thumbnails",
                    "Enables tooltips containing thumbnail images of the character.",
                    Main.getProperties().hasValue(PropertyValue.thumbnail)));
            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.BASE_AQUA, "Preferred Artist", "Which artist's work is used by default."));
            List<Artist> artists = new ArrayList<>(Artwork.allArtists);
            Collections.reverse(artists);// So that they're in alphabetical order
            for (Artist artist : artists) {
                if (!artist.getName().equals("Custom")) {
                    UtilText.nodeContentSB.append(
                            (Main.getProperties().preferredArtist.equals(artist.getFolderName())
                                    ? "<div id='ARTIST_" + artist.getFolderName() + "' class='normal-button selected' style='width:75%; text-align:center; float:right;'>"
                                    + "<b style='color:" + artist.getColour().toWebHexString() + ";'>" + artist.getName() + "</b>"
                                    + "</div>"
                                    : "<div id='ARTIST_" + artist.getFolderName() + "' class='normal-button' style='width:75%; text-align:center; float:right;'>"
                                    + "[style.boldDisabled(" + artist.getName() + ")]"
                                    + "</div>"));
                }
            }
            UtilText.nodeContentSB.append("</div></div>");
            UtilText.nodeContentSB.append(getContentPreferenceDiv("SHARED_ENCYCLOPEDIA",
                    PresetColour.GENERIC_EXCELLENT,
                    "Shared Encyclopedia",
                    "When enabled, your character will use the shared Encyclopedia (whose entries are unlocked across any playthrough). If disabled, unlocked Encyclopedia entries are only shown if your current character has discovered them.",
                    Main.getProperties().hasValue(PropertyValue.sharedEncyclopedia)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("WEATHER_INTERRUPTION",
                    PresetColour.GENERIC_ARCANE,
                    "Storm interruptions",
                    "When enabled, arcane storms will interrupt dialogue to let you know that they've started.",
                    Main.getProperties().hasValue(PropertyValue.weatherInterruptions)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("DIALOGUE_COPY",
                    PresetColour.BASE_BLUE_STEEL,
                    "Automatic text copying",
                    "When enabled, the current scene's text will automatically be copied to your system's clipboard every time a new scene is loaded."
                            + " This option is so that you can easily paste the game's text into text readers without needing to select and copy the scene's text every time.",
                    Main.getProperties().hasValue(PropertyValue.automaticDialogueCopy)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("SILLY",
                    PresetColour.GENERIC_GOOD,
                    "Silly mode",
                    "This enables funny flavour text throughout the game.",
                    Main.getProperties().hasValue(PropertyValue.sillyMode)));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    public static final DialogueNode GAMEPLAY = new DialogueNode("Content Options (Gameplay)", "", true) {
        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getContentPreferenceDiv("ENCHANTMENT_LIMITS",
                    PresetColour.GENERIC_ARCANE,
                    "Enchantment Capacity",
                    "Toggle the 'enchantment capacity' mechanic, which restricts how many enchanted items you can wear. This is on by default, and you will potentially break the balance of the game's combat by turning it off.",
                    Main.getProperties().hasValue(PropertyValue.enchantmentLimits)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("BAD_END",
                    PresetColour.GENERIC_TERRIBLE,
                    "Bad Ends",
                    "Toggle the ability to trigger 'bad ends', which effectively end the game for your character when encountered."
                            + "<br/>[style.italicsMinorBad(Please note that bad ends involve non-con content, regardless of whether or not your non-con option is enabled.)]",
                    Main.getProperties().hasValue(PropertyValue.badEndContent)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("LEVEL_DRAIN",
                    PresetColour.GENERIC_TERRIBLE,
                    "Level Drain",
                    "Toggle the use of the 'orgasmic level drain' perk by unique NPCs (such as some scenes with Amber), which causes them to drain your level for each orgasm you have in sex with them.",
                    Main.getProperties().hasValue(PropertyValue.levelDrain)));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("OPPORTUNISTIC_ATTACKERS",
                    PresetColour.BASE_CRIMSON,
                    "Opportunistic attackers",
                    "This makes random attacks more likely when you're high on lust, low on health, covered in fluids, exposed, or drunk.",
                    Main.game.isOpportunisticAttackersEnabled()));
            UtilText.nodeContentSB.append(getContentPreferenceDiv("OFFSPRING_ENCOUNTERS",
                    PresetColour.BASE_INDIGO,
                    "Offspring Encounters",
                    "This enables you to randomly encounter your offspring throught the world."
                            + "<br/><i>This setting has no effect on the Offspring Map, nor on offspring who you've already met.</i>",
                    Main.game.isOffspringEncountersEnabled()));

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.BASE_BLUE_LIGHT, "Clothing Femininity", "This sets the limitations of clothings' femininity values."));
            for (int i = Main.getProperties().clothingFemininityTitles.length - 1; i >= 0; i--) {
                if (Main.getProperties().getClothingFemininityLevel() == i) {
                    UtilText.nodeContentSB.append("<div id='CLOTHING_FEMININITY_").append(i).append("' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:").append(Main.getProperties().clothingFemininityColours[i].toWebHexString()).append(";'><b>").append(Main.getProperties().clothingFemininityTitles[i]).append("</b></div>");
                } else {
                    UtilText.nodeContentSB.append("<div id='CLOTHING_FEMININITY_").append(i).append("' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>").append("[style.colourDisabled(").append(Main.getProperties().clothingFemininityTitles[i]).append(")]</div>");
                }
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.BASE_PINK, "Sex action bypass", "If this is enabled, sex action corruption requirements may be bypassed."));
            for (int i = 2; i >= 0; i--) {
                UtilText.nodeContentSB.append("<div id='BYPASS_SEX_ACTIONS_").append(i).append("' class='normal-button").append(Main.getProperties().bypassSexActions == i ? " selected" : "").append("' style='width:calc(33% - 8px); margin-right:8px; text-align:center; float:right;'>").append(Main.getProperties().bypassSexActions == i
                        ? "[style.boldGood("
                        : "[style.colourDisabled(").append(com.lilithslegacy.game.Properties.bypassSexActionsLabels[i]).append(")]</div>");
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getContentPreferenceVariableDiv(
                    "PREGNANCY_DURATION",
                    PresetColour.BASE_PINK_DEEP,
                    "Pregnancy duration",
                    "This sets the maximum time it takes for a pregnancy to progress from conception to birth.",
                    Main.getProperties().pregnancyDuration + " week" + (Main.getProperties().pregnancyDuration == 1 ? "" : "s"),
                    Main.getProperties().pregnancyDuration,
                    1,
                    40));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("SPITTING_ENABLED",
                    PresetColour.BASE_BLUE,
                    "Rejecting TF potions",
                    "Forced TF potions may be spat out if this is enabled.",
                    !Main.game.isSpittingDisabled()));

            UtilText.nodeContentSB.append(getContentPreferenceVariableDiv(
                    "FORCED_TF",
                    PresetColour.TRANSFORMATION_GENERIC,
                    "Forced TF",
                    "This sets the amount of NPCs spawning with the '" + Fetish.FETISH_TRANSFORMATION_GIVING.getName(null) + "' fetish, which causes them to forcibly transform you after beating you in combat.",
                    Main.getProperties().forcedTFPercentage + "%",
                    Main.getProperties().forcedTFPercentage,
                    0,
                    100));

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.BASE_GREEN, "Forced TF Racial Limits", "This allows you to set the maximum furry limit of what an NPC will forcibly transform you into."));
            for (FurryPreference fp : Util.newArrayListOfValues(FurryPreference.REDUCED,
                    FurryPreference.MINIMUM,
                    FurryPreference.HUMAN,
                    FurryPreference.MAXIMUM,
                    FurryPreference.NORMAL)) {
                if (Main.getProperties().getForcedTFPreference() == fp) {
                    UtilText.nodeContentSB.append("<div id='FORCED_TF_FURRY_LIMIT_").append(fp).append("' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:").append(fp.getColour().toWebHexString()).append(";'>").append(fp.getName()).append("</div>");
                } else {
                    UtilText.nodeContentSB.append("<div id='FORCED_TF_FURRY_LIMIT_").append(fp).append("' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>").append("[style.colourDisabled(").append(fp.getName()).append(")]</div>");
                }
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.BASE_GREEN, "Forced TF Gender Tendency", "This allows you to override NPC tastes when a forced transformation will alter your gender presentation."));
            for (ForcedTFTendency ftt : Util.newArrayListOfValues(ForcedTFTendency.NEUTRAL,
                    ForcedTFTendency.FEMININE,
                    ForcedTFTendency.FEMININE_HEAVY,
                    ForcedTFTendency.MASCULINE_HEAVY,
                    ForcedTFTendency.MASCULINE)) {
                if (Main.getProperties().getForcedTFTendency() == ftt) {
                    UtilText.nodeContentSB.append("<div id='FORCED_TF_TENDENCY_").append(ftt).append("' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:").append(ftt.getColour().toWebHexString()).append(";'>").append(ftt.getName()).append("</div>");
                } else {
                    UtilText.nodeContentSB.append("<div id='FORCED_TF_TENDENCY_").append(ftt).append("' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>").append("[style.colourDisabled(").append(ftt.getName()).append(")]</div>");
                }
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getContentPreferenceVariableDiv(
                    "FORCED_FETISH",
                    PresetColour.FETISH,
                    "Forced Fetishes",
                    "This sets the amount of NPCs spawning with the '" + Fetish.FETISH_KINK_GIVING.getName(null) + "' fetish, which causes them to try and forcibly give you fetishes after beating you in combat.",
                    Main.getProperties().forcedFetishPercentage + "%",
                    Main.getProperties().forcedFetishPercentage,
                    0,
                    100));


            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.FETISH, "Forced Fetish Tendency",
                    "This allows you to override NPC tastes and control the tendency for forced fetishes to be for topping or bottoming."));
            for (ForcedFetishTendency fft : Util.newArrayListOfValues(ForcedFetishTendency.NEUTRAL,
                    ForcedFetishTendency.BOTTOM,
                    ForcedFetishTendency.BOTTOM_HEAVY,
                    ForcedFetishTendency.TOP_HEAVY,
                    ForcedFetishTendency.TOP)) {
                if (Main.getProperties().getForcedFetishTendency() == fft) {
                    UtilText.nodeContentSB.append("<div id='FORCED_FETISH_TENDENCY_").append(fft).append("' class='normal-button selected' style='width:31%; margin:1%; text-align:center; float:right; color:").append(fft.getColour().toWebHexString()).append(";'>").append(fft.getName()).append("</div>");
                } else {
                    UtilText.nodeContentSB.append("<div id='FORCED_FETISH_TENDENCY_").append(fft).append("' class='normal-button' style='width:31%; margin:1%; text-align:center; float:right;'>").append("[style.colourDisabled(").append(fft.getName()).append(")]</div>");
                }
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getContentPreferenceDiv("COMPANION",
                    PresetColour.BASE_GREEN_LIGHT,
                    "Companions",
                    "Enable the ability to add slaves or friendly occupants as your companion."
                            + "<br/>[style.boldBad(Warning:)] This is an experimental feature, and support for companions was dropped in v0.3.9, so there will be no special dialogue or actions involving your companions outside of Dominion.",
                    Main.getProperties().hasValue(PropertyValue.companionContent)));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    public static final DialogueNode SEX = new DialogueNode("Content Options (Sex & Fetishes)", "", true) {
        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getContentPreferenceDiv("NON_CON",
                    PresetColour.BASE_CRIMSON,
                    "Non-consent",
                    "This enables the 'resist' pace in sex scenes, which contains some more extreme non-consensual descriptions, as well as dialogue references and actions related to this content."
                            + "<br/>[style.italicsMinorBad(Please note that bad ends involve non-con content, regardless of whether or not this option is enabled.)]",
                    Main.getProperties().hasValue(PropertyValue.nonConContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("SADISTIC_SEX",
                    PresetColour.BASE_RED,
                    "Sadistic sex",
                    "This unlocks 'sadistic' sex actions, such as choking, slapping, and spitting on partners in sex.",
                    Main.getProperties().hasValue(PropertyValue.sadisticSexContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("LIPSTICK_MARKING",
                    PresetColour.BASE_RED_DARK,
                    "Lipstick marking",
                    "This enables lipstick marking of bodyparts via kisses during sex.",
                    Main.getProperties().hasValue(PropertyValue.lipstickMarkingContent)));


            UtilText.nodeContentSB.append(getContentPreferenceDiv("VOLUNTARY_NTR",
                    PresetColour.GENERIC_MINOR_BAD,
                    "Voluntary NTR",
                    "When enabled, you will get the option to offer certain enemies sex with your companions as a way to avoid combat.",
                    Main.getProperties().hasValue(PropertyValue.voluntaryNTR)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("INVOLUNTARY_NTR",
                    PresetColour.GENERIC_BAD,
                    "Involuntary NTR",
                    "When enabled, enemies might choose to only have sex with your companion after beating your party in combat."
                            + " When disabled, all post-combat-loss sex scenes will involve you.",
                    Main.getProperties().hasValue(PropertyValue.involuntaryNTR)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("INCEST",
                    PresetColour.BASE_ROSE,
                    "Incest",
                    "This will enable sexual actions between characters who are related to one another.",
                    Main.getProperties().hasValue(PropertyValue.incestContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("LACTATION",
                    PresetColour.BASE_YELLOW_LIGHT,
                    "Lactation",
                    "This enables lactation content.",
                    Main.getProperties().hasValue(PropertyValue.lactationContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("SEXUAL_UDDERS",
                    PresetColour.BASE_ORANGE_LIGHT,
                    "Crotch-boob & udder content",
                    "This enables crotch-boob & udder-related sex actions and allows crotch-boob & udder transformations to be inflicted upon the player.",
                    Main.getProperties().hasValue(PropertyValue.udderContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("URETHRAL",
                    PresetColour.BASE_PINK_DEEP,
                    "Urethral content",
                    "This enables urethral transformations and penetrations.",
                    Main.getProperties().hasValue(PropertyValue.urethralContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("NIPPLE_PEN",
                    PresetColour.BASE_PINK_DEEP,
                    "Nipple penetrations",
                    "This enables nipple-penetration transformations and sex actions.",
                    Main.getProperties().hasValue(PropertyValue.nipplePenContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("ANAL",
                    PresetColour.BASE_ORANGE,
                    "Anal content",
                    "When disabled, removes all anal-related actions from being available during sex.",
                    Main.getProperties().hasValue(PropertyValue.analContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("GAPE",
                    PresetColour.BASE_PINK_DEEP,
                    "Gape content",
                    "When disabled, changes descriptions of gaping orifices to simply be 'loose', and also hides any special gape-related content.",
                    Main.getProperties().hasValue(PropertyValue.gapeContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("PENETRATION_LIMITATION",
                    PresetColour.BASE_PINK_DEEP,
                    "Penetrative size-difference",
                    "When enabled, orifices will have a limited depth to them, meaning that penetrative objects (penises and tails) can be too long to fit all the way inside.",
                    Main.getProperties().hasValue(PropertyValue.penetrationLimitations)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("PENETRATION_LIMITATION_DYNAMIC",
                    PresetColour.BASE_PINK_DEEP,
                    "Elasticity depth effects",
                    "When enabled, if an orifice has an elasticity of at least 'limber', the maximum 'uncomfortable depth' value will be increased, with greater elasticity values increasing it further."
                            + " (Note: Only applies when 'Penetrative size-difference' is also turned on.)",
                    Main.getProperties().hasValue(PropertyValue.elasticityAffectDepth)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("FOOT",
                    PresetColour.BASE_TAN,
                    "Foot content",
                    "When disabled, removes all foot-related actions from being available during sex.",
                    Main.getProperties().hasValue(PropertyValue.footContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("ARMPIT",
                    PresetColour.BASE_PINK_LIGHT,
                    "Armpit content",
                    "When disabled, removes all armpit-related actions from being available during sex.",
                    Main.getProperties().hasValue(PropertyValue.armpitContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("MUSK",
                    PresetColour.BASE_YELLOW_LIGHT,
                    "Musk content",
                    "When disabled, some scenes will either have reduced musk content or be omitted entirely, and the 'marked by musk' status effect will be disabled.",
                    Main.getProperties().hasValue(PropertyValue.muskContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("FURRY_TAIL_PENETRATION",
                    PresetColour.BASE_MAGENTA,
                    "Furry tail penetrations",
                    "This marks all tail types as being suitable for penetration, thereby enabling furry tails to engage in penetrative actions in sex.",
                    Main.getProperties().hasValue(PropertyValue.furryTailPenetrationContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("INFLATION_CONTENT",
                    PresetColour.CUM,
                    "Cum inflation",
                    "This enables cum inflation mechanics.",
                    Main.getProperties().hasValue(PropertyValue.inflationContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("AUTO_SEX_CLOTHING_MANAGEMENT",
                    PresetColour.BASE_BLUE_STEEL,
                    "Post-sex clothing replacement",
                    "Enables equipped clothing to be automatically pulled back into their pre-sex states after sex scenes.",
                    Main.getProperties().hasValue(PropertyValue.autoSexClothingManagement)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("AUTO_SEX_CLOTHING_STRIP",
                    PresetColour.BASE_PINK_LIGHT,
                    "Automatic stripping",
                    "When enabled, all non-spectating characters which you are allowed to strip during sex (including yourself) will start sex naked.",
                    Main.getProperties().hasValue(PropertyValue.autoSexStrip)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("RAPE_PLAY_BY_DEFAULT",
                    PresetColour.BASE_CRIMSON,
                    "Rape-play allowed by default",
                    "When enabled, submissive characters in sex who have the 'unwilling fuck-toy' fetish will be able to engage in rape-play without first being given permission to do so.",
                    Main.getProperties().hasValue(PropertyValue.rapePlayAtSexStart)));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };

    public static final DialogueNode BODIES = new DialogueNode("Content Options (Bodies)", "", true) {
        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append(getContentPreferenceDiv("AGE",
                    PresetColour.AGE_TWENTIES,
                    "Age",
                    "This enables descriptions of the age that characters appear to be.",
                    Main.getProperties().hasValue(PropertyValue.ageContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("FERAL",
                    PresetColour.BASE_TAN,
                    "Feral",
                    "This enables feral content, which contains sexual and non-sexual interactions with sapient characters who have fully-animal bodies.",
                    Main.getProperties().hasValue(PropertyValue.feralContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("CUM_REGENERATION",
                    PresetColour.CUM,
                    "Cum Regeneration",
                    "This enables cum regeneration related content, such as decreasing quantity for multiple orgasms in one session and the full balls status effect."
                            + "<br>When disabled, balls will always be treated as full, but without any negative effects.",
                    Main.getProperties().hasValue(PropertyValue.cumRegenerationContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("FUTA_BALLS",
                    PresetColour.BASE_PINK,
                    "Futanari Testicles",
                    "When enabled, futanari NPCs will be able to have external testicles. When disabled, they are locked to always being internal.",
                    Main.getProperties().hasValue(PropertyValue.futanariTesticles)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("CLOACA",
                    PresetColour.BASE_PINK_LIGHT,
                    "Bipedal Cloacas",
                    "When enabled, certain bipedal races (such as harpies and alligator-morphs) will have cloacas."
                            + " When disabled, all bipeds with cloacas will be treated as having a regular genitalia configuration."
                            + " Some special races, such as lamia, always have cloacas, and are not affected by this.",
                    Main.getProperties().hasValue(PropertyValue.bipedalCloaca)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("VESTIGIAL_MULTI_BREAST",
                    PresetColour.BASE_PURPLE_LIGHT,
                    "Vestigial Multi-breasts",
                    "When enabled, characters who have multiple rows of breasts will have the rows beneath their top one described as being vestigial in size."
                            + " When disabled, breast rows will be described as being one cup size smaller than the one above them.",
                    Main.getProperties().hasValue(PropertyValue.vestigialMultiBreasts)));


            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.NIPPLES, "Multi-breasts", "Choose whether randomly-generated furry characters should be given multiple rows of breasts."));
            int[] buttonOrder = new int[]{2, 1, 0, 3}; // Order buttons in this manner so that they appear to be a little more logical
            for (int i : buttonOrder) {
                UtilText.nodeContentSB.append("<div id='MULTI_BREAST_PREFERENCE_").append(i).append("' class='normal-button").append(Main.getProperties().multiBreasts == i ? " selected" : "").append("' style='width:calc(33% - 8px); margin-right:8px; text-align:center; float:right;'>").append(Main.getProperties().multiBreasts == i
                        ? (i == 0
                        ? "[style.boldTerrible("
                        : (i == 1
                        ? "[style.boldBad("
                        : "[style.boldGood("))
                        : "[style.colourDisabled(").append(com.lilithslegacy.game.Properties.multiBreastsLabels[i]).append(")]</div>");
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getCustomContentPreferenceDivStart(PresetColour.NIPPLES_CROTCH, "Crotch-boobs & Udders", "Choose whether randomly-generated taurs and furry characters should be given udders or crotch-boobs."));
            for (int i = com.lilithslegacy.game.Properties.uddersLabels.length - 1; i >= 0; i--) {
                UtilText.nodeContentSB.append("<div id='UDDER_PREFERENCE_").append(i).append("' class='normal-button").append(Main.getProperties().getUddersLevel() == i ? " selected" : "").append("' style='width:calc(33% - 8px); margin-right:8px; text-align:center; float:right;'>").append(Main.getProperties().getUddersLevel() == i
                        ? (i == 0 ? "[style.boldBad(" : "[style.boldGood(")
                        : "[style.colourDisabled(").append(com.lilithslegacy.game.Properties.uddersLabels[i]).append(")]</div>");
            }
            UtilText.nodeContentSB.append("</div></div>");

            UtilText.nodeContentSB.append(getContentPreferenceDiv("HAIR_FACIAL",
                    PresetColour.BASE_LILAC_LIGHT,
                    "Facial hair",
                    "This enables facial hair descriptions and content.",
                    Main.getProperties().hasValue(PropertyValue.facialHairContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("HAIR_PUBIC",
                    PresetColour.BASE_LILAC,
                    "Pubic hair",
                    "This enables pubic hair descriptions and content.",
                    Main.getProperties().hasValue(PropertyValue.pubicHairContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("HAIR_BODY",
                    PresetColour.BASE_PURPLE,
                    "Underarm hair",
                    "This enables underarm hair descriptions and content.",
                    Main.getProperties().hasValue(PropertyValue.bodyHairContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("HAIR_ASS",
                    PresetColour.BASE_PURPLE_DARK,
                    "Ass hair",
                    "This enables ass hair descriptions and content.",
                    Main.getProperties().hasValue(PropertyValue.assHairContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("FEMININE_BEARD",
                    PresetColour.BASE_BLUE_STEEL,
                    "Feminine beards",
                    "This enables feminine characters to grow beards.",
                    Main.getProperties().hasValue(PropertyValue.feminineBeardsContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("FURRY_HAIR",
                    PresetColour.CLOTHING_DESATURATED_BROWN,
                    "Furry hair",
                    "Toggles whether or not characters with a furry head type will spawn with human-like hair on their heads.",
                    Main.getProperties().hasValue(PropertyValue.furryHairContent)));

            UtilText.nodeContentSB.append(getContentPreferenceDiv("SCALY_HAIR",
                    PresetColour.BASE_GREEN_DARK,
                    "Scaly Hair",
                    "Toggles whether or not characters with a reptilian or amphibious head type will spawn with human-like hair on their heads.",
                    Main.getProperties().hasValue(PropertyValue.scalyHairContent)));

            UtilText.nodeContentSB.append(getBreastsContentPreferenceVariableDiv(
                    "PREGNANCY_BREAST_GROWTH",
                    PresetColour.BASE_PINK,
                    "Average Pregnancy Breast Growth",
                    "Set the <b>average</b> cup size growth that characters will gain from each pregnancy. Actual breast growth will be within " + Util.intToString(Main.getProperties().pregnancyBreastGrowthVariance) + " sizes of this value.",
                    Main.getProperties().pregnancyBreastGrowth == 0
                            ? "[style.boldDisabled(Disabled)]"
                            : Main.getProperties().pregnancyBreastGrowth + " cup" + (Main.getProperties().pregnancyBreastGrowth != 1 ? "s" : ""),
                    Main.getProperties().pregnancyBreastGrowth, 0, 10,
                    Main.getProperties().pregnancyUdderGrowth == 0
                            ? "[style.boldDisabled(Disabled)]"
                            : Main.getProperties().pregnancyUdderGrowth + " cup" + (Main.getProperties().pregnancyUdderGrowth != 1 ? "s" : ""),
                    Main.getProperties().pregnancyUdderGrowth, 0, 10));

            UtilText.nodeContentSB.append(getBreastsContentPreferenceVariableDiv(
                    "PREGNANCY_BREAST_GROWTH_LIMIT",
                    PresetColour.BASE_PINK_LIGHT,
                    "Pregnancy Breast Growth Limit",
                    "Set the maximum limit of cup size that characters' breasts will grow to from pregnancies.",
                    CupSize.getCupSizeFromInt(Main.getProperties().pregnancyBreastGrowthLimit).getCupSizeName() + "-cup",
                    Main.getProperties().pregnancyBreastGrowthLimit, 0, 100,
                    CupSize.getCupSizeFromInt(Main.getProperties().pregnancyUdderGrowthLimit).getCupSizeName() + "-cup",
                    Main.getProperties().pregnancyUdderGrowthLimit, 0, 100));

            UtilText.nodeContentSB.append(getBreastsContentPreferenceVariableDiv(
                    "PREGNANCY_LACTATION",
                    PresetColour.BASE_YELLOW,
                    "Average Pregnancy Lactation",
                    "Set the <b>average</b> increase in lactation that characters will gain as a result of each pregnancy. Actual lactation increase will be within "
                            + Units.fluid(Main.getProperties().pregnancyLactationIncreaseVariance) + " of this value.",
                    Main.getProperties().pregnancyLactationIncrease == 0
                            ? "[style.boldDisabled(Disabled)]"
                            : Units.fluid(Main.getProperties().pregnancyLactationIncrease),
                    Main.getProperties().pregnancyLactationIncrease, 0, 1000,
                    Main.getProperties().pregnancyUdderLactationIncrease == 0
                            ? "[style.boldDisabled(Disabled)]"
                            : Units.fluid(Main.getProperties().pregnancyUdderLactationIncrease),
                    Main.getProperties().pregnancyUdderLactationIncrease, 0, 1000));

            UtilText.nodeContentSB.append(getBreastsContentPreferenceVariableDiv(
                    "PREGNANCY_LACTATION_LIMIT",
                    PresetColour.BASE_YELLOW_LIGHT,
                    "Pregnancy Lactation Limit",
                    "Set the maximum limit of lactation that characters will gain from pregnancies.",
                    Units.fluid(Main.getProperties().pregnancyLactationLimit, Units.ValueType.PRECISE, Units.UnitType.SHORT),
                    Main.getProperties().pregnancyLactationLimit, 0, Lactation.SEVEN_MONSTROUS_AMOUNT_POURING.getMaximumValue(),
                    Units.fluid(Main.getProperties().pregnancyUdderLactationLimit, Units.ValueType.PRECISE, Units.UnitType.SHORT),
                    Main.getProperties().pregnancyUdderLactationLimit, 0, Lactation.SEVEN_MONSTROUS_AMOUNT_POURING.getMaximumValue()));

            UtilText.nodeContentSB.append(getBreastsContentPreferenceVariableDiv(
                    "BREAST_SIZE_PREFERENCE",
                    PresetColour.NIPPLES,
                    "Cup Size Preference",
                    "Affects randomly-generated NPCs' cup sizes (will not be reduced to below AA-cup).",
                    (Main.getProperties().breastSizePreference >= 0 ? "+" : "") + Main.getProperties().breastSizePreference,
                    Main.getProperties().breastSizePreference, -20, 20,
                    (Main.getProperties().udderSizePreference >= 0 ? "+" : "") + Main.getProperties().udderSizePreference,
                    Main.getProperties().udderSizePreference, -20, 20));

            UtilText.nodeContentSB.append(getContentPreferenceVariableDiv(
                    "PENIS_SIZE_PREFERENCE",
                    PresetColour.PENIS,
                    "Penis Size Preference",
                    "Affects randomly-generated NPCs' penis sizes (will not be reduced to below " + Units.size(8) + ").",
                    (Main.getProperties().penisSizePreference >= 0 ? "+" : "") + Units.size(Main.getProperties().penisSizePreference, Units.ValueType.PRECISE, Units.UnitType.SHORT),
                    Main.getProperties().penisSizePreference,
                    -20,
                    20));

            UtilText.nodeContentSB.append(getContentPreferenceVariableDiv(
                    "TRAP_PENIS_SIZE_PREFERENCE",
                    PresetColour.BASE_PINK_LIGHT,
                    Util.capitaliseSentence(Gender.N_P_TRAP.getName()) + " penis size",
                    "The penis size of randomly-generated " + Gender.N_P_TRAP.getName() + "s. 100% represents an unaltered size. Testicle size and cum production will also be altered in proportion to this setting.",
                    (100 + Main.getProperties().trapPenisSizePreference) + "%",
                    Main.getProperties().trapPenisSizePreference,
                    -90,
                    100));

            UtilText.nodeContentSB.append(getSkinColourContentPreferenceVariableDiv(
                    "SKIN_COLOUR_PREFERENCE",
                    PresetColour.RACE_HUMAN,
                    "Skin Colour Preference",
                    "Affects the weighting of human skin colour for randomly-generated NPCs."
                            + " This does not affect 'Greater' furry NPCs, as they have no human skin coverings."));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getContentOptionsResponse(responseTab, index);
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OPTIONS;
        }
    };
}
