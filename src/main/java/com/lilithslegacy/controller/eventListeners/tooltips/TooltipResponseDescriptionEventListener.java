package com.lilithslegacy.controller.eventListeners.tooltips;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.MouseEvent;

import com.lilithslegacy.controller.MainController;
import com.lilithslegacy.controller.TooltipUpdateThread;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.responses.ResponseSex;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.4.5
 * @since 0.1.0
 */
public class TooltipResponseDescriptionEventListener implements EventListener {
    private static final StringBuilder tooltipSB;

    static {
        tooltipSB = new StringBuilder();
    }

    private int index;
    private boolean nextPage = false;
    private boolean previousPage = false;

    @Override
    public void handleEvent(Event event) {

        Main.mainController.setTooltipContent("");

        if (nextPage) {
            if (Main.game.isHasNextResponsePage()) {

                Main.mainController.setTooltipSize(360, 60);

                double xPosition = ((MouseEvent) event).getScreenX() + 16 - 180;
                if (xPosition + 360 > Main.primaryStage.getX() + Main.primaryStage.getWidth() - 16)
                    xPosition = Main.primaryStage.getX() + Main.primaryStage.getWidth() - 360 - 16;
                double yPosition = Main.primaryStage.getY() + Main.primaryStage.getHeight() - (34 * (MainController.RESPONSE_COUNT / 5) + 4) - Main.mainController.getTooltip().getHeight()
                        - (Main.mainScene.getWindow().getHeight() - Main.mainScene.getHeight() - Main.mainScene.getY());

                Main.mainController.getTooltip().setAnchorX(xPosition);
                Main.mainController.getTooltip().setAnchorY(yPosition);

                Main.mainController.setTooltipContent("<div class='title'>Next Page</div>");

                Main.mainController.getTooltip().setAnchorX(xPosition);
                Main.mainController.getTooltip().setAnchorY(yPosition);

                TooltipUpdateThread.updateToolTip(xPosition, yPosition);
            }
        } else if (previousPage) {
            if (Main.game.getResponsePage() != 0) {

                Main.mainController.setTooltipSize(360, 60);

                double xPosition = ((MouseEvent) event).getScreenX() + 16 - 180;
                if (xPosition + 360 > Main.primaryStage.getX() + Main.primaryStage.getWidth() - 16)
                    xPosition = Main.primaryStage.getX() + Main.primaryStage.getWidth() - 360 - 16;
                double yPosition = Main.primaryStage.getY() + Main.primaryStage.getHeight() - (34 * (MainController.RESPONSE_COUNT / 5) + 4) - Main.mainController.getTooltip().getHeight()
                        - (Main.mainScene.getWindow().getHeight() - Main.mainScene.getHeight() - Main.mainScene.getY());

                Main.mainController.getTooltip().setAnchorX(xPosition);
                Main.mainController.getTooltip().setAnchorY(yPosition);

                Main.mainController.setTooltipContent("<div class='title'>Previous Page</div>");

                Main.mainController.getTooltip().setAnchorX(xPosition);
                Main.mainController.getTooltip().setAnchorY(yPosition);

                TooltipUpdateThread.updateToolTip(xPosition, yPosition);
            }

        } else {
            Response response = null;
            if (Main.game.getCurrentDialogueNode() != null) {
                if (Main.game.getResponsePage() == 0) {
                    response = Main.game.getCurrentDialogueNode().getResponse(Main.game.getResponseTab(), index);
                } else {
                    if (index != 0) {
                        response = Main.game.getCurrentDialogueNode().getResponse(Main.game.getResponseTab(), Main.game.getResponsePage() * MainController.RESPONSE_COUNT + index - 1);
                    } else {
                        response = Main.game.getCurrentDialogueNode().getResponse(Main.game.getResponseTab(), Main.game.getResponsePage() * MainController.RESPONSE_COUNT + MainController.RESPONSE_COUNT - 1);
                    }
                }
            }

            if (response != null) {
                tooltipSB.setLength(0);

                int boxHeight = 130;

                if (!response.hasRequirements()) {
                    if (response instanceof ResponseSex) {
                        if (((ResponseSex) response).isMasturbation()) {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX_AS_DOM.toWebHexString()).append(";'>Masturbation</span></div>");
                        } else if (((ResponseSex) response).isPlayerInDominantSlot()) {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX_AS_DOM.toWebHexString()).append(";'>Dominant Sex</span></div>");
                        } else {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Submissive Sex</span></div>");
                        }
                        boxHeight += 44;
                        tooltipSB.append("<div class='description'>").append(response.getTooltipText()).append("</div>");

                    } else if (response.isCombatHighlight()) {
                        tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_COMBAT.toWebHexString()).append(";'>Combat</span></div>");
                        boxHeight += 44;
                        tooltipSB.append("<div class='description'>").append(response.getTooltipText()).append("</div>");

                    } else if (response.getAssociatedCombatMove() != null) {
                        AbstractCombatMove move = response.getAssociatedCombatMove();
                        boolean coreMove = Main.game.getPlayer().getEquippedMoves().contains(move);

                        tooltipSB.append("<div class='title'>" + "<span style='color:").append((coreMove ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_MINOR_BAD).toWebHexString()).append(";'>").append(Util.capitaliseSentence(move.getName(0, Main.game.getPlayer()))).append("</span>").append("</div>");
                        boxHeight += 44;

                        int cost = move.getAPcost(Main.game.getPlayer());
                        int cooldown = move.getCooldown(Main.game.getPlayer());

                        tooltipSB.append("<div class='subTitle' style='width:46%; margin:2% 2% 0% 2%;'>" + "<span style='color:").append((PresetColour.ACTION_POINT_COLOURS[cost]).toWebHexString()).append(";'>").append(coreMove ? cost : (cost - 1) + "[style.colourBad(+1)]").append("</span> AP").append("</div>").append("<div class='subTitle' style='width:46%; margin:2% 2% 0% 2%;'>").append("<span style='color:").append((cooldown - (coreMove ? 0 : 1) <= 0 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_MINOR_BAD).toWebHexString()).append(";'>").append(coreMove ? cooldown : (cooldown - 1) + "[style.colourBad(+1)]").append("</span> turn").append(cooldown == 1 ? "" : "s").append(" cooldown").append("</div>");

                        boxHeight += 36;

                        tooltipSB.append("<div class='description'>").append(response.getTooltipText()).append("</div>");

                        tooltipSB.append("<div class='description-small'>").append(coreMove
                                ? "<i>This is one of your [style.colourMinorGood(core moves)], so there is no extra AP cost or cooldown time for using it!</i>"
                                : "<i>This is [style.colourMinorBad(not one of your core moves)], so there is an extra cost of [style.colourBad(+1 AP)] and [style.colourBad(+1 turn cooldown)] for using it!</i>").append("</div>");
                        boxHeight += 54;

                    } else {
                        tooltipSB.append("<div class='description'>").append(response.getTooltipText()).append("</div>");
                    }


                } else {
                    if (response.isAvailable()) {
                        if (response instanceof ResponseSex) {
                            if (((ResponseSex) response).isPlayerInDominantSlot()) {
                                tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX_AS_DOM.toWebHexString()).append(";'>Dominant Sex</span> (<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Available</span>)</div>");
                            } else {
                                tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Submissive Sex</span> (<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Available</span>)</div>");
                            }
                        } else if (response.isCombatHighlight()) {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_COMBAT.toWebHexString()).append(";'>Combat</span> (<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Available</span>)</div>");
                        } else {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Available</span></div>");
                        }
                        boxHeight += 44;

