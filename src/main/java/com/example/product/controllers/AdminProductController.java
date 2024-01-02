package com.example.product.controllers;

import com.example.product.applications.AddAdminProductFirstOptionService;
import com.example.product.applications.AddAdminProductSecondOptionService;
import com.example.product.applications.AddProductImageService;
import com.example.product.applications.AddProductService;
import com.example.product.applications.DeleteProductFirstOptionService;
import com.example.product.applications.DeleteProductImageService;
import com.example.product.applications.DeleteProductSecondOptionService;
import com.example.product.applications.DeleteProductService;
import com.example.product.applications.GetAdminProductDetailService;
import com.example.product.applications.GetAdminProductsService;
import com.example.product.applications.UpdateProdcutService;
import com.example.product.applications.UpdateProductImageService;
import com.example.product.dtos.AddAdminFirstOptionRequestDto;
import com.example.product.dtos.AddAdminSecondOptionRequestDto;
import com.example.product.dtos.AddProductRequestDto;
import com.example.product.dtos.GetProductByCategoryDto;
import com.example.product.dtos.GetProductDetailDto;
import com.example.product.dtos.UpdateProductImageResponseDto;
import com.example.product.dtos.UpdateProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product/admin")
public class AdminProductController {

    private final AddProductService addProductService;
    private final GetAdminProductsService getAdminProductsService;
    private final GetAdminProductDetailService getAdminProductDetailService;

    private final AddAdminProductFirstOptionService addAdminProductFirstOptionService;
    private final AddAdminProductSecondOptionService addAdminProductSecondOptionService;
    private final DeleteProductSecondOptionService deleteProductSecondOptionService;
    private final DeleteProductFirstOptionService deleteProductFirstOptionService;

    private final DeleteProductService deleteProductService;

    private final DeleteProductImageService deleteProductImageService;
    private final AddProductImageService addProductImageService;
    private final UpdateProdcutService updateProdcutService;

    private final UpdateProductImageService updateProductImageService;



    public AdminProductController(AddProductService addProductService, GetAdminProductsService getAdminProductsService, GetAdminProductDetailService getAdminProductDetailService, AddAdminProductFirstOptionService addAdminProductFirstOptionService, AddAdminProductSecondOptionService addAdminProductSecondOptionService, DeleteProductSecondOptionService deleteProductSecondOptionService, DeleteProductFirstOptionService deleteProductFirstOptionService, DeleteProductService deleteProductService, DeleteProductImageService deleteProductImageService, AddProductImageService addProductImageService, UpdateProdcutService updateProdcutService, UpdateProductImageService updateProductImageService) {
        this.addProductService = addProductService;
        this.getAdminProductsService = getAdminProductsService;
        this.getAdminProductDetailService = getAdminProductDetailService;
        this.addAdminProductFirstOptionService = addAdminProductFirstOptionService;
        this.addAdminProductSecondOptionService = addAdminProductSecondOptionService;
        this.deleteProductSecondOptionService = deleteProductSecondOptionService;
        this.deleteProductFirstOptionService = deleteProductFirstOptionService;
        this.deleteProductService = deleteProductService;
        this.deleteProductImageService = deleteProductImageService;
        this.addProductImageService = addProductImageService;
        this.updateProdcutService = updateProdcutService;
        this.updateProductImageService = updateProductImageService;
    }

    @PostMapping("/product-image")
    @ResponseStatus(HttpStatus.CREATED)
    public String addImage(
            @RequestPart(value = "productId") Long productId,
            @RequestPart(value = "image") MultipartFile image
    ) throws IOException {

        return addProductImageService.addProductImageService(productId, image);
    }


    @DeleteMapping("/product-image/{productImageId}")
    public String deleteProductImage(@PathVariable Long productImageId) throws IOException {
        return deleteProductImageService.deleteProductImage(productImageId);
    }

    @PatchMapping("/product-image")
    public String updateProductImage(
            @RequestPart(value = "UpdateProductImageResponseDto") UpdateProductImageResponseDto updateProductImageResponseDto,
            @RequestPart(value = "image") MultipartFile image
    ) throws IOException {


        return updateProductImageService.updateProductImageService(
                updateProductImageResponseDto.productId(),
                updateProductImageResponseDto.productImageId(),
                image);
    }


    @PostMapping("/first-option")
    @ResponseStatus(HttpStatus.CREATED)
    public String addFirstOption(@Valid @RequestBody AddAdminFirstOptionRequestDto addAdminFirstOptionRequestDto) {

        return addAdminProductFirstOptionService
                .addAdminProductFirstOption(
                        addAdminFirstOptionRequestDto.productId(),
                        addAdminFirstOptionRequestDto.name(),
                        addAdminFirstOptionRequestDto.price()
                );
    }

    @DeleteMapping("/first-option/{productFirstOptionId}")
    public String deleteFirstOption(@PathVariable Long productFirstOptionId) {
        return deleteProductFirstOptionService
                .deleteProductFirstOption(productFirstOptionId);
    }

    @PostMapping("/second-option")
    @ResponseStatus(HttpStatus.CREATED)
    public String addSecondOption(@Valid @RequestBody AddAdminSecondOptionRequestDto addAdminSecondOptionRequestDto) {
        return addAdminProductSecondOptionService
                .addAdminProductSecondOption(
                        addAdminSecondOptionRequestDto.productFirstOptionId(),
                        addAdminSecondOptionRequestDto.name(),
                        addAdminSecondOptionRequestDto.price()
                );
    }

    @DeleteMapping("/second-option/{productSecondOptionId}")
    public String deleteSecondOption(@PathVariable Long productSecondOptionId) {
        return deleteProductSecondOptionService
                .deleteProductSecondOption(productSecondOptionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String post(
            @RequestPart(value = "AddProductRequestDto") AddProductRequestDto addProductRequestDto,
            @RequestPart(value = "image") MultipartFile image
    ) throws IOException {

        return addProductService.addProduct(
                addProductRequestDto.categoryId(),
                addProductRequestDto.name(),
                addProductRequestDto.price(),
                addProductRequestDto.description(),
                image
        );
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId) throws IOException {
        return deleteProductService.deleteProduct(productId);
    }

    @GetMapping("/detail/{productId}")
    public GetProductDetailDto detail(@PathVariable Long productId){

        return getAdminProductDetailService.getProductDetail(productId);
    }

    @GetMapping
    public List<GetProductByCategoryDto> list(){

        return getAdminProductsService.getAdminProducts();
    }

    @PatchMapping
    public String update(@Valid @RequestBody UpdateProductRequestDto updateProductRequestDto) {

        return updateProdcutService.updateProduct(
                updateProductRequestDto.productId(),
                updateProductRequestDto.categoryId(),
                updateProductRequestDto.name(),
                updateProductRequestDto.price(),
                updateProductRequestDto.description()
        );
    }


}
