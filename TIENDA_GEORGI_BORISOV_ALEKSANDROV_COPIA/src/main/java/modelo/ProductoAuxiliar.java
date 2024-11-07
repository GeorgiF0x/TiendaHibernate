package modelo;

public class ProductoAuxiliar {
	private Producto producto; // El producto que está en el carrito
	private int unidades; // La cantidad de unidades de este producto

	// Constructor que recibe el producto y la cantidad de unidades
	public ProductoAuxiliar(Producto producto, int unidades) {
		this.producto = producto;
		this.unidades = unidades;
	}

	// Getter para el producto
	public Producto getProducto() {
		return producto;
	}

	// Getter para las unidades
	public int getUnidades() {
		return unidades;
	}

	// Método para obtener la imagen del producto
	public String getImagen() {
		return producto.getImagen(); // Asumiendo que Producto tiene un método getImagen()
	}
}
