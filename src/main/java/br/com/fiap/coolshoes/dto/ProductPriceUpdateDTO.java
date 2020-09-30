package br.com.fiap.coolshoes.dto;

import java.math.BigDecimal;

public class ProductPriceUpdateDTO {

  private BigDecimal preco;

  public BigDecimal getPreco() {
    return this.preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

}