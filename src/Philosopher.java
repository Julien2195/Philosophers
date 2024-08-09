import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    public int id;
    public final Lock leftFork;
    public final Lock rightFork;
    public final int timeToEat;
    public final int timeToThink;
    public final int timeToSleep;

    public Philosopher(int id, Lock leftFork, Lock rightFork, int timeToEat, int timeToThink, int timeToSleep) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.timeToEat = timeToEat;
        this.timeToThink = timeToThink;
        this.timeToSleep = timeToSleep;
    }

    public void think() throws InterruptedException {
        System.out.println("le philosophr " + id + " pense");
        Thread.sleep(timeToThink);
    }

    public void eat() throws InterruptedException {
        System.out.println("le philosophr " + id + "mange");
        rightFork.lock();
        Thread.sleep(timeToEat);
        System.out.println("le philosopher " + id + " mannge la fork de gauche");
        rightFork.lock();
        System.out.println("le philosophr " + id + " mange la fork de droite");
    }

    public void sleep() throws InterruptedException {
        System.out.println(" le philosopher " +id + " dors");
        Thread.sleep(timeToSleep);
    }

        public void die() {
            System.out.println("Le philosopher " + id + " est mort");
            interrupt();
        }

    public void runCycle() {
        while (isAlive()) {
            try {
                eat();
                think();
                sleep();
            } catch (InterruptedException e) {
                die();
                System.out.println("Philosopher " + id + " est intrompu.");
            }
        }
    }
    public void run() {
        runCycle();
    }
}

class DiningPhilosopher {
    public static void main(String[] args) {
        int numberPhilosopher = 5;
        Philosopher objectPhilosopher[] = new Philosopher[numberPhilosopher];
        Lock[] forks = new ReentrantLock[numberPhilosopher];
        for (int i = 0; i < objectPhilosopher.length; i++) {
            forks[i] = new ReentrantLock();
        }
        for (int i = 0; i < objectPhilosopher.length; i++) {
           Lock leftFork = forks[i];
           Lock rightFork = forks[i + 1];
            objectPhilosopher[i] = new Philosopher(i+1, leftFork, rightFork, 200, 200, 200);
            objectPhilosopher[i].start();
        }
    }
}