                        if (response.getSexPace() != null) {
                            tooltipSB.append("<div class='subTitle'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Sex Pace:</span>").append(" <span style='color:").append(response.getSexPace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(response.getSexPace().getName())).append("</span></div>");
                            boxHeight += 44;
                        }

                        tooltipSB.append("<div class='description'>").append(response.getTooltipText()).append("</div>");

                    } else if (response.isAbleToBypass()) {
                        if (response instanceof ResponseSex) {
                            if (((ResponseSex) response).isPlayerInDominantSlot()) {
                                tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX_AS_DOM.toWebHexString()).append(";'>Dominant Sex</span>").append(" (<span style='color:").append(PresetColour.GENERIC_ARCANE.toWebHexString()).append(";'>Corruptive</span>)</div>");
                            } else {
                                tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Submissive Sex</span>").append(" (<span style='color:").append(PresetColour.GENERIC_ARCANE.toWebHexString()).append(";'>Corruptive</span>)</div>");
                            }
                        } else if (response.isCombatHighlight()) {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_COMBAT.toWebHexString()).append(";'>Combat</span> (<span style='color:").append(PresetColour.GENERIC_ARCANE.toWebHexString()).append(";'>Corruptive</span>)</div>");
                        } else {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_ARCANE.toWebHexString()).append(";'>Corruptive</span></div>");
                        }
                        boxHeight += 44;

                        if (response.getSexPace() != null) {
                            tooltipSB.append("<div class='subTitle'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Sex Pace:</span>").append(" <span style='color:").append(response.getSexPace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(response.getSexPace().getName())).append("</span></div>");
                            boxHeight += 44;
                        }

                        tooltipSB.append("<div class='description'>").append(response.getTooltipText()).append("</div>");

                    } else {
                        if (response instanceof ResponseSex) {
                            if (((ResponseSex) response).isPlayerInDominantSlot()) {
                                tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX_AS_DOM.toWebHexString()).append(";'>Dominant Sex</span>").append(" (<span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Unavailable</span>)</div>");
                            } else {
                                tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Submissive Sex</span>").append(" (<span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Unavailable</span>)</div>");
                            }
                        } else if (response.isCombatHighlight()) {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_COMBAT.toWebHexString()).append(";'>Combat</span> (<span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Unavailable</span>)</div>");
                        } else {
                            tooltipSB.append("<div class='title'><span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Unavailable</span></div>");
                        }
                        boxHeight += 44;

                        if (response.getSexPace() != null) {
                            tooltipSB.append("<div class='subTitle'><span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Sex Pace:</span>").append(" <span style='color:").append(response.getSexPace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(response.getSexPace().getName())).append("</span></div>");
                            boxHeight += 44;
                        }

                        tooltipSB.append("<div class='description'><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(response.getTooltipText()).append("</span></div>");
                    }

                    tooltipSB.append("<div class='description' style='height:").append((response.lineHeight() + 2) * 18).append("; text-align:center;'>").append("<b>Availability:</b>").append(response.getTooltipBlockingList()).append(response.getTooltipRequiredList()).append("</div>");

                    tooltipSB.append("<div class='description-small'>").append(response.getTooltipCorruptionBypassText()).append("</div>");

                    String extraSexInfo = response.getAdditionalSexActionInformationText();
                    if (!extraSexInfo.isEmpty()) {
                        tooltipSB.append("<div class='description-small'>").append(extraSexInfo).append("</div>");
                        boxHeight += 54;
                    }

                    boxHeight += 54;

                    boxHeight += 28 + ((response.lineHeight() + 1) * 18);
                }

                /* TODO
                 * Verify that there is no adverse effects to using this method to calculate the tooltip height,
                 * then remove all boxHeight calculations above, I guess, and apply this method to other
                 * tooltip types that could use this.
                 */

