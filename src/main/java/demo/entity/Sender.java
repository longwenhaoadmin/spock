package demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Sender {

    public List<Receiver> receivers = new ArrayList<>();

    public void send(String txt){
        for (Receiver r:receivers
             ) {
            r.receive(txt);
        }
    }
}
