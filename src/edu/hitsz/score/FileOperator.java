package edu.hitsz.score;

import java.io.*;

/**
 * @author Huang
 */
public class FileOperator {
    public ScoreDaoImpl scoreDao;
    private String path = "./prop/Scores.txt";

    public FileOperator() {
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
