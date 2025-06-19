package moduloComercio.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.RepositorioComercio;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioComercioJPA implements RepositorioComercio {

    @PersistenceContext(unitName = "tallerjava")
    private EntityManager em;

    @Override
    public void guardar(Comercio comercio) {
        if (!existe(comercio.getRut())) {
            em.persist(comercio);
        } else {
            em.merge(comercio);
        }
    }

    @Override
    public Optional<Comercio> buscarPorRut(String rut) {
        Comercio comercio = em.find(Comercio.class, rut);
        return Optional.ofNullable(comercio);
    }

    @Override
    public Comercio buscarPorId(String id) {
        return em.find(Comercio.class, id);
    }

    @Override
    public List<Comercio> obtenerTodos() {
        TypedQuery<Comercio> query = em.createQuery("SELECT c FROM Comercio c", Comercio.class);
        return query.getResultList();
    }

    @Override
    public void eliminarPorRut(String rut) {
        buscarPorRut(rut).ifPresent(c -> {
            Comercio cManaged = em.contains(c) ? c : em.merge(c);
            em.remove(cManaged);
        });
    }

    @Override
    public boolean existe(String rut) {
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Comercio c WHERE c.rut = :rut", Long.class)
            .setParameter("rut", rut)
            .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public void actualizar(Comercio comercio) {
        em.merge(comercio);
    }

}
