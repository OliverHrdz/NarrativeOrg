package network.narrative.auction.config;

/**
 * Auction Spring Boot application.
 *
 */
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import network.narrative.auction.domain.Auction;
import network.narrative.auction.domain.Item;
import network.narrative.auction.domain.User;
import network.narrative.auction.service.Selling;

@SpringBootApplication
@ComponentScan("network.narrative.auction")
@EnableAsync
public class Application {

	static private AtomicInteger nextId = new AtomicInteger(0);

	/* ugly, I know, making a quick and dirty "database" in memory */
	static public final User user1 = new User();
	static public final User user2 = new User();
	static public final User user3 = new User();
	static {
		user1.setId(1);
		user1.setName("Brian");

		user2.setId(2);
		user2.setName("Patrick");

		user3.setId(3);
		user3.setName("Oliver");
	}

	@Autowired
	private Selling sellingService;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		// leaving this in, copied/pasted from Spring Boot tutorial
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

			/* initialize "database" */
			for (int i = 1; i <= 3; i++) {
				// was going to test concurrency here, but actual test is in the
				// sole unit test of this project, so disregard why I'm using
				// threads here and please see that test
				Thread thread = new Thread(new SetUp(sellingService));
				thread.start();
				thread.join();
			}

			for (Auction auction : sellingService.getActiveListings()) {
				System.out.println(auction);
			}

			// crude signal to mark when I can access application, as I was
			// hitting the URL before it completed building the "database" and
			// getting an error
			System.out.println("READY!");
        };
    }

	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	/* made public because it is used by the unit test */
	public static class SetUp implements Runnable {

		private Selling sellingService;

		public SetUp(Selling sellingService) {
			this.sellingService = sellingService;
		}

		@Override
		public void run() {
			Item item1 = new Item();
			Item item2 = new Item();
			Item item3 = new Item();
			Auction auction1 = new Auction();
			Auction auction2 = new Auction();
			Auction auction3 = new Auction();

			item1.setId(nextId.incrementAndGet());
			item1.setDescription("Futon" + item1.getId());

			item2.setId(nextId.incrementAndGet());
			item2.setDescription("4K OLED TV" + item2.getId());

			item3.setId(nextId.incrementAndGet());
			item3.setDescription("coffee mug" + item3.getId());

			auction1.setItem(item1);
			auction1.setSeller(user1);
			auction1.setCurrentBid(25.00);

			auction2.setItem(item2);
			auction2.setSeller(user2);
			auction2.setCurrentBid(999.00);

			auction3.setItem(item3);
			auction3.setSeller(user3);
			auction3.setCurrentBid(1.00);

			sellingService.createListing(auction1);
			sellingService.createListing(auction2);
			sellingService.createListing(auction3);

			sellingService.start(auction1);
			sellingService.start(auction2);
			sellingService.start(auction3);
		}

	}

}
