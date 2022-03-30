package edu.hitsz.bullet;

import edu.hitsz.aircraft.MobEnemy;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HeroBulletTest {
    HeroBullet bullet;

    @BeforeAll
    static void beforeAll() {
        System.out.println("\033[32m**---HeroBulletTest Begin---**\033[0m");
    }

    @BeforeEach
    void setUp() {
        bullet = new HeroBullet(100, 100, 0, 30, 5);
        System.out.println("\n\033[34m---A new bullet Generated---\033[0m");
    }

    @AfterEach
    void tearDown() {
        bullet = null;
    }

    @Test
    void forward1() {
        System.out.println("Forward 1 time");
        assertEquals(100, bullet.getLocationY()); // init
        bullet.forward(); // speedY=30
        assertEquals(130, bullet.getLocationY()); // single
    }

    @Test
    void forward2() {
        System.out.println("Forward 22 time");
        assertEquals(100, bullet.getLocationY()); // init
        for (int i = 0; i < 22; i++) {
            // Height = 768
            bullet.forward();
        }
        assertEquals(760, bullet.getLocationY()); // corner case
    }

    @Test
    void forward3() {
        System.out.println("Forward 23 time");
        assertEquals(100, bullet.getLocationY()); // init
        for (int i = 0; i < 23; i++) {
            // Height = 768
            bullet.forward();
        }
        assertTrue(bullet.notValid());
    }

    @Test
    void getPower1() {
        System.out.println("getPower Test");
        assertEquals(5, bullet.getPower());
    }

    @AfterAll
    static void afterAll() {
        System.out.println("\n\033[32m**---HeroBulletTest End---**\033[0m");
    }
}
