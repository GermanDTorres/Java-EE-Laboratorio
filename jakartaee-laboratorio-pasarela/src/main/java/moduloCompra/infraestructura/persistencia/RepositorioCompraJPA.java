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
        // Usa merge para actualizar o persistir sin errores si existe el ID
        em.merge(compra);
        // No necesitas flush explícito, JTA lo hará al final de la tx
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
