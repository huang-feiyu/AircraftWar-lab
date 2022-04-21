package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;
import edu.hitsz.tool.factory.enemy.BossFactory;
import edu.hitsz.tool.factory.enemy.EliteFactory;
import edu.hitsz.tool.factory.enemy.MobFactory;
import edu.hitsz.tool.factory.prop.BloodFactory;
import edu.hitsz.tool.factory.prop.BombFactory;
import edu.hitsz.tool.factory.prop.BulletFactory;
import edu.hitsz.tool.strategy.BossShoot;
import edu.hitsz.tool.strategy.BulletContext;
import edu.hitsz.tool.strategy.ScatterShoot;
import edu.hitsz.tool.strategy.StraightShoot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    final private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;
    private final BulletContext heroBulletContext;
    private final Stack<Integer> bulletPropTimeStack;

    final private int enemyMaxNumber = 5;
    final private int bossScoreThreshold = 500; // boss生成阈值
    final private int bulletPropDuration = 1200; // 道具生效时间

    private boolean gameOverFlag = false;
    private boolean musicOnFlag = true;
    private int bossOnCount = 0;
    private int count = 0;
    private int score = 0;
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    final private int cycleDuration = 600;
    private int cycleTime = 0;

    private final FileOperator fileOperator;
    private final MusicManager musicManager;

    public Game() {
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        bulletPropTimeStack = new Stack<>();
        heroBulletContext = new BulletContext(new StraightShoot());
        fileOperator = new FileOperator();
        musicManager = new MusicManager(musicOnFlag);

        // Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(4);

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println("\033[32mTIME:\033[0m " + time);
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    enemyAircrafts.addAll(generateEnemy());
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            // 每个时刻重绘界面
            repaint();

            // 游戏结束检查
            gameOverCheckAction();
        };

        // 以固定延迟时间进行执行。本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(() -> {
            for (int t : bulletPropTimeStack) {
                if (time - t >= bulletPropDuration) {
                    bulletPropTimeStack.pop();
                    heroAircraft.increaseShootNum(-2);
                    heroBulletContext.setStrategy(new StraightShoot());
                    System.out.println("\033[96mPROP:\033[0mBulletSupply \033[31munactive\033[0m");
                    if (bulletPropTimeStack.empty()) {
                        break;
                    }
                }}}, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // 敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            // only elite can shoot now
            if (enemyAircraft instanceof EliteEnemy) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
            if (enemyAircraft instanceof BossEnemy) {
                enemyBullets.addAll(
                    new BossShoot().ballisticChange(enemyAircraft.shoot()));
            }
        }

        // 英雄射击
        heroBullets.addAll(heroBulletContext.executeStrategy(heroAircraft.shoot()));
        musicManager.bullet();
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    musicManager.bulletHit();
                    if (enemyAircraft.notValid()) {
                        increaseScore(enemyAircraft);
                        if (enemyAircraft.getClass() != MobEnemy.class) {
                            generateProp(enemyAircraft);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 相撞，我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.crash(heroAircraft) || heroAircraft.crash(prop)) {
                applyProp(prop);
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * 4. 删除无效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    private void gameOverCheckAction() {
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            executorService.shutdown();
            gameOverFlag = true;
            System.out.println("\033[31mGame Over!\033[0m");
            fileOperator.scoreDao.doAdd(new Date(), this.score, "Test", 0);
            fileOperator.scoreDao.printOut();
            fileOperator.writeFile();
            musicManager.gameOver();
        }
    }


    //***********************
    //      Paint 各部分
    //***********************/

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 绘制道具
        paintImageWithPositionRevised(g, props);

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
            heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    //************************
    //       子函数
    //************************
    /**
     * 生成敌机
     */
    private List<AbstractAircraft> generateEnemy() {
        List<AbstractAircraft> enemies = new ArrayList<>();
        enemies.add(((int) (Math.random() * 2) == 0 ?
            new EliteFactory().createEnemy() :
            new MobFactory().createEnemy()));
        if (this.score / bossScoreThreshold > count) {
            enemies.add(new BossFactory().createEnemy());
            count++;
            bossOnCount++;
        }
        if (bossOnCount > 0) {
            musicManager.bgmBoss();
        } else {
            musicManager.bgm();
        }
        return enemies;
    }

    /**
     * 生成道具
     */
    private void generateProp(AbstractAircraft enemyAircraft) {
        int x = enemyAircraft.getLocationX();
        int y = enemyAircraft.getLocationY();
        switch ((int) (Math.random() * 10 + 1)) {
            case 1:
                // blood
                props.add(new BloodFactory().createProp(x, y));
                break;
            case 2:
                // bomb
                props.add(new BombFactory().createProp(x, y));
                break;
            case 3:
                // FireSupply
                props.add(new BulletFactory().createProp(x, y));
            default: // do nothing
        }
    }

    /**
     * 道具产生效果
     */
    private void applyProp(AbstractProp prop) {
        String propPrompt = "\033[96mPROP:\033[0m";
        if (prop instanceof BloodProp) {
            heroAircraft.decreaseHp(-((BloodProp) prop).getBlood());
            System.out.println(propPrompt + "BloodSupply active");
        } else if (prop instanceof BombProp) {
            System.out.println(propPrompt + "BombSupply active");
            musicManager.bomb();
        } else if (prop instanceof BulletProp) {
            heroAircraft.increaseShootNum(2);
            bulletPropTimeStack.push(time);
            heroBulletContext.setStrategy(new ScatterShoot());
            System.out.println(propPrompt + "BulletSupply active");
        }
        musicManager.getSupply();
    }

    /**
     * 干掉敌机，增加分数
     */
    private void increaseScore(AbstractAircraft enemyAircraft) {
        int ans = 0;
        if (MobEnemy.class.equals(enemyAircraft.getClass())) {
            ans = 10;
        } else if (EliteEnemy.class.equals(enemyAircraft.getClass())) {
            ans = 20;
        } else if (BossEnemy.class.equals(enemyAircraft.getClass())) {
            ans = 100;
            bossOnCount--;
        }
        this.score += ans;
    }

}
