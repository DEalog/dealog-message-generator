package de.dealog.msg.event;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import de.dealog.common.model.MessageEvent;
import de.dealog.common.model.MessageEventPayload;
import de.dealog.common.model.MessageEventType;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A bean producing message events every 5 seconds. The message events are written to a Kafka topic (messages).
 */
@ApplicationScoped
@Slf4j
public class MessageEventGenerator {

    private final Lorem lorem = LoremIpsum.getInstance();

    private static Random rand = new Random();

    @Outgoing("generated-messages")
    public Flowable<MessageEvent> generate() {
        log.debug("Generate");

        return Flowable.interval(5, TimeUnit.SECONDS)
                .map(messageEvent -> nextMessageEvent());
    }

    /**
     * Creates a new {@link MessageEvent}
     * @return the {@link MessageEvent}
     */
    private MessageEvent nextMessageEvent(){
        MessageEventType randomEventType = getRandomEventType();

        MessageEventPayload messageEventPayload = new MessageEventPayload();
        messageEventPayload.setIdentifier(UUID.randomUUID().toString());
        messageEventPayload.setHeadline(lorem.getWords(1,5));
        messageEventPayload.setDescription(lorem.getParagraphs(1,2));
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setType(randomEventType);
        messageEvent.setPayload(messageEventPayload);
        log.debug("Send message event w/ type '{}' and '{}'", messageEvent.getType(), messageEventPayload);
        return messageEvent;
    }

    private MessageEventType getRandomEventType() {
        MessageEventType[] types = MessageEventType.values();
        return types[rand.nextInt(types.length)];
    }
}
