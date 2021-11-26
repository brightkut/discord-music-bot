package discord.music.bot;

import discord.music.bot.model.Music;
import discord.music.bot.model.Playlist;
import discord.music.bot.repo.MusicRepo;
import discord.music.bot.repo.PlaylistRepo;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    private final Map<String,String> musicMap;
    private final Map<String,String> playlistMap;
    private static MusicManager musicManager;

    private MusicManager(){
        this.musicMap = new HashMap<>();
        this.playlistMap = new HashMap<>();
    }

    public static MusicManager getMusicManager(){
        if(musicManager == null){
            musicManager = new MusicManager();
        }
        return musicManager;
    }

    public void addMusic(String musicName,String musicLink){
        MusicRepo.getMusicRepo().insertMusic(musicName,musicLink);
    }

    public void addPlaylist(String playlistName,String playlistLink){
        PlaylistRepo.getPlaylistRepo().insertPlaylist(playlistName,playlistLink);
    }

    public String searchMusic (String musicName){
        Music getMusic = MusicRepo.getMusicRepo().getByMusicName(musicName);
        Playlist getPlaylist = PlaylistRepo.getPlaylistRepo().getByPlayListName(musicName);

        if(getMusic != null){
            return getMusic.getMusicLink();
        }else if(getPlaylist != null){
            return getPlaylist.getPlaylistLink();
        }

        return null;
    }

    public boolean isMusicNameExist(TextChannel channel,String musicName){
        if(MusicRepo.getMusicRepo().getByMusicName(musicName) != null){
            channel.sendMessage("Music name: `"+ musicName +"` already exist with the music track").queue();
            return true;
        }else if(PlaylistRepo.getPlaylistRepo().getByPlayListName(musicName) != null){
            channel.sendMessage("Music name: `"+ musicName +"` already exist with the playlist track").queue();
            return true;
        }

        return false;
    }

    public Map<String, String> getMusicMap() {
        this.musicMap.clear();

        MusicRepo.getMusicRepo().getMusics().forEach(music ->
                this.musicMap.put(music.getMusicName(),music.getMusicLink())
        );

        return musicMap;
    }

    public Map<String, String> getPlaylistMap() {
        this.playlistMap.clear();

        PlaylistRepo.getPlaylistRepo().getPlaylists().forEach(playlist ->
                this.playlistMap.put(playlist.getPlaylistName(),playlist.getPlaylistLink())
        );

        return playlistMap;
    }

    public boolean isUrl(String url){
        try {
            new URL(url);
            return true;
        }catch (MalformedURLException exception){
            return false;
        }
    }
}
