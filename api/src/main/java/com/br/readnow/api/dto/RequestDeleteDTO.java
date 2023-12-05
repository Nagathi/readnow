package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDeleteDTO {
    private String email;
    private Long codigo;
}
