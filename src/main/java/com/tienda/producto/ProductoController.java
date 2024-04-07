package com.tienda.producto;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private ProductoDAO productoRepository;

	// Métodos para CRUD

	@GetMapping
	public List<Producto> listarProductos() {
		return (List<Producto>) productoRepository.findAll();
	}

	@PostMapping
	public Producto agregarProducto(@RequestBody Producto producto) {
		return productoRepository.save(producto);
	}

	@GetMapping("/{nombre}")
	public Producto obtenerProducto(@PathVariable String nombre) {

		if (productoRepository.findById(nombre).isPresent()) {
			return productoRepository.findById(nombre).get();
		}
		return null;
	}

	@PutMapping("/{nombre}")
	public Producto actualizarProducto(@PathVariable String nombre, @RequestBody Producto nuevoProducto) {
		Optional<Producto> optionalProducto = productoRepository.findById(nombre);
		if (optionalProducto.isPresent()) {
			Producto productoExistente = optionalProducto.get();
			productoExistente.setNombre(nuevoProducto.getNombre());
			productoExistente.setDescripcion(nuevoProducto.getDescripcion());
			productoExistente.setPrecio(nuevoProducto.getPrecio());
			return productoRepository.save(productoExistente);
		} else {
			nuevoProducto.setNombre(nombre);
			return productoRepository.save(nuevoProducto);
		}
	}

	@DeleteMapping("/{nombre}")
	public void eliminarProducto(@PathVariable String nombre) {
		productoRepository.deleteById(nombre);
	}

}
