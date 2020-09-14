package jmark.html;

/**
 * Expansion on {@link ISimpleHtmlParsable} that allows for tag properties.
 */
public interface IPropertyHtmlParsable extends ISimpleHtmlParsable
{
    @Override
    default void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<").append(getHtmlTagName()).append(" ").append(getHtmlTagProperties()).append(">");
    }

    /**
     * Used to retrieve tag properties for html.
     *
     * @return Tag properties.
     */
    String getHtmlTagProperties();
}
