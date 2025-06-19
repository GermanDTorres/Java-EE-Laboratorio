package moduloCompra.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.Collection;

@ApplicationScoped
public class RepositorioCompraJPA implements RepositorioCompra {

    @PersistenceContext(unitName = "tallerjava")
    private EntityManager em;

    @Override
    @Transactional
    public void guardar(Compra compra) {
        if (compra.getIdCompra() == null || em.find(Compra.class, compra.getIdCompra()) == null) {
            em.persist(compra);  // entidad nueva
        } else {
            em.merge(compra);    // entidad existente
        }
    }

    @Override
    public Compra buscarPorId(String id) {
        return em.find(Compra.class, id);
    }

    @Override
    public Collection<Compra> obtenerCompras() {
        TypedQuery<Compra> query = em.createQuery("SELECT c FROM Compra c", Compra.class);
        return query.getResultList();
    }
}
