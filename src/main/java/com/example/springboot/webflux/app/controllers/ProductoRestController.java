package com.example.springboot.webflux.app.controllers;

import com.example.springboot.webflux.app.models.dao.ProductoDao;
import com.example.springboot.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final ProductoDao productoDao;

  public ProductoRestController(ProductoDao productoDao) {
    this.productoDao = productoDao;
  }

  @GetMapping
  public Flux<Producto> index() {
    Flux<Producto> productos = productoDao.findAll()
            .map(producto -> {
              producto.setNombre(producto.getNombre().toUpperCase());
              return producto;
            })
            .doOnNext(producto -> logger.info(producto.getNombre()));

    return productos;
  }

  @GetMapping("/{id}")
  public Mono<Producto> show(@PathVariable String id) {
//    Mono<Producto> producto = productoDao.findById(id);
    Flux<Producto> productos = productoDao.findAll();

    Mono<Producto> producto = productos.filter(p -> p.getId().equals(id))
            .next()
            .doOnNext(prod -> logger.info(prod.getNombre()));

    return producto;
  }
}
