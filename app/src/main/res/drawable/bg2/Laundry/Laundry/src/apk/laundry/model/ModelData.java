package apk.laundry.model;
public class ModelData {

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return the profit
     */
    public double getProfit() {
        return profit;
    }

    /**
     * @param profit the profit to set
     */
    public void setProfit(double profit) {
        this.profit = profit;
    }
//    public ModelData(String month, double amount, double cost, double profit){
    public ModelData(String month, double amount){
        this.month=month;
        this.amount=amount;
//        this.cost=cost;
//        this.profit=profit;
    }
    public ModelData(){
        
    }
    private String month;
    private double amount;
    private double cost;
    private double profit;
}
