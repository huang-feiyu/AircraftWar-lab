package edu.hitsz.application;

import edu.hitsz.tool.Dao.Score;
import edu.hitsz.tool.Dao.ScoreDaoImpl;

import java.io.*;
import java.util.Date;

/**
 * @author Huang
 */
public class FileOperator {
    protected ScoreDaoImpl scoreDao;
    private String path = "./prop/Scores.txt";

    FileOperator() {
        scoreDao = new ScoreDaoImpl();
        readFile();
    }

    private void readFile() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(this.path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                this.scoreDao.doAdd(scoreDao.parseString(str));
            }
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            for (Score score : scoreDao.getAllScores()) {
                out.write(score.printOut());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileOperator file = new FileOperator();
        System.out.println(file.scoreDao.getAllScores());
    }
}
