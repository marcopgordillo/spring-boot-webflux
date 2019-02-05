package com.example.springboot.webflux.app;

import com.example.springboot.webflux.app.models.dao.ProductoDao;
import com.example.springboot.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final ProductoDao productoDao;
  private final ReactiveMongoTemplate mongoTemplate;

  public SpringBootWebfluxApplication(ProductoDao productoDao, ReactiveMongoTemplate mongoTemplate) {
    this.productoDao = productoDao;
    this.mongoTemplate = mongoTemplate;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootWebfluxApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    mongoTemplate.dropCollection("productos").subscribe();

    Flux.just(new Producto("TV Panasonic pantalla LED", 456.89),
            new Producto("Sony camara Digital HD", 178.95),
            new Producto("Apple iPod", 45.89),
            new Producto("Sony notebook", 846.95),
            new Producto("Hewlett PAckard Multifuncional", 178.95),
            new Producto("Sony camara Digital HD", 178.95),
            new Producto("Bianchi Bicicleta", 70.95))
            .flatMap(producto -> productoDao.save(producto))
            .subscribe(producto -> logger.info("Insert: " + producto.getId() + " " + producto.getNombre()));
  }
}

