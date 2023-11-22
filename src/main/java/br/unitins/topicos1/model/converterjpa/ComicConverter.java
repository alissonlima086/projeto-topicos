package br.unitins.topicos1.model.converterjpa;

import br.unitins.topicos1.model.Binding;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ComicConverter implements AttributeConverter<Binding, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Binding binding) {
        return (binding == null ? null : binding.getId());
    }

    @Override
    public Binding convertToEntityAttribute(Integer id) {
        return Binding.valueOf(id);
    }
    
}
