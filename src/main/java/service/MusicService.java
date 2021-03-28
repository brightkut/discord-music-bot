package service;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import entity.Music;
import repo.MusicRepo;

public class MusicService {

    public void processMusicCommand(Message message){
        String messageContent = message.getContent();
        final MessageChannel channel = message.getChannel().block();
        if(messageContent.startsWith("add song")){
            System.out.println("add");
            String[] messageSplit = messageContent.split(" ");
            String nameSong = messageSplit[2];
            String linkSong = messageSplit[3];

            //insert music to db
            MusicRepo.getMusic().insert(new Music(nameSong,linkSong));

            System.out.println(MusicRepo.getMusic().getMusicList());

            channel.createMessage("add new song success ").block();
        }else if (messageContent.startsWith("play song")){
            System.out.println("play");

            System.out.println(MusicRepo.getMusic().getMusicList());
            String[] messageSplit = messageContent.split(" ");
            String nameSong = messageSplit[2];

            //get music from db
            Music getMusic = MusicRepo.getMusic().getByName(nameSong);

            channel.createMessage("link name: "+ getMusic.getLink()).block();
        }
    }
}
