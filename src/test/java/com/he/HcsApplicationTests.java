package com.he;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.he.config.RabbitMQConfig;
import com.he.controller.StudentStatusController;
import com.he.controller.UsersController;
import com.he.po.StudentStatus;
import com.he.po.Users;
import com.he.service.StudentStatusService;
import com.he.tool.ObjectToByte;
import com.he.viewpo.NewUsers;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HcsApplicationTests {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

//	@Autowired
//	private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//	
//    @Before
//    public void setUp() {
//    	//此种方式可通过spring上下文来自动配置一个或多个controller
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        
//        //此种方式，手工指定想要的controller
//        //mockMvc = MockMvcBuilders.standaloneSetup(new Controller1(), new Controller2()).build();
//    }

	@Autowired
	private UsersController sc;
	
	@Autowired
	private StudentStatusController ss;
	
	
	@Test
	public void contextLoads() throws Exception{
		String message="hello";
		
//		this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_STUDENT, "student.routingkey",message);

		
		
		
//		RequestBuilder request = null;
//		//构造请求
//		request = post("/student/status/initStudent") 
//				.param("s", "1001"); 

//		StudentStatus s=new StudentStatus();
//		s.setClassId("080901201901");
//		s.setId(7001);
//		s.setStudentId("19380501");
//		String word=sc.getMaxStudentId(s);
//		System.out.println(word);
		NewUsers nu=new NewUsers();
		nu.setType("student_basic");
		List<NewUsers> us=this.sc.getAllUsers(nu);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(40);
		for (int i=20; i<60; i++) {
			Thread thread = new Thread(new Work(cyclicBarrier,us.get(i),rabbitTemplate));
			thread.setName("线程-" + (i+1)+":基础编号:"+us.get(i).getPassword());
			thread.start();
		}
//	
	}
	class Work implements Runnable {
		 
		private final CyclicBarrier cyclicBarrier;
		
		private NewUsers s;
		
		private RabbitTemplate rabbitTemplate;
		
		public Work(CyclicBarrier cyclicBarrier,NewUsers s,RabbitTemplate rabbitTemplate) {
			this.cyclicBarrier = cyclicBarrier;
			this.s=s;
			this.rabbitTemplate=rabbitTemplate;
		}
		
		@Override
		public void run() {
			try {
				/**
				 * CyclicBarrier类的await()方法对当前线程（运行cyclicBarrier.await()代码的线程）进行加锁，然后进入await状态；
				 * 当进入CyclicBarrier类的线程数（也就是调用cyclicBarrier.await()方法的线程）等于初始化CyclicBarrier类时配置的线程数时；
				 * 然后通过signalAll()方法唤醒所有的线程。
				 */
				cyclicBarrier.await();
//				String context = "hello " + new Date();
//				System.out.println("Sender : " + context);
				
		
				//成功测试rabbitMQ
//				NewUsers nu=new NewUsers();
//				nu.setId(123);
//				nu.setPassword("456");
//				nu.setType("789");
//				nu.setUsername("大沙皮");
//				byte[] a = ObjectToByte.ser(nu);		
//				this.rabbitTemplate.convertAndSend("exchange","topic.messages",a);
				
				
				Users u=new Users();
				u.setId(s.getId());
				u.setPassword(s.getPassword());
				u.setRoles(s.getRoles());
				u.setType(s.getType());
				u.setUsername(s.getUsername());
				System.out.println(Thread.currentThread().getName() + "启动时间是" + System.currentTimeMillis());
				//List word=ss.getMaxStudentId(u);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
}
