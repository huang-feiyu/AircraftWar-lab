package edu.hitsz.tool.Dao;

import java.util.Date;

/**
 * @author Huang
 */
public class Score implements Comparable<Score> {
    private int score;
    private Date scoreTime;
    private String userName;

    Score(int score, Date time, String name) {
        this.score = score;
        this.scoreTime = time;
        this.userName = name;
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public int getScore() {
        return score;
    }

    public String printOut() {
        // 打印到文件中
        return score + " " + scoreTime.getTime() + " " + userName +"\n";
    }

    @Override
    public String toString() {
        // 打印到控制台
        return "\033[32m" + userName + "\033[0m" +
            " \033[31m" + score + "\033[0m " +scoreTime.toString();
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
