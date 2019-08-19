import spock.lang.*

class AnnotationSpock extends Specification {

    def "不带注解"(){
        expect: ""
        1 == 1
    }

    @Timeout(value = 1)
    def "Timeout注解"(){
        given:  "方法超时将失败"
        Thread.sleep(2000)
    }

    @Ignore
    def "Ignore注解"(){
        expect: "忽略该方法"
        1 == 1
    }

    //@IgnoreRest
    def "IgnoreRest注解"(){
        expect: "忽略其他方法，只执行该方法"
        1 == 1
    }

    @FailsWith(NullPointerException)
    def "FailsWith注解"() throws Exception{
        given:
        throw new NullPointerException();
    }


}