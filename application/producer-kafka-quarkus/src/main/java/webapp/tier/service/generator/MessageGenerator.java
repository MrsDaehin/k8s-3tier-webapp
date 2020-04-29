package webapp.tier.service.generator;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class MessageGenerator {

	private static final Logger LOG = Logger.getLogger(MessageGenerator.class);

	@ConfigProperty(name = "common.message")
	String message;

	@Outgoing("message")
	public Flowable<KafkaRecord<Integer, String>> generate() {

		return Flowable.interval(1000, TimeUnit.MILLISECONDS)
				.onBackpressureDrop()
				.map(tick -> {
					MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
					msgbean.setFullmsgWithType(msgbean, "Generate");
					LOG.info(msgbean.getFullmsg());
					return KafkaRecord.of(msgbean.getId(), msgbean.getMessage());
				});
	}
}
