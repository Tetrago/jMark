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

        Option tree = new Option("t", "tree", true, "Tree description file");
        tree.setRequired(false);
        options.addOption(tree);

        Option name = new Option("n", "name", true, "Name of generated page");
        name.setRequired(false);
        options.addOption(name);

        CommandLineParser clp = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cl;

        try
        {
            cl = clp.parse(options, args);

            String sourcePath = cl.getOptionValue("source");
            String outputPath = cl.getOptionValue("output");
            String treePath = cl.getOptionValue("tree");
            String title = cl.getOptionValue("name");

            Lexer lexer = new Lexer(title == null ? "jMark" : title);
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

            if(treePath != null)
            {
                try(FileWriter writer = new FileWriter(treePath))
                {
                    writer.write(lexer.toString());
                }
            }

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
