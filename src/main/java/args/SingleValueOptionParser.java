package args;

import java.util.List;
import java.util.function.Function;

public class SingleValueOptionParser<T> implements OptionParser<T> {
    Function<String, T> valueParser;
    private final T defaultValue;

    public SingleValueOptionParser(Function<String, T> valueParser, T defaultValue) {
        this.valueParser = valueParser;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());

        boolean optionExists = index != -1;
        boolean noParam = index + 1 == arguments.size() || arguments.get(index + 1).startsWith("-");

        if (!optionExists){
            return defaultValue;
        }

        if (noParam) {
            throw new InsufficientArgumentsException(option.value());
        }

        if (index + 2 < arguments.size() && !arguments.get(index + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }

        return valueParser.apply(arguments.get(index + 1));
    }

}
