package br.com.fiap.coolshoes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.coolshoes.dto.ProductCreateUpdateDTO;
import br.com.fiap.coolshoes.dto.ProductDTO;
import br.com.fiap.coolshoes.dto.ProductPriceUpdateDTO;

@RestController
@RequestMapping("products")
public class ProductController {
  
  private List<ProductDTO> productDTOS = new ArrayList<>();

  public ProductController() {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId(1L);
    productDTO.setCor("Branco");
    productDTO.setModelo("Nike Jordan");
    productDTO.setNumero(40);
    productDTO.setPreco(BigDecimal.valueOf(300.0));

    productDTOS.add(productDTO);

    ProductDTO productDTO01 = new ProductDTO();
    productDTO01.setId(1L);
    productDTO01.setCor("Preto");
    productDTO01.setModelo("Adidas Bounce");
    productDTO01.setNumero(39);
    productDTO01.setPreco(BigDecimal.valueOf(350.0));

    productDTOS.add(productDTO01);


  }

  @GetMapping
  public List<ProductDTO> listAll(@RequestParam(required = false) Integer numero){
    return productDTOS.stream()
            .filter(ProductDTO -> numero == null ||numero.equals(ProductDTO.getNumero()))
            .collect(Collectors.toList());
  }

  @GetMapping("{id}")
  public ProductDTO getById(@PathVariable Long id){
    return productDTOS.stream()
            .filter(productDTO -> id.equals(productDTO.getId()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não encontrado"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDTO create(@RequestBody ProductCreateUpdateDTO productCreateUpdateDTO) {
    ProductDTO productDTO = new ProductDTO();

    productDTO.setId(productDTOS.size() + 1L);
    productDTO.setCor(productCreateUpdateDTO.getCor());
    productDTO.setModelo(productCreateUpdateDTO.getModelo());
    productDTO.setPreco(productCreateUpdateDTO.getPreco());
    productDTO.setNumero(productCreateUpdateDTO.getNumero());
    productDTOS.add(productDTO);

    return productDTO;
  }

  @PutMapping("{id}")
  public ProductDTO update(@PathVariable Long id, @RequestBody ProductCreateUpdateDTO productCreateUpdateDTO){

    ProductDTO productDTO = getById(id);
    productDTO.setCor(productCreateUpdateDTO.getCor());
    productDTO.setModelo(productCreateUpdateDTO.getModelo());
    productDTO.setPreco(productCreateUpdateDTO.getPreco());
    productDTO.setNumero(productCreateUpdateDTO.getNumero());

    return productDTO;
  }

  @PatchMapping("{id}/price")
  public ProductDTO updatePrice(@PathVariable Long id, @RequestBody ProductPriceUpdateDTO productPriceUpdateDTO){

    ProductDTO productDTO = getById(id);
    productDTO.setPreco(productPriceUpdateDTO.getPreco());
 
    return productDTO;
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id){
    ProductDTO productDTO = getById(id);
    productDTOS.remove(productDTO);
  }
  
}
