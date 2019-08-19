import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class DataDriverSpock extends Specification {


    def "数据表"() {
        expect:
        Math.max(a, b) == c

        where: "隔离性：每一列数据测试前都会执行setup和cleanup方法"
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }


    def "数据管道"() {
        expect:
        Math.max(a, b) == c

        where: "数据表只是一个或多个数据管道的语法糖"
        a << [1, 7, 0]
        b << [3, 4, 0]
        c << [3, 7, 0]
    }

    def "单列表"() {
        expect:
        a instanceof Integer

        where: "数据表必须至少有两列。单列表可以写成："
        a | _
        1 | _
        7 | _
        0 | _
    }

    @Shared
            sql = Sql.newInstance("jdbc:mysql://localhost:3306/demo", "root", "")

    @Unroll //每一行数据将会作为一个单独的方法执行
    def "使用外部数据源"() {
        expect:
        Math.max(a, b) == c

        where: "多变量数据管道"
        [a, b, c] << sql.rows("select a, b, c from maxdata")

        /* where:
         row << sql.rows("select * from maxdata")
         a = row.a
         b = row.b
         c = row.c*/
    }

    def "数据变量可以直接赋值"() {

        expect:
        Math.max(a, b) == c

        where:
        a = 3
        b = Math.random() * 100
        c = a > b ? a : b
    }

    def "组合数据表，数据管道和变量直接赋值"() {

        expect:
        Math.max(a, b) == c

        where:
        a | _
        3 | _
        7 | _
        0 | _

        b << [5, 0, 0]

        c = a > b ? a : b
    }
}