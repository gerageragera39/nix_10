package ua.com.alevel.view.dto.request;

import ua.com.alevel.persistence.cardType.CardType;

public class AccountRequestDto extends RequestDto {

    private String cardNumber;
    private Double balance;
    private CardType cardType;
    private Double hryvnas;
    private Double penny;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
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
