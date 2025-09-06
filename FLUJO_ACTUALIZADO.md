# Flujo Actualizado de la Aplicación Biométrica

## Cambios Implementados

### 🚫 **Países que Omiten Registro de Huellas**
- **Estados Unidos** 🇺🇸
- **Guatemala** 🇬🇹

## Flujo de la Aplicación Actualizado

### 1. **Pantalla Principal (MainActivity)**
- Botón "Iniciar Registro"
- Botón "Ver Registros"

### 2. **Registro de Datos Personales (RegistrationActivity)**
- Campo de nombre
- Selección de país
- Selección de ciudad
- **NUEVO:** Mensaje informativo que aparece cuando se selecciona Estados Unidos o Guatemala:
  > ℹ️ Para Estados Unidos y Guatemala se omitirá el registro de huellas dactilares

### 3. **Decisión de Flujo**
```
¿País seleccionado es Estados Unidos o Guatemala?
├── SÍ → Saltar a Registro de Iris (IrisActivity)
└── NO → Continuar a Registro de Huellas (FingerprintActivity)
```

### 4A. **Registro de Huellas (FingerprintActivity)** - Solo para otros países
- 5 botones para cada dedo
- Casillas "faltante" para cada dedo
- Validación: al menos un dedo debe ser registrado
- Al completar → Ir a Registro de Iris

### 4B. **Registro de Iris (IrisActivity)** - Para todos los países
- Captura de fotografía del iris
- Generación de hash SHA-256
- Almacenamiento en base de datos

### 5. **Almacenamiento en Base de Datos**
- **Para países normales:** Hashes de huellas + hash de iris
- **Para Estados Unidos/Guatemala:** Todos los hashes de huellas marcados como "faltante" + hash de iris

### 6. **Visualización de Registros (RecordsActivity)**
- Lista de todos los usuarios
- Detalle de cada registro
- Los registros de Estados Unidos/Guatemala mostrarán "FALTANTE" para todas las huellas

## Implementación Técnica

### Cambios en RegistrationActivity.java
```java
private void proceedToFingerprintRegistration() {
    // Verificar si el país omite registro de huellas
    if ("Estados Unidos".equals(selectedCountry) || "Guatemala".equals(selectedCountry)) {
        // Saltar directamente a IrisActivity
        Intent intent = new Intent(this, IrisActivity.class);
        // Crear hashes vacíos para huellas
        ArrayList<String> emptyFingerprintHashes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            emptyFingerprintHashes.add("faltante");
        }
        intent.putStringArrayListExtra("fingerprintHashes", emptyFingerprintHashes);
        startActivity(intent);
    } else {
        // Flujo normal - ir a FingerprintActivity
        Intent intent = new Intent(this, FingerprintActivity.class);
        startActivity(intent);
    }
}
```

### Cambios en CountryCityUtils.java
- Agregado Guatemala con sus ciudades principales
- Mantenido Estados Unidos existente

### Cambios en activity_registration.xml
- Agregado TextView informativo que se muestra/oculta dinámicamente
- Mensaje: "ℹ️ Para Estados Unidos y Guatemala se omitirá el registro de huellas dactilares"

## Beneficios de la Implementación

1. **Flexibilidad:** Fácil agregar más países que omitan huellas
2. **Transparencia:** El usuario sabe qué esperar antes de continuar
3. **Consistencia:** La base de datos mantiene la misma estructura
4. **Experiencia de Usuario:** Flujo más rápido para países específicos

## Casos de Uso

### Usuario de México
1. Selecciona "México" → No aparece mensaje informativo
2. Completa datos personales
3. Registra huellas dactilares (5 dedos)
4. Captura iris
5. Datos guardados con hashes reales de huellas

### Usuario de Estados Unidos
1. Selecciona "Estados Unidos" → Aparece mensaje informativo
2. Completa datos personales
3. **SALTA** registro de huellas
4. Captura iris directamente
5. Datos guardados con hashes "faltante" para huellas

### Usuario de Guatemala
1. Selecciona "Guatemala" → Aparece mensaje informativo
2. Completa datos personales
3. **SALTA** registro de huellas
4. Captura iris directamente
5. Datos guardados con hashes "faltante" para huellas
