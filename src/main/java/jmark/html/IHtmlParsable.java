package jmark.html;

/**
 * Interface for the {@link HtmlParser} to build document.
 */
public interface IHtmlParsable
{
    /**
     * Appends html opening tag to builder.
     *
     * @param builder   Builder to append to.
     */
    void writeHtmlHeader(StringBuilder builder);

    /**
     * Appends html closing tag to builder.
     *
     * @param builder   Builder to append to.
     */
    void writeHtmlFooter(StringBuilder builder);
}
