# 💳 Pasarela de Pagos — Proyecto Java EE

Una **Pasarela de Pagos** es un sistema tecnológico que actúa como **intermediario seguro** entre clientes, comercios y entidades financieras, autorizando y gestionando pagos electrónicos de forma eficiente.

Este proyecto implementa un **prototipo funcional** desarrollado con **Jakarta EE**, diseñado para simular el flujo real de operaciones que realizan plataformas como MercadoPago, RedPagos o PayPal.

---

## 🎯 Objetivo del sistema

 Desarrollar una plataforma que permita:

 - Autorizar y procesar compras hechas con tarjeta.
 - Registrar comercios y puntos de venta (POS).
 - Facilitar el depósito del dinero al banco del comercio.
 - Generar reportes de ventas y métricas del sistema.
 - Atender reclamos mediante mensajería asincrónica.

---

## 🧩 Funcionalidades principales

| 🔹 Función                         | 💬 Descripción breve                                                                 |
|----------------------------------|--------------------------------------------------------------------------------------|
| 🏪 Registro de Comercios         | Alta de comercios con datos y cuenta bancaria para depósitos.                       |
| 💰 Procesamiento de Pagos        | Validación de tarjeta a través de un medio de pago externo (simulado vía REST).     |
| 🧾 Reportes de Ventas            | Resúmenes diarios, por período y totales por estado (aceptadas, rechazadas, etc.). |
| 🏦 Transferencias a Bancos       | Envío automático del dinero al banco del comercio una vez notificado el depósito.  |
| 📬 Gestión de Reclamos           | Envío y clasificación automática de reclamos vía cola JMS.                         |
| 📈 Monitoreo del Sistema         | Métricas exportadas a Prometheus/Grafana usando eventos CDI y Micrometer.          |

---

## 👥 Actores del sistema

| Actor            | Tipo      | Rol principal                                                     |
|------------------|-----------|--------------------------------------------------------------------|
| 🧑‍💼 Comercio     | Entidad   | Se registra y realiza ventas usando POS.                          |
| 🧑‍💳 Cliente      | Humano    | Realiza la compra usando su tarjeta.                              |
| 🖥️ POS            | Software  | Envia la solicitud de compra a la pasarela.                       |
| 🧑‍🔧 Operario      | Humano    | Supervisa el estado del sistema y sus métricas.                   |
| 🧑‍💻 Soporte       | Software   | Recibe y clasifica reclamos de los comercios.                     |
| 🌐 Medio de pago  | Sistema   | Simulado vía REST, autoriza o rechaza pagos.                      |
| 🏛️ Banco Cliente | Sistema   | Simulado vía SOAP, notifica depósitos realizados.                 |

---

## 🛠️ Arquitectura

### 🗂️ Diagrama de Arquitectura General del Sistema

