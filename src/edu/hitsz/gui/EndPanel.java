package edu.hitsz.gui;

import edu.hitsz.score.FileOperator;
import edu.hitsz.score.Score;
import edu.hitsz.tool.DIFFICULTY;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Huang
 */
public class EndPanel {
    public JPanel endPanel;
    private JTable scoreTable;
    private JScrollPane scrollPane;
    private JLabel rankLabel;
    private JButton deleteButton;
    private JPanel deletePanel;
    private JPanel tablePanel;
    private JLabel difficultyLabel;

    private FileOperator fileOperator;
    private DIFFICULTY difficulty;

    public EndPanel(int score, DIFFICULTY difficulty) {
        difficultyLabel.setText(difficulty.toString());
        fileOperator = new FileOperator();
        this.difficulty = difficulty;

        // 新建Score记录并插入
        String name = JOptionPane.showInputDialog(endPanel,
            "Game Over, Score:" + score + "\nEnter your name: ");
        fileOperator.scoreDao.doAdd(new Date(), score, name, difficulty);
        fileOperator.scoreDao.printOut();
        fileOperator.writeFile();
        representTable();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = scoreTable.getSelectedRows();
                Arrays.sort(rows);
                for (int row : rows) {
                    // time 为唯一标识
                    fileOperator.scoreDao.doDelete(row);
                    System.out.println("\033[31mDelete\033[0m:" + (row + 1));
                }
                System.out.println("\033[31mDelete END\033[0m");
                fileOperator.writeFile();
                representTable();
            }
        });
    }

    private void representTable() {
        // 表格数据
        List<Score> tableData = fileOperator.scoreDao.findByDifficulty(difficulty);
        String[][] data = new String[tableData.size()][0];
        for (int i = 0; i < data.length; i++) {
            data[i] = new String[4];
            String[] temp = tableData.get(i).toString().split(";");
            data[i][0] = Integer.toString(i + 1);
            data[i][1] = temp[0]; data[i][2] = temp[1];
            String[] dateStrs = temp[2].split(" ");
            data[i][3] = dateStrs[1] + " " + dateStrs[2] + " " + dateStrs[3];
        }

        // 表格模型
        String[] columnName = {"rank", "name", "score", "time"};
        DefaultTableModel model = new DefaultTableModel(data, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        // 从表格模型那里获取数据
        scoreTable.setModel(model);
        scrollPane.setViewportView(scoreTable);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EndPanel");
        frame.setContentPane(new EndPanel(100, DIFFICULTY.EASY).endPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
