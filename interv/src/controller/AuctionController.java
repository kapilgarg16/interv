package controller;

import model.Seller;
import service.AuctionService;

public class AuctionController {
    private AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    public void handleAddBuyer(String name) {
        auctionService.addBuyer(name);
    }

    public void handleAddSeller(String name) {
        auctionService.addSeller(name);
    }

    public void handleCreateAuction(String id, double lowestBidLimit, double highestBidLimit, double participationCost, String sellerName) {
        try {
            auctionService.createAuction(id, lowestBidLimit, highestBidLimit, participationCost, sellerName);
            System.out.println("Auction created successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleCreateBid(String buyerName, String auctionId, double amount) {
        try {
            auctionService.createBid(buyerName, auctionId, amount);
            System.out.println("Bid created successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleCloseAuction(String auctionId) {
        try {
            String winner = auctionService.closeAuction(auctionId);
            System.out.println("Auction closed. Winner: " + winner);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleGetProfitLoss(String seller, String auctionId) {
        try {
            double profitLoss = auctionService.getProfitLoss(seller, auctionId);
            System.out.println("Profit/Loss for seller: " + profitLoss);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}