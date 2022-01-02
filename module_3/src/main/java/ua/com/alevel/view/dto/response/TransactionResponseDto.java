package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.persistence.entity.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class TransactionResponseDto extends ResponseDto {

    private Double amount;
    private String categoryName;
    private boolean flag;

    public TransactionResponseDto(Transaction transaction){
        setId(transaction.getId());
        setCreated(transaction.getCreated());
        setUpdated(transaction.getUpdated());
        setVisible(transaction.getVisible());
        this.amount = transaction.getAmount();
        this.categoryName = transaction.getCategory().getName();
        this.flag = transaction.getCategory().getFinances();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getColor(){
        if(this.flag){
            return "incomePlus";
        } else{
            return "incomeMinus";
        }
    }
}
