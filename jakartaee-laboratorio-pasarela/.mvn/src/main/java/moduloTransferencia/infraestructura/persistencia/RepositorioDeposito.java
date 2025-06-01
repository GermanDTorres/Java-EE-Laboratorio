package moduloTransferencia.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloTransferencia.aplicacion.RepositorioDepositos;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RepositorioDeposito implements RepositorioDepositos {

    @PersistenceContext(unitName = "tallerjava")
    private EntityManager em;

    @Override
    public void guardar(Deposito deposito) {
        em.persist(deposito);
    }

    @Override
    public List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime desde, LocalDateTime hasta) {
        String jpql = "SELECT d FROM Deposito d WHERE d.rutComercio = :rut AND d.fecha BETWEEN :desde AND :hasta ORDER BY d.fecha ASC";
        TypedQuery<Deposito> query = em.createQuery(jpql, Deposito.class);
        query.setParameter("rut", rutComercio);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
    }
}
