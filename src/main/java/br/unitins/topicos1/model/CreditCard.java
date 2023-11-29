package br.unitins.topicos1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Payment {

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String impressedCardName;

    @Column(nullable = false)
    private String cpfOwner;

    private CardBrand cardBrand;

    public CreditCard(Double valor, String cardNumber, String impressedCardName,
            String cpfOwner, CardBrand cardBrand) {

        super(valor);

        this.cardNumber = cardNumber;
        this.impressedCardName = impressedCardName;
        this.cpfOwner = cpfOwner;
        this.cardBrand = cardBrand;
    }

    public CreditCard() {

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getImpressedCardName() {
        return impressedCardName;
    }

    public void setImpressedCardName(String impressedCardName) {
        this.impressedCardName = impressedCardName;
    }

    public String getCpfOwner() {
        return cpfOwner;
    }

    public void setCpfOwner(String cpfOwner) {
        this.cpfOwner = cpfOwner;
    }

    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrand cardBrand) {
        this.cardBrand = cardBrand;
    }

}
