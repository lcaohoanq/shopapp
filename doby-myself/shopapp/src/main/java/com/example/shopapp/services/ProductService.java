package com.example.shopapp.services;

import com.example.shopapp.constants.ErrorMessage;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    //using @AutoWire to make field injection vs @RequiredArgsConstructor, final field, constructor injection
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {

        Category existedCategory = categoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() ->
                new DataNotFoundException("Category not found: " + productDTO.getCategoryId()));

        Product newProduct = Product.builder()
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .thumbnail(productDTO.getThumbnail())
            .category(existedCategory)
            .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId).orElseThrow(
            () -> new DataNotFoundException("Cannot find product with id: " + productId));
    }


    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        //take input page, limit to pageRequest from Controller

        //convert Product -> ProductResponse
        return productRepository.findAll(
            pageRequest).map(product -> {
            ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getUpdatedAt());
            return productResponse;
        });
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            //Copy cac thuoc tinh tu DTO -> Product
            Category existedCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                    new DataNotFoundException("Category not found: " + productDTO.getCategoryId()));

            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existedCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id).ifPresent(product -> productRepository.delete(product));
        //ifPresent() method is used to check whether the value is present or not. (from Optional class)
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO)
        throws Exception {
        Product existingProduct = productRepository
            .findById(productId)
            .orElseThrow(() ->
                new DataNotFoundException("Category not found: " + productImageDTO.getProductId()));

        ProductImage newProductImage = ProductImage.builder()
            .product(existingProduct)
            .imageUrl(productImageDTO.getImageUrl())
            .build();
        //khong cho insert qua 5 anh cho mot san pham
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                ErrorMessage.MAXIMUM_IMAGES_PER_PRODUCT
                    + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }

}
