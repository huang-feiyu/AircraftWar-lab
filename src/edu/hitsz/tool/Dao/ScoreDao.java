package edu.hitsz.tool.Dao;

import java.util.Date;
import java.util.List;

/**
 * @author Huang
 */
public interface ScoreDao {
    List<Score> findByName(String name);

    List<Score> getAllScores();

    void doAdd(Date time, int score, String name);

    void doAdd(Score score);

    void doDelete(Date time);
}
