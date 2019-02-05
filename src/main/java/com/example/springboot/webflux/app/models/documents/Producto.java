package com.example.springboot.webflux.app.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "productos")
public class Producto {

  @Id
  private String id;

  private String nombre;

  private Double precio;

  private Date createAt;

  public Producto() {}

  public Producto(String nombre, Double precio) {
    this.nombre = nombre;
    this.precio = precio;
  }

  public static class Builder {
    private String nombre;
    private Double precio;

    public Builder() {}

    public Builder setNombre(String nombre) {
      this.nombre = nombre;
      return this;
    }

    public Builder setPrecio(Double precio) {
      this.precio = precio;
      return this;
    }

    public Producto build() {
      return new Producto(nombre, precio);
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }
}
