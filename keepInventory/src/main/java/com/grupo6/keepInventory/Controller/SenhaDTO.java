package com.grupo6.keepInventory.Controller;

public record SenhaDTO(String senha) {
    public SenhaDTO {
    }

    @Override
    public String senha() {
        return senha;
    }
}
