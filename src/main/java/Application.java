import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import service.MusicService;

public class Application {
    public static void main(String[] args) {
        final String token = "ODI1NzMwNzE1MjM2ODI3MTM2.YGCLpw.OpW36mQYlV-3lYOYoGX-aqsKWNc";
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            MusicService musicService = new MusicService();
            musicService.processMusicCommand(message);
        });

        gateway.onDisconnect().block();
    }
}
