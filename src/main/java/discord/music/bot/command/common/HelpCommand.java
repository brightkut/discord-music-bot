package discord.music.bot.command.common;

import discord.music.bot.Config;
import discord.music.bot.command.CommandContext;
import discord.music.bot.command.CommandManager;
import discord.music.bot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class HelpCommand implements ICommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()){
            StringBuilder builder = new StringBuilder();

            builder.append("List of discord.music.bot.command\n");

            commandManager.getCommands().forEach(it->{
                builder.append("`").append(Config.get("prefix")).append(it.getName()).append("`\n");
            });

            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = commandManager.getCommand(search);

        if(command == null){
            channel.sendMessage("Nothing found for "+ search).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();
        return;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Show the list with commands in the bot\n"+
                "Usage: `!help [command]`";
    }
}
