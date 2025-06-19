package moduloTransferencia.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.dominio.RepositorioTransferencia;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class RepositorioTransferenciaImpl implements RepositorioTransferencia {

    @PersistenceContext(unitName = "tallerjava")
    private EntityManager em;

    @Override
    @Transactional
    public void registrarDeposito(Deposito deposito) {
        em.persist(deposito);
    }

    @Override
    public List<Deposito> obtenerDepositosPorComercioYRango(String rutComercio, LocalDate inicio, LocalDate fin) {
        TypedQuery<Deposito> query = em.createQuery(
                "SELECT d FROM Deposito d WHERE d.rutComercio = :rutComercio AND d.fechaHora BETWEEN :inicio AND :fin",
                Deposito.class
        );
        query.setParameter("rutComercio", rutComercio);
        query.setParameter("inicio", inicio.atStartOfDay());
        query.setParameter("fin", fin.atTime(23, 59, 59));
        return query.getResultList();
    }

    @Override
    public Collection<Deposito> obtenerDepositos() {
        return em.createQuery("SELECT d FROM Deposito d", Deposito.class).getResultList();
    }
}
