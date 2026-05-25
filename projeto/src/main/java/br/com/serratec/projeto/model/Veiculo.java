package br.com.serratec.projeto.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "A placa é obrigatória.")
    @Size(min = 7, max = 8, message = "A placa deve ter entre 7 e 8 caracteres.")
    @Column(unique = true)
    private String placa;

    @NotBlank(message = "A marca é obrigatória.")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório.")
    private String modelo;

    @NotBlank(message = "A data/ano é obrigatória.")
    @PastOrPresent(message = "A data do veículo não pode ser futura.")
    private LocalDate ano;

    private String cor;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDate getAno() {
        return ano;
    }

    public void setAno(LocalDate ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

}

