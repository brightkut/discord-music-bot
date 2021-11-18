import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.voice.AudioProvider;
import service.LavaPlayerAudioProvider;
import service.MusicService;
import service.TaskSchedulerService;

public class Application {
    public static void main(String[] args) {
        final String token = "ODI1NzMwNzE1MjM2ODI3MTM2.YGCLpw.OpW36mQYlV-3lYOYoGX-aqsKWNc";
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        //Audio Config (need to be 1 instance)
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioPlayer player = playerManager.createPlayer();
        AudioProvider provider = new LavaPlayerAudioProvider(player);
        final TaskSchedulerService taskSchedulerService = new TaskSchedulerService(player);

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            MusicService musicService = new MusicService();
            try {
                musicService.processMusicCommand(message,event,playerManager,taskSchedulerService,provider);
            } catch (Exception exception){
                System.out.println("Error occur when use command: "+message.getContent()+ " with error: "+exception.getMessage());
            }
        });

        gateway.onDisconnect().block();
    }
}
