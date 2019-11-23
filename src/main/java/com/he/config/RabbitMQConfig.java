package com.he.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public final static String queueName = "advance_queue";

    public final static String routingKey = "advance_routing_key";

    public final static String exchangeName = "advance_exchange";

    @Bean
    public Queue advanceQueue() {
        return new Queue(queueName);
    }

    @Bean
    DirectExchange advanceExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding bindingExchangeMessage(Queue advanceQueue, DirectExchange advanceExchange) {
        return BindingBuilder.bind(advanceQueue).to(advanceExchange).with(routingKey);
    }
	
    //学生获取学号、宿舍
    
    //声明队列
    @Bean(name = "messages")
    public Queue queueMessages(){
        return new Queue("topic.messages");
    }
  //声明交换机
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("exchange");
    }
    //绑定交换机
    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessages,
    		TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).
        		to(exchange).with("topic.messages");
    }
    
    
    
    

	
//	public static final String QUEUE_STUDENT="studentqueue";//这个是队列的名称
//	public static final String EXCHANGE_STUDENT="studentexchange";//这个是交换机的名称
//	public static final String ROUTINGKEY_STUDENT="student.#.routingkey";//这个是交换机的名称
//	
//	//交换机声明
//    @Bean
//    public Exchange EXCHANGE_STUDENT() {
//    	return ExchangeBuilder.topicExchange(EXCHANGE_STUDENT).durable(true).build();
//    }
//    
//    //队列声明
//    @Bean
//    public Queue QUEUE_STUDENT() {
//    	return new Queue(QUEUE_STUDENT);
//    }
//    
//    //队列绑定到交换机上面
//    public Binding studentIdBinding(Queue queue,Exchange exchange) {
//    	return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_STUDENT).noargs();
//    }
//    
//    
//    @Bean
//    public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);//Create a rabbit template with default strategies and settings.
//        rabbitTemplate.setMessageConverter(jsonConverter());
//        return rabbitTemplate;
//    }
//    @Bean
//    public MessageConverter jsonConverter() {
//        return new Jackson2JsonMessageConverter();//以json的格式传递数据
//    }
//
//
//    public static final String suffix = Long.toString(System.currentTimeMillis());//用时间戳就是为了保证名字的唯一性
//    public final static boolean durable = false;
//    public final static boolean autoDelete = true;
//
//
//    /**
//     * 交换机
//     * @return
//     */
//    @Bean
//    public FanoutExchange taskExchange() {
//        /**
//         * durable： true if we are declaring a durable exchange (the exchange will survive a server restart)
//         * 意思就是：如果我们宣布一个持久的交换机，交换器将在服务器重启后继续存在。false就是服务器重启后将不在存在
//         * autoDelete：true if the server should delete the exchange when it is no longer in use
//         * 意思就是：如果长时间不用这个交换机，就删掉这个交换机
//         */
//
//        return new FanoutExchange(TASK_EXCHANGE + "." + suffix, durable, autoDelete);
//    }
//    /**
//     * 一个队列
//     * @return
//     */
//    @Bean
//    public Queue taskNoticeQueue() {
//        /**
//         * exclusive: true if we are declaring an exclusive queue (the queue will only be used by the declarer's connection)
//         *如果我们声明一个独占队列,队列只会被声明者的连接使用
//         */
//        return new Queue(QUENE_NAME + "." + suffix, durable, false, autoDelete);
//    }
//
//    /**
//     * 将队列和交换机绑定起来
//     * @param taskExchange
//     * @param taskNoticeQueue
//     * @return
//     */
//    @Bean
//    public Binding taskNoticeBinding(FanoutExchange taskExchange,
//                                     Queue taskNoticeQueue) {
//        return BindingBuilder.bind(taskNoticeQueue).to(taskExchange);
//    }
//    /**
//     * 这里顺便说一下@Bean这个注解，@Bean的方法名就是xml中的bean的ID，生成的对象的引用名称
//     * 就比如@Autowired private User users,这个users就是那个方法名
//     */
 
}
