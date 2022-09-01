package args;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: focus.guo @Date: 2022/8/27 19:39 @Description:
 */
class ArgsTest {

    public static Option option(String value) {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }

    @Test
    void should_parse_multi_options() {
        MultiOptions options =
                Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging);
        assertEquals(8080, options.port);
        assertEquals("/usr/logs", options.directory);
    }

    record MultiOptions(
            @Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {}
}
