package modelo;

// Asegúrate de tener esta importación para las anotaciones de JPA
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

@Builder
@Data // Genera automáticamente getters, setters, toString, equals, hashCode, y otros
		// métodos
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "carrito") // Especifica el nombre de la tabla en la base de datos
public class Carrito {

	@Id // Indica que este campo es la clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor del id (usualmente en
														// auto-incremento)
	private int id;

	@Column(name = "usuario_id") // Especifica el nombre de la columna en la tabla
	private int usuarioId;

	@Column(name = "producto_id") // Especifica el nombre de la columna en la tabla
	private int productoId;

	@Column(name = "unidades") // Especifica el nombre de la columna en la tabla
	private int unidades;

	public Carrito(int usuarioId) {
		this.usuarioId = usuarioId;
		this.productoId = 0; // Valor por defecto si no hay productos en el carrito
		this.unidades = 0; // Sin unidades
	}
}
