package args;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SingleValueOptionParser<T> implements OptionParser<T> {
    Function<String, T> valueParser;
    private final T defaultValue;

    public SingleValueOptionParser(Function<String, T> valueParser, T defaultValue) {
        this.valueParser = valueParser;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        return values(arguments, option, 1).map(it -> parse(it.get(0))).orElse(defaultValue);
    }

    static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return Optional.empty();
        }

        List<String> values = values(arguments, index);

        if (values.size() < expectedSize) {
            throw new InsufficientArgumentsException(option.value());
        }
        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option.value());
        }
        return Optional.of(values);
    }

    private T parse(String value) {
        return valueParser.apply(value);
    }

    protected static List<String> values(List<String> arguments, int index) {
        int followingFlag =
                IntStream.range(index + 1, arguments.size())
                        .filter(it -> arguments.get(it).startsWith("-"))
                        .findFirst()
                        .orElse(arguments.size());
        return arguments.subList(index + 1, followingFlag);
    }
}
