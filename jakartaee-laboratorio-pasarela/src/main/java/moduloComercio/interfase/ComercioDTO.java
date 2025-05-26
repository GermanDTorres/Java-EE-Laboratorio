package moduloComercio.interfase;

public class ComercioDTO {
    public String rut;
    public String nombre;
    public String password;

    public boolean esValido() {
        return rut != null && !rut.isBlank() &&
               nombre != null && !nombre.isBlank() &&
               password != null && !password.isBlank();
    }
}
