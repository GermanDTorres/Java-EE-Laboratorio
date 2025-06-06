package moduloComercio.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.EstadoPOS;
import moduloComercio.dominio.POS;
import moduloComercio.dominio.RepositorioComercio;
import moduloComercio.util.HashUtil;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class ServicioComercioImpl implements ServicioComercio {

    @PersistenceContext(unitName = "tallerjava")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(ServicioComercioImpl.class.getName());

    @Inject
    private RepositorioComercio repositorio;

    @Override
    @Transactional
    public void registrarComercio(Comercio comercio) {
        repositorio.guardar(comercio);
    }

    @Override
    public Comercio obtenerComercio(String rut) {
        Optional<Comercio> comercioOpt = repositorio.buscarPorRut(rut);
        return comercioOpt.orElse(null);
    }

    @Override
    public List<Comercio> obtenerTodos() {
        return repositorio.obtenerTodos();
    }

    @Override
    @Transactional
    public void modificarComercio(Comercio comercio) {
        if (comercio == null || comercio.getRut() == null || comercio.getRut().isBlank()) {
            LOGGER.severe("[ServicioComercio] Comercio o RUT inválido en modificarComercio");
            return;
        }
        repositorio.actualizar(comercio);
    }

    @Override
    @Transactional
    public void altaPOS(String rutComercio, String posId) {
        if (rutComercio == null || rutComercio.isBlank()) {
            LOGGER.severe("[ServicioComercio] rutComercio inválido en altaPOS");
            return;
        }
        Optional<Comercio> comercioOpt = repositorio.buscarPorRut(rutComercio);
        if (comercioOpt.isPresent()) {
            Comercio comercio = comercioOpt.get();
            comercio.agregarPOS(new POS(posId));
            repositorio.actualizar(comercio);
        } else {
            LOGGER.severe("[ServicioComercio] Comercio no encontrado en altaPOS");
        }
    }

    @Override
    @Transactional
    public void cambiarEstadoPOS(String rutComercio, String posId, boolean estado) {
        if (rutComercio == null || rutComercio.isBlank()) {
            LOGGER.severe("[ServicioComercio] rutComercio inválido en cambiarEstadoPOS");
            return;
        }
        Optional<Comercio> comercioOpt = repositorio.buscarPorRut(rutComercio);
        if (comercioOpt.isPresent()) {
            Comercio comercio = comercioOpt.get();
            EstadoPOS nuevoEstado = estado ? EstadoPOS.ACTIVO : EstadoPOS.INACTIVO;
            comercio.cambiarEstadoPOS(posId, nuevoEstado);
            repositorio.actualizar(comercio);
        } else {
            LOGGER.severe("[ServicioComercio] Comercio no encontrado en cambiarEstadoPOS");
        }
    }

    @Override
    @Transactional
    public void cambioContrasena(String rutComercio, String nuevaPass) {
        if (rutComercio == null || rutComercio.isBlank()) {
            LOGGER.severe("[ServicioComercio] rutComercio inválido en cambioContrasena");
            return;
        }
        if (nuevaPass == null || nuevaPass.isBlank()) {
            LOGGER.severe("[ServicioComercio] nuevaPass inválida en cambioContrasena");
            return;
        }
        Optional<Comercio> comercioOpt = repositorio.buscarPorRut(rutComercio);
        if (comercioOpt.isPresent()) {
            Comercio comercio = comercioOpt.get();
            String hashPass = HashUtil.sha256(nuevaPass);
            comercio.cambiarContrasena(hashPass);
            repositorio.actualizar(comercio);
        } else {
            LOGGER.severe("[ServicioComercio] Comercio no encontrado en cambioContrasena");
        }
    }

    @Override
    public boolean existeComercio(String rutComercio) {
        if (rutComercio == null || rutComercio.isBlank()) {
            return false;
        }
        return repositorio.existe(rutComercio);
    }

    @Override
    public boolean autenticar(String idComercio, String contrasena) {
        Comercio comercio = repositorio.buscarPorId(idComercio);
        if (comercio == null) return false;

        String hashIngresado = HashUtil.sha256(contrasena);
        return comercio.getPasswordHash().equals(hashIngresado);
    }

    @Override
    public POS buscarPOS(String idPOS) {
        if (idPOS == null || idPOS.isBlank()) {
            return null;
        }

        List<Comercio> comercios = repositorio.obtenerTodos();
        for (Comercio comercio : comercios) {
            POS encontrado = comercio.buscarPOS(idPOS);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

}
