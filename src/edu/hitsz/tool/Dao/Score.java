package edu.hitsz.tool.Dao;

import java.util.Date;

/**
 * @author Huang
 */
public class Score implements Comparable<Score> {
    private int score;
    private Date scoreTime;
    private String userName;
    private int difficulty;

    Score(int score, Date time, String name, int difficulty) {
        this.score = score;
        this.scoreTime = time;
        this.userName = name;
        this.difficulty = difficulty; // 0->Easy, 1->Medium, 2->difficult
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public int getScore() {
        return score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String printOut() {
        // 打印到文件中
        return score + " " + scoreTime.getTime() + " " + userName + " " + difficulty +"\n";
    }

    @Override
    public String toString() {
        // 打印到控制台
        return "\033[32m" + userName + "\033[0m" + " \033[31m" + score + "\033[0m "
            + scoreTime.toString() + " " + getDifficultyName();
    }

    private String getDifficultyName() {
        switch (difficulty) {
            case 0:
                return "\033[35mEASY\033[0m";
            case 1:
                return "\033[35mMEDIUM\033[0m";
            case 2:
                return "\033[35mDIFFICULT\033[0m";
            default:
                return "";
        }
    }

    @Override
    public int compareTo(Score that) {
        if (this.score > that.score) {
            return -1;
        } else if (this.score == that.score) {
            return 0;
        }
        return 1;
    }
}
