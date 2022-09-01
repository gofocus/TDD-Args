package args

import args.InsufficientArgumentsException
import args.SingleValueOptionParser
import args.TooManyArgumentsException
import spock.lang.Specification

import static args.ArgsTest.option

/**
 * @Author: focus.guo
 * @Date: 2022/8/31 13:56
 * @Description:
 */
class SingleValueOptionParserGTest extends Specification {

    def "happy path: 有 option 且参数数量正确时，返回对应的参数"() {
        given:
        def parser = new SingleValueOptionParser<>(valueParser, defaultValue)

        when:
        def value = parser.parse(arguments, option(optionValue))

        then:
        value == result

        where:
        valueParser      | defaultValue | arguments           | optionValue || result
        Integer::valueOf | 0            | ["-p", "8080"]      | "p"         || 8080
        String::valueOf  | "0"          | ["-d", "/var/logs"] | "d"         || "/var/logs"
    }

    def "不应该传入多余的参数（只能一个）"() {
        given:
        def parser = new SingleValueOptionParser<>((it) -> null, null)

        when:
        parser.parse(arguments, option(optionValue))

        then:
        def e = thrown(TooManyArgumentsException)
        e.getOption() == optionValue

        where:
        arguments                   | optionValue
        ["-p", "8080", "8081"]      | "p"
        ["-d", "/var/logs", "/ooo"] | "d"
    }

    def "必须要传参数"() {
        given:
        def parser = new SingleValueOptionParser<>((it) -> null, null)

        when:
        parser.parse(arguments, option(optionValue))

        then:
        thrown(InsufficientArgumentsException)

        where:
        arguments    | optionValue
        ["-p"]       | "p"
        ["-p", "-l"] | "p"
        ["-d",]      | "d"
        ["-d", "-p"] | "d"
    }

    def "参数里没有对应的 option 时，给默认值"() {
        given:
        def parser = new SingleValueOptionParser<>((it) -> null, defaultValue)

        when:
        def value = parser.parse(arguments, option(optionValue))

        then:
        value == defaultValue

        where:
        defaultValue | arguments | optionValue
        0            | ["-d"]    | "p"
        "0"          | ["-p"]    | "d"
    }

}
