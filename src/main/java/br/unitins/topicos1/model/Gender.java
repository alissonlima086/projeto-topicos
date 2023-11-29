package br.unitins.topicos1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
    MASCULINE(1, "Masculino"),
    FEMININE(2, "Feminino");

    private int id;
    private String label;

    Gender(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Gender valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (Gender gender : Gender.values()) {
            if (id.equals(gender.getId()))
                return gender;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }
}
