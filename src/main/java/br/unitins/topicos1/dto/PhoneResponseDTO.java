package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Phone;
import br.unitins.topicos1.model.User;

public record PhoneResponseDTO (
    String areaCode,
    String number,
    User user
){
    public static PhoneResponseDTO valueOf (Phone phone) {
        return new PhoneResponseDTO(phone.getAreaCode(), phone.getNumber(), phone.getUser());
    }
}
