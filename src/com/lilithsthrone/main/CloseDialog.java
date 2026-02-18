package com.lilithsthrone.main;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class CloseDialog {
    
    private static final String TITLE = "Exit game";
    private static final String HEADER_TEXT = "You're about to exit game.\n" +
            "Please make sure you've mad a save or you'll lose your progress!\n" +
            "So think again.";
    private static final String CONTENT_TEXT = "You can save before exiting or return to game and save manually";
    
    private static final String BUTTON_STYLE = "-fx-padding: 0.5em";
    
    public enum CloseChoice {
        RESUME(
                () -> Boolean.TRUE,
                "Return to game",
                false,
                ButtonBar.ButtonData.OK_DONE,
                () -> {
                },
                "-fx-font-size: 1.5em;-fx-text-fill: green;"),
        QUICK(
                () -> Main.isQuickSaveAvailable() && Main.game.isInNewWorld(),
                "Quicksave & EXIT",
                true,
                ButtonBar.ButtonData.NEXT_FORWARD,
                Main::quickSaveGame,
                "-fx-text-fill: blue;"),
        AUTO(
                () -> Main.isQuickSaveAvailable() && Main.game.isInNewWorld(),
                "Autosave & EXIT",
                true,
                ButtonBar.ButtonData.FINISH,
                () -> Main.saveGame("AutoSave_" + Main.game.getPlayer().getName(false), true, true),
                "-fx-text-fill: blue;"),
        EXIT(
                () -> Boolean.TRUE,
                "EXIT WITHOUT SAVE",
                true,
                ButtonBar.ButtonData.APPLY,
                () -> {
                },
                "-fx-font-size: 1.5em;-fx-text-fill: red;"),
        ;
        private final Supplier<Boolean> available;
        private final String label;
        private final boolean isExit;
        private final ButtonType buttonType;
        private final Runnable operation;
        private final String style;
        
        CloseChoice(Supplier<Boolean> available, String label, boolean isExit, ButtonBar.ButtonData buttonData, Runnable operation, String style) {
            this.available = available;
            this.label = label;
            this.isExit = isExit;
            this.buttonType = new ButtonType(label, buttonData);
            this.operation = operation;
            this.style = style;
        }
        
        public String getLabel() {
            return label;
        }
        
        public boolean isExit() {
            return isExit;
        }
        
        public Runnable getOperation() {
            return operation;
        }
        
        public Supplier<Boolean> getAvailable() {
            return available;
        }
        
        public String getStyle() {
            return style;
        }
        
        ButtonType getButtonType() {
            return buttonType;
        }
        
        static Optional<CloseChoice> findChoice(ButtonType param) {
            return Arrays.stream(CloseChoice.values()).filter(it -> it.buttonType == param).findFirst();
        }
    }
    
    private CloseDialog() {
    }
    
    static boolean showDialog() {
        if (Main.game == null || !Main.game.isStarted()) return true;
        
        if (Main.game.getSecondsPassed() == 0L) return true;
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(Main.primaryStage);
        alert.setTitle(TITLE);
        alert.setHeaderText(HEADER_TEXT);
        alert.setContentText(CONTENT_TEXT);
        
        alert.getButtonTypes().setAll(
                Arrays.stream(CloseChoice.values()).map(CloseChoice::getButtonType).collect(Collectors.toList())
        );
        Arrays.stream(CloseChoice.values()).forEach(it ->
                alert.getDialogPane().lookupButton(it.getButtonType()).setStyle(BUTTON_STYLE + ";" + it.getStyle()));
        
        Optional<CloseChoice> closeChoice = alert.showAndWait()
                .map((it) -> CloseChoice.findChoice(it).orElse(CloseChoice.RESUME));
        
        closeChoice.ifPresent(it -> it.getOperation().run());
        return closeChoice.orElse(CloseChoice.RESUME).isExit();
    }
}
