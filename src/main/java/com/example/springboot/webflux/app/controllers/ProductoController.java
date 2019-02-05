package com.example.springboot.webflux.app.controllers;

import com.example.springboot.webflux.app.models.dao.ProductoDao;
import com.example.springboot.webflux.app.models.documents.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

  private final ProductoDao productoDao;

  public ProductoController(ProductoDao productoDao) {
    this.productoDao = productoDao;
  }

  @GetMapping({"/listar", "/"})
  public String listar(Model model) {
    Flux<Producto> productos = productoDao.findAll();

    model.addAttribute("productos", productos);
    model.addAttribute("titulo", "Listado de productos");

    return "listar";
  }
}
