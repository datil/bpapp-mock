# bpapp-mock

_bpapp-mock_ es un simulador del [API de servicios financieros](https://bitbucket.org/datilmedia/bpapp-api) de Banco del Pacífico.

Consiste en un servidor HTTP basado en [Pedestal](https://github.com/pedestal/pedestal) que simula un ambiente de producción para facilitar el desarrollo local de nuevas aplicaciones.

## Cómo usarlo

1. Inicia la aplicación en modo desarrollo: `lein run-dev`

2. Visita [localhost:8080](http://localhost:8080/).

3. Los recursos disponibles están incluidos como un mapa _edn_ en [/resources/responses.edn](https://github.com/datil/bpapp-mock/blob/master/resources/responses.edn).

4. Para más información, revisa la [Referencia del API](https://bitbucket.org/datilmedia/bpapp-api/wiki/Referencia%20del%20API) para conocer cómo desarrollar aplicaciones.

## Cómo extender el _mock_

1. Para agregar un _endpoint_, el primer paso es escribir un _test_ que falle. Las pruebas están incluidas en [/tests/bpapp_mock](https://github.com/datil/bpapp-mock/tree/master/test/bpapp_mock).

2. Para pasar el _test_, se debe incluir la ruta respectiva en [/src/bpapp_mock/service.clj](https://github.com/datil/bpapp-mock/blob/master/src/bpapp_mock/service.clj) y su respuesta en formato _edn_ en [/resources/responses.edn](https://github.com/datil/bpapp-mock/blob/master/resources/responses.edn).

3. Para ejecutar los _tests_: `lein test`

## Enlaces
* [bpapp-api](https://bitbucket.org/datilmedia/bpapp-api)
* [content-api](https://bitbucket.org/datilmedia/content-api)
* [bpapp-ios](https://bitbucket.org/datilmedia/bpapp-ios)
* [bpapp-android](https://bitbucket.org/datilmedia/bpapp-ios)

## Nota de seguridad

Los datos incluídos en este programa son ficticios, únicamente para realizar pruebas en un ambiente de desarrollo. Nunca incluyas información bancaria personal o de terceros en este programa.
