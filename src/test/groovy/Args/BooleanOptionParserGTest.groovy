package Args

import args.BooleanOptionParser
import args.TooManyArgumentsException
import spock.lang.Specification

import static args.ArgsTest.option

/**
 * @Author: focus.guo
 * @Date: 2022/8/31 18:19
 * @Description:
 */
class BooleanOptionParserGTest extends Specification {


    def "不能有多余的参数"() {
        given:
        def parser = new BooleanOptionParser()

        when:
        parser.parse(arguments, option("l"))

        then:
        thrown(TooManyArgumentsException)

        where:
        arguments << [["-l", "t"], ["-l", "t", "ddsfsd"]]
    }


    def "没有 option 时给默认值 false"() {
        given:
        def parser = new BooleanOptionParser()

        when:
        def value = parser.parse(arguments, option("l"))

        then:
        !value

        where:
        arguments << [["-p", "8080"], ["-d", "/var/logs"]]
    }

}