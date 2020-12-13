package bgu.spl.mics.application.passiveObjects;


import java.util.Iterator;
import java.util.List;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private Ewok[] ewokList;
    private static Ewoks instance =null;
    private Object lock;

    private Ewoks(int num){
        ewokList= new Ewok[num+1];
        for(int i=1;i<=num;i++){
            ewokList[i]=new Ewok(i);
        }
        lock = new Object();
    }

    public synchronized static Ewoks getInstance(int num) {  //find a way to initializr without num
        if (instance == null)
            instance = new Ewoks(num);
        return instance;
    }

    public synchronized static Ewoks getInstance() {
        return instance;
    }

    public synchronized void recruit(List<Integer> serials){ // this is a blocking method

            boolean ok = false;
            while (!ok) {
                boolean stop = false;
                Iterator<Integer> it = serials.listIterator();
                int curr = 0;
                while (!stop && it.hasNext()) {
                    curr = it.next();
                    if (!ewokList[curr].isAvailable())
                        stop = true;
                }
                if (!stop) {
                    ok = true;
                    System.out.println("ok= true");
                }
                if (!ok) {
                    System.out.println("ok= false");
                    try {
                        System.out.println("waiting");
                        this.wait();
                        System.out.println("woke up");
                    } catch (InterruptedException e) {
                        System.out.println("intrupted and is good");
                    }
                    ;
                }
            }
            Iterator<Integer> it2 = serials.listIterator();
            int curr = it2.next();
            while (it2.hasNext()) {
                instance.ewokList[curr].acquire();
                curr = it2.next();
            }

    }
    public  void discharge(List<Integer> serials){ // needs synchronized??
            Iterator<Integer> it = serials.listIterator();
            int curr = it.next();
            while (it.hasNext()) {
                instance.ewokList[curr].release();
                curr = it.next();
            }
            System.out.println("discharged");
        synchronized(this) {
            notifyAll();
        }
    }


}
