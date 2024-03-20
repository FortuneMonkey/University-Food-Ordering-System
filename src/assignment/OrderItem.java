package Assignment;

public class OrderItem extends Item {
    private int quantity;
    private String extraNotes;
    private String itemName;
    private String itemId;
    
    // Constructors
    public OrderItem(String itemId, String itemName, String itemDesc, double itemPrice, int quantity, String extraNotes) {
        super(VenID, itemId, itemName, itemDesc, itemPrice);
        this.quantity = quantity;
        this.extraNotes = extraNotes;
        this.itemName = itemName;
        this.itemId = itemId;
    }
    
    // Getters and Setters for quantity and extraNotes
    
    public String getItemId(){
        return itemId;
    }
    public int getQuantity() {
        return quantity;
    }
    
    public String getItemName(){
        return itemName;
    }

    public String getExtraNotes() {
        return extraNotes;
    }
    
    public double calculateTotalPrice() {
        return getPrice() * quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
    }
    
    public String getItemName(String vendorId){
        String itemName = Item.getItemName(ItemID, vendorId);
        return itemName;
    }
}