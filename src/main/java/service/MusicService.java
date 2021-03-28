package service;

import discord4j.core.object.entity.Message;

public class MusicService {

    public void processMusicCommand(Message message){
        String messageContent = message.getContent();
        if(messageContent.startsWith("add song")){
            String[] messageSplit = messageContent.split(" ");
            String nameSong = messageSplit[2];
            String linkSong = messageSplit[3];
        }
    }
}
