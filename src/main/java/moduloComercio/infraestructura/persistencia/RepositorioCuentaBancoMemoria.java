/*package moduloComercio.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import moduloComercio.dominio.CuentaBancoComercio;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioCuentaBancoMemoria implements RepositorioCuentaBanco {

    @PersistenceContext(unitName = "pasarelaPU")
    private EntityManager em;

    @Override
    public void guardar(CuentaBancoComercio cuenta) {
        em.persist(cuenta);
    }

    @Override
    public Optional<CuentaBancoComercio> buscarPorNumero(int nroCuenta) {
        CuentaBancoComercio cuenta = em.find(CuentaBancoComercio.class, nroCuenta);
        return Optional.ofNullable(cuenta);
    }

    @Override
    public List<CuentaBancoComercio> obtenerTodas() {
        TypedQuery<CuentaBancoComercio> query = em.createQuery(
            "SELECT c FROM CuentaBancoComercio c", CuentaBancoComercio.class
        );
        return query.getResultList();
    }

    @Override
    public void actualizar(CuentaBancoComercio cuenta) {
        em.merge(cuenta);
    }

    @Override
    public void eliminarPorNumero(int nroCuenta) {
        CuentaBancoComercio cuenta = em.find(CuentaBancoComercio.class, nroCuenta);
        if (cuenta != null) {
            em.remove(cuenta);
        }
    }
}
*/