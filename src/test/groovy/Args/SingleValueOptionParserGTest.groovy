package Args

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

    def "不应该传入多余的参数（只能一个）"() {
        given:
        def parser = new SingleValueOptionParser<>(valueParser, defaultValue)

        when:
        parser.parse(arguments, option(optionValue))

        then:
        def e = thrown(TooManyArgumentsException)
        e.getOption() == optionValue

        where:
        valueParser      | defaultValue | arguments                   | optionValue
        Integer::valueOf | 0            | ["-p", "8080", "8081"]      | "p"
        String::valueOf  | "0"          | ["-d", "/var/logs", "/ooo"] | "d"
    }

    def "必须要传参数"() {
        given:
        def parser = new SingleValueOptionParser<>(valueParser, defaultValue)

        when:
        parser.parse(arguments, option(optionValue))

        then:
        thrown(InsufficientArgumentsException)

        where:
        valueParser      | defaultValue | arguments    | optionValue
        Integer::valueOf | 0            | ["-p"]       | "p"
        Integer::valueOf | 0            | ["-p", "-l"] | "p"
        String::valueOf  | "0"          | ["-d",]      | "d"
        String::valueOf  | "0"          | ["-d", "-p"] | "d"
    }

    def "参数里没有对应的 option 时，给默认值"() {
        given:
        def parser = new SingleValueOptionParser<>(valueParser, defaultValue)

        when:
        def value = parser.parse(arguments, option(optionValue))

        then:
        value == result

        where:
        valueParser      | defaultValue | arguments | optionValue || result
        Integer::valueOf | 0            | ["-d"]    | "p"         || 0
        String::valueOf  | "0"          | ["-p",]   | "d"         || "0"
    }

}
