package moduloComercio.infraestructura.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloComercio.dominio.POS;

import java.util.List;
import java.util.Optional;

public class RepositorioPOSJPA implements RepositorioPOS {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardar(POS pos) {
        em.persist(pos);
    }

    @Override
    public Optional<POS> buscarPorId(int id) {
        POS pos = em.find(POS.class, id);
        return pos != null ? Optional.of(pos) : Optional.empty();
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
        buscarPorId(id).ifPresent(p -> em.remove(p));
    }

    @Override
    public boolean existePOS(int id) {
        Long count = em.createQuery(
            "SELECT COUNT(p) FROM POS p WHERE p.id = :id", Long.class)
            .setParameter("id", id)
            .getSingleResult();
        return count != null && count > 0;
    }
}
