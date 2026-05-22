package br.com.serratec.trabalho.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("AUTONOMO")
@Entity
public class VendedorAutonomo extends Vendedor {

    private Double comissao;

    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }


}
