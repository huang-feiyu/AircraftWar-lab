package edu.hitsz.score;

import edu.hitsz.tool.DIFFICULTY;

import java.util.Date;

/**
 * @author Huang
 */
public class Score implements Comparable<Score> {
    private int score;
    private Date scoreTime;
    private String userName;
    private DIFFICULTY difficulty;

    Score(int score, Date time, String name, DIFFICULTY difficulty) {
        this.score = score;
        this.scoreTime = time;
        this.userName = name;
        this.difficulty = difficulty; // 0->Easy, 1->Medium, 2->difficult
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public String getScoreName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    public DIFFICULTY getDifficulty() {
        return difficulty;
    }

    public String printOut() {
        int difficulty2int = 0;
        switch (difficulty) {
            case EASY: difficulty2int = 0; break;
            case MEDIUM: difficulty2int = 1; break;
            case DIFFICULT: difficulty2int = 2; break;
            default:
        }
        // 打印到文件中
        return score + " " + scoreTime.getTime() + " " + userName + " " + difficulty2int +"\n";
    }

    @Override
    public String toString() {
        // 打印到控制台&Table
        return userName + ";" + score + ";" + scoreTime.toString() + ";" + getDifficultyName();
    }

    private String getDifficultyName() {
        switch (difficulty) {
            case EASY:
                return "\033[35mEASY\033[0m";
            case MEDIUM:
                return "\033[35mMEDIUM\033[0m";
            case DIFFICULT:
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
