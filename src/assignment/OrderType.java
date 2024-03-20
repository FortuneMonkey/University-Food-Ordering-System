
package Assignment;


public enum OrderType {
    DINE_IN("Dine-in"),
    TAKEAWAY("Takeaway"),
    DELIVERY("Delivery");

    private final String displayName;

    OrderType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}    

