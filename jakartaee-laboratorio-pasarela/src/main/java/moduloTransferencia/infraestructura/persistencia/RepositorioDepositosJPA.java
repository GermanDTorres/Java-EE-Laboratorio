package moduloTransferencia.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import moduloTransferencia.aplicacion.RepositorioDepositos;
import moduloTransferencia.dominio.Deposito;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Named("repositorioJPA")
public class RepositorioDepositosJPA implements RepositorioDepositos {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void guardar(Deposito deposito) {
        em.persist(deposito);
    }

    @Override
    public List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime desde, LocalDateTime hasta) {
        return em.createQuery("""
                SELECT d FROM Deposito d
                WHERE d.rutComercio = :rut
                AND d.fecha BETWEEN :desde AND :hasta
                """, Deposito.class)
            .setParameter("rut", rutComercio)
            .setParameter("desde", desde)
            .setParameter("hasta", hasta)
            .getResultList();
    }
}
