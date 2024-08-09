import java.util.concurrent.Semaphore;

public class Philosopher2 extends Thread {
    public int id;
    public final Semaphore forks;
    public final int timeToEat;
    public final int timeToThink;
    public final int timeToSleep;
    public long lastMealTime;

    public Philosopher2(int id, Semaphore forks, int timeToEat, int timeToThink, int timeToSleep) {
        this.id = id;
        this.forks = forks;
        this.timeToEat = timeToEat;
        this.timeToThink = timeToThink;
        this.timeToSleep = timeToSleep;
        this.lastMealTime = System.currentTimeMillis();
    }

    public void run() {
        runCycle();
    }

    public void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep(timeToThink);
    }

    public void eat() throws InterruptedException {
        forks.acquire(2);
        try {
            System.out.println("Philosopher " + id + " mange");
            Thread.sleep(timeToEat);
            lastMealTime = System.currentTimeMillis();
        } finally {
            forks.release(2);
        }
    }

    public void sleep() throws InterruptedException {
        System.out.println("Philosopher " + id + " dort");
        Thread.sleep(timeToSleep);
    }

    public void die() {
        System.out.println("Philosopher " + id + " est mort");
        interrupt();
    }

    public void runCycle() {
        while (true) {
            try {
                if (System.currentTimeMillis() - lastMealTime > 200) {
                    die();
                    break;
                }
                eat();
                think();
                sleep();
            } catch (InterruptedException e) {
                die();
                break;
            }
        }
    }
}

class DiningPhilosopher2 {
    public static void main(String[] args) {
        int numberPhilosopher = 5;
        Semaphore forks = new Semaphore(numberPhilosopher);
        Philosopher2[] philosophers = new Philosopher2[numberPhilosopher];

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher2(i + 1, forks, 200, 200, 200 );
            philosophers[i].start();
        }
    }
}
