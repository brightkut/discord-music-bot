package discord.music.bot.command.music;

import discord.music.bot.MusicManager;
import discord.music.bot.command.CommandContext;
import discord.music.bot.command.ICommand;
import discord.music.bot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState != null && !selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in voice channel for this command to work").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState =  member.getVoiceState();

        if (memberVoiceState != null && !memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in voice channel for this command to work").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        List<String> args = ctx.getArgs();
        String url = args.get(0);

        if(!MusicManager.getMusicManager().isUrl(url)){

            //Find in db
            String getMusicLink = MusicManager.getMusicManager().searchMusic(url);
            System.out.println(getMusicLink);

            if(getMusicLink == null){
                channel.sendMessage("Music name is not found for play command").queue();
                return;
            }

            PlayerManager.getInstance().loadAndPlay(channel,getMusicLink);
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel,url);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Play a song\n" +
                "Usage: `!play <youtube link> or <music or playlist name>`";
    }
}
