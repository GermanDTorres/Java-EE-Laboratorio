package moduloComercio.aplicacion.impl;

import moduloComercio.aplicacion.ServicioComercio;
import moduloComercio.dominio.Comercio;
import moduloComercio.dominio.POS;
import moduloComercio.dominio.RepositorioComercio;
import moduloComercio.dominio.EstadoPOS;
import moduloComercio.util.HashUtil;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ServicioComercioImpl implements ServicioComercio {

    private static final Logger LOGGER = Logger.getLogger(ServicioComercioImpl.class.getName());
    private final RepositorioComercio repositorio;

    public ServicioComercioImpl(RepositorioComercio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void registrarComercio(Comercio comercio) {
        if (comercio == null) {
            LOGGER.severe("[ServicioComercio] Comercio es null en registrarComercio");
            return;
        }
        // Asumimos que el comercio ya tiene la contraseña en texto plano
        // Se genera el hash internamente en Comercio al hacer setPasswordPlano en el constructor
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
    public void modificarComercio(Comercio comercio) {
        if (comercio == null) {
            LOGGER.severe("[ServicioComercio] Comercio es null en modificarComercio");
            return;
        }
        repositorio.actualizar(comercio);
    }

    @Override
    public void altaPOS(String rutComercio, int posId) {
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
    public void cambiarEstadoPOS(String rutComercio, int posId, boolean estado) {
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
            // Guardar hash de la nueva contraseña
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
}
