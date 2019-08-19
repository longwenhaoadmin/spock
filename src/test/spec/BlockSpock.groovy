import spock.lang.Specification


class BlockSpock extends Specification {

   public "块介绍"() {
        given: "给定快,第一步：给定一个栈"
        def stack = new Stack()
        and:    "第二步：定义入栈资源"
        def elem = "push me"

        when: "操作快"
        stack.push(elem)
        //assert stack.empty

        then: "条件快"
        !stack.empty
        stack.size() == 1
        stack.peek() == elem
        and:    "判定不通过"
        //stack.size() == 2
    }

    def "抛异常"() {
        def stack = new Stack()

        when:
        stack.pop()

        then: "使用thrown()判定操作抛出异常"
        thrown(EmptyStackException)
        stack.empty


    }

    def "不抛异常"() {
        given:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:   "没有空指针异常，但是抛出其他异常也将失败"
        notThrown(NullPointerException)
    }

    def "期望块"() {

        when:
        def x = Math.max(1, 2)

        then:
        x == 2
        println "111"
        x < 1

        expect:
        Math.max(1, 2) == 2
    }

    def "清理块"() {
        given:
        def file = new File("D:\\123.txt")
        file.createNewFile()

        cleanup:
        file.delete()
    }

    def "where块"() {

        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }
}