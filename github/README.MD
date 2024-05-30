# Taller de GitHub Actions: Configuración de un Pipeline de Integración Continua

Este taller te guiará paso a paso para configurar un pipeline de Integración Continua (CI) utilizando GitHub Actions. Aprenderás a crear un proyecto en GitHub, agregar un archivo de configuración `.github/workflows/ci.yml`, y verificar la ejecución del pipeline.

## Paso 1: Crear un Proyecto en GitHub

1. **Iniciar Sesión en GitHub:**
   - Accede a tu cuenta de GitHub o regístrate si no tienes una.

2. **Crear un Nuevo Repositorio:**
   - En el panel de control, haz clic en el botón "New" (Nuevo) para crear un nuevo repositorio.
   - Asigna un nombre al repositorio, configura la visibilidad (público o privado) y añade un archivo README si lo deseas.
   - Haz clic en "Create repository" (Crear repositorio).

## Paso 2: Agregar el Archivo de Configuración de GitHub Actions

1. **Crear el Archivo de Configuración:**
   - En el repositorio del proyecto, navega a la pestaña "Actions".
   - GitHub te sugerirá algunos flujos de trabajo predefinidos. Para crear uno desde cero, haz clic en "Set up a workflow yourself" (Configurar un flujo de trabajo tú mismo).

2. **Definir el Pipeline en `.github/workflows/ci.yml`:**
   - Agrega el siguiente contenido al archivo para configurar un pipeline básico con etapas de build y test:
     ```yaml
     name: CI Pipeline

     on: [push, pull_request]

     jobs:
       build:
         runs-on: ubuntu-latest

         steps:
         - uses: actions/checkout@v2

         - name: Set up Node.js
           uses: actions/setup-node@v2
           with:
             node-version: '14'

         - name: Install dependencies
           run: npm install

         - name: Build the project
           run: npm run build

       test:
         runs-on: ubuntu-latest
         needs: build

         steps:
         - uses: actions/checkout@v2

         - name: Set up Node.js
           uses: actions/setup-node@v2
           with:
             node-version: '14'

         - name: Install dependencies
           run: npm install

         - name: Run tests
           run: npm test
     ```
   - Este archivo define dos trabajos (`build` y `test`) que se ejecutarán en una máquina virtual con Ubuntu. El trabajo de `build` se ejecuta primero, seguido por el trabajo de `test`.

3. **Guardar el Archivo:**
   - Guarda el archivo en la ruta `.github/workflows/ci.yml` en tu repositorio.

## Paso 3: Commit y Push

1. **Realizar un Commit del Archivo:**
   - Si estás utilizando la interfaz web de GitHub, GitHub automáticamente creará un commit con el archivo `ci.yml`.
   - Si estás trabajando en tu máquina local, guarda el archivo en `.github/workflows/ci.yml` y realiza un commit.

2. **Push al Repositorio (si estás usando Git localmente):**
   - Usa los siguientes comandos para realizar un commit y un push de los cambios:
     ```sh
     git add .github/workflows/ci.yml
     git commit -m "Add GitHub Actions CI configuration"
     git push origin main
     ```

## Paso 4: Verificar la Ejecución del Pipeline

1. **Ver el Pipeline en GitHub:**
   - Navega a la pestaña "Actions" en el menú del repositorio.
   - Verás una lista de flujos de trabajo ejecutados.

2. **Revisar el Estado del Pipeline:**
   - Observa el estado del pipeline que acabas de crear. Deberías ver que los trabajos `build` y `test` se están ejecutando o ya se han completado.
   - Haz clic en el flujo de trabajo para ver los detalles de cada trabajo, incluidos los logs de ejecución.

## Resumen

En este taller, has aprendido a:
- Crear un nuevo proyecto en GitHub.
- Configurar un pipeline de CI básico utilizando un archivo `.github/workflows/ci.yml`.
- Realizar commit y push de cambios al repositorio.
- Verificar la ejecución del pipeline en GitHub.

La configuración de un pipeline de CI ayuda a automatizar la integración y prueba del código, mejorando la eficiencia y la calidad del desarrollo de software.
