package com.lilithslegacy.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Objects;

public class Loading {
    private final JWindow window;

    private Loading() {
        window = new JWindow();
        window.setAlwaysOnTop(true);
        window.setBackground(new Color(0, 0, 0, 0));
        window.setLayout(new BorderLayout());
        Container pane = window.getContentPane();
        JLabel label = new JLabel(new ImageIcon(Objects.requireNonNull(Loading.class.getResource("/com/lilithslegacy/main/loading.png"))));
        label.setBackground(new Color(0, 0, 0, 0));
        label.setOpaque(true);
        pane.add(label, BorderLayout.CENTER);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static Loading create() {
        return new Loading();
    }

    public void hide() {
        window.dispose();
    }

    public void notifyProgress(double percent) {
        window.setOpacity((float) (percent < 0.5 ? percent : 1 - percent));
    }

}
