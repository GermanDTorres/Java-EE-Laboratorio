package moduloTransferencia.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloTransferencia.dominio.Deposito;
import moduloTransferencia.dominio.RepositorioTransferencia;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class RepositorioTransferenciaImpl implements RepositorioTransferencia {

    @PersistenceContext(unitName = "tallerjava") // Asegurate de que este nombre coincida con tu persistence.xml
    private EntityManager em;

    @Override
    public void registrarDeposito(Deposito deposito) {
        em.persist(deposito);
    }

    @Override
    public List<Deposito> obtenerDepositosPorComercioYRango(String rutComercio, LocalDate inicio, LocalDate fin) {
        TypedQuery<Deposito> query = em.createQuery(
                "SELECT d FROM Deposito d WHERE d.rutComercio = :rutComercio AND d.fecha BETWEEN :inicio AND :fin",
                Deposito.class
        );
        query.setParameter("rutComercio", rutComercio);
        query.setParameter("inicio", inicio);
        query.setParameter("fin", fin);
        return query.getResultList();
    }

	@Override
	public Collection<Deposito> obtenerDepositos() {
		// TODO Auto-generated method stub
		return null;
	}
}
