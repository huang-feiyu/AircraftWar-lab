package edu.hitsz.score;

import edu.hitsz.tool.DIFFICULTY;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static edu.hitsz.tool.DIFFICULTY.*;

/**
 * @author Huang
 */
public class ScoreDaoImpl implements ScoreDao {
    private List<Score> scores = new LinkedList<>();

    @Override
    public List<Score> findByName(String name) {
        List<Score> ans = new LinkedList<>();
        for (Score score : scores) {
            if (score.getScoreName().equals(name)) {
                ans.add(score);
            }
        }
        return ans;
    }

    @Override
    public List<Score> findByDifficulty(DIFFICULTY difficulty) {
        List<Score> ans = new LinkedList<>();
        for (Score score : scores) {
            if (score.getDifficulty() == difficulty) {
                ans.add(score);
            }
        }
        return ans;
    }

    @Override
    public List<Score> getAllScores() {
        return scores;
    }

    @Override
    public void doAdd(Date time, int score, String name, DIFFICULTY difficulty) {
        Score theScore = new Score(score, time, name, difficulty);
        scores.add(theScore);
    }

    @Override
    public void doAdd(Score theScore) {
        scores.add(theScore);
    }

    @Override
    public void doDelete(Date time) {
        System.out.println("doDelete(Date time)" + time);
        scores.removeIf(score -> score.getScoreTime().equals(time));
    }

    public void doDelete(Score score) {
        doDelete(score.getScoreTime());
    }

    public void doDelete(int row) {
        scores.remove(row);
    }

    public void printOut() {
        System.out.println("\n\033[34m==========Score Rank==========\033[0m");
        Collections.sort(scores);
        int count = 0;
        for (Score score : scores) {
            count++;
            System.out.println("\033[33mRANK:" + count + "\033[0m " + score);
        }
    }

    public Score parseString(String str) {
        String[] strings = str.split(" ");
        DIFFICULTY int2difficulty = EASY;
        switch (Integer.parseInt(strings[3])) {
            case 0: int2difficulty = EASY; break;
            case 1: int2difficulty = MEDIUM; break;
            case 2: int2difficulty = DIFFICULT; break;
            default:
        }
        return new Score(Integer.parseInt(strings[0]),
            new Date(Long.parseLong(strings[1])), strings[2], int2difficulty);
    }
}
