package args;

import java.util.List;

public class BooleanOptionParser implements OptionParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());

        int nextIndex = index + 1;
        if (nextIndex < arguments.size() && !arguments.get(nextIndex).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }

        return index != -1;
    }
}
