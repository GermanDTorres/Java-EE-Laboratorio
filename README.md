# Pasarela de Pagos - Proyecto Jakarta EE

Este es un sistema de pasarela de pagos desarrollado con **Jakarta EE**, utilizando una arquitectura basada en **DDD (Domain-Driven Design)**, con separación por módulos funcionales: Compra, Comercio, Transferencia y Monitoreo.

---

## 🧩 Módulos

### 1. `moduloCompra`
Gestiona las operaciones de compra y pago de los clientes.

**Responsabilidades principales:**
- Registrar compras
- Procesar pagos
- Emitir reportes por comercio
- Notificar resultados a monitoreo

---

### 2. `moduloComercio`
Administra los datos de los comercios y sus POS (terminales de punto de venta).

**Responsabilidades:**
- Alta de comercio
- Alta/baja de POS
- Cambio de contraseña
- Validación de credenciales

---

### 3. `moduloTransferencia`
Se encarga de registrar las transferencias a cuentas de los comercios con descuento por comisión.

**Funciones clave:**
- Recibir notificaciones desde el medio de pago
- Registrar depósitos bancarios
- Consultar depósitos por comercio y fecha

---

### 4. `moduloMonitoreo`
Monitorea y registra eventos relacionados con pagos y transacciones.

**Notificaciones que maneja:**
- Pago registrado
- Pago exitoso
- Pago fallido
- Transferencias

---

## 🛠️ Arquitectura

El sistema está dividido por capas:

📦 modulo
┣ 📂 dominio
┣ 📂 aplicacion
┣ 📂 interfaz
┗ 📂 infraestructura


### Capas:
- `dominio`: Entidades y lógica de negocio principal.
- `aplicacion`: Casos de uso (servicios).
- `infraestructura`: Repositorios, base de datos, adaptadores.
- `interfaz`: API REST.

---

## 🔐 Autenticación

- Los comercios se identifican por RUT + contraseña.
- Las contraseñas se almacenan hasheadas con **SHA-256**.
- Los endpoints como `/resumenDiario` o `/resumenPeriodo` requieren validación previa.

---

## 📊 Casos de Uso Principales

### 1. Procesar Pago
- Cliente realiza una compra
- Se valida el POS y el comercio
- Se registra la compra
- Se notifica a Monitoreo

### 2. Consultar Ventas por Periodo
- Comercio se autentica con RUT y contraseña
- Se devuelve listado y montos de las ventas

### 3. Alta de POS
- Comercio solicita alta de POS con su ID
- Se registra y asigna nuevo terminal

---

## 📈 Diagramas de Secuencia (Sistema)

![Image](https://github.com/user-attachments/assets/03130e1d-c824-490a-a022-579d8b558804)
[IMAGENES]
[IMAGENES]
[IMAGENES]

---

## ⚙️ Tecnologías
Jakarta EE 10

JAX-RS (API REST)

JUnit 5 (Tests)

Maven

MariaDB (persistencia)

SHA-256 (hash de contraseñas)

