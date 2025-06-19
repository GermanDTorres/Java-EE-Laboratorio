package moduloComercio.dominio;

import java.io.Serializable;

public class ReclamoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String texto;

    public ReclamoDTO() {}

    public ReclamoDTO(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean esValido() {
        return texto != null && !texto.isBlank();
    }

    @Override
    public String toString() {
        return "ReclamoDTO{" +
                "texto='" + texto + '\'' +
                '}';
    }
}
