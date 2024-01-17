package pro.sky.telegrambot.timer;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;


class NotificationTaskTimerTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @InjectMocks
    private NotificationTaskTimer notificationTaskTimer;

    @Test
    public void testTask() {
        MockitoAnnotations.openMocks(this);

        LocalDateTime oneDaysAgo = LocalDateTime.of(2023, 10, 3, 0, 0);
        Client client = new Client();
        client.setDateTimeToTook(oneDaysAgo.minusDays(2));
        client.setChatId(12345L);

        when(clientRepository.findByDateTimeToTookBefore(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(Optional.of(client));

        notificationTaskTimer.task();

        verify(clientRepository, times(1)).findByDateTimeToTookBefore(ArgumentMatchers.any(LocalDateTime.class));
        verify(telegramBotUpdatesListener, times(1)).executeSendMessage(client.getChatId(), " «Дорогой усыновитель, мы заметили, что ты заполняешь " +
                "отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее" +
                " к этому занятию. В противном случае волонтеры приюта будут обязаны " +
                "самолично проверять условия содержания животного»");
    }
}