package repo;

import entity.Music;
import exception.CustomException;

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

    public Music getByName(String name) throws CustomException {
        Optional<Music> getMusic = getMusicByName(name);

        //Check music exist
        if (getMusic.isEmpty()){
            throw new CustomException("music not found",2);
        }
        return getMusic.get();
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void insert(Music music) throws CustomException {
        //Check music exist
        if(!getMusicByName(music.getName()).isEmpty()){
            throw new CustomException("music already exist",2);
        }
        musicList.add(music);
    }

    private Optional<Music> getMusicByName(String name){
        return musicList.stream().filter(music -> music.getName().equals(name)).findFirst();
    }
}
