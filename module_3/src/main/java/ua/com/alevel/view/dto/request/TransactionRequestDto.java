package ua.com.alevel.view.dto.request;

import ua.com.alevel.persistence.entity.Category;

public class TransactionRequestDto extends RequestDto {

    private Double amount;

    private Double hryvnas;
    private Double penny;

    private String categoryName;

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

    public Double getHryvnas() {
        return hryvnas;
    }

    public void setHryvnas(Double hryvnas) {
        this.hryvnas = hryvnas;
    }

    public Double getPenny() {
        return penny;
    }

    public void setPenny(Double penny) {
        this.penny = penny;
    }
}
