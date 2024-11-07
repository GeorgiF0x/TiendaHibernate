package modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detalle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pedido_id")
    private int pedidoId;

    @Column(name = "producto_id")
    private int productoId;

    private int unidades;

    @Column(name = "preciounidad")
    private double precioUnidad;

    private double impuesto;
    
    private double total;
}
