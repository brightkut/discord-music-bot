package repo;

import entity.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicRepo {
    private List<Music> musicList ;

    private static MusicRepo musicRepo;

    private MusicRepo(){
    }

    private MusicRepo(List<Music> musicList){
        this.musicList = musicList;
    }

    public static MusicRepo getMusic() {
        if(musicRepo == null){
            musicRepo = new MusicRepo(new ArrayList<>());
        }
        return musicRepo;
    }

    public Music getByName(String name){

        Optional<Music> getMusic = musicList.stream().filter(music -> music.getName().equals(name)).findFirst();

        if (getMusic.isEmpty()){
            return null;
        }
        return getMusic.get();
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void insert(Music music){
        musicList.add(music);
    }
}
