package acquire;

public class Stock implements StockInterface{
    private String corpName;
    private int price;

    protected Stock(String corpName, int price) {
        this.corpName = corpName;
        this.price = price;
    }

    protected String getCorpName() {
        return corpName;
    }

    protected int getPrice() {
        return price;
    }
}
