package main.java.bgu.spl.mics.application.services;

import main.java.bgu.spl.mics.MicroService;
import main.java.bgu.spl.mics.application.messages.BombDestroyerEvent;
import main.java.bgu.spl.mics.application.messages.DestroyPlanetBroadcast;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration=duration;
    }

    @Override
    protected void initialize() {
        subscribeEvent(BombDestroyerEvent.class,(BombDestroyerEvent b)->{
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Lando, lando lando");
            complete(b,true);

        });

        subscribeBroadcast(DestroyPlanetBroadcast.class, (DestroyPlanetBroadcast d) -> {
            terminate();
        });
    }

    protected void writeDiaryTerminate() {
        diary.setLandoTerminate(System.currentTimeMillis());
    }
}
