package args


import spock.lang.Specification

import static args.ArgsTest.option

/**
 * @Author: focus.guo
 * @Date: 2022/8/31 18:19
 * @Description:
 */
class BooleanOptionParserGTest extends Specification {

    def "happy path: 有 option 时返回 true"() {
        given:
        def parser = OptionParsers.bool()

        when:
        def value = parser.parse(arguments, option("l"))

        then:
        value

        where:
        arguments << [["-l"], ["-l", "-p", "8080"]]
    }

    def "不能有多余的参数"() {
        given:
        def parser = OptionParsers.bool()

        when:
        parser.parse(arguments, option("l"))

        then:
        thrown(TooManyArgumentsException)

        where:
        arguments << [["-l", "t"], ["-l", "t", "ddsfsd"]]
    }


    def "没有 option 时给默认值 false"() {
        given:
        def parser = OptionParsers.bool()

        when:
        def value = parser.parse(arguments, option("l"))

        then:
        !value

        where:
        arguments << [["-p", "8080"], ["-d", "/var/logs"]]
    }

}
