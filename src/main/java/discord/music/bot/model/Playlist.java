package discord.music.bot.model;

import org.bson.types.ObjectId;

public class Playlist {
    private ObjectId id;
    private String playlistId;
    private String playlistName;
    private String playlistLink;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistLink() {
        return playlistLink;
    }

    public void setPlaylistLink(String playlistLink) {
        this.playlistLink = playlistLink;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", playlistId='" + playlistId + '\'' +
                ", playlistName='" + playlistName + '\'' +
                ", playlistLink='" + playlistLink + '\'' +
                '}';
    }
}
