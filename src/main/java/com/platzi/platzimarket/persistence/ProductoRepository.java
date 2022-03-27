package com.platzi.platzimarket.persistence;

import com.platzi.platzimarket.domain.Product;
import com.platzi.platzimarket.domain.repository.ProductRepository;
import com.platzi.platzimarket.persistence.crud.ProductoCrudRepository;
import com.platzi.platzimarket.persistence.entity.Producto;
import com.platzi.platzimarket.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){
        return mapper.toProducts((List<Producto>) productoCrudRepository.findAll());
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId){
        return Optional.of(mapper.toProducts(productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity){
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true)
                .map(producto -> mapper.toProducts(producto));
    }

    @Override
    public Optional<Product> getProduct(int idProducto){
        return productoCrudRepository.findById(idProducto).map( prod -> mapper.toProduct(prod));
    }

    @Override
    public Product save(Product product){
        return mapper.toProduct(productoCrudRepository.save(mapper.toProducto(product)));
    }

    @Override
    public void delete(int idProducto){
        productoCrudRepository.deleteById(idProducto);
    }
}
