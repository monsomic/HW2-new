package bgu.spl.mics.application.messages;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.Event;


import java.util.List;

public class AttackEvent implements Event<Boolean> {

	private Attack atk;

    public AttackEvent(Attack a){
        atk=a;
    }

    public List<Integer> getSerials(){
        return atk.getSerials();
    }

    public int getDuration(){
        return atk.getDuration();
    }
}
