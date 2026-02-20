package network.narrative.auction.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import network.narrative.auction.domain.Auction;
import network.narrative.auction.domain.BidResult;
import network.narrative.auction.domain.User;

/*
 *  this is a naive design where we're only creating one service; a full blown auction system
 *  would obviously have multiple services, so I would not design it to have all business logic
 *  in one giant "god" class.
 */

/**
 * Service that manages auctions.
 * 
 * @author ohernandez
 *
 */
public interface Selling {

	/**
	 * Add an auction to the site;
	 * 
	 * @param auction
	 *            a newly created
	 *            {@link network.narrative.auction.domain.Auction Auction}
	 * 
	 */
	void createListing(Auction auction);

	/**
	 * Retrieve all the auctions that are still active.
	 * 
	 * @return a collection of {@link network.narrative.auction.domain.Auction
	 *         Auction} objects
	 */
	Collection<Auction> getActiveListings();

	/**
	 * Retrieve all auctions.
	 * 
	 * @return a collection of {@link network.narrative.auction.domain.Auction
	 *         Auction} objects
	 */
	Collection<Auction> getAllListings();

	/**
	 * Start an auction.
	 * 
	 * @param auction
	 *            the {@link network.narrative.auction.domain.Auction Auction}
	 *            to start the bidding on.
	 */
	void start(Auction auction); // TODO validate necessary Auction attrs populated, throw checked exception if not 

	/**
	 * Places a bid on an auction.
	 * 
	 * @param auctionId
	 *            the ID of the {@link network.narrative.auction.domain.Auction
	 *            Auction} a bid is being placed on
	 * @param bid
	 *            the amount of the bid
	 * @param bidder
	 *            user placing the bid
	 * @return a {@link java.util.concurrent.CompletableFuture
	 *         CompletableFuture} containing a
	 *         {@link network.narrative.auction.domain.BidResult BidResult}
	 *         containing whether or not the bid was accepted
	 */
	CompletableFuture<BidResult> placeBid(Integer auctionId, Double bid, User bidder);
}
