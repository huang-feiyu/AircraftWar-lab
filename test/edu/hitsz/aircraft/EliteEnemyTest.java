package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {
    EliteEnemy elite;

    @BeforeAll
    static void beforeAll() {
        System.out.println("\033[32m**---EliteEnmeyTest Begin---**\033[0m");
    }

    @BeforeEach
    void setUp() {
        elite = new EliteEnemy(100, 100, 0, 30, 30);
        System.out.println("\n\033[34m---A new Elite Generated---\033[0m");
    }

    @AfterEach
    void tearDown() {
        elite = null;
    }

    @Test
    void shoot1() {
        List<BaseBullet> bullets = elite.shoot();

        System.out.println("Bullets Attribute Test");
        assertEquals(EnemyBullet.class, bullets.toArray()[0].getClass());
    }

    @Test
    void shoot2() {
        List<BaseBullet> bullets = elite.shoot();

        System.out.println("Bullets Num Test");
        assertEquals(bullets.size(), 1);
    }

    @Test
    void crash1() {
        // Elite: 105x68; HeroBullet: 9x18
        // => Elite(100±52.5, 100±17), HeroBullet(x±4.5,y±9)
        System.out.println("Crash center test");
        HeroBullet bullet = new HeroBullet(100, 100, 0, 0, 0);
        assertTrue(elite.crash(bullet));
    }

    @Test
    void crash2() {
        System.out.println("Crash x corner case, false");
        HeroBullet bullet = new HeroBullet(100, 100, 0, 0, 0);

        bullet.setLocation(43.5, 100);
        assertFalse(elite.crash(bullet));
        bullet.setLocation(157, 100);
        assertFalse(elite.crash(bullet));
    }

    @Test
    void crash3() {
        System.out.println("Crash x corner case, true");
        HeroBullet bullet = new HeroBullet(100, 100, 0, 0, 0);

        bullet.setLocation(44, 100);
        assertTrue(elite.crash(bullet));
        bullet.setLocation(156.5, 100);
        assertTrue(elite.crash(bullet));
    }

    @Test
    void crash4() {
        System.out.println("Crash y corner case, false");
        HeroBullet bullet = new HeroBullet(100, 100, 0, 0, 0);

        bullet.setLocation(100, 74);
        assertFalse(elite.crash(bullet));
        bullet.setLocation(100, 126);
        assertFalse(elite.crash(bullet));
    }

    @Test
    void crash5() {
        System.out.println("Crash y corner case, true");
        HeroBullet bullet = new HeroBullet(100, 100, 0, 0, 0);

        bullet.setLocation(100, 75);
        assertTrue(elite.crash(bullet));
        bullet.setLocation(100, 125);
        assertTrue(elite.crash(bullet));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("\n\033[32m**---EliteEnemyTest End---**\033[0m");
    }
}
