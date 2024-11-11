import controller.AuctionController;
import model.Seller;
import service.AuctionService;

public class AuctionSystem {
    public static void main(String[] args) {
        // Initialize service and controller layers
        AuctionService auctionService = new AuctionService();
        AuctionController auctionController = new AuctionController(auctionService);

        // 1. Add buyers
        System.out.println("Adding Buyers...");
        auctionController.handleAddBuyer("buyer1");
        auctionController.handleAddBuyer("buyer2");
        auctionController.handleAddBuyer("buyer3");

        // 2. Add sellers
        System.out.println("\nAdding Sellers...");
        auctionController.handleAddSeller("seller1");
        auctionController.handleAddSeller("seller2");

        System.out.println("\nCreating Auction A1...");
        auctionController.handleCreateAuction("A1", 10, 50, 3, "seller1");

        // 4. Place bids for Auction A1
        System.out.println("\nPlacing Bids for Auction A1...");
        auctionController.handleCreateBid("buyer1", "A1", 17); // buyer1 bids 17
        auctionController.handleCreateBid("buyer2", "A1", 19); // buyer2 bids 19
        auctionController.handleCreateBid("buyer3", "A1", 30); // buyer3 bids 30

        // 5. Close Auction A1 and display the winner
        System.out.println("\nClosing Auction A1...");
        auctionController.handleCloseAuction("A1");

        // 6. Get profit/loss for seller1 for Auction A1
        System.out.println("\nGetting Profit/Loss for seller1 in Auction A1...");
        auctionController.handleGetProfitLoss("seller1", "A1");


        System.out.println("\nCreating Auction A2...");
        auctionController.handleCreateAuction("A2", 5, 20, 2, "seller2");

        // 8. Place bids for Auction A2
        System.out.println("\nPlacing Bids for Auction A2...");
        auctionController.handleCreateBid("buyer1", "A2", 15); // buyer1 bids 15
        auctionController.handleCreateBid("buyer2", "A2", 18); // buyer2 bids 18

        // 9. Close Auction A2 and display the result (this will give a winner)
        System.out.println("\nClosing Auction A2...");
        auctionController.handleCloseAuction("A2");

        // 10. Get profit/loss for seller2 in Auction A2
        System.out.println("\nGetting Profit/Loss for seller2 in Auction A2...");
        auctionController.handleGetProfitLoss("seller2", "A2");
    }
}