package edu.hitsz.tool.Dao;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Huang
 */
public class ScoreDaoImpl implements ScoreDao {
    private List<Score> scores = new LinkedList<>();

    @Override
    public List<Score> findByName(String name) {
        List<Score> ans = new LinkedList<>();
        for (Score score : scores) {
            if (score.getScoreTime().equals(name)) {
                ans.add(score);
            }
        }
        return ans;
    }

    @Override
    public List<Score> findByDifficulty(int difficulty) {
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
    public void doAdd(Date time, int score, String name, int difficulty) {
        Score theScore = new Score(score, time, name, difficulty);
        scores.add(theScore);
    }

    @Override
    public void doAdd(Score theScore) {
        scores.add(theScore);
    }

    @Override
    public void doDelete(Date time) {
        scores.removeIf(score -> score.getScoreTime().equals(time));
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
        return new Score(Integer.parseInt(strings[0]),
            new Date(Long.parseLong(strings[1])), strings[2], Integer.parseInt(strings[3]));
    }
}
