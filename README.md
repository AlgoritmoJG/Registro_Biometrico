# Aplicación de Registro Biométrico Android

## Descripción
Aplicación Android desarrollada en Java que permite el registro de datos biométricos de usuarios mediante escaneo de huellas dactilares (5 dedos) y fotografía del iris, almacenando los datos de forma segura en SQLite.

## Características Principales

### 1. Registro de Huellas Dactilares
- Escaneo de 5 dedos usando BiometricPrompt
- Interfaz con 5 botones individuales para cada dedo:
  - Pulgar
  - Índice
  - Medio
  - Anular
  - Meñique
- Cada botón tiene una casilla "faltante" debajo
- Validación: Al menos un dedo debe ser escaneado

### 2. Selección de País y Ciudad
- Lista desplegable con países
- Lista desplegable con ciudades del país seleccionado
- Ambas selecciones son obligatorias

### 3. Registro de Iris
- Fotografía del ojo usando la cámara del dispositivo
- Generación automática de hash SHA-256 de la imagen

### 4. Visualización de Registros
- Lista de todos los usuarios registrados
- Detalle de cada registro con información completa
- Visualización de hashes de huellas e iris

### 5. Base de Datos SQLite
- Almacenamiento seguro de solo hashes, no datos biométricos crudos
- Tabla de usuarios con campos completos
- Operaciones CRUD básicas

## Requisitos Técnicos
- Android API 24+ (Android 7.0)
- Java 11
- Permisos de cámara y biometría
- Sensor de huellas dactilares (opcional)

## Permisos Requeridos
```xml
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

## Estructura del Proyecto

### Actividades
- `MainActivity`: Pantalla principal con opciones de registro y visualización
- `RegistrationActivity`: Formulario de datos personales y selección país/ciudad
- `FingerprintActivity`: Registro de huellas dactilares con botones y casillas faltantes
- `IrisActivity`: Fotografía del iris
- `RecordsActivity`: Visualización de registros almacenados
- `RecordDetailActivity`: Detalle de un registro específico

### Clases de Datos
- `User`: Modelo de datos del usuario
- `DatabaseContract`: Contrato de la base de datos
- `DatabaseHelper`: Helper para operaciones de base de datos

### Utilidades
- `HashUtils`: Generación de hashes SHA-256
- `BiometricUtils`: Manejo de autenticación biométrica
- `CountryCityUtils`: Manejo de listas de países y ciudades
- `DataAdapter`: Adaptador para mostrar listas de registros

## Flujo de la Aplicación

1. **Pantalla Principal**: Usuario selecciona "Iniciar Registro" o "Ver Registros"
2. **Registro de Datos**: Formulario con nombre, país y ciudad
3. **Registro de Huellas**: Escaneo de 5 dedos con opción de marcar como faltante
4. **Registro de Iris**: Fotografía del ojo con cámara
5. **Confirmación**: Almacenamiento en base de datos
6. **Visualización**: Lista de registros y detalles individuales

## Seguridad
- Solo se almacenan hashes SHA-256, nunca datos biométricos originales
- Validaciones de entrada en todos los campos
- Manejo seguro de errores
- Protección contra accesos no autorizados

## Dependencias
- AndroidX Biometric
- CameraX para captura de imágenes
- Material Design Components
- SQLite para base de datos local

## Instalación
1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar dependencias
4. Compilar y ejecutar en dispositivo o emulador

## Notas Importantes
- La aplicación requiere un dispositivo con sensor de huellas dactilares para funcionalidad completa
- Los permisos de cámara y biometría deben ser concedidos por el usuario
- Los datos se almacenan localmente en el dispositivo
