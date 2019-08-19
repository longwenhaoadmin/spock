import spock.lang.Specification

class shop{
    def static buyPc(){
        //return 关键字在Groovy中是可选的。
        new pc("Sunny",2333,4096,"Linux");
    }
}

class pc{
    def vendor
    def clockRate
    def ram
    def os

    def pc(vendor,clockRate,ram,os){
        this.vendor = vendor
        this.clockRate = clockRate
        this.ram = ram
        this.os = os
    }
}

class OtherMethodSpock extends Specification {

    def "辅助方法"() {
        def pc
        when:
        pc = shop.buyPc()

        then:   "一般写法"
        pc.vendor == "Sunny"
        pc.clockRate >= 2333
        pc.ram >= 4096
        pc.os == "Linux"

        when:
        pc = shop.buyPc()

        then:   "使用with辅助方法"
        with(pc) {
            vendor == "Sunny"
            clockRate >= 2333
            ram >= 4096
            os == "Linux"
        }

        when:
        pc = shop.buyPc()

        then:   "使用verifyAll辅助方法（软断言，所有验证都将被执行）"
        verifyAll() {
            pc.vendor == "Sunny"
            pc.clockRate >= 2333
            pc.ram >= 4096
            pc.os == "Linux"
            1 == 1
        }
    }

}

