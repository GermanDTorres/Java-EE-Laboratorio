/*package moduloComercio.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.RepositorioComercio;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioComercioMemoria implements RepositorioComercio {

    @PersistenceContext(unitName = "pasarelaPU")
    private EntityManager em;

    @Override
    public void guardar(Comercio comercio) {
        em.persist(comercio);
    }

    @Override
    public Optional<Comercio> buscarPorRut(String rut) {
        Comercio comercio = em.find(Comercio.class, rut);
        return Optional.ofNullable(comercio);
    }

    @Override
    public List<Comercio> obtenerTodos() {
        TypedQuery<Comercio> query = em.createQuery("SELECT c FROM Comercio c", Comercio.class);
        return query.getResultList();
    }

    @Override
    public void actualizar(Comercio comercio) {
        em.merge(comercio);
    }

    @Override
    public void eliminarPorRut(String rut) {
        Comercio comercio = em.find(Comercio.class, rut);
        if (comercio != null) {
            em.remove(comercio);
        }
    }

    @Override
    public boolean existe(String rut) {
        Comercio comercio = em.find(Comercio.class, rut);
        return comercio != null;
    }

    @Override
    public Comercio buscarPorId(String id) {
        return em.find(Comercio.class, id);
    }

    @Override
    public List<Comercio> listarTodos() {
        return obtenerTodos();
    }

    @Override
    public void guardarComercio(Comercio comercio) {
        guardar(comercio);
    }

    @Override
    public void actualizarComercio(Comercio comercio) {
        actualizar(comercio);
    }

    @Override
    public Comercio obtenerComercio(String rutComercio) {
        return em.find(Comercio.class, rutComercio);
    }
}
*/