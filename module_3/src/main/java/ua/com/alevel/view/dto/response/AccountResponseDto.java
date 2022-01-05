package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.cardType.CardType;
import ua.com.alevel.persistence.entity.Account;

import java.util.UUID;

public class AccountResponseDto extends ResponseDto {

    private String cardNumber;
    private Double balance;
    private CardType cardType;
    private Integer transactionCount;

    public AccountResponseDto() {
    }

    public AccountResponseDto(Account account) {
        setId(account.getId());
        setCreated(account.getCreated());
        setUpdated(account.getUpdated());
        setVisible(account.getVisible());
        this.balance = account.getBalance();
        this.cardType = account.getCardType();
        this.cardNumber = account.getCardNumber();
    }

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

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }
}
