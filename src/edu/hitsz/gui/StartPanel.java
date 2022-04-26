package edu.hitsz.gui;

import edu.hitsz.application.ImageManager;
import edu.hitsz.tool.DIFFICULTY;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Huang
 */
public class StartPanel {
    public JPanel gameMenuPanel;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton difficultButton;
    private JCheckBox soundButton;
    private JPanel modePanel;
    private JPanel soundPanel;

    public boolean soundFlagOn = true;
    public DIFFICULTY difficulty = DIFFICULTY.EASY;

    public StartPanel() {
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                soundFlagOn = checkBox.isSelected();
                System.out.println("\033[32mSound: \033[0m" + soundFlagOn);
            }
        });
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = DIFFICULTY.EASY;
                System.out.println("\033[32mDifficulty: \033[0m" + difficulty);
                synchronized (gameMenuPanel) {
                    gameMenuPanel.notify();
                }
            }
        });
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = DIFFICULTY.MEDIUM;
                System.out.println("\033[32mDifficulty: \033[0m" + difficulty);
                synchronized (gameMenuPanel) {
                    gameMenuPanel.notify();
                }
            }
        });
        difficultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = DIFFICULTY.DIFFICULT;
                System.out.println("\033[32mDifficulty: \033[0m" + difficulty);
                synchronized (gameMenuPanel) {
                    gameMenuPanel.notify();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPanel");
        frame.setContentPane(new StartPanel().gameMenuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
