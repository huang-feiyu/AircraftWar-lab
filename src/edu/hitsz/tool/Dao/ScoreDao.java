package edu.hitsz.tool.Dao;

import java.util.Date;
import java.util.List;

/**
 * @author Huang
 */
public interface ScoreDao {
    List<Score> getAllScores();

    List<Score> findByName(String name);

    List<Score> findByDifficulty(int difficulty);

    void doAdd(Date time, int score, String name, int difficulty);

    void doAdd(Score score);

    void doDelete(Date time);
}
