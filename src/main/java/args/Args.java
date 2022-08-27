package args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: focus.guo
 * @Date: 2022/8/27 19:37
 * @Description:
 */
public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
        try {
            List<String> arguments = Arrays.stream(args).toList();
            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(it -> parseOption(arguments, it))
                    .toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Object value = null;
        Option option = parameter.getAnnotation(Option.class);

        if (parameter.getType() == boolean.class) {
            value = arguments.contains("-" + option.value());
        }
        if (parameter.getType() == int.class) {
            int index = arguments.indexOf("-" + option.value());
            value = Integer.parseInt(arguments.get(index + 1));
        }
        if (parameter.getType() == String.class) {
            int index = arguments.indexOf("-" + option.value());
            value = arguments.get(index + 1);
        }
        return value;
    }
}