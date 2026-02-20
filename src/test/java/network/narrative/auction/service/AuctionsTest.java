package network.narrative.auction.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import network.narrative.auction.config.Application.SetUp;
import network.narrative.auction.domain.Auction;

public class AuctionsTest {

	private Auctions underTest;

	@Before
	public void setUp() {
		underTest = new Auctions();
	}

	@Test
	public void testCreateListingsThreadSafe() throws InterruptedException {
		Thread[] threads = new Thread[3];

		// create 3 threads that will add auction listings concurrently, racing
		// to increment and get the next auction id
		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(new SetUp(underTest));
			threads[i] = thread;
			thread.start();
		}

		// wait for all 3 threads to finish adding listings
		for (Thread thread : threads) {
			thread.join();
			System.out.println("finished adding listing");
		}

		List<Auction> auctions = underTest.getActiveListings();
		assertEquals(9, auctions.size());

		/*
		 * check there are no gaps in generated IDs due to a race condition
		 * incrementing the next ID value
		 */

		boolean[] idFound = new boolean[9];

		for (int i = 0; i < 9; i++) {
			// note, ids start at 1
			Auction auction = auctions.get(i);
			idFound[auction.getId() - 1] = true;
		}

		for (boolean found : idFound) {
			if (!found) {
				fail("Auctions.createListing() failed to handle race condition generating IDs");
			}
		}
	}

/* TODO
	@Test
	public void testCreateListing() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActiveListings() {
		fail("Not yet implemented");
	}

	@Test
	public void testStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlaceBid() {
		fail("Not yet implemented");
	}
*/
}
