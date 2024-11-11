package model;

import model.Bid;
import model.Seller;

import java.util.HashMap;
import java.util.Map;

public class Auction {
    private String id;
    private double lowestBidLimit;
    private double highestBidLimit;
    private double participationCost;
    private Seller seller;
    private Map<String, Bid> bids = new HashMap<>();
    private boolean isClosed;

    public Auction(String id, double lowestBidLimit, double highestBidLimit, double participationCost, Seller seller) {
        this.id = id;
        this.lowestBidLimit = lowestBidLimit;
        this.highestBidLimit = highestBidLimit;
        this.participationCost = participationCost;
        this.seller = seller;
        this.isClosed = false;
    }

    public String getId() {
        return id;
    }

    public double getLowestBidLimit() {
        return lowestBidLimit;
    }

    public double getHighestBidLimit() {
        return highestBidLimit;
    }

    public double getParticipationCost() {
        return participationCost;
    }

    public Seller getSeller() {
        return seller;
    }

    public void addBid(String buyerName, double amount) throws Exception {
        if (isClosed) {
            throw new Exception("Auction is already closed");
        }
        if (amount < lowestBidLimit || amount > highestBidLimit) {
            throw new Exception("Bid amount is out of auction limits");
        }
        bids.put(buyerName, new Bid(buyerName, amount));
    }

    public void updateBid(String buyerName, double amount) throws Exception {
        if (isClosed) {
            throw new Exception("Auction is already closed");
        }
        if (bids.containsKey(buyerName)) {
            addBid(buyerName, amount);
        } else {
            throw new Exception("Bidder not found");
        }
    }

    public void withdrawBid(String buyerName) {
        bids.remove(buyerName);
    }

    public Map<String, Bid> getBids() {
        return bids;
    }

    public void closeAuction() {
        this.isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }
}