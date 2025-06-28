# 💳 Pasarela de Pagos — Proyecto Java EE

Una **Pasarela de Pagos** es un sistema tecnológico que actúa como **intermediario seguro** entre clientes, comercios y entidades financieras, autorizando y gestionando pagos electrónicos de forma eficiente.

Este proyecto implementa un **prototipo funcional** desarrollado con **Jakarta EE**, diseñado para simular el flujo real de operaciones que realizan plataformas como Getnet.

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

| Función                         |  Descripción breve                                                                 |
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
| 🤖 Soporte        | Software  | Clasifica automáticamente los reclamos recibidos.                 |
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

**Capas**:
- `dominio`: Entidades y lógica de negocio principal.
- `aplicacion`: Casos de uso (servicios).
- `infraestructura`: Repositorios, base de datos, adaptadores.
- `interfaz`: API REST.

---

## 🧩 Módulos

### 🛒 `moduloCompra` — Gestión de Compras
Gestiona las operaciones de compra y pago de los clientes.

Responsabilidades:
- 🧾 Registrar compras  
- 💳 Procesar pagos  
- 📊 Emitir reportes por comercio  
- 📢 Notificar resultados a monitoreo

---

### 🏪 `moduloComercio` — Administración de Comercios
Administra los datos de los comercios y sus terminales POS.

Responsabilidades:
- 📝 Alta de comercio  
- 🖥️ Alta/baja de POS  
- 🔐 Cambio de contraseña  
- ✅ Validación de credenciales

---

### 🔁 `moduloTransferencia` — Gestión de Transferencias
Registra transferencias a cuentas bancarias de los comercios.

Funciones:
- 📬 Recibir notificaciones desde el medio de pago  
- 🏦 Registrar depósitos bancarios  
- 📆 Consultar depósitos por comercio y fecha

---

### 📈 `moduloMonitoreo` — Observabilidad del Sistema
Monitorea eventos clave como pagos, transferencias y reclamos.

Notificaciones manejadas:
- 📥 Pago registrado  
- ✅ Pago exitoso  
- ❌ Pago rechazado  
- 🔁 Transferencias  

---

## 🔄 Relaciones entre módulos

| 🧩 Módulo origen       | 🔁 Relación       | 🎯 Módulo destino / tecnología         | 💬 Descripción breve                                  |
|------------------------|------------------|----------------------------------------|--------------------------------------------------------|
| `moduloComercio`       | JMS              | `ReclamoConsumer`                      | Envía reclamos a través de mensajería JMS.             |
| `ReclamoConsumer`      | evento           | `moduloMonitoreo`                      | Emite eventos de reclamos clasificados.                |
| `moduloCompra`         | REST             | `AutorizadorPagoHttp`                  | Consulta a medio de pago simulado.                     |
| `moduloCompra`         | evento           | `moduloMonitoreo`                      | Reporta resultado de compra.                           |
| `moduloTransferencia`  | SOAP             | `ClienteBancoSOAP`                     | Notifica transferencia a banco cliente.               |
| `moduloTransferencia`  | evento           | `moduloMonitoreo`                      | Informa sobre depósitos completados.                   |
| `moduloMonitoreo`      | escucha evento   | Todos                                  | Recoge eventos de sistema.                             |

---

## 🔐 Autenticación

- Comercios se autentican con RUT y contraseña.
- Contraseñas protegidas con SHA-256.
- Endpoints como `/resumenDiario` requieren autenticación.

---

## 📊 Casos de Uso Principales

### 1. Procesar Compra
- El cliente realiza una compra.
- Se valida POS y comercio.
- Se registra la compra.
- Se notifica resultado.

### 2. Consultar Ventas
- Comercio se autentica.
- Visualiza ventas por fecha/estado.

### 3. Alta de POS
- Comercio solicita un nuevo POS.
- Se asigna y persiste.

---

## 📈 Diagramas de Secuencia

### DSS 1: Registrar Comercio
![Image](https://github.com/user-attachments/assets/03130e1d-c824-490a-a022-579d8b558804)

### DSS 2: Procesar Compra
![Image](https://github.com/user-attachments/assets/4d90f847-c88f-4281-beb6-a0763fa37c59)

### DSS 3: Transferencia con Depósito
![Image](https://github.com/user-attachments/assets/359d7926-5289-418c-b5c8-4d3280db4b47)

### DSS 4: Autenticación para Reportes
![Image](https://github.com/user-attachments/assets/f2fee370-ef8e-4055-b9b6-c058c38d99a4)

---

## 🚦 Rate Limiting

Sistema `Token Bucket` que:
- Limita operaciones por comercio.
- Rechaza operaciones si se excede el límite.
- Se recarga automáticamente.

### 🔄 Diagrama Secuencia del RateLimiter
![ratelimiter](https://github.com/user-attachments/assets/fe19b107-11df-4c57-81fb-188cecfc6ec4)

### 🧪 Pruebas con JMeter
 
![Screenshot](https://github.com/user-attachments/assets/1fd32f7d-40f1-4a4c-ae46-234df38c26e4)

---

## 📡 Observabilidad con Grafana + InfluxDB

### 🎯 Métricas Registradas
- `pagos_confirmados_total`
- `pagos_rechazados_total`
- `reclamos_total`
- `notificaciones_banco_total`
- `reportes_ventas_total`

### 📊 Visualización de Reclamos Clasificados  

🟩 Positivos | 🟨 Neutros | 🟥 Negativos  

![reclamos](https://github.com/user-attachments/assets/38385480-9842-410c-968f-70107e864b69)

---

### 💳 Pagos Confirmados vs Rechazados  

✅ Confirmados | ❌ Rechazados  

![pagos](https://github.com/user-attachments/assets/9018610c-4761-414a-9782-1f47f4dd2234)

---

### 🧾 Reportes y Transferencias  

📄 Reportes generados | 🏦 Transferencias acreditadas  

![ventas-transferencias](https://github.com/user-attachments/assets/f8a63585-053b-4d93-9c11-ac4a0457914a)

---

## 📬 Mensajería Asíncrona con JMS

**Flujo de Reclamos:**
1. Comercio llama `realizarReclamo(...)`
2. Se envía mensaje a la cola `java:/jms/queue/reclamos`
3. `ReclamoConsumer` procesa, clasifica y persiste.
4. Se actualizan métricas en `moduloMonitoreo`.

---

## ⚙️ Tecnologías Utilizadas

- Jakarta EE 10  
- JAX-RS / JPA  
- JMS (Jakarta Messaging)  
- Prometheus + Grafana  
- InfluxDB + Micrometer  
- JUnit 5  
- MariaDB  
- Docker
