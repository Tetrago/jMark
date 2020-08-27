package jmark;

import org.apache.commons.cli.*;

import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        Options options = new Options();

        Option source = new Option("s", "source", true, "Source markdown file");
        source.setRequired(true);
        options.addOption(source);

        Option output = new Option("o", "output", true, "Output html file");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser clp = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cl;

        try
        {
            cl = clp.parse(options, args);

            String sourcePath = cl.getOptionValue("source");
            String outputPath = cl.getOptionValue("output");

            Lexer lexer = new Lexer();
            HtmlParser parser = new HtmlParser(lexer);

            StringBuilder builder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new FileReader(sourcePath)))
            {
                String line;
                while((line = reader.readLine()) != null)
                {
                    builder.append(line).append('\n');
                }
            }

            lexer.analyze(builder.toString());

            try(FileWriter writer = new FileWriter(outputPath))
            {
                writer.write(parser.parse());
            }
        }
        catch(ParseException | IOException e)
        {
            System.err.println(e.getMessage());
            formatter.printHelp("jMark", options);
        }
    }
}
