# CI para proyectos que no son Java

El taller principal usa Java + Maven, pero el Proyecto 3 se puede construir en otro lenguaje.
La estructura del workflow es la misma; solo cambian el paso que instala el lenguaje y los comandos de build/pruebas.

Copia el bloque que corresponda en `.github/workflows/ci.yml` de tu proyecto.

> Revisa siempre la versión vigente de cada action en su página de GitHub (`actions/checkout`, `actions/setup-node`, etc.).
> Versiones viejas (`@v2`, `@v3`) usan runtimes de Node.js que GitHub ya deprecó.

## Node.js (Jest / Mocha)

```yaml
name: CI
on:
  push:
  pull_request:
    branches: [main]
permissions:
  contents: read
jobs:
  build-y-pruebas:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7
      - uses: actions/setup-node@v7
        with:
          node-version: '24'      # versión LTS vigente
          cache: npm
      - run: npm ci               # instala exactamente lo que dice package-lock.json
      - run: npm run build --if-present
      - run: npm test -- --coverage   # Jest; con Mocha usa nyc o c8 para la cobertura
```

Umbral de cobertura con Jest (`package.json`):

```json
"jest": {
  "coverageThreshold": { "global": { "lines": 80 } }
}
```

## Python (PyTest)

```yaml
name: CI
on:
  push:
  pull_request:
    branches: [main]
permissions:
  contents: read
jobs:
  build-y-pruebas:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7
      - uses: actions/setup-python@v7
        with:
          python-version: '3.13'
          cache: pip
      - run: pip install -r requirements.txt pytest pytest-cov
      - run: pytest --cov=. --cov-fail-under=80   # falla si la cobertura baja del 80%
```

## PHP (PHPUnit)

```yaml
name: CI
on:
  push:
  pull_request:
    branches: [main]
permissions:
  contents: read
jobs:
  build-y-pruebas:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7
      - uses: shivammathur/setup-php@v2
        with:
          php-version: '8.4'
          coverage: xdebug
      - run: composer install --no-interaction --prefer-dist
      - run: vendor/bin/phpunit --coverage-text
```

## Si tu equipo usa un script propio (`run_tests.sh`)

Es válido encapsular el build en un script y llamarlo desde el pipeline (`run: ./run_tests.sh`), pero:

- El script tiene que estar **en el repositorio** (versionado) y con permiso de ejecución:
  `git update-index --chmod=+x run_tests.sh` (en Windows, `chmod` no basta).
- Empieza con `set -e` para que el pipeline falle si cualquier comando falla.
- El runner necesita tener instalado el lenguaje: instálalo con la action `setup-*` correspondiente antes de llamar el script.
