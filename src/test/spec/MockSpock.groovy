import spock.lang.Specification

class Publisher {
    List<Subscriber> subscribers = []
    int messageCount = 0

    void send(String message) {
        subscribers*.receive(message)
        messageCount++
    }
}

interface Subscriber {
    void receive(String message)
}


class InteractionBasedSpock extends Specification {

    Publisher publisher = new Publisher()
    //创建模拟对象
    def subscriber = Mock(Subscriber)
    //左侧给出类型，右侧可以不用传入参数
    Subscriber subscriber2 = Mock()

    def setup() {
        publisher.subscribers << subscriber // <<  Groovy中List.add()的简洁方式
        publisher.subscribers << subscriber2
    }

    def "发送消息给所有的接收者"() {
        when:
        publisher.send("hello")

        then:   "验证接收者的receive方法都被调用"
        1 * subscriber.receive("hello")
        1 * subscriber2.receive("hello")
    }

    def "要素介绍"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive(*_)
        1 * subscriber2.receive("hello1")

       /* and:    "基数"
        1 * subscriber.receive("hello")      // 只调用一次
        0 * subscriber.receive("hello")      // 没有调用
        (1..3) * subscriber.receive("hello") // 调用次数介于1-3次
        (1.._) * subscriber.receive("hello") // 至少一次调用
        (_..3) * subscriber.receive("hello") // 最多三次调用
        _ * subscriber.receive("hello")      // 任意次数调用

        and:    "目标约束"
        1 * subscriber.receive("hello")  // 目标为subscriber的一次调用
        1 * _.receive("hello")          // 目标为任意mock对象的一次调用

        and:    "方法约束"
        1 * subscriber.receive("hello") // 调用方法名为'receive'的方法
        1 * subscriber./r.*e/("hello")  // 调用匹配正则表达式的方法（这里是匹配方法名以'r'开头，以'e'结尾）

        and:    "参数约束"
        1 * subscriber.receive("hello")     // 传入参数为'hello'的该方法
        1 * subscriber.receive(!"hello")    // 传入参数不是'hello'的该方法
        1 * subscriber.receive()            // 无参的该方法
        1 * subscriber.receive(_)           // 任意单参数的该方法 (包括'null')
        1 * subscriber.receive(*_)          // 任意个参数的该方法 (包括无参)
        1 * subscriber.receive(!null)       // 参数不为'null'
        1 * subscriber.receive(_ as String) // 参数不为'null'且参数类型为'String'
        1 * subscriber.receive({ it.size() > 3 && it.contains('a') })
        // 参数需要满足指定的条件
        // {}里需要返回true或者false
        // 取决于它们是否匹配
        // （这里是指参数的size需要>3且该参数需要包含a）*/
    }

}