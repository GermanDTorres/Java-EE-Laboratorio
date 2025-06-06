package moduloTransferencia.infraestructura.persistencia;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloTransferencia.aplicacion.RepositorioDepositos;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class RepositorioDeposito implements RepositorioDepositos {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardar(Deposito deposito) {
        em.persist(deposito);
    }

    @Override
    public List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime desde, LocalDateTime hasta) {
        TypedQuery<Deposito> query = em.createQuery(
            "SELECT d FROM Deposito d WHERE d.rutComercio = :rut AND d.fechaHora BETWEEN :desde AND :hasta",
            Deposito.class
        );
        query.setParameter("rut", rutComercio);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
    }
}
