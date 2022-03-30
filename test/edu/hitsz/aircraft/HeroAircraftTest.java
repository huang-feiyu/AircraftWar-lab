package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft hero;

    @BeforeAll
    static void beforeAll() {
        System.out.println("\033[32m**---HeroAircraftTest Begin---**\033[0m");
    }

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
        System.out.println("\n\033[34m---A new Hero Generated---\033[0m");
    }

    @AfterEach
    void tearDown() {
        hero = null;
    }

    @Test
    void getInstance1() {
        System.out.println("Singleton Pattern Test");
        assertEquals(hero, HeroAircraft.getInstance());
    }

    @Test
    void testShoot1() {
        List<BaseBullet> bullets = hero.shoot();
        System.out.println("Bullets Attribute Test");
        assertEquals(HeroBullet.class, bullets.toArray()[0].getClass());
    }

    @Test
    void testShoot2() {
        List<BaseBullet> bullets = hero.shoot();
        System.out.println("Bullets Num Test");
        assertEquals(bullets.size(), 5);
    }


    @Test
    void decreaseHp1() {
        System.out.println("decreaseHp maxHp upper bound");
        hero.decreaseHp(-100); // maxHp
        assertEquals(hero.getMaxHp(), hero.getHp());

    }

    @Test
    void decreaseHp2() {
        System.out.println("decreaseHp implement correctness");
        hero.decreaseHp(10);
        assertEquals(hero.getMaxHp() - 10, hero.getHp());
    }

    @Test
    void decreaseHp3() {
        System.out.println("decrease vanish() caller test");
        hero.decreaseHp(hero.getMaxHp());
        assertTrue(hero.notValid());
    }

    @AfterAll
    static void afterAll() {
        System.out.println("\n\033[32m**---HeroAircraftTest End---**\033[0m");
    }
}
