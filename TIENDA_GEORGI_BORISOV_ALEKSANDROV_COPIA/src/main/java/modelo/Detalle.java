package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle")
public class Detalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "pedido_id") // Columna 'pedido_id' para almacenar el ID del pedido
	private int pedidoId; // Almacena solo el ID del pedido como un entero

	@Column(name = "producto_id") // Columna 'producto_id' para almacenar el ID del producto
	private int productoId; // Almacena solo el ID del producto como un entero

	private int unidades;

	@Column(name = "preciounidad") // Columna 'preciounidad' para el precio por unidad
	private double precioUnidad;

	private double impuesto; // Columna 'impuesto' para el valor del impuesto

	private double total; // Columna 'total' para el total de la línea de detalle

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}

	public int getPedidoId() {
		return this.pedidoId;
	}
}
