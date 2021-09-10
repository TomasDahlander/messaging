package se.nackademin.messaging.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

    /*
    TODO: Uppgift 1: Configuration
     På consumer sidan behöver vi skapa upp en kö och binda den till rätt exchange.
     Namnet på kön ska vara "audit-log"
     Vi behöver öven konfigurera vår rabbitTemplate med rätt conection factory och message converter.
     Precis så som vi gjorde i andra tjänsten.
     */

    @Bean
    public AuditLogRepository auditLogRepository() {
        return new AuditLogRepository();
    }

    // Detta för att testfallet ska fungera
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("account-opened");
    }
    // Detta för att testfallet ska fungera
    @Bean FanoutExchange fanoutExchange2(){
        return new FanoutExchange("account-deposit");
    }

    @Bean
    public Queue myQueue() {
        return new Queue("audit-log-open");
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("audit-log-deposit");
    }

    @Bean
    public Binding declareBindingGeneric() {
        return new Binding("audit-log-open", Binding.DestinationType.QUEUE,"account-opened","",null);
    }

    @Bean
    public Binding declareBindingGeneric2() {
        return new Binding("audit-log-deposit", Binding.DestinationType.QUEUE,"account-deposit","",null);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, final Jackson2JsonMessageConverter converter) {
        RabbitTemplate rt = new RabbitTemplate(connectionFactory);
        rt.setMessageConverter(converter);
        return rt;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
