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

## DSS 1: Registrar Comercio
![Image](https://github.com/user-attachments/assets/03130e1d-c824-490a-a022-579d8b558804)

## DSS 2: Procesar Compra
![Image](https://github.com/user-attachments/assets/4d90f847-c88f-4281-beb6-a0763fa37c59)

## DSS 3: Transferencia con Depósito
![Image](https://github.com/user-attachments/assets/359d7926-5289-418c-b5c8-4d3280db4b47)

## DSS 4: Autenticación para Reportes
![Image](https://github.com/user-attachments/assets/f2fee370-ef8e-4055-b9b6-c058c38d99a4)

---

## ⚙️ Tecnologías
Jakarta EE 10

JAX-RS (API REST)

JUnit 5 (Tests)

Maven

MariaDB (persistencia)

SHA-256 (hash de contraseñas)

---

## 🔄 Iteración 2 – Integración con Sistemas Externos

En esta segunda parte del proyecto se implementaron funcionalidades clave para permitir la integración con servicios externos, mejorar la seguridad del sistema y controlar el acceso a los recursos de forma eficiente.

### 🎯 Objetivos alcanzados

- Exposición de APIs REST seguras para comercios
- Autenticación por RUT y contraseña (con hash SHA-256)
- Implementación de Rate Limiting por comercio
- Consumo de servicios externos REST y SOAP
- Simulación de sistemas externos (mocks)

---

## 🚦 Rate Limiter por Comercio

Se implementó un sistema de **Token Bucket Rate Limiting**.

- Cada comercio tiene un límite de operaciones por minuto.
- Si el bucket está vacío, se rechaza la operación con un mensaje de error.
- El bucket se recarga automáticamente con el tiempo.

---

## 🌐 Integración con Sistemas Externos

### 🔸 Servicio REST del Medio de Pago (Mock)

- Simula autorización de pagos de tarjetas.
- Puede aceptar o rechazar pagos de tarjeta.

### 🔸 Servicio SOAP del Banco del Cliente (Mock)
Simula el banco que recibe la transferencia.

Se invoca automáticamente desde el módulo Transferencia.

Retorna un código de confirmación.

---

📡 Iteración 3 – Observabilidad y Monitoreo con Grafana e InfluxDB
En esta tercera iteración, se abordó un requerimiento no funcional clave: la observabilidad del sistema. Esto implica registrar eventos significativos del funcionamiento interno de la pasarela de pagos, permitiendo así su monitoreo por parte de los equipos de operaciones.

🎯 Objetivos alcanzados
- Se integró el sistema con InfluxDB para almacenar métricas en tiempo real.

- Se utilizó Grafana como herramienta de visualización de dashboards.

- Se implementó un nuevo dashboard personalizado con indicadores clave del sistema.

- Se completó la configuración con JMS (ActiveMQ) para registrar reclamos.

- El módulo moduloMonitoreo quedó encargado de registrar todos los eventos monitoreables sin acoplarse directamente a otros módulos.

⚙️ Tecnologías utilizadas
InfluxDB: motor de base de datos orientado a series temporales.

Grafana: herramienta web para construir dashboards en tiempo real.

Micrometer: biblioteca para la instrumentación de métricas en Java.

Docker: para contenerizar Grafana + InfluxDB en entorno local.

📊 Métricas registradas
A través del módulo moduloMonitoreo, se registran en InfluxDB las siguientes métricas del sistema:

Pagos confirmados	pagos_confirmados_total
Pagos rechazados	pagos_rechazados_total
Reclamos recibidos	reclamos_total
Depósitos notificados por el banco	notificaciones_banco_total
Reportes de ventas consultados	reportes_ventas_total

Cada métrica es incrementada por eventos observados a través de eventos CDI y MDB (para reclamos).

📈 Dashboard en Grafana
Se creó un nuevo dashboard en Grafana titulado "Pasarela" con los siguientes paneles:

📦 Pagos realizados: gráfico de barras diferenciando pagos confirmados y rechazados.

🧾 Reportes de ventas: contador de solicitudes de reporte de ventas por comercio.

🏦 Notificaciones de banco: total de transferencias notificados al sistema.

🗣️ Reclamos: cantidad de reclamos recibidos por parte de los comercios.

El dashboard permite observar en tiempo real el comportamiento del sistema.

Grafana e InfluxDB se ejecutan juntos en un contenedor Docker basado en la imagen:
philhawthorne/docker-influxdb-grafana

✅ Implementaciones destacadas

Las métricas se envían a InfluxDB usando Micrometer o InfluxDB Java Client.
