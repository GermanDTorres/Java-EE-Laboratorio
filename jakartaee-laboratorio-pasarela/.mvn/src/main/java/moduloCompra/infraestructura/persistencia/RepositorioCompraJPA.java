package moduloCompra.infraestructura.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloCompra.dominio.Compra;
import moduloCompra.dominio.RepositorioCompra;

import java.util.Collection;

public class RepositorioCompraJPA implements RepositorioCompra {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardar(Compra compra) {
        em.persist(compra);
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
