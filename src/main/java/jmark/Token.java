package jmark;

public class Token
{
    public enum Type
    {
        Heading,
        Text,
        UnorderedList,
        OrderedList
    }

    private Type type_;
    private String value_;

    public Token(Type type, String value)
    {
        type_ = type;
        value_ = value;
    }

    public Type getType() { return type_; }
    public  String getValue() { return value_; }
}
