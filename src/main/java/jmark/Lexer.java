package jmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
    private static final Pattern PATTERN = Pattern.compile("(#+)|([^\\n]+)|(- [^\\n]+)|(\\d\\. [^\\n]+)");
    private static final Token.Type[] PATTERN_TOKEN_TYPES =
            {
                    Token.Type.Heading,
                    Token.Type.Text,
                    Token.Type.UnorderedList,
                    Token.Type.OrderedList
            };

    private List<Token> tokens_ = new ArrayList<>();

    public boolean analyze(String source)
    {
        tokens_.clear();
        Matcher matcher = PATTERN.matcher(source);

        while(matcher.find())
        {
            String match;
            for(int i = 0; i < PATTERN_TOKEN_TYPES.length; ++i)
            {
                if((match = matcher.group(i + 1)) != null)
                {
                    tokens_.add(new Token(PATTERN_TOKEN_TYPES[i], match));
                    break;
                }
            }
        }

        return !tokens_.isEmpty();
    }

    public List<Token> getTokens() { return Collections.unmodifiableList(tokens_); }
}
