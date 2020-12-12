package test.java.bgu.spl.mics;

import static org.junit.jupiter.api.Assertions.*;

import main.java.bgu.spl.mics.*;
import main.java.bgu.spl.mics.MicroService;
import main.java.bgu.spl.mics.MessageBusImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class anaAref implements Event<Boolean> {
    private boolean b;

    public anaAref(){
        b=false;
    }
}

class eloimsheishmor implements Broadcast {
    private boolean a;

    public eloimsheishmor(){
        a=false;
    }
}

class stam extends MicroService {
    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public stam(String name) {
        super(name);
    }

    @Override
    protected void initialize() {
        this.subscribeBroadcast(eloimsheishmor.class, (callback)->{});
        this.subscribeEvent(anaAref.class, (callback)->{});
    }

    @Override
    protected void writeDiaryTerminate() {

    }
    public void sendB(Broadcast b){
        sendBroadcast(b);
    }
    public <T> Future<T> sendE(Event e){
        return sendEvent(e);
    }
    public <T> void comp(Event e, T result){
        complete(e,result);
    }
}


class MessageBusImplTest {

    private MessageBusImpl MBI;
    private anaAref e; //event
    private eloimsheishmor b; //broadcast

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MBI = MBI.getInstance();
        e = new anaAref();
        b = new eloimsheishmor();
    }

    @org.junit.jupiter.api.Test
    void complete() {
        // check if the future object has been updated correctly
        MicroService m1 = new stam("Nahum");
        MicroService m2 = new stam("Shelly");
        MBI.register(m1);
        MBI.register(m2);
        ((stam) m1).initialize();
        Future<Boolean> f = ((stam)m2).sendE(e);
        ((stam)m1).comp(e, true);
        assertTrue(f.get()); //expected true value
        MBI.unregister(m1);
        MBI.unregister(m2);
    }

    @Test
    void sendBroadcast() {
        //check that all microServices received the broadcast
        MicroService m1 = new stam("orel");
        MicroService m2 = new stam("bobby");
        MicroService m3 = new stam("gaul");
        MBI.register(m1);
        MBI.register(m2);
        MBI.register(m3);
        ((stam) m1).initialize();
        ((stam) m2).initialize();
       // m1.subscribeBroadcast(eloimsheishmor.class, (callback)->{});
       // m2.subscribeBroadcast(eloimsheishmor.class, (callback)->{});
        ((stam)m3).sendB(b);
        try {
            assertTrue(MBI.awaitMessage(m1).equals(b));
        }
        catch(InterruptedException nahum){System.out.println("not good");}

        try {
            assertTrue(MBI.awaitMessage(m2).equals(b));
        }
        catch(InterruptedException nahum){System.out.println("not good2");}

        MBI.unregister(m1);
        MBI.unregister(m2);
        MBI.unregister(m3);
    }

    @org.junit.jupiter.api.Test
    void sendEvent() {
        // send a message to the proper queue(check that it goes to the right queue)

        MicroService m1 = new stam("orel");
        MicroService m2 = new stam("bobby");
        MBI.register(m1);
        MBI.register(m2);
        ((stam)m2).initialize();
        Future<Boolean> f = ((stam)m1).sendE(e);
        try {
            assertTrue(MBI.awaitMessage(m2).equals(e));
        }
        catch(InterruptedException taco){System.out.println("not good3");}

        MBI.unregister(m1);
        MBI.unregister(m2);

    }


}