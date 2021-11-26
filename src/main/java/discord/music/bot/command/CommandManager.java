package discord.music.bot.command;

import discord.music.bot.Config;
import discord.music.bot.command.common.HelpCommand;
import discord.music.bot.command.common.PingCommand;
import discord.music.bot.command.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new AddMusicCommand());
        addCommand(new GetMusicCommand());
    }

    private void addCommand(ICommand cmd){
        boolean nameFound = this.commands.stream().anyMatch((it)->it.getName().equals(cmd.getName()));

        if(nameFound){
            throw new IllegalArgumentException("A discord.music.bot.command with this name already present");
        }

        commands.add(cmd);
    }

    public ICommand getCommand(String search){
         String searchLower = search.toLowerCase();

         for(ICommand command: this.commands){
             if(command.getName().equals(searchLower) || command.getAliases().contains(searchLower)){
                return command;
             }
         }
         return null;
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    public void handle(GuildMessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)"+ Pattern.quote(Config.get("prefix")),"")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand command = this.getCommand(invoke);

        if(command != null){
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1,split.length);

            CommandContext ctx = new CommandContext(event,args);
            command.handle(ctx);
        }
    }
}