//				if(false) {
//					// for every response tooltip, print the height values
//					// very spammy
//					System.out.println("predicted: " + boxHeight);
//					System.out.println("measured:  " + realHeight);
//				}

                boxHeight = Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

                Main.mainController.setTooltipSize(360, boxHeight);

                double xPosition = ((MouseEvent) event).getScreenX() + 16 - 180;
                if (xPosition + 360 > Main.primaryStage.getX() + Main.primaryStage.getWidth() - 16)
                    xPosition = Main.primaryStage.getX() + Main.primaryStage.getWidth() - 360 - 16;

                double yPosition = Main.primaryStage.getY() + Main.primaryStage.getHeight() - (34 * (MainController.RESPONSE_COUNT / 5) + 4) - boxHeight
                        - (Main.mainScene.getWindow().getHeight() - Main.mainScene.getHeight() - Main.mainScene.getY());

                Main.mainController.getTooltip().setAnchorX(xPosition);
                Main.mainController.getTooltip().setAnchorY(yPosition);

                TooltipUpdateThread.updateToolTip(xPosition, yPosition);
            } else {
                Main.mainController.getTooltip().hide();
            }

        }
    }

    public TooltipResponseDescriptionEventListener setIndex(int index) {
        this.index = index;

        nextPage = false;
        previousPage = false;
        return this;
    }

    public TooltipResponseDescriptionEventListener nextPage() {
        nextPage = true;
        previousPage = false;

        return this;
    }

    public TooltipResponseDescriptionEventListener previousPage() {
        nextPage = false;
        previousPage = true;

        return this;
    }

}
