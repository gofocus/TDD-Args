package args

import org.assertj.core.util.Lists
import spock.lang.Specification
import static args.ArgsTest.option;

class ListOptionParserGTest extends Specification {

    def "should_parse_list_options"() {

        given:
        def parser = OptionParsers.list(String[].&new, String.&valueOf)
        def list = ["-g", "this", "is"]

        when:
        def result = parser.parse(list, option("g"))

        then:
        result == ["this", "is"] as String[]

    }

    def "should_use_empty_array_as_default_value"() {

        given:
        def parser = OptionParsers.list(String[].&new, String.&valueOf)

        when:
        def result = parser.parse(Lists.newArrayList(), option("g"))

        then:
        result == [] as String[]

    }

}
