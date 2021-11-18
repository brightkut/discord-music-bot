package service;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.voice.AudioProvider;
import entity.Music;
import exception.CustomException;
import repo.MusicRepo;

import java.util.ArrayList;
import java.util.List;

public class CommandService {
    private static CommandService commandService;

    public static CommandService getCommandService(){
        if(commandService==null){
            commandService = new CommandService();
        }
        return commandService;
    }

    public void addSong(String messageContent,MessageChannel channel) throws CustomException {
        System.out.println("add song command");
        String[] messageSplit = messageContent.split(" ");
        String nameSong;
        String linkSong;

        // Check name and link song in input
        try {
            nameSong = messageSplit[2];
            linkSong = messageSplit[3];
            System.out.println("add song name: " + nameSong + " with link song: " + linkSong);
        } catch (Exception exception) {
            System.out.println("Error when get name or link song in add song command with error: " + exception.getMessage());
            throw new CustomException("missing name or link song in add song command",1);
        }

        //insert music to db
        MusicRepo.getMusic().insert(new Music(nameSong, linkSong));

        for (Music m : MusicRepo.getMusic().getMusicList()) {
            System.out.println(m.getName() + m.getLink());
        }

        channel.createMessage("add new song success ").block();
    }

    public void addBot(MessageCreateEvent event,MessageChannel channel, AudioProvider provider){
        System.out.println("add bot command");
        final Member member = event.getMember().orElse(null);

        System.out.println("member: "+member);
        if (member != null) {
            final VoiceState voiceState = member.getVoiceState().block();
            System.out.println("voice state: "+voiceState);
            if (voiceState != null) {
                final VoiceChannel voiceChannel = voiceState.getChannel().block();
                System.out.println("voice channel: "+voiceChannel);
                if (voiceChannel != null) {
                    // join returns a VoiceConnection which would be required if we were
                    // adding disconnection features, but for now we are just ignoring it.
                    voiceChannel.join(spec -> spec.setProvider(provider)).block();
                }
                System.out.println("voice channel after join: "+voiceChannel);
            }
        }
        channel.createMessage("add bot to channel success").block();
    }

    public List<String> getSongs(){
        List<String>musicSongs = new ArrayList<>();
        for (Music music: MusicRepo.getMusic().getMusicList()){
            musicSongs.add("song name: "+music.getName()+" song link: "+music.getLink());
        }
        return musicSongs;
    }
}