![img6](https://github.com/user-attachments/assets/260edcdf-62d9-46d6-a1e8-dfbba7397c78)

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

## 🧩 Módulos

### 🛒 moduloCompra — Gestión de Compras
Gestiona las operaciones de compra y pago de los clientes.

Responsabilidades principales:

🧾 Registrar compras

💳 Procesar pagos

📊 Emitir reportes por comercio

📢 Notificar resultados a monitoreo
---
### 🏪 moduloComercio — Administración de Comercios
Administra los datos de los comercios y sus terminales de punto de venta (POS).

Responsabilidades:

- 📝 Alta de comercio

- 🖥️ Alta/baja de POS

- 🔐 Cambio de contraseña

- ✅ Validación de credenciales
---
### 🔁 moduloTransferencia — Gestión de Transferencias
Se encarga de registrar las transferencias a cuentas de los comercios, aplicando comisiones.

Funciones clave:

- 📬 Recibir notificaciones desde el medio de pago

- 🏦 Registrar depósitos bancarios

- 📆 Consultar depósitos por comercio y fecha
---
### 📈 moduloMonitoreo — Observabilidad del Sistema
Monitorea y registra eventos relacionados con pagos, transferencias y reclamos.

Notificaciones que maneja:

- 📥 Pago registrado

- ✅ Pago exitoso

- ❌ Pago fallido

- 🔁 Transferencias

---

## 🔄 Relaciones entre módulos

La Pasarela de Pagos está compuesta por varios módulos que interactúan mediante distintos mecanismos (REST, eventos, JMS, SOAP). A continuación se resumen las principales conexiones entre ellos:

| 🧩 Módulo origen         | 🔁 Relación       | 🎯 Módulo destino / tecnología         | 💬 Descripción breve                                  |
|-------------------------|------------------|----------------------------------------|--------------------------------------------------------|
| `moduloComercio`        | JMS            | `ReclamoConsumer`                      | Envía reclamos a través de mensajería JMS.             |
| `ReclamoConsumer`       | evento         | `moduloMonitoreo`                      | Emite eventos de reclamos clasificados.                |
| `moduloComercio`        | persistencia    | `BD_Comercio`                          | Guarda comercios y POS.                               |
| `moduloCompra`          | REST           | `AutorizadorPagoHttp`                  | Valida la compra con el medio de pago simulado.       |
| `moduloCompra`          | evento         | `moduloMonitoreo`                      | Emite eventos por compras aprobadas o rechazadas.     |
| `moduloCompra`          | persistencia    | `BD_Compra`                            | Guarda información de compras realizadas.             |
| `moduloCompra`          | uso interno    | `RateLimiter`                          | Controla el flujo de solicitudes de compra.           |
| `moduloTransferencia`   | SOAP           | `ClienteBancoSOAP`                     | Simula depósito bancario a comercio.                  |
| `moduloTransferencia`   | evento         | `moduloMonitoreo`                      | Emite evento al confirmar depósito.                   |
| `moduloTransferencia`   | persistencia    | `BD_Transferencia`                     | Guarda transferencias realizadas.                     |
| `moduloMonitoreo`       | escucha evento | Todos los emisores de eventos          | Observa todos los eventos del sistema.                |
| `moduloMonitoreo`       | persistencia    | `BD_Monitoreo`                         | Guarda métricas y eventos procesados.                 |

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
- Jakarta EE 10

- JAX-RS (API REST)

- JUnit 5 (Tests)

- Maven

- MariaDB (persistencia)

- SHA-256 (hash de contraseñas)

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


### 🔄 Diagrama Secuencia del RateLimiter

![ratelimiter](https://github.com/user-attachments/assets/fe19b107-11df-4c57-81fb-188cecfc6ec4)

---

### 🧪 Pruebas de carga y control de flujo con JMeter

![Screenshot from 2025-06-28 18-25-45](https://github.com/user-attachments/assets/1fd32f7d-40f1-4a4c-ae46-234df38c26e4)

---

## 🌐 Integración con Sistemas Externos

### 🔸 Servicio REST del Medio de Pago (Mock)

- Simula autorización de pagos de tarjetas.
- Puede aceptar o rechazar pagos de tarjeta.

### 🔸 Servicio SOAP del Banco del Cliente (Mock)

- Simula el banco que recibe la transferencia.

- Se invoca automáticamente desde el módulo Transferencia.

Retorna un código de confirmación.

---

## 📡 Iteración 3 – Observabilidad y Monitoreo con Grafana e InfluxDB
En esta tercera iteración, se abordó un requerimiento no funcional clave: la observabilidad del sistema. Esto implica registrar eventos significativos del funcionamiento interno de la pasarela de pagos, permitiendo así su monitoreo por parte de los equipos de operaciones.

### 🎯 Objetivos alcanzados
- Se integró el sistema con InfluxDB para almacenar métricas en tiempo real.

- Se utilizó Grafana como herramienta de visualización de dashboards.

- Se implementó un nuevo dashboard personalizado con indicadores clave del sistema.

- Se completó la configuración con JMS (ActiveMQ) para registrar reclamos.

- El módulo moduloMonitoreo quedó encargado de registrar todos los eventos monitoreables sin acoplarse directamente a otros módulos.

## ⚙️ Tecnologías utilizadas
- InfluxDB: motor de base de datos orientado a series temporales.

- Grafana: herramienta web para construir dashboards en tiempo real.

- Micrometer: biblioteca para la instrumentación de métricas en Java.

- Docker: para contenerizar Grafana + InfluxDB en entorno local.

## 📊 Métricas registradas
A través del módulo moduloMonitoreo, se registran en InfluxDB las siguientes métricas del sistema:

- Pagos confirmados	pagos_confirmados_total
- Pagos rechazados	pagos_rechazados_total
- Reclamos recibidos	reclamos_total
- Depósitos notificados por el banco	notificaciones_banco_total
- Reportes de ventas consultados	reportes_ventas_total

Cada métrica es incrementada por eventos observados a través de eventos CDI y MDB (para reclamos).

## 📈 Dashboard en Grafana
Se creó un nuevo dashboard en Grafana titulado "Pasarela" con los siguientes paneles:

## 📦 Pagos realizados: gráfico de barras diferenciando pagos confirmados y rechazados.

## 🧾 Reportes de ventas
- Contador de solicitudes de reporte de ventas por comercio.

## 🏦 Notificaciones de banco 
- Total de transferencias notificados al sistema.

## 🗣️ Reclamos 
- Cantidad de reclamos recibidos por parte de los comercios.

### El dashboard permite observar en tiempo real el comportamiento del sistema.

---

Grafana e InfluxDB se ejecutan juntos en un contenedor Docker basado en la imagen:
philhawthorne/docker-influxdb-grafana

## ✅ Implementaciones destacadas

- Las métricas se envían a InfluxDB usando Micrometer o InfluxDB Java Client.

---

📊 Visualización de Reclamos Clasificados
Gráfico que muestra la cantidad de reclamos procesados, agrupados por clasificación:

🟩 Positivos

🟨 Neutros

🟥 Negativos

![Screenshot from 2025-06-28 18-49-43](https://github.com/user-attachments/assets/38385480-9842-410c-968f-70107e864b69)

- Esta visualización ayuda al equipo de soporte a detectar tendencias o problemas frecuentes reportados por los comercios.
--- 
💳 Pagos Confirmados vs Rechazados
Gráfico que compara la cantidad de pagos exitosos y rechazados procesados por la pasarela:

✅ Confirmados (pagos_confirmados_total)

❌ Rechazados (pagos_rechazados_total)

![Screenshot from 2025-06-28 18-51-51](https://github.com/user-attachments/assets/9018610c-4761-414a-9782-1f47f4dd2234)

- Útil para monitorear la tasa de éxito en las transacciones y detectar posibles fallos con medios de pago. 
---
🧾 Reportes de Ventas y Transferencias Bancarias
Dos paneles independientes que visualizan:

📄 Total de reportes de ventas generados por los comercios.

🏦 Transferencias bancarias notificadas y acreditadas (notificaciones_banco_total).

![Screenshot from 2025-06-28 18-52-42](https://github.com/user-attachments/assets/f8a63585-053b-4d93-9c11-ac4a0457914a)

- Permite medir la actividad comercial y el flujo financiero hacia los comercios.

---

# 📬 Iteración 4: Mensajería Asíncrona

Esta iteración incorpora procesamiento asincrónico de reclamos mediante **Jakarta Messaging (JMS)**, con una arquitectura desacoplada basada en una **cola point-to-point** para escalar el sistema y mejorar su resiliencia.

---

## 🔁 Arquitectura

Se implementa una cola JMS que permite que el módulo **Comercio** envíe mensajes de reclamo a un consumidor JMS. Este proceso es asíncrono y desacoplado.

- El productor envía reclamos a la cola `java:/jms/queue/reclamos`.
- El consumidor clasifica los reclamos y los persiste en la base de datos.
- Se actualizan métricas que se visualizan en **Grafana**.

---

## 📥 1. Productor de mensajes (Message Producer)

Cuando el comercio ejecuta `realizarReclamo(String texto)`, se realiza lo siguiente:

- Se crea un mensaje JMS de tipo `TextMessage` con el contenido del reclamo.
- Se envía a la cola configurada.
- Se desacopla el procesamiento del reclamo del flujo principal del comercio.

Clase relacionada:
- `EnviarMensajeReclamoUtil`

---

## 📤 2. Consumidor de mensajes (Message Consumer)

Se implementa un **Message-Driven Bean (MDB)** que:

- Escucha la cola `java:/jms/queue/reclamos`.
- Clasifica el texto como **positivo**, **neutro** o **negativo** (lógica de ejemplo).
- Persiste el reclamo en la base de datos con su clasificación.
- Publica métricas en el sistema de monitoreo.

Clase relacionada:
- `ReclamoConsumer`

---

## 💾 3. Persistencia

El reclamo se almacena en base de datos con los siguientes campos:

- `id`
- `texto`
- `clasificacion` (POSITIVO, NEUTRO, NEGATIVO)

Entidad:
- `Reclamo.java`

---

## 📊 4. Monitoreo en Grafana

Cada vez que se procesa un reclamo:

- Se incrementan contadores de métricas por tipo (negativo, neutro, positivo).
- Las métricas son registradas con **Micrometer** e InfluxDB.
- Grafana muestra la cantidad de reclamos clasificados por tipo (color verde, amarillo y rojo).

Clases relacionadas:
- `ServicioMonitoreoImpl`
- `EventoReclamoClasificado`

---

## ⚙️ 5. Configuración de cola JMS

La cola JMS reclamos fue creada utilizando el CLI de administración.
Esto permite configurar la cola de forma programática desde consola de WildFly
