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
                ImageManager.bgImg = "src/images/bg.jpg";
                chooseBgImage();
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
                ImageManager.bgImg = ((int) (Math.random() * 2)) == 0 ?
                    "src/images/bg2.jpg" : "src/images/bg3.jpg";
                chooseBgImage();
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
                ImageManager.bgImg = ((int) (Math.random() * 2)) == 0 ?
                    "src/images/bg4.jpg" : "src/images/bg5.jpg";
                chooseBgImage();
                difficulty = DIFFICULTY.DIFFICULT;
                System.out.println("\033[32mDifficulty: \033[0m" + difficulty);
                synchronized (gameMenuPanel) {
                    gameMenuPanel.notify();
                }
            }
        });
    }

    private void chooseBgImage() {
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream(ImageManager.bgImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("\033[32mBackground: \033[0m" + ImageManager.bgImg);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPanel");
        frame.setContentPane(new StartPanel().gameMenuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
