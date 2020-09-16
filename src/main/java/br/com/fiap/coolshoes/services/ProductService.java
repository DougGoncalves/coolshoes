package br.com.fiap.coolshoes.services;

import java.util.List;

import br.com.fiap.coolshoes.dto.ProductCreateUpdateDTO;
import br.com.fiap.coolshoes.dto.ProductDTO;
import br.com.fiap.coolshoes.dto.ProductPriceUpdateDTO;

public interface ProductService {

  List<ProductDTO> findAll(Integer numero);
  ProductDTO findById(Long id);
  ProductDTO create(ProductCreateUpdateDTO productCreateUpdateDTO);
  ProductDTO update(Long id, ProductCreateUpdateDTO productCreateUpdateDTO);
  ProductDTO updatePrice(Long ig, ProductPriceUpdateDTO productPriceUpdateDTO);
  void delete(Long id);
  
}
