package br.com.fiap.coolshoes.services;

import br.com.fiap.coolshoes.dto.ProductCreateUpdateDTO;
import br.com.fiap.coolshoes.dto.ProductDTO;
import br.com.fiap.coolshoes.dto.ProductPriceUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private List<ProductDTO> productDTOS = new ArrayList<>();

    @Override
    public List<ProductDTO> findAll(Integer numero) {
        return productDTOS.stream()
                .filter(productDTO -> numero == null || numero.equals(productDTO.getNumero()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        return null;
    }

    @Override
    public ProductDTO create(ProductCreateUpdateDTO productCreateUpdateDTO) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(productDTOS.size() + 1L);
        productDTO.setCor(productCreateUpdateDTO.getCor());
        productDTO.setPreco(productCreateUpdateDTO.getPreco());
        productDTO.setModelo(productCreateUpdateDTO.getModelo());
        productDTO.setNumero(productCreateUpdateDTO.getNumero());
        productDTOS.add(productDTO);

        return productDTO;
    }

    @Override
    public ProductDTO update(Long id, ProductCreateUpdateDTO productCreateUpdateDTO) {
        return null;
    }

    @Override
    public ProductDTO updatePrice(Long id, ProductPriceUpdateDTO productPriceUpdateDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}