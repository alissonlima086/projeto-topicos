package br.unitins.topicos1.model;

public enum Binding{
    CAPA_DURA(1, "Hardcover"), CAPA_COMUM(2, "Paperback");

    private int id;
    private String label;

    Binding(int id, String label){
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Binding valueOf(Integer id) throws IllegalArgumentException{
        if(id == null){
            return null;
        }
        for(Binding binding : Binding.values()){
            if(id.equals(binding.getId())){
                return binding;
            }
        }

        throw new IllegalArgumentException("id invalido: "+ id);
    }
}