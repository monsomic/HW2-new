package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Ewok;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EwokTest {
    private Ewok e;
    private Future future;

    @BeforeEach
     void setUpFuture(){
        future = new Future<>();
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUpEwoks() { e = new Ewok(1); }

    @org.junit.jupiter.api.Test
    void acquire() {
        e.acquire();
        assertTrue(!e.isAvailable());
    }

    @org.junit.jupiter.api.Test
    void release() {
        e.release();
        assertTrue(e.isAvailable());
    }

}

