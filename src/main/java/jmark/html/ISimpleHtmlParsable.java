package jmark.html;

/**
 * Simplified wrapper around {@link IHtmlParsable} for elements with the same opening and closing tag.
 */
public interface ISimpleHtmlParsable extends IHtmlParsable
{
    @Override
    default void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<").append(getHtmlTagName()).append(">");
    }

    @Override
    default void writeHtmlFooter(StringBuilder builder)
    {
        builder.append("</").append(getHtmlTagName()).append(">");
    }

    /**
     * Get html tag name.
     *
     * @return Name of the tag.
     */
    String getHtmlTagName();
}
