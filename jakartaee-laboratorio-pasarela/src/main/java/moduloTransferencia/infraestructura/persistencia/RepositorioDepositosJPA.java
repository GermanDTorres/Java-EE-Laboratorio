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
    private EntityManager em;

    @Override
    @Transactional
    public void guardar(Deposito deposito) {
        try {
            em.persist(deposito);
        } catch (Exception e) {
            System.err.println("[RepositorioDepositosJPA] ❌ Error al guardar el depósito: " + e.getMessage());
            throw e; // Re-lanzar para que el contenedor gestione correctamente la transacción
        }
    }

    @Override
    public List<Deposito> consultarPorComercioYRangoFechas(String rutComercio, LocalDateTime desde, LocalDateTime hasta) {
        try {
            return em.createQuery("""
                    SELECT d FROM Deposito d
                    WHERE d.rutComercio = :rut
                    AND d.fecha BETWEEN :desde AND :hasta
                    ORDER BY d.fecha ASC
                    """, Deposito.class)
                .setParameter("rut", rutComercio)
                .setParameter("desde", desde)
                .setParameter("hasta", hasta)
                .getResultList();
        } catch (Exception e) {
            System.err.println("[RepositorioDepositosJPA] ❌ Error al consultar depósitos: " + e.getMessage());
            throw e;
        }
    }
}
