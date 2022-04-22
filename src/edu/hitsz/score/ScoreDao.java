package edu.hitsz.score;

import edu.hitsz.tool.DIFFICULTY;

import java.util.Date;
import java.util.List;

/**
 * @author Huang
 */
public interface ScoreDao {
    List<Score> getAllScores();

    List<Score> findByName(String name);

    List<Score> findByDifficulty(DIFFICULTY difficulty);

    void doAdd(Date time, int score, String name, DIFFICULTY difficulty);

    void doAdd(Score score);

    void doDelete(Date time);
}
