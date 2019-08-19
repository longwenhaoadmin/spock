import spock.lang.FailsWith
import spock.lang.Specification

class Send {
    List<Receive> Receives = []
    int messageCount = 0

    void send(String message) {
        Receives*.receive(message)
        messageCount++
    }
}

interface Receive {
    String receive(String message)
}



class StubSpock extends Specification {

    Send Send = new Send()
    //创建模拟对象
    def receive = Mock(Receive)
    Receive receive2 = Mock()

    def "stubbing介绍"(){

        given:
        receive.receive(_) >> "ok"

        and:    "不同的调用返回不同的值"
        receive2.receive("message1") >> "ok"
        receive2.receive("message2") >> "fail"

        expect: ""
        receive.receive("123") == "ok"
        receive2.receive("message1") == "ok"
        receive2.receive("message2") == "fail"
    }

    def "同一个方法返回不同的结果"(){
        receive.receive(_) >>> ["ok", "error", "error", "ok"]
        expect: ""
        receive.receive("1") == "ok"
        receive.receive("2") == "error"
        receive.receive("3") == "error"
        receive.receive("4") == "ok"
    }

    def "根据传入参数计算返回值"(){

        /*receive.receive(_) >> { args -> args[0].size() > 3 ? "ok" : "fail" }*/

        receive.receive(_) >> { String message -> message.size() > 3 ? "ok" : "fail" }

        expect: ""
        receive.receive("1234") == "ok"
        receive.receive("2") == "fail"
    }

    @FailsWith(InternalError)
    def "链式指定"(){
        receive.receive(_) >>> ["ok", "fail", "ok"] >> { throw new InternalError() } >> "ok"

        expect: ""
        receive.receive("1") == "ok"
        receive.receive("2") == "fail"
        receive.receive("3") == "ok"
        receive.receive("4")

    }

    def "结合mock和stub"(){

        2 * receive.receive("message1") >> "ok"
        1 * receive.receive("message2") >> "fail"

        expect: ""
        receive.receive("message1") == "ok"
        //receive.receive("message1") == "fail"
        receive.receive("message2") == "fail"

    }

    //
    Receive receive3 = Stub {
        receive("message1") >> "ok"
        receive("message2") >> "fail"
    }

    def "stub与mock的区别"(){

        expect: ""
        receive3.receive("message1") == "ok"

        when:   ""
        receive.receive("message2")

        then:   "mock方法模拟的对象可以用于stubbing和mocking"
        1*receive.receive("message2")

        when:   ""
        receive3.receive("message2")

        then:   "stub方法模拟的对象仅可用于stubbing"
        1*receive3.receive("message2")
    }

    def "stub与mock的区别-2"(){

        expect: "mock与stub在没指定下的默认值"
        verifyAll() {
            receive.receive("message3") == null  //mock对象默认返回null
            receive3.receive("message3") == "" //stub对象默认返回""
           /* stub对象对于基本类型，返回基元类型的默认值。

            对于非原始数值（例如BigDecimal），返回零。

            对于非数值，返回“空”或“虚拟”对象*/
        }
    }

}