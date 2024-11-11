package service;

import model.Auction;
import model.Bid;
import model.Buyer;
import model.Seller;

import java.util.HashMap;
import java.util.Map;

public class AuctionService {

    private Map<String, Auction> auctions = new HashMap<>();
    private Map<String, Buyer> buyers = new HashMap<>();
    private Map<String, Seller> sellers = new HashMap<>();

    public void addBuyer(String name) {
        buyers.put(name, new Buyer(name));
    }

    public void addSeller(String name) {
        sellers.put(name, new Seller(name));
    }

    public void createAuction(String id, double lowestBidLimit, double highestBidLimit, double participationCost, String sellerName) throws Exception {
        if (!sellers.containsKey(sellerName)) {
            throw new Exception("Seller not found");
        }
        Seller seller = sellers.get(sellerName);
        Auction auction = new Auction(id, lowestBidLimit, highestBidLimit, participationCost, seller);
        auctions.put(id, auction);
    }

    public void createBid(String buyerName, String auctionId, double amount) throws Exception {
        if (!buyers.containsKey(buyerName)) {
            throw new Exception("Buyer not found");
        }
        if (!auctions.containsKey(auctionId)) {
            throw new Exception("Auction not found");
        }
        Auction auction = auctions.get(auctionId);
        auction.addBid(buyerName, amount);
    }

    public void updateBid(String buyerName, String auctionId, double amount) throws Exception {
        if (!buyers.containsKey(buyerName)) {
            throw new Exception("Buyer not found");
        }
        if (!auctions.containsKey(auctionId)) {
            throw new Exception("Auction not found");
        }
        Auction auction = auctions.get(auctionId);
        auction.updateBid(buyerName, amount);
    }

    public void withdrawBid(String buyerName, String auctionId) throws Exception {
        if (!buyers.containsKey(buyerName)) {
            throw new Exception("Buyer not found");
        }
        if (!auctions.containsKey(auctionId)) {
            throw new Exception("Auction not found");
        }
        Auction auction = auctions.get(auctionId);
        auction.withdrawBid(buyerName);
    }

    public String closeAuction(String auctionId) throws Exception {
        if (!auctions.containsKey(auctionId)) {
            throw new Exception("Auction not found");
        }
        Auction auction = auctions.get(auctionId);
        auction.closeAuction();

        return calculateWinner(auction);
    }

    private String calculateWinner(Auction auction) {
        Map<String, Bid> bids = auction.getBids();
        Map<Double, String> uniqueBids = new HashMap<>();

        for (Bid bid : bids.values()) {
            if (!uniqueBids.containsKey(bid.getAmount())) {
                uniqueBids.put(bid.getAmount(), bid.getBuyerName());
            } else {
                uniqueBids.remove(bid.getAmount());
            }
        }

        double highestUniqueBid = -1;
        String winner = null;
        for (Map.Entry<Double, String> entry : uniqueBids.entrySet()) {
            if (entry.getKey() > highestUniqueBid) {
                highestUniqueBid = entry.getKey();
                winner = entry.getValue();
            }
        }

        return winner != null ? winner : "No winner";
    }

    public double getProfitLoss(String seller, String auctionId) throws Exception {
        if (!auctions.containsKey(auctionId)) {
            throw new Exception("Auction not found");
        }
        Auction auction = auctions.get(auctionId);
        if (!auction.getSeller().getName().equals(seller)) {
            throw new Exception("Seller not associated with this auction");
        }

        double totalParticipationCost = auction.getBids().size() * 0.2 * auction.getParticipationCost();
        double avgBid = (auction.getLowestBidLimit() + auction.getHighestBidLimit()) / 2;
        double winningBid = -1;

        String winner = calculateWinner(auction);
        if (!winner.equals("No winner")) {
            for (Bid bid : auction.getBids().values()) {
                if (bid.getBuyerName().equals(winner)) {
                    winningBid = bid.getAmount();
                }
            }
        }

        double profitLoss = (winningBid != -1 ? winningBid : 0) + totalParticipationCost - avgBid;
        return profitLoss;
    }
}
