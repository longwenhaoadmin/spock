package demo.entity;

public class Receiver {

    public String receive(String txt){
        System.out.println("接收:"+txt);
        return "ok";
    }
}
