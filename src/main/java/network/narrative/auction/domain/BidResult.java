package network.narrative.auction.domain;

/**
 * The results of a user placing a bid on an auction.
 * 
 * @author ohernandez
 *
 */
public class BidResult { // this class would not be a JPA entity

	private boolean succeeded;

	private String message;

	public boolean isSucceeded() {
		return succeeded;
	}

	public boolean getSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
