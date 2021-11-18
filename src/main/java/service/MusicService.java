package service;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.voice.AudioProvider;
import entity.Music;
import exception.CustomException;
import repo.MusicRepo;

public class MusicService {

    public void processMusicCommand(Message message, MessageCreateEvent event,AudioPlayerManager playerManager,TaskSchedulerService taskSchedulerService,AudioProvider provider ) throws CustomException {
        String messageContent = message.getContent();
        final MessageChannel channel = message.getChannel().block();
        try {
            if (messageContent.startsWith("!add song")) {
                CommandService.getCommandService().addSong(messageContent,channel);
            } else if (messageContent.startsWith("!play song")) {
                System.out.println("play");

                System.out.println(MusicRepo.getMusic().getMusicList());
                String[] messageSplit = messageContent.split(" ");
                String nameSong = messageSplit[2];

                //get music from db
                Music getMusic = MusicRepo.getMusic().getByName(nameSong);

                channel.createMessage("link name: " + getMusic.getLink()).block();

            }else if(messageContent.equals("!get songs")){
                channel.createMessage(CommandService.getCommandService().getSongs().toString()).block();
            }else if(messageContent.equals("!add bot")){
                CommandService.getCommandService().addBot(event,channel,provider);
            }
            else if(messageContent.equals("t")){
                System.out.println("playing music");
                playerManager.loadItem("https://www.youtube.com/watch?v=cO32fdcxTVQ",taskSchedulerService);
            }
        }catch (CustomException exception){
            System.out.println("Error Business when "+exception.getErrorMessage());
            channel.createMessage(exception.getErrorMessage()).block();
        }catch (Exception exception){
            System.out.println("Error System when "+ exception.getMessage());
            channel.createMessage(exception.getMessage()).block();
        }
    }
}
