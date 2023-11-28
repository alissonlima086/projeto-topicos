package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Phone;
import br.unitins.topicos1.model.User;

public record PhoneResponseDTO (
    Long id,
    String areaCode,
    String number
){
    public static PhoneResponseDTO valueOf (Phone phone) {
        return new PhoneResponseDTO(phone.getId(), phone.getAreaCode(), phone.getNumber());
    }
}
