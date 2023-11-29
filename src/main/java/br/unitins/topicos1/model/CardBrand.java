package br.unitins.topicos1.model;

public enum CardBrand {

    VISA(1, "Visa"),
    AMERICAN_EXPRESS(2, "American Express"),
    HIPERCARD(3, "Hipercard"),
    DINERS(4, "Diners"),
    MASTERCARD(5, "Mastercard"),
    ELO(6, "Elo");

    private int id;
    private String label;

    CardBrand(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static CardBrand valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (CardBrand cardBrand : CardBrand.values()) {
            if (id.equals(cardBrand.getId())) {
                return cardBrand;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }

}