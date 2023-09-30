package br.unitins.topicos1.model;

public enum Type{
    COMIC(1, "Comic"), MANGA(2, "Manga");

    private int id;
    private String label;

    Type(int id, String label){
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Type valueOf(Integer id) throws IllegalArgumentException{
        if(id == null){
            return null;
        }
        for(Type type : Type.values()){
            if(id.equals(type.getId())){
                return type;
            }
        }

        throw new IllegalArgumentException("id invalido: "+ id);
    }
}