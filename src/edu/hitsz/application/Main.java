package edu.hitsz.application;

import edu.hitsz.gui.EndPanel;
import edu.hitsz.gui.StartPanel;
import edu.hitsz.tool.DIFFICULTY;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        // 设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
            WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 开始游戏
        StartPanel startPanel = new StartPanel();
        frame.add(startPanel.gameMenuPanel);
        frame.setVisible(true);
        synchronized (startPanel.gameMenuPanel) {
            try {
                startPanel.gameMenuPanel.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 前置准备：进入游戏
        frame.setVisible(false);
        frame.remove(startPanel.gameMenuPanel);
        System.out.println("\n\033[32m=========WELCOME!=========\033[0m");

        // 开始游戏
        Game game = new Game(startPanel.soundFlagOn, startPanel.difficulty);
        frame.add(game);
        frame.setVisible(true);
        game.action();
        synchronized (JFrame.class) {
            try {
                JFrame.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.remove(game);
        frame.setVisible(false);

        // 游戏结束
        EndPanel endPanel = new EndPanel(game.getScore(), startPanel.difficulty);
        frame.setContentPane(endPanel.endPanel);
        frame.setVisible(true);
    }
}
