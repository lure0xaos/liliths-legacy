package com.lilithslegacy.controller;

import com.lilithslegacy.controller.eventListeners.tooltips.TooltipInformationEventListener;
import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.dialogue.companions.CompanionManagement;
import com.lilithslegacy.game.dialogue.places.dominion.cityHall.CityHall;
import com.lilithslegacy.game.dialogue.places.dominion.slaverAlley.SlaverAlleyDialogue;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.BodyChanging;
import com.lilithslegacy.game.dialogue.utils.CharactersPresentDialogue;
import com.lilithslegacy.game.dialogue.utils.EnchantmentDialogue;
import com.lilithslegacy.game.dialogue.utils.OptionsDialogue;
import com.lilithslegacy.game.dialogue.utils.PhoneDialogue;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.LoadedEnchantment;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.Artwork;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;
import javafx.stage.FileChooser;
import org.w3c.dom.events.EventTarget;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maxis010, Innoxia
 * @version 0.4.6.4
 * @since 0.4.6.4
 */
public class FileController {

    private static Path lastOpened = null;

    public static void initArtworkListeners() {
        GameCharacter character = Main.game.getCurrentDialogueNode().equals(PhoneDialogue.CHARACTER_APPEARANCE)
                ? Main.game.getPlayer()
                : (Main.game.getCurrentDialogueNode().equals(CompanionManagement.SLAVE_MANAGEMENT_INSPECT)
                ? Main.game.getDialogueFlags().getManagementCompanion()
                : CharactersPresentDialogue.characterViewed);

        String id = "ARTWORK_ADD";
        if (MainController.document.getElementById(id) != null) {
            ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                // Create file chooser for .jpg and .png images in the most recently used directory
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Add Images");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
                if (lastOpened != null) {
                    chooser.setInitialDirectory(lastOpened.toFile());
                }

                List<Path> files = chooser.showOpenMultipleDialog(Main.primaryStage).stream().map(p -> FS.newPath(p.getPath())).collect(Collectors.toList());
                if (files != null && !files.isEmpty()) {
                    lastOpened = files.get(0).getParent();

                    character.importImages(files);

                    if (!character.isPlayer()) {
                        CharactersPresentDialogue.resetContent(character);
                    }
                    Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                }
            }, false);
            MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation(
                    "Add custom artwork",
                    "Browse your own images and add them to the character."
                            + " Please note that GIF animation files are limited to a <b>maximum of 10MB</b> in size, and if over 1MB, <b>may</b> cause [style.italicsBad(significant lag)], depending on your system."
                            + "<br/>Custom images for the currently played game are located in folder: <b>'data/images/" + Main.game.getId() + "'</b>"
                            + "<br/>This character's ID is <b>'" + character.getId() + "'</b>",
                    130));
        }

        if (character.hasArtwork()) {
            try {
                Artwork artwork = character.getCurrentArtwork();

                id = "ARTWORK_INFO";
                if (MainController.document.getElementById(id) != null) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        if (!artwork.getArtist().getWebsites().isEmpty()) {
                            Util.openLinkInDefaultBrowser(artwork.getArtist().getWebsites().get(0).getURL());
                        }
                    }, false);

                    String description;
                    if (artwork.getArtist().getName().equals("Custom")) {
                        description = "You added this yourself.";
                    } else if (artwork.getArtist().getWebsites().isEmpty()) {
                        description = "This artist has no associated websites!";
                    } else {
                        description = "Click to open <b style='color:" + artwork.getArtist().getColour().toWebHexString() + ";'>" + artwork.getArtist().getWebsites().get(0).getName() + "</b>"
                                + " (" + artwork.getArtist().getWebsites().get(0).getURL() + ") <b>externally</b> in your default browser!";
                    }
                    MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation(
                            "Artwork by <b style='color:" + artwork.getArtist().getColour().toWebHexString() + ";'>" + artwork.getArtist().getName() + "</b>",
                            description));
                }

                id = "ARTWORK_PREVIOUS";
                if (MainController.document.getElementById(id) != null) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        if (artwork.getTotalArtworkCount() > 1) {
                            artwork.incrementIndex(-1);
                            if (!character.isPlayer())
                                CharactersPresentDialogue.resetContent(character);
                            Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                        }
                    }, false);
                }

                id = "ARTWORK_NEXT";
                if (MainController.document.getElementById(id) != null) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        if (artwork.getTotalArtworkCount() > 1) {
                            artwork.incrementIndex(1);
                            if (!character.isPlayer())
                                CharactersPresentDialogue.resetContent(character);
                            Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                        }
                    }, false);
                }

                id = "ARTWORK_ARTIST_PREVIOUS";
                if (MainController.document.getElementById(id) != null) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        if (character.getArtworkList().size() > 1) {
                            character.incrementArtworkIndex(-1);
                            if (!character.isPlayer())
                                CharactersPresentDialogue.resetContent(character);
                            Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                        }
                    }, false);
                }

                id = "ARTWORK_ARTIST_NEXT";
                if (MainController.document.getElementById(id) != null) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        if (character.getArtworkList().size() > 1) {
                            character.incrementArtworkIndex(1);
                            if (!character.isPlayer())
                                CharactersPresentDialogue.resetContent(character);
                            Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                        }
                    }, false);
                }

                id = "ARTWORK_DELETE";
                if (MainController.document.getElementById(id) != null) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        Path self = artwork.getCurrentImage();
                        try {
                            Files.delete(self);
                        } catch (IOException e1) {
                            throw new RuntimeException(e1);
                        }
                        character.updateImages();
                        if (!character.isPlayer())
                            CharactersPresentDialogue.resetContent(character);
                        Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                    }, false);
                    MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation(
                            "Remove custom artwork",
                            "Removes the current image from this character."
                                    + "<br/>[style.italicsBad(Please note that this will delete the image from the game's folder!)]"));
                }
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, "MainController Artwork handling error.");
            }
        }
    }

    public static void initSaveLoadListeners() {
        String id;
        for (Path f : Main.getSavedGames(false)) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);

            id = "OVERWRITE_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || OptionsDialogue.overwriteConfirmationName.equals(f.getFileName().toString())) {
                        OptionsDialogue.overwriteConfirmationName = "";
                        Main.saveGame(fileName, true, false);
                    } else {
                        OptionsDialogue.overwriteConfirmationName = f.getFileName().toString();
                        OptionsDialogue.loadConfirmationName = "";
                        OptionsDialogue.deleteConfirmationName = "";
                        Main.game.setContent(new Response("Save/Load", "Open the save/load game window.", OptionsDialogue.SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Overwrite", ""));
            } else {
                id = "OVERWRITE_" + fileIdentifier + "_DISABLED";
                if (MainController.document.getElementById(id) != null) {
                    MainController.addEventListener(MainController.document, id, "mousemove", MainController.moveTooltipListener, false);
                    MainController.addEventListener(MainController.document, id, "mouseleave", MainController.hideTooltipListener, false);
                    TooltipInformationEventListener el2 = new TooltipInformationEventListener().setInformation("Overwrite (Disabled)",
                            (!Main.game.isStarted()
                                    ? "You need to have started a game before you can overwrite a save!"
                                    : "You cannot overwrite save files unless you are in a tile's default scene!"));
                    MainController.addEventListener(MainController.document, id, "mouseenter", el2, false);
                }
            }
            id = "LOAD_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || OptionsDialogue.loadConfirmationName.equals(f.getFileName().toString())) {
                        OptionsDialogue.loadConfirmationName = "";
                        Main.loadGame(fileName);
                    } else {
                        OptionsDialogue.overwriteConfirmationName = "";
                        OptionsDialogue.loadConfirmationName = f.getFileName().toString();
                        OptionsDialogue.deleteConfirmationName = "";
                        Main.game.setContent(new Response("Save/Load", "Open the save/load game window.", OptionsDialogue.SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Load", ""));
            }
            id = "DELETE_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || OptionsDialogue.deleteConfirmationName.equals(f.getFileName().toString())) {
                        OptionsDialogue.deleteConfirmationName = "";
                        Main.deleteGame(fileName);
                        Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
                    } else {
                        OptionsDialogue.overwriteConfirmationName = "";
                        OptionsDialogue.loadConfirmationName = "";
                        OptionsDialogue.deleteConfirmationName = f.getFileName().toString();
                        Main.game.setContent(new Response("Save/Load", "Open the save/load game window.", OptionsDialogue.SAVE_LOAD));
                    }

                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Delete", ""));
            }
        }
        id = "NEW_SAVE";
        if (MainController.document.getElementById(id) != null) {
            ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenPField').innerHTML=document.getElementById('new_save_name').value;");
                Main.saveGame(Main.mainController.getWebEngine().getDocument().getElementById("hiddenPField").getTextContent(), false, false);
            }, false);
            MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Save", ""));
        } else {
            id = "NEW_SAVE_DISABLED";
            if (MainController.document.getElementById(id) != null) {
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Save (Disabled)",
                        (!Main.game.isStarted()
                                ? "You need to have started a game before you can save!"
                                : "You cannot save the game unless you are in a tile's default scene!")));
            }
        }
    }

    public static void initImportExportListeners() {
        for (Path f : Main.getCharactersForImport()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);

            String id = "DELETE_CHARACTER_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || OptionsDialogue.deleteConfirmationName.equals(f.getFileName().toString())) {
                        OptionsDialogue.deleteConfirmationName = "";
                        Main.deleteExportedCharacter(fileName);
                    } else {
                        OptionsDialogue.overwriteConfirmationName = "";
                        OptionsDialogue.loadConfirmationName = "";
                        OptionsDialogue.deleteConfirmationName = f.getFileName().toString();
                        Main.game.setContent(new Response("Import/Export", "Open the Import/Export window.", OptionsDialogue.IMPORT_EXPORT));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Delete", ""));
            }
        }
        if (MainController.document.getElementById("NEW_SAVE") != null) {
            ((EventTarget) MainController.document.getElementById("NEW_SAVE")).addEventListener("click", e -> {
                Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenPField').innerHTML=document.getElementById('new_save_name').value;");
                Main.saveGame(Main.mainController.getWebEngine().getDocument().getElementById("hiddenPField").getTextContent(), false, false);
            }, false);
        }
    }

    public static void initPlayerImportListeners() {
        for (Path f : Main.getCharactersForImport()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            if (MainController.document.getElementById("IMPORT_CHARACTER_" + fileIdentifier) != null) {
                ((EventTarget) MainController.document.getElementById("IMPORT_CHARACTER_" + fileIdentifier)).addEventListener("click", e -> {
                    Main.importCharacter(f);
                }, false);
            }
        }
    }

    public static void initSlaveImportListeners() {
        for (Path f : Main.getSlavesForImport()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);
            if (MainController.document.getElementById("IMPORT_SLAVE_" + fileIdentifier) != null) {
                ((EventTarget) MainController.document.getElementById("IMPORT_SLAVE_" + fileIdentifier)).addEventListener("click", e -> {
                    try {
                        Game.importCharacterAsSlave(fileName);
                        MainController.updateUI();
                        Main.game.flashMessage(PresetColour.GENERIC_GOOD, "Imported Character!");
                    } catch (Exception ex) {
                        Main.game.flashMessage(PresetColour.GENERIC_BAD, "Import Failed!");
                    }
                }, false);
            }
        }
    }

    public static void initAuctionListeners() {
        for (NPC npc : Main.game.getCharactersPresent()) {
            String id = npc.getId() + "_BID";
            if (MainController.document.getElementById(id) != null) {
                if (Main.game.getPlayer().isHasSlaverLicense()) {
                    ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                        SlaverAlleyDialogue.setupBidding(npc);
                        Main.game.setContent(new Response("", "", SlaverAlleyDialogue.AUCTION_BIDDING));
                    }, false);
                    MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation(
                            UtilText.parse(npc, "Bid on [npc.name]"),
                            UtilText.parse(npc, "Start bidding on [npc.name]. There's a chance that the bidding might exceed [npc.her] value, so make sure you have enough money first!")));
                } else {
                    MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation(
                            UtilText.parse(npc, "Bid on [npc.name]"),
                            UtilText.parse(npc, "You don't have a slaver license, so you're unable to big on any slaves!")));
                }
            }
        }
    }

    public static void initLodgerImportListeners() {
        for (Path f : Main.getSlavesForImport()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);

            if (MainController.document.getElementById("IMPORT_LODGER_" + fileIdentifier) != null) {
                ((EventTarget) MainController.document.getElementById("IMPORT_LODGER_" + fileIdentifier)).addEventListener("click", e -> {
                    try {
                        Game.importCharacterAsLodger(fileName);
                        MainController.updateUI();
                        Main.game.flashMessage(PresetColour.GENERIC_GOOD, "Imported Character!");
                    } catch (Exception ex) {
                        Main.game.flashMessage(PresetColour.GENERIC_BAD, "Import Failed!");
                    }
                }, false);
            }
        }
    }

    public static void initLodgerWaitingListeners() {
        for (NPC npc : Main.game.getCharactersPresent()) {
            String id = npc.getId() + "_LODGER";
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    CityHall.setupLodger(npc);
                    Main.game.setContent(new Response("", "", CityHall.CITY_HALL_APPROACH_LODGER));
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation(
                        UtilText.parse(npc, "Find [npc.name]"),
                        UtilText.parse(npc, "Look around the waiting area and see if you can find [npc.name]...")));
            }
        }
    }

    public static void initClubberImportListeners() {
        for (Path f : Main.getSlavesForImport()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);
            if (MainController.document.getElementById("IMPORT_CLUBBER_" + fileIdentifier) != null) {
                ((EventTarget) MainController.document.getElementById("IMPORT_CLUBBER_" + fileIdentifier)).addEventListener("click", e -> {
                    try {
                        Game.importCharacterAsClubber(fileName);
                        MainController.updateUI();
                        Main.game.setContent(new Response("", "", Main.game.getDefaultDialogue()));
                        Main.game.flashMessage(PresetColour.GENERIC_GOOD, "Imported Character!");
                    } catch (Exception ex) {
                        Main.game.flashMessage(PresetColour.GENERIC_BAD, "Import Failed!");
                    }
                }, false);
            }
        }
    }

    public static void initEnchantmentSaveLoadListeners() {
        String id;
        for (Path f : EnchantmentDialogue.getSavedEnchants()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);

            id = "OVERWRITE_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || EnchantmentDialogue.overwriteConfirmationName.equals(f.getFileName().toString())) {
                        EnchantmentDialogue.overwriteConfirmationName = "";
                        EnchantmentDialogue.saveEnchant(fileName, true, EnchantmentDialogue.ENCHANTMENT_SAVE_LOAD);
                    } else {
                        EnchantmentDialogue.overwriteConfirmationName = f.getFileName().toString();
                        EnchantmentDialogue.loadConfirmationName = "";
                        EnchantmentDialogue.deleteConfirmationName = "";
                        Main.game.setContent(new Response("Save/Load", "Open the save/load enchantment window.", EnchantmentDialogue.ENCHANTMENT_SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Overwrite", ""));
            }
            id = "LOAD_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || EnchantmentDialogue.loadConfirmationName.equals(f.getFileName().toString())) {
                        EnchantmentDialogue.loadConfirmationName = "";
                        LoadedEnchantment lEnch = EnchantmentDialogue.loadEnchant(fileName);

                        EnchantmentDialogue.resetNonTattooEnchantmentVariables();
                        AbstractCoreItem abstractItem = lEnch.getSuitableItem();
                        EnchantmentDialogue.initModifiers(abstractItem);
                        EnchantmentDialogue.getEffects().clear();
                        for (ItemEffect ie : lEnch.getEffects()) {
                            EnchantmentDialogue.addEffect(ie);
                        }
                        EnchantmentDialogue.setOutputName(lEnch.getName());
                        Main.game.setContent(new Response("Save/Load", "Open the save/load enchantment window.", EnchantmentDialogue.ENCHANTMENT_MENU));
                    } else {
                        EnchantmentDialogue.overwriteConfirmationName = "";
                        EnchantmentDialogue.loadConfirmationName = f.getFileName().toString();
                        EnchantmentDialogue.deleteConfirmationName = "";
                        Main.game.setContent(new Response("Save/Load", "Open the save/load enchantment window.", EnchantmentDialogue.ENCHANTMENT_SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Load", ""));
            }
            id = "DELETE_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || EnchantmentDialogue.deleteConfirmationName.equals(f.getFileName().toString())) {
                        EnchantmentDialogue.deleteConfirmationName = "";
                        EnchantmentDialogue.deleteEnchant(fileName);
                        EnchantmentDialogue.initSaveLoadMenu();
                        Main.game.setContent(new Response("Save/Load", ".", EnchantmentDialogue.ENCHANTMENT_SAVE_LOAD));
                    } else {
                        EnchantmentDialogue.overwriteConfirmationName = "";
                        EnchantmentDialogue.loadConfirmationName = "";
                        EnchantmentDialogue.deleteConfirmationName = f.getFileName().toString();
                        Main.game.setContent(new Response("Save/Load", ".", EnchantmentDialogue.ENCHANTMENT_SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Delete", ""));
            }
        }

        id = "NEW_SAVE";
        if (MainController.document.getElementById(id) != null) {
            ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenPField').innerHTML=document.getElementById('new_save_name').value;");
                EnchantmentDialogue.saveEnchant(Main.mainController.getWebEngine().getDocument().getElementById("hiddenPField").getTextContent(), false, EnchantmentDialogue.ENCHANTMENT_SAVE_LOAD);
            }, false);
            MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Save", ""));
        }
        for (Map.Entry<String, LoadedEnchantment> entry : EnchantmentDialogue.getLoadedEnchantmentsMap().entrySet()) {
            id = "LOADED_ENCHANTMENT_" + entry.getKey();
            if (MainController.document.getElementById(id) != null) {
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setLoadedEnchantment(entry.getValue()));
            }
        }
    }

    public static void initBodySaveLoadListeners() {
        String id;
        for (Path f : BodyChanging.getSavedBodies()) {
            String fileIdentifier = Util.getFileIdentifier(f);
            String fileName = Util.getFileName(f);

            id = "OVERWRITE_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || BodyChanging.overwriteConfirmationName.equals(f.getFileName().toString())) {
                        BodyChanging.overwriteConfirmationName = "";
                        BodyChanging.saveBody(fileName, true);
                    } else {
                        BodyChanging.overwriteConfirmationName = f.getFileName().toString();
                        BodyChanging.loadConfirmationName = "";
                        BodyChanging.deleteConfirmationName = "";
                        Main.game.setContent(new Response("Save/Load", "Open the save/load transformation window.", BodyChanging.BODY_CHANGING_SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Overwrite", ""));
            }
            id = "LOAD_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    Body loadedBody = BodyChanging.loadBody(fileName);
                    if (BodyChanging.isPresetTransformationAvailable(loadedBody)) {
                        if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || BodyChanging.loadConfirmationName.equals(f.getFileName().toString())) {
                            BodyChanging.loadConfirmationName = "";
                            BodyChanging.applyLoadedBody(loadedBody);
                            Main.game.setContent(new Response("Save/Load", "Open the save/load transformation window.", BodyChanging.BODY_CHANGING_CORE));
                        } else {
                            BodyChanging.overwriteConfirmationName = "";
                            BodyChanging.loadConfirmationName = f.getFileName().toString();
                            BodyChanging.deleteConfirmationName = "";
                            Main.game.setContent(new Response("Save/Load", "Open the save/load transformation window.", BodyChanging.BODY_CHANGING_SAVE_LOAD));
                        }
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Load",
                        BodyChanging.isPresetTransformationAvailable(BodyChanging.loadBody(fileName))
                                ? ""
                                : BodyChanging.getPresetTransformationUnavailabilityText(BodyChanging.loadBody(fileName))));
            }
            id = "DELETE_" + fileIdentifier;
            if (MainController.document.getElementById(id) != null) {
                ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                    if (!Main.getProperties().hasValue(PropertyValue.overwriteWarning) || BodyChanging.deleteConfirmationName.equals(f.getFileName().toString())) {
                        BodyChanging.deleteConfirmationName = "";
                        BodyChanging.deleteBody(fileName);
                        BodyChanging.initSaveLoadMenu();
                        Main.game.setContent(new Response("Save/Load", ".", BodyChanging.BODY_CHANGING_SAVE_LOAD));
                    } else {
                        BodyChanging.overwriteConfirmationName = "";
                        BodyChanging.loadConfirmationName = "";
                        BodyChanging.deleteConfirmationName = f.getFileName().toString();
                        Main.game.setContent(new Response("Save/Load", ".", BodyChanging.BODY_CHANGING_SAVE_LOAD));
                    }
                }, false);
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Delete", ""));
            }
        }
        id = "NEW_SAVE";
        if (MainController.document.getElementById(id) != null) {
            ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                Main.mainController.getWebEngine().executeScript("document.getElementById('hiddenPField').innerHTML=document.getElementById('new_save_name').value;");
                BodyChanging.saveBody(Main.mainController.getWebEngine().getDocument().getElementById("hiddenPField").getTextContent(), false);
            }, false);
            MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Save", ""));
        }
        for (Map.Entry<String, Util.Value<String, Body>> entry : BodyChanging.getPresetTransformationsMap().entrySet()) {
            id = "LOADED_BODY_" + entry.getKey();
            if (MainController.document.getElementById(id) != null) {
                MainController.addTooltipListeners(id, new TooltipInformationEventListener().setLoadedBody(entry.getValue().getValue(), BodyChanging.getTarget()));
            }
        }
    }
}
