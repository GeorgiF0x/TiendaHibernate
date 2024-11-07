package modelo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int id;
    private int rolId = 1; // Por defecto, rolId 1 user normal
    private String email;
    private String clave;
    private String nombre;
    private String apellidos;
    private boolean baja;

    public void establecerRolAdmin() {
        this.rolId = 2;
    }
}

