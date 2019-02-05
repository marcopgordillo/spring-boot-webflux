package com.example.springboot.webflux.app.controllers;

import com.example.springboot.webflux.app.models.dao.ProductoDao;
import com.example.springboot.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductoController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final ProductoDao productoDao;

  public ProductoController(ProductoDao productoDao) {
    this.productoDao = productoDao;
  }

  @GetMapping({"/listar", "/"})
  public String listar(Model model) {
    Flux<Producto> productos = productoDao.findAll()
            .map(producto -> {
              producto.setNombre(producto.getNombre().toUpperCase());
              return producto;
            });

    productos.subscribe(producto -> logger.info(producto.getNombre()));

    model.addAttribute("productos", productos);
    model.addAttribute("titulo", "Listado de productos");

    return "listar";
  }

  @GetMapping("/listar-datadriver")
  public String listarDataDriver(Model model) {
    Flux<Producto> productos = productoDao.findAll()
            .map(producto -> {
              producto.setNombre(producto.getNombre().toUpperCase());
              return producto;
            })
            .delayElements(Duration.ofSeconds(1));

    productos.subscribe(producto -> logger.info(producto.getNombre()));

    model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
    model.addAttribute("titulo", "Listado de productos");

    return "listar";
  }
}
