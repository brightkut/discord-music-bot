package discord.music.bot.command.music;

import discord.music.bot.MusicManager;
import discord.music.bot.command.CommandContext;
import discord.music.bot.command.ICommand;
import discord.music.bot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class AddMusicCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
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

        if(args.isEmpty()){
            channel.sendMessage("No music name and music link for add to the system").queue();
            return;
        }else if(args.size() == 1){
            channel.sendMessage("No music link for add to the system").queue();
            return;
        }
        String musicName = args.get(0);
        String musicLink = args.get(1);

        //Check url
        if(!MusicManager.getMusicManager().isUrl(musicLink)){
            channel.sendMessage("Invalid <youtube link> for add music command").queue();
            return;
        }

        //Check music name duplicate
        if(MusicManager.getMusicManager().isMusicNameExist(channel,musicName)){
            return;
        }

        // Add music name
        PlayerManager.getInstance().addMusic(ctx.getChannel(),musicLink,musicName);

        return;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getHelp() {
        return "Add music to the system\n"+
                "Usage: `!add <music or playlist name> <music or playlist youtube link>`\n" +
                "Remark: You can't use white space for the name and link";
    }
}
