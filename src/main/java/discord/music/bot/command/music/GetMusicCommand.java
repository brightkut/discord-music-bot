package discord.music.bot.command.music;

import discord.music.bot.MusicManager;
import discord.music.bot.command.CommandContext;
import discord.music.bot.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Set;

public class GetMusicCommand implements ICommand {
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

        Set<String> allMusicName = MusicManager.getMusicManager().getMusicMap().keySet();
        Set<String> allPlaylistName = MusicManager.getMusicManager().getPlaylistMap().keySet();

        channel.sendMessage(getTemplate(allMusicName,allPlaylistName)).queue();
        return;
    }

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getHelp() {
        return "Get all music and playlist name";
    }

    public String getTemplate(Set<String> musicNames,Set<String> playlistNames){
        String template = "List of Music:\n";
        // Set music template
        int start =1;
        for (String musicName: musicNames){
            template+= start +". `"+musicName+"`\n";
            start++;
        }

        template+="\nList of Playlist:\n";

        //Set playlist template
        int start2 = 1;
        for (String playlistName: playlistNames){
            template+= start2 +". `"+playlistName+"`\n";
            start2++;
        }

        return template;
    }
}
