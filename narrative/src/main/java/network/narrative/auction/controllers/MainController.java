package network.narrative.auction.controllers;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import network.narrative.auction.config.Application;
import network.narrative.auction.domain.Auction;
import network.narrative.auction.domain.BidResult;
import network.narrative.auction.domain.User;
import network.narrative.auction.service.Selling;

@Controller
public class MainController {

	@Autowired
	private Selling sellingService;

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request, Model model) {
		Collection<Auction> listings = sellingService.getAllListings();
		model.addAttribute("listings", listings);

		return new ModelAndView("HomePage", "model", model);
    }

	// TODO gracefully handle exceptions
	@RequestMapping(value = "/bid", method = RequestMethod.POST)
	public ModelAndView bid(@RequestParam(value = "userId") int userId,
			@RequestParam(value = "auctionId") int auctionId, @RequestParam(value = "bid") double bid,
			HttpServletRequest request, Model model) throws InterruptedException, ExecutionException {

		User bidder = null;

		switch (userId) {
		case 1:
			bidder = Application.user1;
			break;
		case 2:
			bidder = Application.user2;
			break;
		case 3:
			bidder = Application.user3;
			break;
		}

		CompletableFuture<BidResult> future = sellingService.placeBid(auctionId, bid, bidder);
		BidResult result = future.get();  // wait until we know if bid was successful or not

		model.addAttribute("bidResult", result);

		Collection<Auction> listings = sellingService.getAllListings();
		model.addAttribute("listings", listings);

		return new ModelAndView("HomePage", "model", model);
	}

}
