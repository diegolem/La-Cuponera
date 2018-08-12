package sv.edu.udb.www.beans;

import java.util.List;

/**
 *
 * @author Diego Lemus
 */
public class PromotionState {
    private int idPromotionState;
    private String state;
    private List<Promotion> promotions;

    public int getIdPromotionState() {
        return idPromotionState;
    }

    public void setIdPromotionState(int idPromotionState) {
        this.idPromotionState = idPromotionState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
    
    // Constructores
    public PromotionState(){
        this.idPromotionState = 0;
        this.state = "";
        this.promotions = null;
    }
    
    public PromotionState(int idPromotionState, String state){
        this.idPromotionState = idPromotionState;
        this.state = state;
        this.promotions = null;
    }
    
    public PromotionState(int idPromotionState, String state, List<Promotion> promotions){
        this.idPromotionState = idPromotionState;
        this.state = state;
        this.promotions = promotions;
    }
}
