package args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: focus.guo @Date: 2022/8/27 19:37 @Description:
 */
public class Args {

    private static final Map<Class<?>, OptionParser> PARSERS =
            Map.of(
                    boolean.class, new BooleanOptionParser(),
                    int.class, new SingleValueOptionParser<>(Integer::parseInt),
                    String.class, new SingleValueOptionParser<>(String::valueOf));

    public static <T> T parse(Class<T> optionsClass, String... args) {
        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
        try {
            List<String> arguments = Arrays.stream(args).toList();

            Object[] values =
                    Arrays.stream(constructor.getParameters())
                            .map(it -> parseOption(arguments, it))
                            .toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Option option = parameter.getAnnotation(Option.class);
        Class<?> parameterType = parameter.getType();
        OptionParser parser = PARSERS.get(parameterType);
        return parser.parse(arguments, option);
    }
}
