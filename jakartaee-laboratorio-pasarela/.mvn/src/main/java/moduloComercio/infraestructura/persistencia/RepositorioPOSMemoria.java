/*package moduloComercio.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloComercio.dominio.POS;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioPOSMemoria implements RepositorioPOS {

    @PersistenceContext(unitName = "pasarelaPU")
    private EntityManager em;

    @Override
    public void guardar(POS pos) {
        em.persist(pos);
    }

    @Override
    public Optional<POS> buscarPorId(int id) {
        POS pos = em.find(POS.class, id);
        return Optional.ofNullable(pos);
    }

    @Override
    public List<POS> obtenerTodos() {
        TypedQuery<POS> query = em.createQuery("SELECT p FROM POS p", POS.class);
        return query.getResultList();
    }

    @Override
    public void actualizar(POS pos) {
        em.merge(pos);
    }

    @Override
    public void eliminarPorId(int id) {
        POS pos = em.find(POS.class, id);
        if (pos != null) {
            em.remove(pos);
        }
    }

    @Override
    public boolean existePOS(int id) {
        POS pos = em.find(POS.class, id);
        return pos != null;
    }
}
*/